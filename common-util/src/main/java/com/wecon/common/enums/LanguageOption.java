/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 * @author zengzhipeng
 */
public enum LanguageOption
{

    //英语
    English("en-US", 1),
    //印地语(印度)
    Hindi("hi-IN", 2),
    //印尼语
    Indonesian("id-ID", 3),
    //马来西亚语
    Malaysian("ms-MY", 4),
    //泰语
    Thai("th-TH", 5),
    //中文
    Chinese("zh-CN", 6),
    //越南语
    Vietnamese("vi-VN", 7),
    //繁体中文
    TraditionalChinese("zh-TW", 8);
//    French("法语",9),
//    Russian("俄语", 10),
//    Spanish("西班牙语", 11),
//    Portuguese("葡萄牙语", 12),    
//    Arabic("阿语", 3),

    public int value;
    public String key;
    public String desc;

    LanguageOption(String _key, int _value)
    {
        this.key = _key;
        this.value = _value;
    }

    public static LanguageOption valueOf(int value)
    {
        switch (value)
        {
            case 1:
                return English;
            case 2:
                return Hindi;
            case 3:
                return Indonesian;
            case 4:
                return Malaysian;
            case 5:
                return Thai;
            case 6:
                return Chinese;
            case 7:
                return Vietnamese;
            case 8:
                return TraditionalChinese;
            default:
                return English;
        }
    }
    
    public static String getDesc(int value)
    {
        switch (value)
        {
            case 1:
                return "英语";
            case 2:
                return "印地语(印度)";
            case 3:
                return "印尼语";
            case 4:
                return "马来西亚语";
            case 5:
                return "泰语";
            case 6:
                return "中文";
            case 7:
                return "越南语";
            case 8:
                return "繁体中文";
            default:
                return "英语";
        }
    }

    public static LanguageOption keyOf(String key)
    {
        if (key == null)
        {
            return English;
        }
        switch (key)
        {
            case "en-US":
                return English;
            case "hi-IN":
                return Hindi;
            case "id-ID":
                return Indonesian;
            case "ms-MY":
                return Malaysian;
            case "th-TH":
                return Thai;
            case "zh-CN":
                return Chinese;
            case "vi-VN":
                return Vietnamese;
            case "zh-TW":
                return TraditionalChinese;
            default:
                return English;
        }
    }
}
