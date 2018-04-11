package com.wecon.restful.doc;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.wecon.restful.core.Output;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.core.AbstractAction;
import com.wecon.restful.core.WebApiInstance;

/**
 * 在线接口文档
 * @author sean
 */
public class DocManager
{
	// key = module, value = api list
	private static Map<String, Map<String, ApiDoc>> doc = new TreeMap<>();
	// 所有模块
	private static List<Module> modules = new LinkedList<>();

	/**
	 * 生成接口文档
	 * @param api
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void genApiDoc(WebApiInstance api) throws InstantiationException, IllegalAccessException
	{
		AbstractAction action = (AbstractAction) api.controller.newInstance();
		String moduleName = action.getModuleName();
		String moduleLabel = action.getModuleLabel();

		Map<String, ApiDoc> moduleApis = doc.get(moduleName);
		if (moduleApis == null)
		{
			moduleApis = new TreeMap<>();
			doc.put(moduleName, moduleApis);

			Module module = new Module();
			module.name = moduleName;
			module.label = moduleLabel;
			modules.add(module);
		}

		// 开始解析api
		ApiDoc doc = new ApiDoc();
		doc.name = api.method.getAnnotation(RequestMapping.class).value()[0];
		doc.label = doc.name;
		if (api.controller.getAnnotation(Label.class) != null)
		{
			doc.label = api.controller.getAnnotation(Label.class).value();
		}
		if (api.method.getAnnotation(WebApi.class) != null)
		{
			doc.auth = api.method.getAnnotation(WebApi.class).forceAuth();
		}
		if (api.method.getAnnotation(Deprecated.class) != null)
		{
			doc.deprecated = true;
		}
		if (api.method.getAnnotation(Remark.class) != null)
		{
			doc.remark = api.method.getAnnotation(Remark.class).value();
		}

		
		JSONObject data = new JSONObject();
		action.getData(data, api.method.getName());
		Output output = new Output(data);
		doc.jsonData = JSON.toJSONString(output, true);

		// 解析参数
		Parameter[] params = api.method.getParameters();
		if (params.length == 1)
		{
			Parameter param = params[0];
			if (param.getAnnotation(Valid.class) != null)
			{
				Class<?> clazz = param.getType();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields)
				{
					Param paramEntity = new Param();
					paramEntity.name = field.getName();
					paramEntity.label = paramEntity.name;
					if (field.getAnnotation(Label.class) != null)
					{
						paramEntity.label = field.getAnnotation(Label.class).value();
					}
					if (field.getAnnotation(NotNull.class) != null)
					{
						paramEntity.must = true;
					}
					if (field.getAnnotation(Length.class) != null)
					{
						Length length = field.getAnnotation(Length.class);
						paramEntity.format.add("字符串长度限制: min=" + length.min() + ", max=" + length.max());
					}
					if (field.getAnnotation(Range.class) != null)
					{
						Range range = field.getAnnotation(Range.class);
						paramEntity.format.add("数值大小限制: min=" + range.min() + ", max=" + range.max());
					}
					doc.params.add(paramEntity);
				}
			}
		}

		moduleApis.put(doc.name, doc);
	}

	protected static ApiDoc getApi(String module, String api)
	{
		Map<String, ApiDoc> apis = doc.get(module);
		if (apis != null)
		{
			return apis.get(api);
		}
		return null;
	}

	protected static List<ApiDoc> getModuleApis(String module)
	{
		Map<String, ApiDoc> map = doc.get(module);
		if (map != null)
		{
			List<ApiDoc> apis = new LinkedList<ApiDoc>();
			for (String api : map.keySet())
			{
				apis.add(map.get(api));
			}
			// 按照label排序
			Collections.sort(apis, new Comparator<ApiDoc>()
			{
				@Override
				public int compare(ApiDoc o1, ApiDoc o2)
				{
					return o1.label.compareTo(o2.label);
				}
			});
			return apis;
		}
		return null;
	}

	protected static List<Module> getModules()
	{
		return modules;
	}
}
