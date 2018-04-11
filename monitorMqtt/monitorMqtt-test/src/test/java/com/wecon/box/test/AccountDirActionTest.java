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
 * Created by zengzhipeng on 2017/8/15.
 */
public class AccountDirActionTest extends TestBase {
    /**
     * 获取分组列表
     * 1.分组类型不存在
     * 2.获取列表
     */
    @Test
    public void getAccountDirList() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/userdiract/getuserdirs");
        request.param("type", "-1");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11011");

        request = MockMvcRequestBuilders.post("/userdiract/getuserdirs");
        request.param("type", "0");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
    }

    /**
     * 保存分组
     * 1.新增分组。同一用户同一类型，分组不能重名
     */
    @Test
    public void saveAccountDir() {
        /*MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/userdiract/saveuserdir");
        request.param("id", "0");
        request.param("name", "盒子分组3");
        request.param("type", "0");
        request.param("device_id", "0");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11010");*/
    }

    /**
     * 删除分组
     */
    @Test
    public void delAccountDir() {

    }


    @Before
    public void init() {
        // 设置客户端
        Client client = new Client();
        client.userId = 1000007;
        client.sid = UUID.randomUUID().toString();
        client.devid = "25dc170b77781111"; //UUID.randomUUID().toString();
        client.fuid = "359776057360000";
        client.version = "1.0.0";
        client.projectSource = 1;
        TestBase.setClient(client);
    }
}
