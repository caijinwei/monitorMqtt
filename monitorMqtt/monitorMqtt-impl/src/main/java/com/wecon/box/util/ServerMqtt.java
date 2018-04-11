package com.wecon.box.util;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by caijinw on 2017/7/27.
 */
public class ServerMqtt {
	// tcp://MQTT安装的服务器地址:MQTT定义的端口号
	private String HOST;
	// 定义一个主题

	// 定义MQTT的ID，可以在MQTT服务配置中指定
	// public static final String clientid = "server11";

	public MqttClient client;
	public MqttTopic topic11;
	private String userName;
	private String passWord;

	public MqttMessage message;

	/**
	 * 构造函数
	 *
	 * @throws MqttException
	 */
	public ServerMqtt(String clientid) throws MqttException {
		init();
		// MemoryPersistence设置clientid的保存形式，默认为以内存保存
		client = new MqttClient(HOST, clientid, new MemoryPersistence());
		connect();
	}

	/*
	 * 构造函数 默认clientId是server11
	 */
	public ServerMqtt() throws MqttException {
		init();
		// MemoryPersistence设置clientid的保存形式，默认为以内存保存
		/*
		 * client = new MqttClient(HOST, "server11", new MemoryPersistence());
		 * connect();
		 */
	}

	private void init() {
		HOST = BoxWebConfigContext.boxWebConfig.getMqttHost();
		userName = BoxWebConfigContext.boxWebConfig.getMqttUsername();
		passWord = BoxWebConfigContext.boxWebConfig.getMqttPwd();
	}

	/**
	 * 用来连接服务器
	 */
	private void connect() {

		try {
			MqttConnectOptions options = new MqttConnectOptions();
			options.setCleanSession(false);
			options.setUserName(userName);
			options.setPassword(passWord.toCharArray());
			// 设置超时时间
			options.setConnectionTimeout(10000);
			// 设置会话心跳时间
			options.setKeepAliveInterval(20);
			client.setCallback(new PushCallback());
			client.connect(options);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param topic
	 * @param message
	 * @throws MqttPersistenceException
	 * @throws MqttException
	 */
	public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
		MqttDeliveryToken token = topic.publish(message);
		token.waitForCompletion();
		System.out.println("message is published completely! " + token.isComplete());
		// System.out.println("内容是 "+message.toString());
		// System.out.println("主题是 "+topic.toString());

	}

	/**
	 * 启动入口
	 *
	 * @param args
	 * @throws MqttException
	 */
	public static void main(String[] args) throws MqttException {
		ServerMqtt server = new ServerMqtt("clientId");
		server.message = new MqttMessage();
		server.message.setQos(1);
		server.message.setRetained(true);
		server.message.setPayload("hello,topic11".getBytes());
		server.topic11 = server.client.getTopic("pibox");
		server.publish(server.topic11, server.message);
		/*
		 * pibox/cts/<machine_code>/logs
		 */
		server.client.close();
	}
}