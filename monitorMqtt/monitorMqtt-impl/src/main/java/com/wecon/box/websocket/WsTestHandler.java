package com.wecon.box.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.amazonaws.services.opsworks.model.App;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhipeng on 2017/9/25.
 */
public class WsTestHandler extends AbstractWebSocketHandler {

    private static final Logger logger = LogManager.getLogger(WsTestHandler.class.getName());

    private String params;
    private Map<String, Object> bParams;
    private Client client;
    private Map<String, Client> clients = new HashMap<String, Client>();
    private ThreadLocal<Client> clientTl = new ThreadLocal<>();

    private int count = 0;

    /**
     * 收到消息
     *
     * @param session
     * @param message
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        logger.debug("WsTestHandler handleTextMessage - 收到消息 - " + session.getId());
        params = message.getPayload();
        try {
            count++;
            session.sendMessage(new TextMessage("WsTestHandler handleTextMessage - 收到消息 - " + params));
//            session.sendMessage(new TextMessage("handleTextMessage - count:" + count));
//            session.sendMessage(new TextMessage("handleTextMessage - this.client.userId:" + this.client.userId));
//            session.sendMessage(new TextMessage("handleTextMessage - this.clientMap.userId:" + this.clients.get(session.getId()).userId));
//            session.sendMessage(new TextMessage("handleTextMessage - AppContext.getSession().client.userId:" + AppContext.getSession().client.userId));
            System.out.println(this.clientTl.get() == null);
//            session.sendMessage(new TextMessage("handleTextMessage - this.clientTl.userId:" + this.clientTl.get().userId));


//            session.sendMessage(new TextMessage("server get msg:" + params));

            bParams = JSON.parseObject(params, new TypeReference<Map<String, Object>>() {
            });


        } catch (Exception e) {
            logger.error(e);
//            e.printStackTrace();
        }
    }

    /**
     * 连接成功后
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        logger.debug("WsTestHandler afterConnectionEstablished - 连接成功后 - " + session.getId());
        session.sendMessage(new TextMessage("WsTestHandler afterConnectionEstablished - 连接成功后 - " + session.getId()));
        try {
            this.client = AppContext.getSession().client;
            this.clientTl.set(AppContext.getSession().client);
            this.clients.put(session.getId(), AppContext.getSession().client);

//            test(session);
            /*session.sendMessage(new TextMessage("afterConnectionEstablished - this.sessionTl.getId:" + this.sessionTl.get().getId()));
            session.sendMessage(new TextMessage("afterConnectionEstablished - this.clientTl.userId:" + this.clientTl.get().userId));*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("afterConnectionEstablished，" + e.getMessage());
        }
    }

    private void test(WebSocketSession session) {
        try {
            session.sendMessage(new TextMessage("test - this.clientTl.userId:" + this.clientTl.get().userId));
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("test，" + e.getMessage());
        }
    }

    /**
     * 关闭连接后
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug("WsTestHandler afterConnectionClosed - 关闭连接后--------------------------------------------------");
        this.clients.remove(session.getId());
        /*logger.debug("afterConnectionClosed - this.clientTl.userId:" + this.clientTl.get().userId);
        logger.debug("关闭连接后");*/
    }


}
