package com.wecon.box.param;

import javax.validation.constraints.NotNull;

/**
 * Created by cai95 on 2018/4/10.
 */
public class NotificationPram {
    public long notificationId;
    public String name;

    @NotNull
    public int type;

    @NotNull
    public String number;

    @NotNull
    public int maxTime;

    @NotNull
    public long serverId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }
}
