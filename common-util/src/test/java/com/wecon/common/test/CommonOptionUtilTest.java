package com.wecon.common.test;

import com.wecon.common.enums.ResourceTypeOption;
import com.wecon.common.util.CommonOptionUtil;
import org.junit.Test;

/**
 * Created by fengbing on 2016/4/1.
 */
public class CommonOptionUtilTest
{
    @Test
    public void testAllOption() throws Exception
    {
        System.out.println(CommonOptionUtil.getEnumValOptions(ResourceTypeOption.class));
    }

    @Test
    public void testPartOption()
    {
        System.out.println(CommonOptionUtil.getEnumValOptions(ResourceTypeOption.Article, ResourceTypeOption.Banner, ResourceTypeOption.None));
    }
}
