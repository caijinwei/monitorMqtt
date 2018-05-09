package com.wecon.box.entity;

/**
 * Created by cai95 on 2018/5/2.
 */
public class AlarmData {
    public Long serverId;
    public Integer type =0;
    public Integer data;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
