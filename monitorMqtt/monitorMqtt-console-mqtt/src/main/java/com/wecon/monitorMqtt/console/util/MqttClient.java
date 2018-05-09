package com.wecon.monitorMqtt.console.util;

import com.wecon.box.entity.MqttConfig;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */
public class MqttClient {
    //tcp://MQTT安装的服务器地址:MQTT定义的端口号
    public  String host = "tcp://caijinwei.win:1883";

    public MqttCallback mqttCallback;

    //定义MQTT的ID，可以在MQTT服务配置中指定
    public  String clientid = "server11";

    public org.eclipse.paho.client.mqttv3.MqttClient client;
    public MqttTopic topic11;
    public String userName = "admin";
    public String passWord = "password";

    public MqttMessage message;

    /**
     * 构造函数
     * @throws MqttException
     */
    public MqttClient() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new org.eclipse.paho.client.mqttv3.MqttClient(host, clientid, new MemoryPersistence());
        connect();
    }
    public MqttClient(MqttConfig mqttConfig,MqttCallback mqttCallback) throws  MqttException{
        this.userName =  mqttConfig.username;
        this.clientid = "id"+(int)(1+Math.random()*1000);
        this.passWord = mqttConfig.getPassword();
        this.host ="tcp://"+mqttConfig.getServerIP()+":"+mqttConfig.getPort();
        this.mqttCallback = mqttCallback;
        client = new org.eclipse.paho.client.mqttv3.MqttClient(host, clientid, new MemoryPersistence());
        connect(mqttCallback);
        /*
        * $SYS/broker/clients/connected
        * */
    }

    /**
     *  用来连接服务器
     */
    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connect(MqttCallback mqttCallback) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(mqttCallback);
            client.connect(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param topic
     * @param message
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    public void subscribe(String topic) throws MqttException {
        if (client == null || !client.isConnected()) {
            connect();
        }
        // 订阅消息
        int[] Qos = {1};
        List<String> topicPort = new ArrayList<String>();
        topicPort.add(topic);
        String[] topics = new String[topicPort.size()];
        topicPort.toArray(topics);
        client.subscribe(topics, Qos);
    }

    public void close() throws MqttException {
        client.close();
        client.disconnect();
    }

    /**
     *  启动入口
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException {
        MqttClient server = new MqttClient();
        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload("hello,topic11".getBytes());
        server.topic11 = server.client.getTopic("pibox");
        server.publish(server.topic11 , server.message);

        server.client.close();
    }
}