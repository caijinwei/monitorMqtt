package com.wecon.box.test;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.core.Client;
import com.wecon.restful.test.TestBase;

/**
 * @author lanpenghui 2017年8月17日上午11:47:06
 */
public class DeviceActionTest extends TestBase {

	/**
	 * 通过机器id获取实时数据分组
	 */
	@Test
	public void getBoxGroup() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/baseInfoAction/getBoxGroup");
		String ret = test(request, true);
		JSONObject jsonObject = JSON.parseObject(ret);
		Assert.assertEquals(jsonObject.get("code").toString(), "200");
		System.out.println(test(request, true));

	}
	 @Before
	    public void init() {
	        // 设置客户端
	        Client client = new Client();
	        client.userId = 1000002;
	        client.sid = UUID.randomUUID().toString();
	        client.devid = "25dc170b77781111"; //UUID.randomUUID().toString();
	        client.fuid = "359776057360000";
	        client.version = "1.0.0";
	        client.projectSource = 1;
	        TestBase.setClient(client);
	    }
}
