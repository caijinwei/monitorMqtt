package com.wecon.box.action;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.api.MqttConfigApi;
import com.wecon.box.api.NotificationApi;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.entity.Notification;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.param.NotificationPram;
import com.wecon.restful.core.Output;
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.doc.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by cai95 on 2018/4/10.
 */
@RestController
@RequestMapping(value = "notification")
public class NotificationAction  {

    @Autowired
    NotificationApi notificationApi;

    @Autowired
    MqttConfigApi mqttConfigApi;

    @RequestMapping("update")
    public Output updateNotification(@Valid NotificationPram notification){

        Notification model =new Notification();
        model.maxTime = notification.maxTime;
        model.notificationId = notification.notificationId;
        model.serverId = notification.serverId;
        model.number= notification.number;
        model.name = notification.name;
        model.type = notification.type;

        if(notification.notificationId ==0){
            notificationApi.addNotification(model);
        }else{
            notificationApi.updateNotification(model);
        }
        return new Output();
    }

    @Label("获取server对应的报警配置")
    @RequestMapping("showAllNotification")
    public Output showAllNotification(@RequestParam("serverId")Long serverId){

        if(serverId == null){
            throw new BusinessException(ErrorCodeOption.Fail_Get_NotificationId.key,ErrorCodeOption.Fail_Get_NotificationId.value);
        }
        MqttConfig mqttConfig = mqttConfigApi.getMqttConfig(serverId);
        List<Notification> notificationList = notificationApi.getNotification(serverId);

        JSONObject data = new JSONObject();
        data.put("list",notificationList);
        data.put("serverName",mqttConfig.getServerName());

        return new Output(data);
    }


    @RequestMapping("delete")
    public Output deleteNotification(@RequestParam Long notificationId){
        if(notificationId == null){
            throw new BusinessException(ErrorCodeOption.Fail_Delete_Notification.key,ErrorCodeOption.Fail_Delete_Notification.value);
        }
        notificationApi.deleteNotification(notificationId);

        return new Output();
    }
}
