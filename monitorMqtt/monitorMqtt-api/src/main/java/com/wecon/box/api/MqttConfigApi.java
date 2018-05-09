package com.wecon.box.api;

import com.wecon.box.entity.MqttConfig;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cai95 on 2018/4/8.
 */
@Component
public interface MqttConfigApi
{
    public MqttConfig getMqttConfig(Long serverId);

    public List<MqttConfig> getMqttConfigList();

    public List<MqttConfig> getMqttConfigByAccoutId(Long accountId);

    public void setMqttConfig(MqttConfig mqttConfig);

    public Long addMqttConfig(MqttConfig mqttConfig);

    public void deleteMqttConfig(Long serverId);

    public void bindAccountMqtt(Long serverId,Long accountId);

    public void delAccountMqtt(Long serverId);


}
