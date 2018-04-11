package com.wecon.common.enums;

/**
 * 项目类型枚举
 * Created by fengbing on 2015/12/3.
 */
public enum ProjectOption implements EnumVal
{
    UNKNOW("未知", 0),
    MOVETOANDROID("MoveToAndroid", 1),
    ANDROIDGENIUS("AndroidGenius", 2),
    GENIUS("genius", 100016),
    WALLPAPER("wallpaper", 20000020),
    DR_NEWS("DrNews", 20000038),
    DR_NEWS_WEB("DrNewsWeb", 38225);

    public String key;
    public int value;

    ProjectOption(String _key, int _value)
    {
        this.key = _key;
        this.value = _value;
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

    public static ProjectOption valueOf(int value)
    {
        switch (value)
        {
            case 1:
                return MOVETOANDROID;
            case 2:
                return ANDROIDGENIUS;
            case 100016:
                return GENIUS;
            case 20000020:
                return WALLPAPER;
            default:
                return UNKNOW;
        }
    }
}
