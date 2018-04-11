package com.wecon.restful.core;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * web api 实例
 * @author sean
 */
public class WebApiInstance
{
	public String id;
	public Class<?> controller;
	public Method method;

	public boolean forceAuth;
	public boolean master = true;
	public String[] authority;
	public boolean skipSign = false;

	@Override
	public String toString()
	{
		return "WebApiInstance [id=" + id + ", controller=" + controller.getName() + ", method=" + method.getName() + ", forceAuth=" + forceAuth
				+ ", master=" + master + ", authority=" + Arrays.toString(authority) + "]";
	}
}
