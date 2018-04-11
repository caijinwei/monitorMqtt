package com.wecon.common.web;

import com.wecon.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * getHeader 参数不区分大小写
 */
public final class HttpHeaderUtils
{
    private HttpHeaderUtils() {

    }

    /**
     * 如果http头中包含 X-Felink-RequestID 获取作为请求标识
     * 否则使用UUID生成请求唯一标识
     *
     * @param request http请求
     * @return 返回请求唯一标识符
     */
    public static String getRequestId(final HttpServletRequest request) {
        String requestId = null;
        if (request != null) {
            requestId = request.getHeader("X-Felink-RequestID");
        }
        if (requestId == null) {
            requestId = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        }
        return requestId;
    }

    /**
     * 获取用户设备的User-Agent
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return 返回真实用户IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        // haproxy 会在x-forwared-for最后注入真实ip,通常情况可以取x-forwarded-for最后一个ip
        // if(!StringUtils.isEmptyOrNull(ip)) {
        //   String[] ips = ip.split(",");
        //    if (ips.length > 0) {
        //        ip = ips[ips.length - 1];
        //    }
        //}
        if (StringUtil.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtil.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtil.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtil.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        // 前端接入的是haproxy 使用该方法会取到内网的IP
        if (StringUtil.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
