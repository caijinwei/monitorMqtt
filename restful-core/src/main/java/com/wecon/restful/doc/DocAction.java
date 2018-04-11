package com.wecon.restful.doc;

import java.util.Collection;
import java.util.List;

import com.wecon.restful.core.Output;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.annotation.WebApi;

@RestController
@RequestMapping(value = "doc", produces = "application/json;charset=utf-8")
@Description("接口文档")
public class DocAction
{
	@Description("查询模块列表")
	@RequestMapping("inquireModuleList")
	@WebApi(forceAuth = false)
	public Output inquireModuleList()
	{
		List<Module> modules = DocManager.getModules();
		JSONObject data = new JSONObject();
		data.put("moduleList", modules);
		return new Output(data);
	}

	@Description("查询模块接口列表")
	@RequestMapping("inquireApiList")
	@WebApi(forceAuth = false)
	public Output inquireApiList(String module)
	{
		Collection<ApiDoc> apis = DocManager.getModuleApis(module);
		JSONObject data = new JSONObject();
		data.put("apiList", apis);
		return new Output(data);
	}

	@Description("查询接口")
	@RequestMapping("inquireApi")
	@WebApi(forceAuth = false)
	public Output inquireApi(String module, String api)
	{
		ApiDoc doc = DocManager.getApi(module, api);
		JSONObject data = new JSONObject();
		data.put("api", doc);
		return new Output(data);
	}
}
