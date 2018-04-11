package com.wecon.common.enums;

/**
 * Created by linkaixun on 2016/3/8.
 */
public enum StatTypeOption implements EnumVal
{
    Download("下载统计",1),
    Browse("浏览统计",2),
    DownloadSuccess("下载成功",4),
    SetupSuccess("安装成功",5),
    SetupFail("安装失败",6),
    DownloadFail("下载失败",8),
    Share("分享",9),
    Click("点击",10);

    private int value;
    private String key;

    StatTypeOption(String _key, int _value)
    {
        value = _value;
        key = _key;
    }

    @Override
    public int getValue()
    {
        return value;
    }

    @Override
    public String getKey()
    {
        return key;
    }
}
