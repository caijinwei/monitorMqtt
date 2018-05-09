package com.wecon.monitorMqtt.console.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/**
 * 服务端发布消息 Created by zengzhipeng on 2017/7/25.
 */
public class PublishTask extends Thread {
	private static final Logger logger = LogManager.getLogger(PublishTask.class);
	private MqttTopic topic;
	private MqttMessage message;

	public PublishTask(MqttTopic topic, MqttMessage message) {
		super();
		this.topic = topic;
		this.message = message;
	}

	public void run() {

		MqttDeliveryToken token;
		try {
			token = topic.publish(message);
			token.waitForCompletion();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
}
