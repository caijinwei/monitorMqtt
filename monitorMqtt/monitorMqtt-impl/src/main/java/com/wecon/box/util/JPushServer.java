package com.wecon.box.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whp on 2017/9/18.
 */
public class JPushServer {
    private String masterSecret = "c4cc5cbbebcb20541c983cc6";
    private String appKey = "952963faa8710180979ac5c5";
    private String pushUrl = "https://api.jpush.cn/v3/push";
    private boolean apns_production = true;
    private int time_to_live = 86400;
    private static final Logger logger = LogManager.getLogger(JPushServer.class);

    /**
     * 推送操作
     */
    public void push(String alias, String alert){
        try{
            String result = push(pushUrl, alias, alert, appKey, masterSecret, apns_production, time_to_live);
            System.out.print(result);
            JSONObject resData = JSONObject.parseObject(result);
            if(resData.containsKey("error")){
                logger.info("针对别名为" + alias + "的信息推送失败！");
                JSONObject error = JSONObject.parseObject(resData.get("error").toString());
                logger.info("错误信息为：" + error.get("message").toString());
            }else{
                logger.info("针对别名为" + alias + "的信息推送成功！");
            }
        }catch(Exception e){
            logger.error("针对别名为" + alias + "的信息推送失败！",e);
        }
    }

    /**
     * 组装极光推送专用json串
     * @param alias
     * @param alert
     * @return json
     */
    public static JSONObject generateJson(String alias, String alert, boolean apns_production, int time_to_live) throws Exception{
        JSONObject json = new JSONObject();
        JSONArray platform = new JSONArray();//平台
        platform.add("android");
        platform.add("ios");

        JSONObject audience = new JSONObject();//推送目标
        JSONArray alias1 = new JSONArray();
        alias1.add(alias);
        audience.put("alias", alias1);

        JSONObject notification = new JSONObject();//通知内容
        JSONObject android = new JSONObject();//android通知内容
        android.put("alert", "有新的报警数据");
        android.put("builder_id", 1);
        JSONObject android_extras = new JSONObject();//android额外参数
        android_extras.put("type", "alias");
        android.put("extras", android_extras);

        JSONObject ios = new JSONObject();//ios通知内容
        ios.put("alert", "有新的报警数据");
        ios.put("sound", "default");
        ios.put("badge", "+1");
        JSONObject ios_extras = new JSONObject();//ios额外参数
        ios_extras.put("type", "alias");
        ios.put("extras", ios_extras);
        notification.put("android", android);
        notification.put("ios", ios);

        JSONObject options = new JSONObject();//设置参数
        options.put("time_to_live", Integer.valueOf(time_to_live));
        options.put("apns_production", apns_production);

        JSONObject message = new JSONObject();
        message.put("title", alert);
        message.put("msg_content", alert);

        json.put("platform", platform);
        json.put("audience", audience);
        json.put("notification", notification);
        json.put("message", message);
        json.put("options", options);
        return json;

    }

    /**
     * 推送方法-调用极光API
     * @param reqUrl
     * @param alias
     * @param alert
     * @return result
     */
    public static String push(String reqUrl, String alias, String alert, String appKey, String masterSecret, boolean apns_production, int time_to_live) throws Exception{
        String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
        String authorization = "Basic " + base64_auth_string;
        return sendPostRequest(reqUrl, generateJson(alias,alert,apns_production,time_to_live).toString(),"UTF-8",authorization);
    }

    /**
     * 发送Post请求（json格式）
     * @param reqURL
     * @param data
     * @param encodeCharset
     * @param authorization
     * @return result
     */
    @SuppressWarnings({ "resource" })
    public static String sendPostRequest(String reqURL, String data, String encodeCharset,String authorization){
        HttpPost httpPost = new HttpPost(reqURL);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String result = "";
        try {
            StringEntity entity = new StringEntity(data, encodeCharset);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader("Authorization",authorization.trim());
            response = client.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(), encodeCharset);
        } catch (Exception e) {
            //log.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
        }finally{
            client.getConnectionManager().shutdown();
        }
        return result;
    }
    /**
     * BASE64加密工具
     */
    public static String encryptBASE64(String str) {
        byte[] key = str.getBytes();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String strs = base64Encoder.encodeBuffer(key);
        return strs;
    }

    public static void main(String[] arg){
        List<Map> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Map data = new HashMap();
            data.put("boxId", -100);
            data.put("monitorId", i);
            data.put("monitorName", "test"+i);
            data.put("monitorTime", Timestamp.valueOf("2015-02-15 20:25:30"));
            data.put("state", 1);
            data.put("number", "5"+i);
            list.add(data);
        }
        new JPushServer().push("lph", JSON.toJSONString(list));
    }
}
