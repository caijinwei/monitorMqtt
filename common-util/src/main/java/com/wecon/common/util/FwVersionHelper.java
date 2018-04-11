package com.wecon.common.util;

import com.wecon.common.enums.FwVersionOption;
import com.wecon.common.enums.MobileOption;

import java.util.regex.Pattern;

/**
 * 固件转化枚举
 * Created by fengbing on 2015/12/3.
 */
public class FwVersionHelper
{
    //验证传入的固件是否为纯数字
    private static final Pattern regexFwIsNumber = Pattern.compile("[0-9]*");
    //对传入的固件版本进行清理
    private static final Pattern regexFmtFwVersion = Pattern.compile("[^0-9\\._]");

    /***
     * 根据手机平台 和 固件版本字符串，转换成相应的固件版本枚举返回
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwVersion 固件版本号
     * @return 转化后的固件枚举
     */
    public static FwVersionOption getFwVersion(MobileOption phoneType, String fwVersion)
    {
        FwVersionOption fw = FwVersionOption.UNKNOWN;
        if (regexFwIsNumber.matcher(fwVersion).matches() && fwVersion.length() >= 5)
        {
            fw = FwVersionOption.valueOf(Integer.valueOf(fwVersion));
            return fw;
        }
        boolean isdef = true;
        String fmtFw = regexFmtFwVersion.matcher(fwVersion).replaceAll("");
        if (!fmtFw.isEmpty())
        {
            isdef = false;
            char firstLetter = fmtFw.charAt(0);
            switch (firstLetter)
            {
                case '1':
                    fw = getFwVersion_1X(phoneType, fmtFw);
                    break;
                case '2':
                    fw = getFwVersion_2X(phoneType, fmtFw);
                    break;
                case '3':
                    fw = getFwVersion_3X(phoneType, fmtFw);
                    break;
                case '4':
                    fw = getFwVersion_4X(phoneType, fmtFw);
                    break;
                case '5':
                    fw = getFwVersion_5X(phoneType, fmtFw);
                    break;
                case '6':
                    fw = getFwVersion_6X(phoneType, fmtFw);
                    break;
                case '7':
                    fw = getFwVersion_7X(phoneType, fmtFw);
                    break;
                default:
                    isdef = true;
                    break;
            }
        }

        if (isdef)
        {
            if (phoneType == MobileOption.MOVE_PHONE)
            {
                fw = FwVersionOption.UNKNOWN;
            }
            else
            {
                fw = FwVersionOption.ANDROID_4_4_0;
            }
        }
        return fw;
    }

    /**
     * 1.x的固件处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_1X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "1.0":
                fw = FwVersionOption.ANDROID_1_0_0;
                break;
            case "1.1":
                fw = FwVersionOption.ANDROID_1_1_0;
                break;
            case "1.5":
                fw = FwVersionOption.ANDROID_1_5_0;
                break;
            case "1.6":
                fw = FwVersionOption.ANDROID_1_6_0;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_1_6_0;
                }
                break;
        }
        return fw;
    }

    /**
     * 2.x 固件的处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_2X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "2.0":
                fw = FwVersionOption.ANDROID_2_0_0;
                break;
            case "2.0.1":
                fw = FwVersionOption.ANDROID_2_0_1;
                break;
            case "2.1":
                fw = FwVersionOption.ANDROID_2_1_0;
                break;
            case "2.2":
                fw = FwVersionOption.ANDROID_2_2_0;
                break;
            case "2.3":
                fw = FwVersionOption.ANDROID_2_3_0;
                break;
            case "2.3.1":
                fw = FwVersionOption.ANDROID_2_3_1;
                break;
            case "2.3.2":
                fw = FwVersionOption.ANDROID_2_3_2;
                break;
            case "2.3.3":
                fw = FwVersionOption.ANDROID_2_3_3;
                break;
            case "2.3.4":
                fw = FwVersionOption.ANDROID_2_3_4;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_2_0_0;
                }
                break;
        }
        return fw;
    }

    /**
     * 3.x固件的处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_3X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "3.0":
                fw = FwVersionOption.ANDROID_3_0_0;
                break;
            case "3.1":
                fw = FwVersionOption.ANDROID_3_1_0;
                break;
            case "3.2":
                fw = FwVersionOption.ANDROID_3_2_0;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_3_0_0;
                }
                break;
        }
        return fw;
    }

    /**
     * 4.x固件的处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_4X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "4.0":
                fw = FwVersionOption.ANDROID_4_0_0;
                break;
            case "4.0.1":
                fw = FwVersionOption.ANDROID_4_0_1;
                break;
            case "4.0.2":
                fw = FwVersionOption.ANDROID_4_0_2;
                break;
            case "4.0.3":
                fw = FwVersionOption.ANDROID_4_0_3;
                break;
            case "4.0.4":
                fw = FwVersionOption.ANDROID_4_0_4;
                break;
            case "4.1":  // Android 4.1(Jelly Bean 果冻豆)更新包于2012年6月28日在Google I/O大会上随搭载Android 4.1的Nexus 7平板电脑一起发布
                fw = FwVersionOption.ANDROID_4_1_0;
                break;
            case "4.1.1":
                fw = FwVersionOption.ANDROID_4_1_1;
                break;
            case "4.2":
                fw = FwVersionOption.ANDROID_4_2_0;
                break;
            case "4.2.2":
                fw = FwVersionOption.ANDROID_4_2_2;
                break;
            case "4.3":
            case "4.3.1":
            case "4.3.2":
            case "4.3.3":
            case "4.3.4":
            case "4.3.5":
                fw = FwVersionOption.ANDROID_4_3_0;
                break;
            case "4.4":
                fw = FwVersionOption.ANDROID_4_4_0;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_4_0_0;
                }
                break;
        }
        return fw;
    }

    /**
     * 5.x固件的处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_5X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "5.0":
            case "5.0.1":
                fw = FwVersionOption.ANDROID_5_0_0;
                break;
            case "5.1":
            case "5.1.1":
                fw = FwVersionOption.ANDROID_5_1_0;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_5_0_0;
                }
                break;
        }
        return fw;
    }

    /**
     * 6.x固件的处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_6X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "6.0":
                fw = FwVersionOption.ANDROID_6_0_0;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_6_0_0;
                }
                break;
        }
        return fw;
    }

    /**
     * 7.x固件的处理
     *
     * @param phoneType 手机平台，此方法现默认android
     * @param fwStr     固件版本号（格式：xx.yy）
     * @return 转化后的固件枚举
     */
    private static FwVersionOption getFwVersion_7X(MobileOption phoneType, String fwStr)
    {
        FwVersionOption fw;
        switch (fwStr)
        {
            case "7.0":
                fw = FwVersionOption.ANDROID_7_0_0;
                break;
            case "7.1":
                fw = FwVersionOption.ANDROID_7_1_0;
                break;
            default:
                if (phoneType == MobileOption.MOVE_PHONE)
                {
                    fw = FwVersionOption.UNKNOWN;
                }
                else
                {
                    fw = FwVersionOption.ANDROID_7_0_0;
                }
                break;
        }
        return fw;
    }

}
