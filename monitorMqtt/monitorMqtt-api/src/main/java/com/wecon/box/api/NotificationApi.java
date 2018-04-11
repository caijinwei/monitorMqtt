package com.wecon.box.api;

import com.wecon.box.entity.Notification;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cai95 on 2018/4/10.
 */
@Component
public interface NotificationApi
{
    public List<Notification> getNotification();

    public void updateNotification(Notification notification);

    public void addNotification(Notification notification);

    public void deleteNotification(Long notificationId);

    public List<Notification> getNotification(Long serverId);
}
