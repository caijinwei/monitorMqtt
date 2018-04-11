package com.wecon.common.enums;

/**
 * 定义此枚举值时要跟艳强那边申请
 */
public enum ResourceTypeOption implements EnumVal
{
    None("非现有资源",0),
    Soft("软件",1),
    Picture("壁纸",4),
    Banner("ad",23),
    Article("文章",60),
    PicSearchKeyword("壁纸搜索关键字",72),
    Gif("gif", 82),
    NewsArticle("newsArticle", 83),
    Gallery("gallery", 84),
    Video("video", 85);


    private int value;
    private String key;

    ResourceTypeOption(String _key, int _value)
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
