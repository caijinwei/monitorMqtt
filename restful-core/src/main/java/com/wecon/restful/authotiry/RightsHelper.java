package com.wecon.restful.authotiry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.alibaba.fastjson.JSON;

/**
 *
 */
public class RightsHelper
{
	private static final Logger logger = LogManager.getLogger(RightsHelper.class);

	private static String reportUrl = "http://x.com/UserRights/GetRights.ashx";
	private static String cacheName = "userAuth";
	private static String proxyHost;
	private static Integer proxyPort;
	private static Map<Integer, String> sysMap = new HashMap<>();

	private static final String authResults = "authResults";
	private static final String authUrls = "authUrls";
	private static final String key = "EE11CBB19052E40B07AAC0CA060C23EE";

	/**
	 * 用户权限校验
	 * @param account
	 * @param right
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static boolean isRight(String account, String right) throws IOException
	{
		if (account == null)
		{
			return false;
		}
		String cacheKey = authUrls + account + key;
		if (CacheUtil.isExists(cacheName, cacheKey))
		{
			return getAuthUrls(account).contains(right);
		}
		else
		{
			cacheUserAuthority(account);
			return getAuthUrls(account).contains(right);
		}
	}

	/**
	 * 获取权限缓存
	 * @param account
	 * @return
	 */
	public static List<RightResults> getAuthResults(String account)
	{
		List<RightResults> results = new ArrayList<>();
		try
		{
			String cacheKey = authResults + account + key;
			if (!CacheUtil.isExists(cacheName, cacheKey))
			{
				cacheUserAuthority(account);
			}
			results = (List<RightResults>) CacheUtil.get(cacheName, cacheKey);
			return results;
		}
		catch (Exception x)
		{
			return results;
		}
	}

	/**
	 * 缓存用户权限集
	 * @param account
	 * @throws IOException 
	 */
	private static void cacheUserAuthority(String account) throws IOException
	{
		// 缓存authresults
		ArrayList<RightResults> results = getReportJsonResult(account);
		CacheUtil.put(cacheName, authResults + account + key, results);

		// 缓存authUrl
		HashSet<String> urls = new HashSet<>();
		for (RightResults result : results)
		{
			for (RightResults.ResponseRight right : result.getRights())
			{
				if (right.getUrl() != null && !right.getUrl().isEmpty())
				{
					urls.add(right.getUrl());
				}
			}
		}
		CacheUtil.put(cacheName, authUrls + account + key, urls);
	}

	/**
	 * 获取用户权限集
	 * @param account
	 * @return
	 */
	private static Set<String> getAuthUrls(String account)
	{
		Set<String> urls = (Set<String>) CacheUtil.get(cacheName, authUrls + account + key);
		if (urls == null)
		{
			return new HashSet<>();
		}
		return urls;
	}

	private static ArrayList<RightResults> getReportJsonResult(String account) throws IOException
	{
		ArrayList<RightResults> arrList = new ArrayList<>();

		if (sysMap.size() <= 0)
		{
			return arrList;
		}
		HttpRequestUtil requester = new HttpRequestUtil();
		if (proxyHost != null && proxyPort != null)
		{
			requester.setProxyHost(proxyHost);
			requester.setProxyPort(proxyPort);
		}
		for (Map.Entry<Integer, String> entry : sysMap.entrySet())
		{
			String sign = DigestUtils.md5Hex(entry.getKey() + entry.getValue() + account);
			String url = reportUrl + "?" + "sign=" + sign + "&sysId=" + entry.getKey() + "&account=" + account;
			String getResult = requester.doGet(url);
			logger.debug("read authority result: " + getResult);

			RightResults result = JSON.parseObject(getResult, RightResults.class);
			if (result.getState() == 0 && result.getRights().size() > 0)
			{
				Collections.sort(result.getRights(), new levelComparator());
				arrList.add(result);
			}
		}
		return arrList;
	}

	public void setReportUrl(String reportUrl)
	{
		RightsHelper.reportUrl = reportUrl;
	}

	public void setCacheName(String cacheName)
	{
		RightsHelper.cacheName = cacheName;
	}

	public void setProxyHost(String proxyHost)
	{
		RightsHelper.proxyHost = proxyHost;
	}

	public void setProxyPort(Integer proxyPort)
	{
		RightsHelper.proxyPort = proxyPort;
	}

	public void setSysMap(Map<Integer, String> sysMap)
	{
		RightsHelper.sysMap = sysMap;
	}

	private static class levelComparator implements Comparator<RightResults.ResponseRight>
	{
		public int compare(RightResults.ResponseRight object1, RightResults.ResponseRight object2)
		{
			return new Double(object1.getLevel()).compareTo(new Double(object2.getLevel()));
		}
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println(RightsHelper.isRight("15980546806", "getsite"));
	}
}
