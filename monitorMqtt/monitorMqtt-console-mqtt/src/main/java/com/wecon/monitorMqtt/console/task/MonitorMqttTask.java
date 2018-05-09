package com.wecon.monitorMqtt.console.task;

import com.wecon.box.api.DataApi;
import com.wecon.box.api.MqttConfigApi;
import com.wecon.box.entity.AlarmData;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.entity.Notification;
import com.wecon.monitorMqtt.console.util.AbstractTask;
import com.wecon.monitorMqtt.console.util.MqttClient;
import com.wecon.monitorMqtt.console.util.PushAlarm;
import com.wecon.monitorMqtt.console.util.ThreadPoolExecutor;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by cai95 on 2018/5/2.
 */
public class MonitorMqttTask extends AbstractTask {

    @Autowired
    DataApi dataApi;

    @Autowired
    static MqttConfigApi mqttConfigApi;

    private MqttConfig mqttConfig;
    private List<Notification> notifications;
    private MqttClient mqttClient;

    public MonitorMqttTask(MqttConfig mqttConfig, List<Notification> notifications) throws MqttException {
        this.mqttConfig = mqttConfig;
        this.notifications = notifications;
        init();
    }

    public void destroyMqtt(){
        try {
            mqttClient.close();
        } catch (MqttException e) {
            System.out.println("mosquitto服务器:"+mqttClient.host+"   关闭异常");
            e.printStackTrace();
        }
    }

    //初始化参数
    public void init() throws MqttException {

        this.mqttClient = new MqttClient(mqttConfig, new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("mosquitto服务器:" + mqttConfig.getServerIP() + "     连接丢失");
                //报警通知
                PushAlarm.pushLostconn(notifications.get(0).serverId, notifications);
                return;
            }

            public void messageArrived(String s, MqttMessage mqttMessage) {
                try {
                    System.out.println("mqttMessage:   " + mqttMessage.toString());
                    int currentConnect = new Integer(mqttMessage.toString());

                    /*
                    * 当连接数超过配置的最大连接数时
                    *       1.记录
                    *       2.报警
                    * */
                    AlarmData alarmData = new AlarmData();
                    alarmData.data = new Integer(mqttMessage.toString());
                    alarmData.serverId = mqttConfig.serverId;

                    if (currentConnect >= mqttConfig.maxConn) {
                        PushAlarm.pushOverconn(mqttConfig.serverId, notifications);
                        alarmData.type = 1;
                    }
                    //实时记录在线连接数
                    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
                    DataApi dataApi = applicationContext.getBean(DataApi.class);

                    dataApi.insertConnData(alarmData);

                } catch (Exception e) {
                    /*
                    * 系统异常
                    * */
                    e.printStackTrace();
                }
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        //订阅在线连接数
        mqttClient.subscribe("$SYS/broker/clients/connected");
        /*
        *
        mqttConfig.setMqttClietId("111");
		mqttConfig.setMqttHost("tcp://caijinwei.win");
		mqttConfig.setMqttPort("1883");
		mqttConfig.setMqttPasswd("password");
		mqttConfig.setMqttUsername("admin");
		ClientMQTT mqtt=new ClientMQTT(mqttConfig,new PushCallback());
		mqtt.publish("1111","11111");
        */
    }



//    public static void main(String[] args) throws MqttException {
//
//        MqttConfig mqttConfig = new MqttConfig();
//        mqttConfig.serverIP = "caijinwei.win";
//        mqttConfig.serverId = 1;
//        mqttConfig.port = 1883;
//        mqttConfig.maxConn =2;
//        mqttConfig.username = "admin";
//        mqttConfig.password = "password";
//        List<Notification> list = new ArrayList<Notification>();
//        MonitorMqttTask mqttTask = new MonitorMqttTask(mqttConfig, list);
//        mqttTask.start();
//
//    }

    public static void main(String[] args) throws MqttException, InterruptedException {

        MqttConfig mqttConfig = new MqttConfig();
        mqttConfig.serverIP = "caijinwei.win";
        mqttConfig.serverId = 1;
        mqttConfig.port = 1883;
        mqttConfig.maxConn = 1000;
        mqttConfig.username = "admin";
        mqttConfig.password = "password";
        List<Notification> list = new ArrayList<Notification>();
        AbstractTask abstractTask = new MonitorMqttTask(mqttConfig, list);
        abstractTask.setType(1);




        List<Notification> list1 = new ArrayList<Notification>();
            MonitorMqttTask mqttTask = new MonitorMqttTask(mqttConfig,list);
            mqttTask.run();
//
//        AbstractTask abstractTask1 = new MonitorMqttTask(mqttConfig1, list);
//        abstractTask1.setType(1);
//
//         ThreadPoolExecutor threadPoolExecutor = ThreadPoolExecutor.getInstance();
////        List<int[]> initParams = new ArrayList<int[]>();
////        int[] parm = {1, 4};
//////        int[] parm2 = {2,2};
////        initParams.add(parm);
//////        initParams.add(parm2);
////        threadPoolExecutor.initThreadPool(initParams);
//
//
//        AbstractTask[] abstractTaskList = new AbstractTask[]{abstractTask,abstractTask1};
//
//        threadPoolExecutor.batchAddTask(abstractTaskList);

    }
}