package com.wecon.common.web;

/**
 * IP地址的帮助类
 */
public class IpAddrHelper
{
    /**
     * ip地址转化为long类型的值
     *
     * @param strIp 待转化的ip地址
     * @return 返回对应的long类型值
     */
    public static long convertIpToLong(String strIp)
    {
        if (strIp == null || strIp.isEmpty())
        {
            return 0;
        }

        String[] arr = strIp.split("\\.");
        if (arr.length != 4)
        {
            return 0;
        }

        long[] ip = new long[4];
        //将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(arr[0]);
        ip[1] = Long.parseLong(arr[1]);
        ip[2] = Long.parseLong(arr[2]);
        ip[3] = Long.parseLong(arr[3]);
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    //将十进制整数形式转换成127.0.0.1形式的ip地址
    public static String convertLongToIP(long longIp)
    {
        StringBuffer sb = new StringBuffer("");
        //直接右移24位
        sb.append(String.valueOf(longIp >>> 24));
        sb.append(".");
        //将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        //将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        //将高24位置0
        sb.append(String.valueOf(longIp & 0x000000FF));
        return sb.toString();
    }
}
