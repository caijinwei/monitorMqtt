package com.wecon.restful.test;

import com.wecon.restful.core.Client;
import com.wecon.restful.core.RestfulContextListener;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring.xml" })
@ActiveProfiles("dev")
public class TestBase
{
	@Autowired
	protected WebApplicationContext wac;
	protected MockMvc mockMvc;

	public static Client client = new Client();

	@Before
	public void setup()
	{
		wac.getServletContext().setInitParameter("offline.test", "true");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * 设置客户端
	 * @param client
	 */
	public static void setClient(Client client)
	{
		TestBase.client = client;
	}

	/**
	 * 测试
	 * @param request
	 * @return json
	 * @throws Exception
	 */
	protected String test(MockHttpServletRequestBuilder request, boolean format)
	{
		try
		{
			RestfulContextListener.ENV = "dev";
			ResultActions result = mockMvc.perform(request);
			MvcResult mvcResult = result.andReturn();
			MockHttpServletResponse response = mvcResult.getResponse();
			String json = response.getContentAsString();
			JSONObject jo = JSON.parseObject(json);
			return JSON.toJSONString(jo, format);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
