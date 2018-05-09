package com.wecon.monitorMqtt.console.util;

import com.wecon.box.entity.Notification;

import java.util.List;

/**
 * Created by cai95 on 2018/5/2.
 */
public class PushAlarm {

    public static void pushOverconn(Long sevrerId,List<Notification> notifications){

        /*
        * 如果时间没有超过最短推送时间，直接不推送
        * */

        System.out.println("超出连接数  报警通知推送线程报警！！！！");



    }

    public static void pushLostconn(Long sevrerId,List<Notification> notifications){

        /*
        * 如果时间没有超过最短推送时间，直接不推送
        * */

        System.out.println("超出报警   报警通知推送线程报警！！！！");



    }

}
