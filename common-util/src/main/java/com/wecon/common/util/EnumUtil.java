package com.wecon.common.util;

import com.wecon.common.enums.EnumVal;

import java.util.ArrayList;
import java.util.List;

/**
 * 枚举处理类
 * Created by fengbing on 2016/1/8.
 */
public class EnumUtil
{
    /**
     * 根据value转化为对应类型的枚举对象
     *
     * @param classEnumVal 枚举对象的class类型
     * @param value 所要转化的值
     * @param <T> 枚举泛型类型
     * @return 返回匹配到的枚举对象，否则返回null
     */
    public static <T extends EnumVal> T toEnum(Class<T> classEnumVal, int value)
    {
        EnumVal[] enums = classEnumVal.getEnumConstants();

        for (EnumVal e : enums)
        {
            if (value == e.getValue())
            {
                return (T) e;
            }
        }
        return null;
    }

    /**
     * 根据value转化为对应类型的枚举对象
     *
     * @param classEnumVal 枚举对象的class类型
     * @param arrValue 所要转化的值数组
     * @param <T> 枚举泛型类型
     * @return 返回匹配到的枚举对象，否则返回元素为0个的list
     */
    public static <T extends EnumVal> List<T> toEnum(Class<T> classEnumVal, int[] arrValue)
    {
        List<T> rets = new ArrayList<>();

        if(arrValue != null && arrValue.length>0)
        {
            EnumVal[] enums = classEnumVal.getEnumConstants();
            for (int value : arrValue)
            {
                for (EnumVal e : enums)
                {
                    if (value == e.getValue())
                    {
                        rets.add((T) e);
                        break;
                    }
                }
            }
        }
        return rets;
    }
}
