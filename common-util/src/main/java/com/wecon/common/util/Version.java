package com.wecon.common.util;

import java.util.regex.Pattern;

/**
 * Created by fengbing on 2015/12/7.
 */
public class Version
{
    private Integer _major = 0;
    private Integer _minor = 0;
    private Integer _build = -1;
    private Integer _reversion = -1;

    public Version(int major, int minor, int build, int reversion)
    {
        _major = major;
        _minor = minor;
        _build = build;
        _reversion = reversion;
    }

    /**
     * 版本号比较
     * 比value大时，返回1；
     * 比value小时，返回-1；
     * 相等时，返回0
     *
     * @param value
     * @return
     */
    public int compareTo(Version value)
    {
        if (value == null)
        {
            return 1;
        }

        if (this._major != value._major)
        {
            if (this._major > value._major)
            {
                return 1;
            }
            return -1;
        }
        else
        {
            if (this._minor != value._minor)
            {
                if (this._minor > value._minor)
                {
                    return 1;
                }
                return -1;
            }
            else
            {
                if (this._build != value._build)
                {
                    if (this._build > value._build)
                    {
                        return 1;
                    }
                    return -1;

                }
                else
                {
                    if (this._reversion == value._reversion)
                    {
                        return 0;
                    }
                    if (this._reversion > value._reversion)
                    {
                        return 1;
                    }
                    return -1;
                }
            }
        }
    }

    public static Version Parse(String version)
    {
        if (version == null)
        {
            throw new IllegalArgumentException("version不能为null");
        }

        String[] array = version.split("\\.");
        int num = array.length;
        if (num < 2 || num > 4)
        {
            throw new IllegalArgumentException("version长度错误 值:" + version);
        }

        int major = StringUtil.toInteger(array[0], -1);
        if (major < 0)
        {
            throw new IllegalArgumentException("version的major段值非法 值:" + version);
        }
        int minor = StringUtil.toInteger(array[1], -1);
        if (minor < 0)
        {
            throw new IllegalArgumentException("version的minor段值非法 值:" + version);
        }


        int build = 0;
        int reversion = 0;
        num -= 2;
        if (num > 0)
        {
            build = StringUtil.toInteger(array[2], -1);
            if (build < 0)
            {
                throw new IllegalArgumentException("version的build段值非法 值:" + version);
            }
            num--;
            if (num > 0)
            {
                reversion = StringUtil.toInteger(array[3], -1);
                if (reversion < 0)
                {
                    throw new IllegalArgumentException("version的reversion段值非法 值:" + version);
                }
            }
        }

        return new Version(major, minor, build, reversion);
    }

    /**
     * 版本号格式校验正则表达式{x.y} 或{x.y.z}或{x.y.z.k}，其中x、y、z、k都必须是数字。
     */
    private final static Pattern pattern = Pattern.compile("[0-9]{1,10}(\\.[0-9]{1,10}){1,3}");

    /**
     * 校验版本号是否符合格式{x.y} 或{x.y.z}或{x.y.z.k}，其中x、y、z、k都必须是数字。
     *
     * @param version 待校验的版本号
     * @return 符合格式，返回true，否则，返回false
     */
    public static boolean validFormat(String version)
    {
        if (version == null || "".equals(version))
        {
            return false;
        }
        return pattern.matcher(version).matches();
    }
}