package com.wecon.restful.core;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.HashMap;
import java.util.Map;

public class Config
{
	/**
	 * 请求签名密钥
	 */
	private static String signKey;
	/**
	 * 自定义请求签名密钥映射表中，Key名称在请求参数中的字段名
	 */
	private static String customSignKeyNameFrom;
	/**
	 * 自定义请求签名密钥映射表
	 */
	private static Map<String, String> customSignKeyMap = new HashMap<>();

	private Config()
	{
	}


	public static String getSignKey()
	{
		return signKey;
	}

	public void setSignKey(String signKey)
	{
		Config.signKey = signKey;
	}

	public static String getCustomSignKeyNameFrom()
	{
		return customSignKeyNameFrom;
	}

	public static void setCustomSignKeyNameFrom(String customSignKeyNameFrom)
	{
		Config.customSignKeyNameFrom = customSignKeyNameFrom;
	}

	public static Map<String, String> getCustomSignKeyMap()
	{
		return customSignKeyMap;
	}

	public static void setCustomSignKeyMap(Map<String, String> customSignKeyMap)
	{
		Config.customSignKeyMap = customSignKeyMap;
	}

	/**
	 * 根据自定义签名key名称，获取本次对应的签名串值
	 * @param customSignKeyName
	 * @return
     */
	public static String getSignKeyV1(String customSignKeyName)
	{
		if(customSignKeyName == null || customSignKeyName.isEmpty())
		{
			return getSignKey();
		}
		String customSignKeyVal = customSignKeyMap.getOrDefault(customSignKeyName, null);
		if(customSignKeyVal == null || customSignKeyVal.isEmpty())
		{
			return getSignKey();
		}
		else
		{
			return DigestUtils.md5Hex(customSignKeyVal);
		}
	}
}
