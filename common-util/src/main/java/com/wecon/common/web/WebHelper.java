package com.wecon.common.web;


import com.wecon.common.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.*;

/**
 * 网页接口请求类
 */
public final class WebHelper
{
    public static String postBody(String urlPath, String body, List<NameValuePair> headers, String proxy, String charset) throws Exception
    {
        return postBody(urlPath, body, headers, proxy, charset, 0);
    }

    public static String postBody(String urlPath, String body, List<NameValuePair> headers, String proxy, String charset, int millisecondTimeOut) throws Exception
    {
        String html = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = buildHostRequestConfig(proxy, 80, millisecondTimeOut);
        try
        {
            HttpPost httppost = new HttpPost(urlPath);
            HttpEntity postentity = EntityBuilder.create().setText(body).build();
            httppost.setEntity(postentity);


            HttpRequestBase requestBase = httppost;
            requestBase.addHeader("CacheControl", "no-cache");
            if (charset.equalsIgnoreCase("utf-8"))
            {
                requestBase.addHeader("Accept-Charset", charset);
            }
            if (headers != null && headers.size() > 0)
            {
                for (NameValuePair nvp : headers)
                {
                    requestBase.addHeader(nvp.getName(), nvp.getValue());
                }
            }
            if (requestConfig != null)
            {
                requestBase.setConfig(requestConfig);
            }

            CloseableHttpResponse response = httpclient.execute(requestBase);
            try
            {
                HttpEntity entity = response.getEntity();
                if (entity != null)
                {
                    html = EntityUtils.toString(entity);
                }

            }
            finally
            {
                response.close();
            }

        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        return html;
    }

    /**
     * 请求url，并输出网页内容，默认为使用get与utf-8进行请求
     *
     * @param url
     * @return 网页返回内容
     */
    public static String getPage(String url) throws Exception
    {
        return getPage(url, null, "get", "", "utf-8");
    }

    /**
     * 请求并返回网页内容
     *
     * @param url        所要请求的url
     * @param params     提交的参数
     * @param httpMethod get或post提交，默认为get
     * @param proxy      是否设置host
     * @param charset    使用的字符集，默认为utf-8
     * @return
     */
    public static String getPage(String url, List<NameValuePair> params, String httpMethod, String proxy, String charset)
            throws Exception
    {
        return getPage(url, params, httpMethod, proxy, 80, charset, null);
    }

    public static String getPage(String url, List<NameValuePair> params, String httpMethod, String proxy, int proxyPort, String charset)
            throws Exception
    {
        return getPage(url, params, httpMethod, proxy, proxyPort, charset, null);
    }

    /**
     * 请求并返回网页内容
     *
     * @param url        所要请求的url
     * @param params     提交的参数
     * @param httpMethod get或post提交，默认为get
     * @param proxy      是否设置host
     * @param charset    使用的字符集，默认为utf-8
     * @return
     */
    public static String getPage(String url, List<NameValuePair> params, String httpMethod, String proxy, int proxyPort, String charset, List<NameValuePair> headers)
            throws Exception
    {
        return getPage(url, params, httpMethod, proxy, proxyPort, charset, headers, 0);
    }

    public static String getPage(String url, List<NameValuePair> params, String httpMethod, String proxy, int proxyPort, String charset, List<NameValuePair> headers, int millisecondTimeOut)
            throws Exception
    {
        boolean isget = isGet(httpMethod);
        String html = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = buildHostRequestConfig(proxy, proxyPort, millisecondTimeOut);
        try
        {
            HttpRequestBase requestBase = createRequest(isget, url, params);
            requestBase.addHeader("CacheControl", "no-cache");
            if (!StringUtil.isNullOrEmpty(charset)) //charset.equalsIgnoreCase("utf-8"))
            {
                requestBase.addHeader("Accept-Charset", charset);
            }
            if (headers != null && headers.size() > 0)
            {
                for (NameValuePair nvp : headers)
                {
                    requestBase.addHeader(nvp.getName(), nvp.getValue());
                }
            }
            if (requestConfig != null)
            {
                requestBase.setConfig(requestConfig);
            }
            CloseableHttpResponse response = httpclient.execute(requestBase);
            try
            {
                HttpEntity entity = response.getEntity();
                if (entity != null)
                {
                    if (StringUtil.isNullOrEmpty(charset))
                    {
                        html = EntityUtils.toString(entity);
                    }
                    else
                    {
                        html = EntityUtils.toString(entity, charset);
                    }

                }

            }
            finally
            {
                response.close();
            }

        }
        catch (Exception ex)
        {
            throw ex;
        }
        finally
        {
            try
            {
                httpclient.close();
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        return html;
    }

    /**
     * 判断请求是否设置host参数
     *
     * @param proxy              代理IP
     * @param proxyPort          代理ip端口
     * @param millisecondTimeOut 超时时间，单位：毫秒，默认值为3000
     * @return
     */
    private static RequestConfig buildHostRequestConfig(String proxy, int proxyPort, int millisecondTimeOut)
    {
        //RequestConfig requestConfig = null;
        RequestConfig.Builder builder = RequestConfig.custom();
        if (millisecondTimeOut <= 0)
        {
            millisecondTimeOut = 3000;
        }
        builder.setSocketTimeout(millisecondTimeOut).setConnectTimeout(millisecondTimeOut);
        if (proxy != null && !proxy.isEmpty())
        {
            HttpHost httpproxy = new HttpHost(proxy, proxyPort);
            builder.setProxy(httpproxy);
//            requestConfig = RequestConfig.custom().setProxy(httpproxy)
//                    .setSocketTimeout(3000).setConnectTimeout(3000).build();
        }
        return builder.build();
    }

    /**
     * 创建一个http请求对象
     *
     * @param url
     * @return
     */
    private static HttpRequestBase createRequest(boolean isGet, String url, List<NameValuePair> params) throws Exception
    {
        HttpRequestBase requestBase = null;
        if (isGet)
        {
            if (params != null)
            {
                url = combinGetUrl(url, params);
            }
            HttpGet httpget = new HttpGet(url);
            requestBase = httpget;
        }
        else
        {
            HttpPost httppost = new HttpPost(url);
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, "utf-8");
            httppost.setEntity(uefEntity);
            requestBase = httppost;
        }
        return requestBase;
    }

    //private static void initRequest(HttpRequestBase request, String charset, Map<String, String> headers, boolean isGet, )

    /**
     * 判断并返回本次请求方式
     *
     * @param httpMethod
     * @return
     */
    private static boolean isGet(String httpMethod)
    {
        return !httpMethod.equalsIgnoreCase("POST");
    }

    /**
     * 组合生成完整的get请求url
     *
     * @param url
     * @param params
     * @return
     */
    private static String combinGetUrl(String url, List<NameValuePair> params)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++)
        {
            if (i > 0)
            {
                sb.append("&");
            }
            sb.append(params.get(i).getName() + "=" + params.get(i).getValue());
        }

        if (url.indexOf("?") < 0)
        {
            return url + "?" + sb.toString();
        }
        else
        {
            return url + "&" + sb.toString();
        }
    }

    /**
     * 计算请求的签名代理，应用于内部站点间的请求校验
     *
     * @param params  参数列表
     * @param signKey 签名key
     * @return
     */
    public static String getSign(List<NameValuePair> params, String signKey)
    {
        if (params == null || params.size() == 0)
        {
            return "";
        }

        Map<String, String[]> map = new HashMap<>();
        for (NameValuePair nvp : params)
        {
            map.put(nvp.getName(), new String[]{nvp.getValue()});
        }
        return getSignValue(map, signKey);
    }

    private static String getSignValue(Map<String, String[]> map, String primaryKey)
    {
        Set<String> keyset = new TreeSet<>();
        keyset.addAll(map.keySet());

        StringBuilder buf = new StringBuilder();
        boolean pass = false;
        String[] vals;
        for (String key : keyset)
        {
            vals = map.get(key);
            pass = false;

            if (vals == null)
            {
                continue;
            }
            for (String it : vals)
            {
                if (it == null || it.isEmpty())
                {
                    pass = true;
                    break;
                }
            }
            if (pass)
            {
                continue;
            }

            buf.append(key).append("=");
            for (String val : map.get(key))
            {
                buf.append(val).append(",");
            }
            buf.deleteCharAt(buf.length() - 1);
            buf.append("&");
        }
        buf.append("key=").append(primaryKey);
        String sign = DigestUtils.md5Hex(buf.toString());
        return sign;
    }
}

