package com.wecon.monitorMqtt.console.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.wecon.box.api.MqttConfigApi;
import com.wecon.box.api.NotificationApi;
import com.wecon.box.constant.ConstKey;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.entity.Notification;
import com.wecon.box.enums.OpTypeOption;
import com.wecon.common.redis.RedisManager;
import com.wecon.common.util.CommonUtils;
import com.wecon.monitorMqtt.console.util.AbstractTask;
import com.wecon.monitorMqtt.console.util.ThreadPoolExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by whp on 2018/1/29.
 */
public class SubscribeTask extends Thread {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

    private static final Logger logger = LogManager.getLogger(SubscribeTask.class);

    public void run() {

        /**
         * 订阅Web端操作配置信息，以便推送该条数据给盒子端
         */
        Jedis jedis = new Jedis("caijinwei.win");
        jedis.subscribe(new SubscribeListener(), new String[]{"upd_mosquitto_cfg"});
        logger.debug("upd_device_cfg subscribe success");
    }

    class SubscribeListener extends JedisPubSub {
        MqttConfigApi mqttConfigApi = applicationContext.getBean(MqttConfigApi.class);
        NotificationApi notificationApi = applicationContext.getBean(NotificationApi.class);

        @Override
        public void onMessage(String channel, String message) {
            logger.debug("upd_device_cfg subscribe callback，channel：" + channel + "message：" + message);
            if (!CommonUtils.isNullOrEmpty(message)) {
                try {
                    Map<String, Object> param = JSON.parseObject(message, new TypeReference<Map<String, Object>>() {
                    });
                    int opType = Integer.parseInt(param.get("op_type").toString());
                    long opId = Long.parseLong(param.get("op_id").toString());

                    MqttConfig mqttConfig = mqttConfigApi.getMqttConfig(opId);
                    List<Notification> notificationList = notificationApi.getNotification(opId);
                    if(notificationList.size() >0 && mqttConfig !=null ){
                        new MonitorMqttTask(mqttConfig,notificationList);
                    }
                } catch (Exception e) {
                    logger.error("upd_device_cfg subscribe callback error：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args){
        new SubscribeTask().start();
    }
}
