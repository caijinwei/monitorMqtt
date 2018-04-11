package com.wecon.restful.core;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类加载器
 *
 * @author Sean
 */
public final class ClassCollector
{
	private String[] pkgPrefixs;

	/**
	 * 包名前缀
	 *
	 * @param pkgPrefix
	 */
	public ClassCollector(String[] pkgPrefixs)
	{
		this.pkgPrefixs = pkgPrefixs;
	}

	/**
	 * 扫描所有类
	 *
	 * @return
	 */
	public Map<String, List<Class<?>>> collect() throws Exception
	{
		List<Class<?>> acts = new ArrayList<Class<?>>(50);

		Map<String, List<Class<?>>> classes = new HashMap<String, List<Class<?>>>();
		classes.put("controller", acts);

		this.list(classes);
		return classes;
	}

	/**
	 * 列出所有注解类
	 *
	 * @param classes
	 */
	private void list(Map<String, List<Class<?>>> classes) throws Exception
	{
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ClassScanner scanner = new ClassScanner();
		List<String> clsStrs = scanner.findClass(loader, pkgPrefixs);
		int length = clsStrs.size();
		Class<?> cls = null;
		for (int i = 0; i < length; i++)
		{
			cls = loader.loadClass(clsStrs.get(i));

			if (cls != null && cls.getAnnotation(RestController.class) != null)
			{
				classes.get("controller").add(cls);
			}
		}
	}
}
