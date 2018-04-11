package com.wecon.common.web;

import com.wecon.common.util.StringUtil;
import org.apache.log4j.Logger;

/**
 *
 */
public class IpUtils
{
    /**
     * 日志记录
     */
    private static final Logger logger = Logger.getLogger(IpUtils.class);

    /**
     * 检测ip是否有效
     * 暂时只支持ip4
     * 本地局域网ip
     * 127.*
     * 10.0.0.0~10.255.255.255
     * 172.16.0.0~172.31.255.255
     * 192.168.0.0~192.168.255.255
     * 保留地址：http://www.iana.org/assignments/iana-ipv4-special-registry/iana-ipv4-special-registry.xhtml
     */
    public static boolean isValidIp(String ip) {

        try {
            if (ip != null && !ip.isEmpty()) {
                String[] strArr = ip.split("\\.");
                if (strArr.length == 4) {
                    int a = Integer.parseInt(strArr[0]);
                    int b = Integer.parseInt(strArr[1]);
                    //int c = Integer.parseInt(strArr[2]);
                    //int d = Integer.parseInt(strArr[3]);
                    if (a == 10 || a == 127) {
                        return false;
                    }
                    if (a == 192 && b == 168) {
                        return false;
                    }
                    if (a == 172 && b >= 16 && b <= 31) {
                        return false;
                    }
                    if (a == 169 && b == 254) {
                        return false;
                    }
                    if (a == 100 && b >= 64 && b <= 127) {
                        return false;
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            logger.warn(ip + " is invalid, exp:" + e.getMessage());
        }
        return false;
    }


    public static String formatIp(String ip) {
        // 取到ip需要进行ip分隔，判断是否传上来的ip是否多个，
        // 并且取其中一个外网的ip 10.121.165.27, 106.78.117.241
        if (!StringUtil.isNullOrEmpty(ip)) {
            String[] ipArr = ip.split(",");
            if (ipArr.length >= 2) {
                for (String str : ipArr) {
                    String strTmp = str.trim();
                    if (isValidIp(strTmp)) {
                        return strTmp;
                    }
                }
            }
        }
        return ip;
    }
}
