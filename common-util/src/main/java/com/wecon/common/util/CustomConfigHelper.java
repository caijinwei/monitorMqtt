package com.wecon.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义key名称的全局参数配置类
 * 示例：
 * <bean id="customconfighelper" class="CustomConfigHelper">
 * <property name="configs">
 * <map key-type="java.lang.String">
 * <entry key="appname" value="this is genius"></entry>
 * <entry key="appid" value="123456" value-type="int"></entry>
 * <entry key="appid_long" value="123456" value-type="long"></entry>
 * <entry key="b1" value="false" value-type="boolean"></entry>
 * </map>
 * </property>
 * </bean>
 * Created by fengbing on 2015/12/24.
 */
public class CustomConfigHelper
{
    private static Map<String, Object> configs = new HashMap<>();

    /**
     * 获取string类型的配置值
     *
     * @param key 配置key
     * @return 配置值或key不存在时返回空字符串
     */
    public static String getString(String key)
    {
        return getString(key, null);
    }

    /**
     * 获取String类型的配置值
     *
     * @param key          配置key
     * @param defaultValue key不存在时返回的默认值
     * @return 返回配置值
     */
    public static String getString(String key, String defaultValue)
    {
        Object val = configs.get(key);
        if (val == null)
        {
            return defaultValue;
        }
        return (String) val;
    }

    /**
     * 获取boolean类型的配置值
     *
     * @param key 配置key
     * @return 返回配置值或key不存在时返回false
     */
    public static boolean getBoolean(String key)
    {
        return getBoolean(key, false);
    }

    /**
     * 获取String类型的配置值
     *
     * @param key          配置key
     * @param defaultValue key不存在时返回的默认值
     * @return 返回配置值
     */
    public static boolean getBoolean(String key, boolean defaultValue)
    {
        Object val = configs.get(key);
        if (val == null)
        {
            return defaultValue;
        }
        return (boolean) val;
    }

    /**
     * 获取int类型的配置值
     *
     * @param key 配置key
     * @return 返回配置值，或key不存在时，返回0
     */
    public static int getInt32(String key)
    {
        return getInt32(key, 0);
    }

    /**
     * 获取int类型的配置值
     *
     * @param key          配置key
     * @param defaultValue key不存在时返回的默认值
     * @return 返回配置值
     */
    public static int getInt32(String key, int defaultValue)
    {
        Object val = configs.get(key);
        if (val == null)
        {
            return defaultValue;
        }
        return (int) val;
    }

    /**
     * 获取long类型的配置值
     *
     * @param key 配置key
     * @return 返回配置值，或key不存在时，返回0
     */
    public static long getInt64(String key)
    {
        return getInt64(key, 0);
    }

    /**
     * 获取long类型的配置值
     *
     * @param key          配置key
     * @param defaultValue key不存在时返回的默认值
     * @return 返回配置值
     */
    public static long getInt64(String key, long defaultValue)
    {
        Object val = configs.get(key);
        if (val == null)
        {
            return defaultValue;
        }
        return (long) val;
    }

    public void setConfigs(Map<String, Object> configs)
    {
        CustomConfigHelper.configs = configs;
    }

}
