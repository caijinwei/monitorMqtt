package com.wecon.common.util;

/**
 * 客户端分辨率的拆解计算方法
 * Created by fengbing on 2016/9/22.
 */
public class ResolutionUtil
{
    /**
     * 获取分辨率中的宽（格式：width*height）
     *
     * @param resolution （格式：width*height）
     * @return 默认返回0
     */
    public static int getWidth(String resolution)
    {
        if (StringUtil.isNullOrEmpty(resolution))
        {
            return 0;
        }
        String[] arr = resolution.split("\\*");
        if (arr.length != 2)
        {
            return 0;
        }
        return StringUtil.toInteger(arr[0], 0);
    }

    /**
     * 获取分辨率中的高（格式：width*height）
     *
     * @param resolution （格式：width*height）
     * @return 默认返回0
     */
    public static int getHeight(String resolution)
    {
        if (StringUtil.isNullOrEmpty(resolution))
        {
            return 0;
        }
        String[] arr = resolution.split("\\*");
        if (arr.length != 2)
        {
            return 0;
        }
        return StringUtil.toInteger(arr[1], 0);
    }
}
