package com.wecon.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wecon.common.enums.EnumVal;

import java.util.List;
import java.util.Map;

/**
 * 生成下拉选择控件的json数据的处理类
 * Created by zengzhipeng
 */
public class CommonOptionUtil
{
    /**
     * 传入实现EnumVal接口的枚举类，输出包含全部枚举值生成的下拉框选择json数据
     *
     * @param classEnumVal 实现EnumVal接口的枚举类
     * @param <T>          EnumVal接口
     * @return 返回jsonArray列表，子对象为jsonObject（包含text与value两个元素：text为枚举的key，value为枚举的value值）
     */
    public static  <T extends EnumVal> JSONArray getEnumValOptions(Class<T> classEnumVal)
    {
        JSONArray options = new JSONArray();
        EnumVal[] enums = classEnumVal.getEnumConstants();
        for (EnumVal e : enums)
        {
            options.add(createOption(e.getValue(), e.getKey()));
        }
        return options;
    }

    /**
     * 传入实现EnumVal接口的枚举类，输出传入的枚举数组生成的下拉框选择json数据
     * 用于生产的下拉框不需要有全部枚举值的情况
     *
     * @param v1  实现EnumVal接口的枚举类
     * @param <T> EnumVal接口
     * @return 返回jsonArray列表，子对象为jsonObject（包含text与value两个元素：text为枚举的key，value为枚举的value值）
     */
    public static <T extends EnumVal> JSONArray getEnumValOptions(EnumVal... v1)
    {
        JSONArray options = new JSONArray();

        for (int i = 0; i < v1.length; i++)
        {
            options.add(createOption(v1[i].getValue(), v1[i].getKey()));
        }
        return options;
    }

    /**
     * 根据传入的对象，生成下拉框的json数据
     *
     * @param optionsList 源数据对象
     * @return 返回jsonArray列表，子对象为jsonObject（包含text与value两个元素：text为Map.Entry的key值，value为Map.Entry的value值）
     */
    public static JSONArray getCustomOptions(List<Map.Entry<String, Integer>> optionsList)
    {
        JSONArray options = new JSONArray();
        for (Map.Entry<String, Integer> entry : optionsList)
        {
            options.add(createOption(entry.getValue(), entry.getKey()));
        }
        return options;
    }


    /**
     * 生成单个配置项的json object值
     *
     * @param value 值
     * @param text  值描述内容
     * @return 返回jsonobject（含有两个数据：value，key）
     */
    private static JSONObject createOption(int value, String text)
    {
        JSONObject option = new JSONObject();
        option.put("value", value);
        option.put("text", text);
        return option;
    }
}
