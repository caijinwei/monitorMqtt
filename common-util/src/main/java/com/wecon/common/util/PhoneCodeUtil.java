package com.wecon.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码校验的工具类
 * Created by fengbing on 2015/12/1.
 */
public class PhoneCodeUtil
{
    //印度的手机号码格式
    private final static Pattern inMobilePhone = Pattern.compile("[9,8,7][0-9]{9}");
    //中国的手机号码格式
    private final static Pattern cnMobilePhone = Pattern.compile("1[3,4,5,7,8][0-9]{9}");
    //马来西亚
    private final static Pattern myMobilePhone = Pattern.compile("1(?:1[1-5]\\d{2}|[02-4679][2-9]\\d|59\\d{2}|8(?:1[23]|[2-9]\\d))\\d{5}");
    //印尼
    private final static Pattern idMobilePhone = Pattern.compile("8\\d{9,10}");
    //菲律宾
    private final static Pattern phMobilePhone = Pattern.compile("[8,9]\\d{9}");
    //新加坡
    private final static Pattern sgMobilePhone = Pattern.compile("[8,9]\\d{7}");
    //泰国
    private final static Pattern thMobilePhone = Pattern.compile("[1,6,8,9]\\d{8}");
    //越南
    private final static Pattern vnMobilePhone = Pattern.compile("9\\d{8}|1\\d{9}");

    /**
     * 各国国家手机号码格式校验，目前支持中国，印度两个国家，其余国家默认返回true
     *
     * @param dialCountryCode 国家区号，不需要带+号
     * @param mobilePhoneCode 手机号码
     * @return 如果合法返回true，格式不对返回false
     */
    public static boolean ValidPhoneCode(String dialCountryCode, String mobilePhoneCode)
    {
        String cc = dialCountryCode;
        if (cc.startsWith("+"))
        {
            cc = cc.replace("+", "");
        }
        boolean result = true;
        Matcher matcher = null;
        switch (cc)
        {
            case "91":
            {

                matcher = inMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "86":
            {
                matcher = cnMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "60": //MY，马来西亚+60
            {
                matcher = myMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "62": //ID，印尼
            {
                matcher = idMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "63": //PH，菲律宾+63
            {
                matcher = phMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "65": //SG，新加坡+65
            {
                matcher = sgMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "66": //TH，泰国+66
            {
                matcher = thMobilePhone.matcher(mobilePhoneCode);
            }
            break;
            case "84": //VN，越南+84
            {
                matcher = vnMobilePhone.matcher(mobilePhoneCode);
            }
            break;
        }
        //印尼+62、马来西亚+60、泰国+66、越南+84、新加坡+65、菲律宾+63
        if (matcher != null)
        {
            result = matcher.matches();
        }
        return result;
    }
}
