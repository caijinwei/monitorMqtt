package com.wecon.box.entity;

/**
 * Created by cai95 on 2018/4/10.
 */
public class Notification {
   public long notificationId;
    public String name;
    public int type;
    public String number;
    public int maxTime;
    public long serverId;



    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", number='" + number + '\'' +
                ", maxTime=" + maxTime +
                '}';
    }
}
