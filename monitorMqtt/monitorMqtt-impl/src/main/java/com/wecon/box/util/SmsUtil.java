package com.wecon.box.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by zengzhipeng on 2017/8/15.
 */
public class SmsUtil {
	//115.28.23.78
	//http://m.5c.com.cn/api/send/
    public static final String APIURL = "http://115.28.23.78/api/send/";
    public static final String APIKEY = "7128e18ccb802f9d8e6a6c017b213292";
    public static final String USERNAME = "weikongdz";
    public static final String PASSWORD = "mlrt1234";

    /**
     * 发送短息
     *
     * @param mobile  手机号码可以多个用逗号分开“,”
     * @param content
     * @return
     */
    public static String sendSMS(String mobile, String content) throws Exception {
        String resutl = "";
        HttpURLConnection connection = null;
        try {
            // 创建StringBuffer对象用来操作字符串
            StringBuffer sb = new StringBuffer(APIURL + "?");
            // APIKEY
            sb.append("apikey=" + APIKEY);
            //用户名
            sb.append("&username=" + USERNAME);
            //密码
            sb.append("&password=" + PASSWORD);
            //手机号码
            sb.append("&mobile=" + mobile);
            //消息内容转URL标准码
            sb.append("&content=" + URLEncoder.encode(content + "【V-BOX】", "GBK"));
            // 创建url对象
            URL url = new URL(sb.toString());
            // 打开url连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置url请求方式 ‘get’ 或者 ‘post’
            connection.setRequestMethod("POST");
            // 发送
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            // 返回发送结果
            resutl = in.readLine();
        } catch (Exception e) {
            throw e;
//            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return resutl;
    }
    public static void main(String args[]){
    	
    	try {
			String a=sendSMS("13655099598","2222");
			System.out.println(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    }
    

}
