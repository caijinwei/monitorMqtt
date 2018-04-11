package com.wecon.restful.core;

import com.alibaba.fastjson.JSONObject;

/**
 * 返回参数
 * @author sean
 */
public class Output
{
	protected String reqId;
	protected int code = 200;
	protected String msg = "ok";
	protected Object result = new Object();

	public Output()
	{
		if (AppContext.getSession() != null)
		{
			this.reqId = String.valueOf(AppContext.getSession().request.getAttribute("reqid"));
		}
		else
		{
			this.reqId = String.valueOf(System.currentTimeMillis());
		}
	}

	public Output(JSONObject data)
	{
		this();
		this.result = data;
	}

	public String getReqId()
	{
		return reqId;
	}

	public int getCode()
	{
		return code;
	}

	public String getMsg()
	{
		return msg;
	}

	public Object getResult()
	{
		return result;
	}

}
