package com.wecon.box.websocket;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.util.DebugInfoCallback;
import com.wecon.box.util.ServerMqtt;
import com.wecon.common.util.CommonUtils;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 * Created by caijinw on 2017/9/12.
 */
@Component
public class DebugInfoHandler extends AbstractWebSocketHandler {

//    String machine_code;
//    ClientMQTT client;
//    private static final Logger logger = LogManager.getLogger(DebugInfoHandler.class.getName());
//    private Timer timer;
//
//    /**
//     * 收到消息
//     * 1)下发给mqtt
//     *
//     * @param session
//     * @param message
//     * @throws Exception
//     */
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        machine_code = message.getPayload();
//        logger.debug("get message:"+machine_code);
//        /*
//        * mqtt 发送
//        * */
//        mqttSend(machine_code, 1);
//        /*
//        * mqtt监听主题
//        * */
//        DebugInfoCallback debugInfoCallback = new DebugInfoCallback(session);
//        client = new ClientMQTT("pibox/cts/" + machine_code+"/logs", "debug"+session.getId(),debugInfoCallback);
//        client.start();
//    }
//
//    /**
//     * 连接成功后
//     *
//     * @param session
//     * @throws Exception
//     */
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        logger.debug("连接成功");
//        session.sendMessage(new TextMessage("连接成功"));
//    }
//
//
//    /**
//     * 关闭连接后
//     *
//     * @param session
//     * @param status
//     * @throws Exception
//     */
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        if (this.timer != null) {
//            this.timer.cancel();
//            logger.debug("timer cancel");
//        }
//        /*
//        * 发送关闭连接
//        * */
//        if (CommonUtils.isNotNull(machine_code)) {
//            mqttSend(machine_code, 0);
//
//        } else {
//            throw new BusinessException(ErrorCodeOption.DebugInfo_PramaIsNotFount_MachineCode.key, ErrorCodeOption.DebugInfo_PramaIsNotFount_MachineCode.value);
//        }
//        client.close();
//        logger.debug("关闭连接");
//    }
//
//    /*
//    * mqtt发送 数据
//    * */
//    public void mqttSend(String machine_code, Integer opType) throws MqttException {
//        long userId=AppContext.getSession().client.userId;
//        ServerMqtt server = new ServerMqtt("dg"+userId);
//        server.message = new MqttMessage();
//        server.message.setQos(0);
//        server.message.setRetained(true);
//
//        Map data = new HashMap<String, String>();
//        //  1、 上报调试信息   2、关闭上报
//        data.put("op_type", opType);
//
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("act", 2006);
//        jsonObject.put("machine_code", machine_code);
//        jsonObject.put("feedback", 0);
//        jsonObject.put("data", data);
//        String msg = jsonObject.toJSONString();
//
//        server.message.setPayload((msg).getBytes());
//        server.topic11 = server.client.getTopic("pibox/stc/" + machine_code);
//        //mqtt发送成功
//        server.publish(server.topic11, server.message);
//        server.client.disconnect();
//    }
}

