package com.wecon.box.impl;

import com.wecon.box.api.NotificationApi;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.entity.Notification;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.restful.core.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by cai95 on 2018/4/10.
 */
@Component
public class NotificationImpl implements NotificationApi {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Notification> getNotification() {
        String sql = "select * from notification where 1=1";
       return jdbcTemplate.query(sql,new Object[]{},new NotificationRowMapper());
    }

    @Override
    public List<Notification> getNotification(Long serverId) {
        String sql = "select * from notification where server_id = ?";
        return jdbcTemplate.query(sql,new Object[]{serverId},new NotificationRowMapper());
    }

    @Override
    public void updateNotification(Notification notification) {
        String sql = "UPDATE `notification` SET `name`=?, `type`=?, `number`=?, `server_id`=? , `update_date`=current_timestamp()  WHERE (`notification_id`=?)";
        Object[] args =new Object[]{notification.name,notification.type,notification.number,notification.serverId,notification.notificationId};
        jdbcTemplate.update(sql,args);
    }

    @Override
    public void addNotification(Notification notification) {
        System.out.println("---------->"+notification.toString());
        String sql = "INSERT INTO `notification` (`name`, `type`, `number`,`max_time` ,`server_id`,`create_date`,`update_date`) VALUES (?,?,?,?,?,current_timestamp(),current_timestamp())";
        Object[] args = new Object[]{notification.name,notification.type,notification.number,notification.maxTime,notification.serverId};
        jdbcTemplate.update(sql,args);
    }

    @Override
    public void deleteNotification(Long notificationId) {

        if(notificationId == null){
            throw new BusinessException(ErrorCodeOption.Fail_Delete_Notification.key,ErrorCodeOption.Fail_Delete_Notification.value);
        }
        String sql = "DELETE FROM `notification` WHERE `notification_id` = ?";
        Object[] args = new Object[]{notificationId};
        jdbcTemplate.update(sql,args);
    }


    public static final class NotificationRowMapper implements RowMapper<Notification> {
        @Override
        public Notification mapRow(ResultSet rs, int i) throws SQLException {
            Notification model = new Notification();

            model.notificationId = rs.getLong("notification_id");
            model.name = rs.getString("name");
            model.type = rs.getInt("type");
            model.number = rs.getString("number");
            model.maxTime = rs.getInt("max_time");
            model.serverId =rs.getLong("server_id");

            return model;
        }
    }
}
