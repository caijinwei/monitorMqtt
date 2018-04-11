package com.wecon.common.test;

import com.wecon.common.util.PhoneCodeUtil;

/**
 * Created by fengbing on 2015/12/24.
 */
public class PhoneCodeUtilTest
{
    public static void main(String[] args)
    {

        testPhoneCode("91", "9652565338");
        testPhoneCode("91", "7330907561");
        testPhoneCode("91", "9951444401");
        testPhoneCode("86", "13705057624");

        testPhoneCode("60","1151245678");
        testPhoneCode("60","102912345");
        testPhoneCode("60","1599912345");
        testPhoneCode("60","1589912345");
        testPhoneCode("60","181212345");
        testPhoneCode("60","181312345");
        testPhoneCode("60","181912345");
        testPhoneCode("60","182912345");

        testPhoneCode("62","8123456789");
        testPhoneCode("62","80123456789");
        testPhoneCode("62","7123456789");
        testPhoneCode("62","70123456789");

        testPhoneCode("63","8123456789");
        testPhoneCode("63","9123456789");
        testPhoneCode("63","90123456789");
        testPhoneCode("63","7123456789");
        testPhoneCode("63","70123456789");

        testPhoneCode("65","81234567");
        testPhoneCode("65","82345678");
        testPhoneCode("65","91234567");
        testPhoneCode("65","92345678");
        testPhoneCode("65","923456789");
        testPhoneCode("65","9234567");
        testPhoneCode("65","72234567");
        testPhoneCode("65","72234568");

        testPhoneCode("66","112345678");
        testPhoneCode("66","123456789");
        testPhoneCode("66","623456789");
        testPhoneCode("66","823456789");
        testPhoneCode("66","923456789");
        testPhoneCode("66","423456789");

        testPhoneCode("84","912345678");
        testPhoneCode("84","9123456789");
        testPhoneCode("84","1123456789");
        testPhoneCode("84","112345678");
    }

    private static void testPhoneCode(String dailCC, String phonenum)
    {
        boolean valid = PhoneCodeUtil.ValidPhoneCode(dailCC, phonenum);

        System.out.printf("%s, %s, %s", dailCC, phonenum, valid).println();
        System.out.println();
    }
}
