package com.wecon.monitorMqtt.console.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class ConnectOptions {
	public static MqttConnectOptions options = null;

	public static MqttConnectOptions getConnectOptions(String username, String password) {
		try {
			// MQTT的连接设置
			options = new MqttConnectOptions();
			// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
			options.setCleanSession(false);
			// 设置连接的用户名
			options.setUserName(username);
			// 设置连接的密码
			options.setPassword(password.toCharArray());
			// 设置超时时间 单位为秒
			options.setConnectionTimeout(10);
			// 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
			options.setKeepAliveInterval(20);
			return options;

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

}
