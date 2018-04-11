package com.wecon.box.util;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * Created by caijinw on 2017/9/13.
 */

public class DebugInfoCallback implements MqttCallback{

    WebSocketSession session;

    public DebugInfoCallback(WebSocketSession session){
        this.session=session;
    }

    public void connectionLost(Throwable throwable) {

    }
    public void messageArrived(String s, MqttMessage message) throws Exception {
        sendWSMassage(session,new String(message.getPayload(),"UTF-8"));
    }
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }

    //wbsock发送数据
    public void sendWSMassage(WebSocketSession session,String message) throws IOException {
        session.sendMessage(new TextMessage(message));
    }
}
