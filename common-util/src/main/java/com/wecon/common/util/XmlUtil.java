package com.wecon.common.util;

import org.dom4j.Attribute;
import org.dom4j.Element;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * xml节点值的读取方法
 * Created by fengbing on 2015/12/16.
 */
public class XmlUtil
{
    /**
     * 读取Xml节点中的值，并转化为日期类型，转化失败返回默认值
     *
     * @param node         xml节点
     * @param defaultValue 默认值
     * @return 转化后的日期
     */
    public static Date getDate(Element node, Date defaultValue)
    {
        if (node != null)
        {
            String value = node.getText();
            if (!StringUtil.isNullOrEmpty(value))
            {
                try
                {
                    if (value.length() > 10)
                    {
                        return TimeUtil.fromString(value, "yyyy-MM-dd HH:mm:ss");
                    }
                    else
                    {
                        return TimeUtil.fromString(value, "yyyy-MM-dd");
                    }
                }
                catch (ParseException pe)
                {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 读取Xml节点中的值，并转化为日期类型（yyyyMMddHHmmss），转化失败返回默认值
     *
     * @param node         xml节点
     * @param defaultValue 默认值
     * @return 转化后的日期
     */
    public static long getDateValue(Element node, long defaultValue)
    {
        if (node != null)
        {
            String value = node.getText();
            if (!StringUtil.isNullOrEmpty(value))
            {
                try
                {
                    if (value.length() > 10)
                    {
                        return TimeUtil.getYYYYMMDDHHMMSSTime(TimeUtil.fromString(value, "yyyy-MM-dd HH:mm:ss"));
                    }
                    else
                    {
                        return TimeUtil.getYYYYMMDDHHMMSSTime(TimeUtil.fromString(value, "yyyy-MM-dd"));
                    }
                }
                catch (ParseException pe)
                {
                    return defaultValue;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 获取节点的值，并转化为boolean类型，节点为null时或值不是数字时，返回默认值
     *
     * @param node         xml节点
     * @param defaultValue 默认值
     * @return 返回节点中的值或默认值
     */
    public static boolean getBoolean(Element node, boolean defaultValue)
    {
        if (node != null)
        {
            return StringUtil.toBoolean(node.getText(), defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取节点的值，并转化为integer类型，节点为null时或值不是数字时，返回默认值
     *
     * @param node         节点
     * @param defaultValue 默认值
     * @return node中配置的值
     */
    public static int getInteger(Element node, int defaultValue)
    {
        if (node != null)
        {
            return StringUtil.toInteger(node.getText(), defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取属性的值，并转化为int类型，属性为null或值不是int类型时，返回默认值
     *
     * @param attribute    属性对象
     * @param defaultValue 默认值
     * @return 属性的值转化后的结果
     */
    public static int getInteger(Attribute attribute, int defaultValue)
    {
        if (attribute != null)
        {
            return StringUtil.toInteger(attribute.getValue(), defaultValue);
        }
        return defaultValue;
    }

    /**
     * 获取节点的值，节点为null时返回默认值
     *
     * @param node         节点
     * @param defaultValue 默认值
     * @return node中配置的值
     */
    public static String getString(Element node, String defaultValue)
    {
        if (node != null)
        {
            return node.getText();
        }
        return defaultValue;
    }

    /**
     * 解析节点，并将值转化为list类型，使用|分割
     *
     * @param node 节点
     * @return list对象
     */
    public static List<String> getArrayList(Element node)
    {
        return getArrayList(node, null);
    }

    /**
     * 解析节点，并将值转化为list类型
     *
     * @param node     节点
     * @param splitstr 分割符
     * @return list对象
     */
    public static List<String> getArrayList(Element node, String splitstr)
    {
        List<String> arrlist = null;
        if (node != null && !StringUtil.isNullOrEmpty(node.getText()))
        {
            String[] arr = StringUtil.isNullOrEmpty(splitstr) ? node.getText().split("\\|") : node.getText().split(splitstr);
            if (arr.length > 0)
            {
                arrlist = new ArrayList<>();
                for (String str : arr)
                {
                    if (str != null && !str.isEmpty())
                    {
                        arrlist.add(str);
                    }
                }
            }
        }
        return arrlist;
    }
}
