package com.wecon.restful.core;

import java.util.HashMap;
import java.util.Map;

/**
 * app上下文
 * @author sean
 */
public final class AppContext
{
	private static final ThreadLocal<Session> sessionPool = new ThreadLocal<>();
	protected static final Map<String, WebApiInstance> webapiMap = new HashMap<>();

	protected static void setSession(Session session)
	{
		sessionPool.set(session);
	}

	protected static WebApiInstance getWebApi(String url)
	{
		return webapiMap.get(url);
	}

	public static Session getSession()
	{
		return sessionPool.get();
	}
}
