package com.wecon.box.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.core.Client;
import com.wecon.restful.test.TestBase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

/**
 * Created by caijinw on 2017/8/16.
 */
public class ViewAccountRoleActionTest extends TestBase {
    /**
     * 获取分组列表
     * 1.分组类型不存在
     * 2.获取列表
     */
    @Test
    public void getDeviceNameById() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/Viewpoint/getDeviceName");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "30001");

        request = MockMvcRequestBuilders.post("/Viewpoint/getDeviceName");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
    }

    /*
    * 获取报警监控点监控点
    * */
    @Test
    public  void getAlarmList()
    {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/Viewpoint/showAlarm");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);

        request = MockMvcRequestBuilders.post("/Viewpoint/getDeviceName");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
    }

    @Before
    public void init() {
        // 设置客户端
        Client client = new Client();
        client.userId = 1000017;
        client.sid = UUID.randomUUID().toString();
        client.devid = "25dc170b77781111"; //UUID.randomUUID().toString();
        client.fuid = "359776057360000";
        client.version = "1.0.0";
        client.projectSource = 1;
        TestBase.setClient(client);
    }
}

