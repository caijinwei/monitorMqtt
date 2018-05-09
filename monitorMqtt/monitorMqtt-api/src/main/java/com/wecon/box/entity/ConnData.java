package com.wecon.box.entity;

/**
 * Created by cai95 on 2018/5/7.
 */
public class ConnData {
    public Long id;
    public Long serviceId;
    public int data;
    public String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ConnData{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", data=" + data +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
