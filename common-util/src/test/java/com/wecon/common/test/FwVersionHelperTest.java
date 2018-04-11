package com.wecon.common.test;

import com.wecon.common.enums.FwVersionOption;
import com.wecon.common.enums.MobileOption;
import com.wecon.common.util.FwVersionHelper;

/**
 * Created by fengbing on 2015/12/14.
 */
public class FwVersionHelperTest
{
    public static void main(String[] args)
    {
        //System.out.println(FwVersionHelper.getFwVersion(MobileOption.ANDROID, "android4.0").toString());
        convertFw("android4.0");
        convertFw("40000");
        convertFw("40420");
        convertFw("4.3");
        convertFw("4.3.2");

        convertFw("5.x");
        convertFw("5.0");
        convertFw("5.0.6");
        convertFw("5.1");
        convertFw("5.1.5");
        convertFw("7.0");
        convertFw("7.1");
        convertFw("7.2");
        convertFw("8.0");

    }

    private static void convertFw(String fwStr)
    {
        FwVersionOption fw = FwVersionHelper.getFwVersion(MobileOption.ANDROID, fwStr);

        System.out.printf("fwStr=%s; fwOption=%s; fwValue=%d", fwStr, fw.key, fw.value).println();
    }
}
