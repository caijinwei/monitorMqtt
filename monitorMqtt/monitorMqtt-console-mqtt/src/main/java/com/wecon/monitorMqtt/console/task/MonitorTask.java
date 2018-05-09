package com.wecon.monitorMqtt.console.task;

import java.util.ArrayList;
import java.util.List;
import com.wecon.monitorMqtt.console.util.ThreadPoolExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import com.alibaba.fastjson.JSONObject;
import com.wecon.monitorMqtt.console.config.ConnectOptions;
import com.wecon.monitorMqtt.console.util.MqttConfigContext;
import com.wecon.common.util.CommonUtils;

/**
 * @author lanpenghui 2017年9月2日下午5:09:01
 */
public class MonitorTask extends Thread {
//	public static MqttClient client;
//	private final String clientId = "WECON_REVEIVE_DATA";
//	private String serverTopic = "pibox/cts/#";
//
//	private final int BASE_DATA = 1000;
//	private final int REAL_DATA = 1001;
//	private final int HISTORY_DATA = 1002;
//	private final int ALARM_DATA = 1003;
//	private final int WILL_DATA = 1004;
//
//	private static final Logger logger = LogManager.getLogger(MonitorTask.class);
//	private static int sleepTime = 1000 * 30;
//
//	public void run() {
//		logger.info("MonitorTask run start");
//		// 初始化线程池，根据不同类型分配不同线程数
//		List<int[]> initParams = new ArrayList<int[]>();
//		initParams.add(new int[] { BASE_DATA, 5 });
//		initParams.add(new int[] { REAL_DATA, 10 });
//		initParams.add(new int[] { HISTORY_DATA, 10 });
//		initParams.add(new int[] { ALARM_DATA, 10 });
//		initParams.add(new int[] { WILL_DATA, 1 });
//		ThreadPoolExecutor.getInstance().initThreadPool(initParams);
//
//		while (true) {
//			try {
//				if (client == null || !client.isConnected()) {
//					logger.info("mqtt connection is disconnection !");
//					connect();
//				}
//				sleep(sleepTime);
//
//			} catch (Exception e) {
//				logger.error(e);
//			}
//		}
//	}
//
//	/**
//	 *
//	 */
//	private void connect() {
//		try {
//
//			MqttConnectOptions options = ConnectOptions.getConnectOptions(MqttConfigContext.mqttConfig.getUsername(),
//					MqttConfigContext.mqttConfig.getPassword());
//			System.out.println("to connect mqtt......");
//			logger.info("to connect mqtt......");
//			client = new MqttClient(MqttConfigContext.mqttConfig.getHost(), clientId, new MemoryPersistence());
//			client.connect(options);
//			// 订阅盒子的所有发送主题
//			client.subscribe(serverTopic);
//
//			System.out.println("MQTT connection is successful !");
//			logger.info("MQTT connection is successful !");
//
//			client.setCallback(new MqttCallback() {
//
//				public void messageArrived(String topic, MqttMessage message) throws Exception {
//					manageData(topic, message);
//				}
//
//				public void deliveryComplete(IMqttDeliveryToken token) {
//
//					logger.info("deliveryComplete执行");
//				}
//
//				public void connectionLost(Throwable cause) {
//					if (!client.isConnected()) {
//						logger.info("Connection is broken  !");
//					}
//
//				}
//
//			});
//
//		} catch (MqttException e) {
//			logger.info("MqttException e==" + e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/**
//	 * 主题和消息处理
//	 */
//	public void manageData(String topic, MqttMessage message) {
//		String[] idexs = topic.split("/");
//		// 如果未获取到主题直接返回
//		if (null == idexs || idexs.length < 1) {
//			logger.info("主题为空！");
//			System.out.println("主题为空！");
//			return;
//		}
//		if (message.getPayload().length < 1) {
//			logger.info("消息为空！");
//			System.out.println("消息为空！");
//			return;
//
//		}
//		String boxMsg = "";
//		try {
//			boxMsg = new String(message.getPayload(), "UTF-8").trim();
//			JSONObject jsonObject = JSONObject.parseObject(boxMsg);
//			System.out.println("jsonObject=" + jsonObject);
//			String machineCode = jsonObject.getString("machine_code");
//			// 机器码为空消息直接忽略
//			if (CommonUtils.isNullOrEmpty(jsonObject.getString("machine_code"))) {
//				return;
//			}
//			// 如果消息的机器码和主题中的机器码不匹配直接忽略消息
//			if (!idexs[2].equals(machineCode)) {
//				logger.info("主题中的机器码和消息的机器码不匹配！");
//				System.out.println("主题中的机器码和消息的机器码不匹配！");
//				return;
//			}
//			// 数据为空
//			if (CommonUtils.isNullOrEmpty(jsonObject.getString("data"))) {
//				logger.info("data为空！");
//				System.out.println("data为空！");
//				return;
//			}
//			// act为空
//			if (CommonUtils.isNullOrEmpty(jsonObject.getInteger("act"))) {
//				logger.info("act为空！");
//				System.out.println("act为空！");
//				return;
//			}
//			if (CommonUtils.isNullOrEmpty(jsonObject.getInteger("feedback"))) {
//				logger.info("feedback为空！");
//				System.out.println("feedback为空！");
//				return;
//			}
//			Integer act = jsonObject.getInteger("act");
////			BusDataHandleTask task = new BusDataHandleTask(client, jsonObject);
////
////			/** 指定任务类型，以便获取对应线程进行处理 **/
////			task.setType(act);
////			ThreadPoolExecutor.getInstance().addTask(task);
//		} catch (Exception e) {
//			String simplename = e.getClass().getSimpleName();
//			if (!"JSONException".equals(simplename)) {
//				e.printStackTrace();
//			}
//
//		}
//
//	}

}
