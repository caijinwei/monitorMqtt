package com.wecon.box.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.test.TestBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by caijinw on 2017/8/29.
 */
public class DeviceShowActionTest extends TestBase {


    @Test
    public void showAllDeviceDir(){
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/baseInfoAction/showAllDeviceDir");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
        System.out.println(test(request, true));
    }
}
