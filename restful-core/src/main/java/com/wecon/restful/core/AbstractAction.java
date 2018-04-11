package com.wecon.restful.core;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractAction
{
	/**
	 * 模块名
	 * @return
	 */
	public abstract String getModuleName();

	/**
	 * 模块标签， 用于显示
	 * @return
	 */
	public abstract String getModuleLabel();

	/**
	 * 接口返回数据说明
	 * @param data
	 */
	public abstract void getData(JSONObject data, String version);
}
