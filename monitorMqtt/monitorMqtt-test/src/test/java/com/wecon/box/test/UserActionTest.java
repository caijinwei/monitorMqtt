package com.wecon.box.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.restful.core.Client;
import com.wecon.restful.test.TestBase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

/**
 * 用户相关接口测试
 * Created by zengzhipeng on 2017/8/11.
 */
public class UserActionTest extends TestBase {
    /**
     * 测试接口
     */
    @Test
    public void test() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/testact/gd");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
        System.out.println(ret);
    }

    /**
     * 修改密码接口
     * 1.旧密码错误
     * 2.修改成功
     */
    @Test
    public void changePwd() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/chgpwd");
        request.param("oldpwd", DigestUtils.md5Hex("2"));
        request.param("newpwd", DigestUtils.md5Hex("1"));
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11008");

        request = MockMvcRequestBuilders.post("/user/chgpwd");
        request.param("oldpwd", DigestUtils.md5Hex("1"));
        request.param("newpwd", DigestUtils.md5Hex("1"));
        ret = test(request, true);
        System.out.println(ret);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
    }

    /**
     * 修改邮件
     * 1.邮件没变化
     * 2.发送激活邮件
     */
    @Test
    public void changeEmail() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/chgemail");
        request.param("email", "zengzp@we-con.com.cn");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11009");

        /*request = MockMvcRequestBuilders.post("/user/chgemail");
        request.param("email", "86779961@qq.com");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");*/
    }

    /**
     * 检查当前登录用户有效
     */
    @Test
    public void checkSid() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/check");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
        JSONObject resultObject = JSON.parseObject(jsonObject.get("result").toString());
        Assert.assertEquals(resultObject.get("auth").toString(), "1");
        System.out.println(ret);
    }

    /**
     * 登录接口
     * 1.登录成功
     * 2.密码错误
     * 3.用户不存在
     */
    @Test
    public void signin() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/signin");
        request.param("alias", "zzp");
        request.param("password", DigestUtils.md5Hex("1"));
        request.param("isremeber", "0");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
        JSONObject resultObject = JSON.parseObject(jsonObject.get("result").toString());
        Assert.assertNotNull(resultObject.get("sid"));
        System.out.println(ret);

        request = MockMvcRequestBuilders.post("/user/signin");
        request.param("alias", "zzp");
        request.param("password", DigestUtils.md5Hex("100"));
        request.param("isremeber", "0");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11003");

        request = MockMvcRequestBuilders.post("/user/signin");
        request.param("alias", "zzpabcdefg110");
        request.param("password", DigestUtils.md5Hex("100"));
        request.param("isremeber", "0");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11002");
    }

    /**
     * 登出
     */
    @Test
    public void signout() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/signout");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
    }

    /**
     * 邮箱地址注册
     * 1.邮箱格式有误
     * 2.用户已存在
     * 3.新增帐号--不测
     */
    @Test
    public void signupByEmail() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/signupemail");
        request.param("username", "zzp");
        request.param("password", DigestUtils.md5Hex("1"));
        request.param("email", "123@123");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11001");

        request = MockMvcRequestBuilders.post("/user/signupemail");
        request.param("username", "zzp");
        request.param("password", DigestUtils.md5Hex("1"));
        request.param("email", "123@qq.com");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11000");
    }

    /**
     * 激活邮件
     * 需要redis数据支持，不测
     */
    @Test
    public void emailActive() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/signupemailactive");
        request.param("type", "3");
        request.param("uid", "1000007");
        request.param("token", "123123");
        System.out.println(test(request, true));
    }

    /**
     * 获取登录用户基本信息--从session中取信息
     * 需要redis数据支持，不测
     */
    @Test
    public void getUserInfo() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/userinfo");
        System.out.println(test(request, true));
    }

    /**
     * 获取登录用户信息--从db中取信息
     */
    @Test
    public void getUserInfoDetail() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/userinfod");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
        System.out.println(test(request, true));
    }

    /**
     * 获取视图帐号分页列表
     */
    @Test
    public void getViewUserList() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/getviewusers");
        request.param("pageIndex", "1");
        request.param("pageSize", "20");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
        System.out.println(test(request, true));
    }

    /**
     * 新增视图帐号
     * 1.用户存在
     */
    @Test
    public void addViewUser() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/addviewuser");
        request.param("username", "dd");
        request.param("password", DigestUtils.md5Hex("1"));
        request.param("state", "1");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11000");
    }

    /**
     * 修改视图用户状态
     */
    @Test
    public void chgViewUserState() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/chgviewuserstate");
        request.param("user_id", "1000010");
        request.param("state", "1");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");
    }

    /**
     * 发送验证码
     * 1.手机号码验证
     * 2.发送验证码-暂不测
     */
    @Test
    public void sendVercode() {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/sendvercode");
        request.param("phonenum", "123456789101");
        String ret = test(request, true);
        JSONObject jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "11017");

        /*request.param("phonenum", "13655099598");
        ret = test(request, true);
        jsonObject = JSON.parseObject(ret);
        Assert.assertEquals(jsonObject.get("code").toString(), "200");*/
    }

    /**
     * 手机注册帐号
     * 1.新增帐号-不测
     */
    @Test
    public void signupPhone() {
        /*MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/signupphone");
        request.param("username", "13655099598");
        request.param("phonenum", "13655099598");
        request.param("password", DigestUtils.md5Hex("1"));
        request.param("vercode","123456");*/
    }

    /**
     * 更新手机号，需要发短信，不测
     */
    @Test
    public void changePhonenum() {
        /*MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/chgphonenum");
        request.param("phonenum", "13655099598");
        request.param("vercode","123456");*/
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
