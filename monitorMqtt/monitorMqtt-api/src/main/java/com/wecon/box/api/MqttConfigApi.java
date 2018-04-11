package com.wecon.box.api;

import com.wecon.box.entity.MqttConfig;
import org.springframework.stereotype.Component;

/**
 * Created by cai95 on 2018/4/8.
 */
@Component
public interface MqttConfigApi
{
    public MqttConfig getMqttConfig(Long serverId);

    public void setMqttConfig(MqttConfig mqttConfig);

    public void addMqttConfig(MqttConfig mqttConfig);

    public void deleteMqttConfig(Long serverId);

}
