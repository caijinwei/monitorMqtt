package com.wecon.box.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.alibaba.fastjson.JSONObject;
import com.wecon.common.util.CommonUtils;

/**
 * @author lanpenghui 2017年10月14日下午2:13:14
 */
public class SendvalueCallback implements MqttCallback {
	private static final Logger logger = LogManager.getLogger(SendvalueCallback.class.getName());

	private WebSocketSession session;
	private String addr_id;

	public SendvalueCallback(WebSocketSession session, String addr_id) {
		this.session = session;
		this.addr_id = addr_id;
	}

	@Override
	public void connectionLost(Throwable arg0) {

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {

	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) {

		try {
			
			String[] idexs = arg0.split("/");
			String reMessage = new String(arg1.getPayload(), "UTF-8").trim();
			logger.debug("mqtt消息：" + reMessage);

			JSONObject json = new JSONObject();
			JSONObject jsonObject = JSONObject.parseObject(reMessage);
			String machineCode = jsonObject.getString("machine_code");
			// 机器码为空消息直接忽略
			if (CommonUtils.isNullOrEmpty(jsonObject.getString("machine_code"))) {
				return;
			}
			// 如果消息的机器码和主题中的机器码不匹配直接忽略消息
			if (!idexs[2].equals(machineCode)) {
				logger.info("主题中的机器码和消息的机器码不匹配！");
				return;
			}
			// act为空
			if (CommonUtils.isNullOrEmpty(jsonObject.getInteger("act"))) {

				logger.info("act为空！");
				return;
			}
			if (1 != jsonObject.getInteger("act")) {
				logger.info("act不为1！");
				return;
			}
			if (CommonUtils.isNullOrEmpty(jsonObject.getInteger("feedback_act"))) {
				logger.info("feedback_act为空！");
				return;
			}
			if (2000 != jsonObject.getInteger("feedback_act")) {
				logger.info("feedback_act不为2000！");
				return;
			}
			// 数据为空
			if (CommonUtils.isNullOrEmpty(jsonObject.getString("data"))) {
				logger.info("data为空！");
				return;
			}
			System.out.println("选中的addrid==" + addr_id);
			System.out.println("接收的消息==" + reMessage);
			JSONObject jsonBase = jsonObject.getJSONObject("data");
			if (!CommonUtils.isNullOrEmpty(addr_id) && !CommonUtils.isNullOrEmpty(jsonBase.getInteger("addr_id"))) {
				if (Integer.parseInt(addr_id) == jsonBase.getInteger("addr_id")) {

					int upd_state = jsonBase.getInteger("upd_state");
					if (1 == upd_state) {
						json.put("resultData", 1);// 反馈成功信息

					} else {
						json.put("resultData", 0);
						json.put("resultError", jsonBase.getString("upd_error"));
					}
					sendWSMassage(session, json.toJSONString());
					addr_id = "-1";
					
				}
			}

		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			if (!"JSONException".equals(simplename)) {
				JSONObject errorjson = new JSONObject();
				errorjson.put("resultData", 0);
				errorjson.put("resultError", "下发错误，请重试");
				sendWSMassage(session, errorjson.toJSONString());
				logger.error(e);
				e.printStackTrace();
			}

		}

	}

	// wbsock发送数据
	public void sendWSMassage(WebSocketSession session, String message) {
		try {
			if (session != null && session.isOpen()) {
				session.sendMessage(new TextMessage(message));
			} else {
				logger.debug(session != null);
				logger.debug(session.isOpen());
				logger.debug("----websockect 断开连接----");
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

}
