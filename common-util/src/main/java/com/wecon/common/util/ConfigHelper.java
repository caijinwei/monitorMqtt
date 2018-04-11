package com.wecon.common.util;

/**
 * 底层定义的字段，在spring.xml中进行配置
 * 示例：
 * <bean id="confighelper" class="ConfigHelper">
 * <property name="thumbnailImageCryptKey" value="123456789123456789"></property>
 * </bean>
 * Created by fengbing on 2015/12/24.
 */
public class ConfigHelper
{

    //缩略图地址生成的密钥串
    private static String thumbnailImageCryptKey;

    public static String getThumbnailImageCryptKey()
    {
        return thumbnailImageCryptKey;
    }

    public void setThumbnailImageCryptKey(String thumbnailImageCryptKey)
    {
        ConfigHelper.thumbnailImageCryptKey = thumbnailImageCryptKey;
    }

}
