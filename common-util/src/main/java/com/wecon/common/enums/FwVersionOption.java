package com.wecon.common.enums;

/**
 * 固件枚举
 * Created by fengbing on 2015/12/3.
 */
public enum FwVersionOption
{
    UNKNOWN("未知版本", -1),
    ALLPLATFORM("所有平台固件版本", 0),
    ANDROID("ALL ANDROID", 40000),
    ANDROID_1_0_0("ANDROID1.0", 40100),
    ANDROID_1_1_0("ANDROID1.1", 40110),
    ANDROID_1_5_0("ANDROID1.5", 40150),
    ANDROID_1_6_0("ANDROID1.6", 40160),
    //ANDROID_2("ANDROID2.X", 42000),

    ANDROID_2_0_0("ANDROID2.0", 40200),
    ANDROID_2_0_1("ANDROID2.0.1", 40201),
    ANDROID_2_1_0("ANDROID2.1", 40210),
    ANDROID_2_2_0("ANDROID2.2", 40220),

    ANDROID_2_3_0("ANDROID2.3", 40230),
    ANDROID_2_3_1("ANDROID2.3.1", 40231),
    ANDROID_2_3_2("ANDROID2.3.2", 40232),
    ANDROID_2_3_3("ANDROID2.3.3", 40233),
    ANDROID_2_3_4("ANDROID2.3.4", 40234),

    ANDROID_3_0_0("ANDROID3.0", 40300),
    ANDROID_3_1_0("ANDROID3.1", 40310),
    ANDROID_3_2_0("ANDROID3.2", 40320),

    ANDROID_4_0_0("ANDROID4.0", 40400),
    ANDROID_4_0_1("ANDROID4.0.1", 40401),
    ANDROID_4_0_2("ANDROID4.0.2", 40402),
    ANDROID_4_0_3("ANDROID4.0.3", 40403),
    ANDROID_4_0_4("ANDROID4.0.4", 40404),

    ANDROID_4_1_0("ANDROID4.1", 40410),
    ANDROID_4_1_1("ANDROID4.1.1", 40411),

    ANDROID_4_2_0("ANDROID4.2", 40420),
    ANDROID_4_2_2("ANDROID4.2.2", 40422),

    ANDROID_4_3_0("ANDROID4.3", 40430),
    ANDROID_4_4_0("ANDROID4.4", 40440),
    ANDROID_5_0_0("ANDROID5.0", 40500),
    ANDROID_5_1_0("ANDROID5.1", 40510),
    ANDROID_6_0_0("ANDROID6.0", 40600),
    ANDROID_7_0_0("ANDROID7.0", 40700),
    ANDROID_7_1_0("ANDROID7.1", 40710),
    ANDROID_MAX("ANDROIDMAX", 49999);

    public int value;
    public String key;

    FwVersionOption(String _key, int _value)
    {
        this.key = _key;
        this.value = _value;
    }

    public static FwVersionOption valueOf(int value)
    {

        switch (value)
        {
            case -1:
                return UNKNOWN;
            case 0:
                return ALLPLATFORM;
            case 40000:
                return ANDROID;
            case 40100:
                return ANDROID_1_0_0;
            case 40110:
                return ANDROID_1_1_0;
            case 40150:
                return ANDROID_1_5_0;
            case 40160:
                return ANDROID_1_6_0;
            case 40200:
                return ANDROID_2_0_0;
            case 40201:
                return ANDROID_2_0_1;
            case 42200:
                return ANDROID_2_0_0;
            case 40210:
                return ANDROID_2_1_0;
            case 40220:
                return ANDROID_2_2_0;
            case 40230:
                return ANDROID_2_3_0;
            case 40231:
                return ANDROID_2_3_1;
            case 40232:
                return ANDROID_2_3_2;
            case 40233:
                return ANDROID_2_3_3;
            case 40234:
                return ANDROID_2_3_4;
            case 40300:
                return ANDROID_3_0_0;
            case 40310:
                return ANDROID_3_1_0;
            case 40320:
                return ANDROID_3_2_0;
            case 40400:
                return ANDROID_4_0_0;
            case 40401:
                return ANDROID_4_0_1;

            case 40402:
                return ANDROID_4_0_2;
            case 40403:
                return ANDROID_4_0_3;
            case 40404:
                return ANDROID_4_0_4;
            case 40410:
                return ANDROID_4_1_0;
            case 40411:
                return ANDROID_4_1_1;
            case 40420:
                return ANDROID_4_2_0;
            case 40422:
                return ANDROID_4_2_2;
            case 40430:
                return ANDROID_4_3_0;
            case 40440:
                return ANDROID_4_4_0;
            case 40500:
                return ANDROID_5_0_0;
            case 40510:
                return ANDROID_5_1_0;
            case 40600:
                return ANDROID_6_0_0;
            case 40700:
                return ANDROID_7_0_0;
            case 40710:
                return ANDROID_7_1_0;
            case 49999:
                return ANDROID_MAX;
            default:
                return ANDROID;
        }
    }
}
