package com.wecon.common.web;

import com.wecon.common.util.CryptoHelper;
import com.wecon.common.util.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 图片服务器地址生成帮助类
 */
public class ImageUrlHelper
{
    //默认的加密密钥串
    private static final String THUMBO_SECRET_KEY = "hCqq5mb4YUxYU7WTLMTs6qiU-RdvgXKVESWURj";

    /**
     * 计算urlPath的加密结果，并拼接输出一个完整的缩略图的imgUrl地址；
     *
     * @param host    图片服务器的域名，需要带有http://
     * @param urlpath 图片的url路径部分
     * @return 返回host+hmac_sha1+urlpath
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String getThumbnailUrl(String host, String urlpath) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, InvalidKeyException
    {
        return getThumbnailUrl(host, urlpath, THUMBO_SECRET_KEY);
    }

    /**
     * 计算urlPath的加密结果，并拼接输出一个完整的缩略图的imgUrl地址；
     *
     * @param host      图片服务器的域名，需要带有http://
     * @param urlpath   图片的url路径部分
     * @param secretKey 加密密钥，为空时，使用默认值
     * @return 返回host+hmac_sha1+urlpath
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static String getThumbnailUrl(String host, String urlpath, String secretKey) throws UnsupportedEncodingException,
            NoSuchAlgorithmException, InvalidKeyException
    {
        if (urlpath.startsWith("/"))
        {
            urlpath = urlpath.substring(1);
        }
        String hashSha = CryptoHelper.hash_HMAC_Sha1_Base64UrlSafe(urlpath, StringUtil.isNullOrEmpty(secretKey) ? THUMBO_SECRET_KEY : secretKey);
        if (!host.endsWith("/"))
        {
            host += "/";
        }
        return host + hashSha + "/" + urlpath;
    }
}
