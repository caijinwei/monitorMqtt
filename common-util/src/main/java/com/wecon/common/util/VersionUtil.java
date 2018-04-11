package com.wecon.common.util;

/**
 * Created by fengbing on 2015/12/7.
 */
public class VersionUtil
{
    /**
     * 版本号比较
     * version1比version2大时，返回1；
     * version1比version2小时，返回-1；
     * 相等时，返回0
     *
     * @param version1
     * @param version2
     * @return
     * @throws IllegalArgumentException
     */
    public static int compareTo(String version1, String version2) throws IllegalArgumentException
    {
        if (StringUtil.isNullOrEmpty(version1) || StringUtil.isNullOrEmpty(version2))
        {
            throw new IllegalArgumentException("VersionUtil.compreTo，传入的参数不能为null");
        }

        Version ver1 = Version.Parse(version1);
        Version ver2 = Version.Parse(version2);

        return ver1.compareTo(ver2);
    }


}


