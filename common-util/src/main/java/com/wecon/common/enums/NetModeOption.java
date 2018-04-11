package com.wecon.common.enums;

/**
 * 网络连接类型
 * Created by fengbing on 2015/12/3.
 */
public enum NetModeOption
{
    UNKNOW("未知", 0),
    WIFI("WIFI", 10),
    MOBILE_2G("2G网络", 20),
    MOBILE_3G("3G网络", 30),
    MOBILE_4G("4G网络",40),
    ND_2G("2G网络", 50),
    ND_3G("3G网络", 60),
    ND_4G("4G网络",70);

    public int value;
    public String key;

    NetModeOption(String _key, int _value)
    {
        this.key = _key;
        this.value = _value;
    }

    public static NetModeOption valueOf(int value)
    {
        switch (value)
        {
            case 0:
                return UNKNOW;
            case 10:
                return WIFI;
            case 20:
                return MOBILE_2G;
            case 30:
                return MOBILE_3G;
            case 40:
                return MOBILE_4G;
            case 50:
                return ND_2G;
            case 60:
                return ND_3G;
            case 70:
                return ND_4G;
            default:
                return UNKNOW;
        }
    }
}
