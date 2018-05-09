package com.wecon.box.action;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.api.MqttConfigApi;
import com.wecon.box.api.TestApi;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.param.MqttConfigParam;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by cai95 on 2018/4/8.
 */
@RestController
@RequestMapping(value = "mqttConfig")
public class MqttConfigAction {

    @Autowired
    TestApi testApi;

    @Autowired
    MqttConfigApi mqttConfigApi;

    @Label("jdbc连接测试")
    @RequestMapping(value = "testConnect")
    public Output testConnect() {
        System.out.println("------begin------");
        testApi.testJdbcConn();
        System.out.println("------end------");
        return new Output();
    }

    @Label("server绑定到用户")
    @RequestMapping(value = "bindAccountMqtt")
    public Output bindAccountMqtt(@RequestParam("server_id") Long serverId, @RequestParam("account_id") Long accountId) {
        if (serverId != null && accountId != null && serverId > 0 && accountId > 0) {
            throw new BusinessException(ErrorCodeOption.Bind_Account_Server_Error.key, ErrorCodeOption.Bind_Account_Server_Error.value);
        }

        mqttConfigApi.bindAccountMqtt(serverId, accountId);

        return new Output();
    }

    @Label("server解绑用户")
    @RequestMapping(value = "unbindAccountMqtt")
    public Output unbindAccountMqtt(@RequestParam("server_id") Long serverId) {
        if(serverId == null || serverId <= 0){
            throw new BusinessException(ErrorCodeOption.UnBind_Account_Server_Error.key,ErrorCodeOption.UnBind_Account_Server_Error.value);
        }
        mqttConfigApi.delAccountMqtt(serverId);
        return new Output();
    }

    @Label("查找账户下配置MQTT服务器")
    @RequestMapping(value = "accServerList")
    public Output accServerList(@RequestParam("account_id") Long accountId) {
        if (accountId == null || accountId <= 0) {
            throw new BusinessException(ErrorCodeOption.Find_Account_Servers_Error.key, ErrorCodeOption.Find_Account_Servers_Error.value);
        }

        List<MqttConfig> mqttConfigList = mqttConfigApi.getMqttConfigByAccoutId(accountId);
        JSONObject data =new JSONObject();
        data.put("mqttServerList",mqttConfigList);


        return new Output(data);
    }

    @Label("设置MQTTConfig")
    @RequestMapping(value = "updateMqttConfig")
    public Output updateMqttConfig(@Valid MqttConfigParam mqttConfigParam) {
        MqttConfig model = new MqttConfig();

        model.maxConn = mqttConfigParam.maxConn;
        model.password = mqttConfigParam.password;
        model.isSsl = mqttConfigParam.isSsl;
        model.serverId = mqttConfigParam.serverId;
        model.port = mqttConfigParam.port;
        model.serverIP = mqttConfigParam.serverIP;
        model.serverName = mqttConfigParam.serverName;
        model.username = mqttConfigParam.username;
        model.websocketPort = mqttConfigParam.websocketPort;

        System.out.println("Action获取到的值是" + model.toString());
        if (model.serverId == 0) {
            mqttConfigApi.bindAccountMqtt(mqttConfigApi.addMqttConfig(model),AppContext.getSession().client.userId);
        } else {
            mqttConfigApi.setMqttConfig(model);
        }
        return new Output();
    }

    @Label("删除MQTT配置")
    @RequestMapping(value = "delete")
    public Output deleteMqttConfig(@RequestParam("serverId") Long serverId) {
        if (serverId == null) {
            throw new BusinessException(ErrorCodeOption.Fail_Delete_MqttConfig.key, ErrorCodeOption.Fail_Delete_MqttConfig.value);
        }
        mqttConfigApi.deleteMqttConfig(serverId);

        return new Output();
    }

    @Label("获取MQTTConfig通过serverId")
    @RequestMapping(value = "getMqttConfigById")
    public Output getMqttConfigById(@RequestParam("serverId") Long serverId) {

//
        if (serverId != null) {
            JSONObject data = new JSONObject();
            MqttConfig mqttConfig = mqttConfigApi.getMqttConfig(serverId);
            System.out.println("测试-----------------------------" + mqttConfig.toString());

            data.put("mqttConfig", mqttConfig);
            return new Output(data);
        } else {
            throw new BusinessException(ErrorCodeOption.Get_Data_Error.key, ErrorCodeOption.Get_Data_Error.value);
        }
    }
}
