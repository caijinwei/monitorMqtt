package com.wecon.box.impl;

import com.wecon.box.api.MqttConfigApi;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.restful.core.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai95 on 2018/4/9.
 */
@Component
public class MqttConfigImpl implements MqttConfigApi {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public MqttConfig getMqttConfig(Long serverId) {
        if(serverId == null){
            return null;
        }
        String sql = "SELECT * FROM mqttConfig where server_id = ?;";
        Object[] parms = new Object[]{serverId};
        List<MqttConfig> resultList = jdbcTemplate.query(sql,parms,new MqttConfigRowMapper());
        return resultList.get(0);
    }

    @Override
    public void setMqttConfig(MqttConfig mqttConfig) {

        String sql = "UPDATE mqttConfig  SET server_name = ?, `port` = ?, user_name = ?, `password` = ?, is_ssl = ? ,max_conn = ? ,update_date =CURRENT_TIMESTAMP() WHERE server_id = ?;";

        int isSsl = 0;
        if(mqttConfig.getIsSsl()){
            isSsl=1;
        }else{
            isSsl=0;
        }
        Object[] args = new Object[]{mqttConfig.getServerName(),mqttConfig.getPort(),mqttConfig.getUsername(),mqttConfig.getPassword(),isSsl,mqttConfig.getMaxConn(),mqttConfig.getServerId()};
        jdbcTemplate.update(sql,args);

    }

    @Override
    public void addMqttConfig(MqttConfig mqttConfig) {

        String sql = "INSERT INTO `mqttConfig` (`server_name`, `password`, `port`, `is_ssl`,  `server_ip`, `user_name`, `max_conn`, `update_date`, `create_date`) VALUES (?, ?, ?,  ?, ?, ?, ?,current_timestamp(),current_timestamp());";

        int isSsl = 0;
        if(mqttConfig.getIsSsl()){
            isSsl=1;
        }else{
            isSsl=0;
        }
        Object[] args = new Object[]{mqttConfig.getServerName(),mqttConfig.getPassword(),mqttConfig.getPort(),isSsl,mqttConfig.getServerIP(),mqttConfig.getUsername(),mqttConfig.getMaxConn()};
        jdbcTemplate.update(sql,args);

    }

    @Override
    public void deleteMqttConfig(Long serverId) {
        if(serverId == null){
            throw new BusinessException(ErrorCodeOption.Fail_Delete_MqttConfig.key,ErrorCodeOption.Fail_Delete_MqttConfig.value);
        }
        String sql = "DELETE FROM `mqttConfig` WHERE (`server_id`=?)";
        Object[]  args =new Object[]{serverId};
        jdbcTemplate.update(sql,args);
    }


    public static final class MqttConfigRowMapper implements RowMapper<MqttConfig> {
        @Override
        public MqttConfig mapRow(ResultSet rs, int i) throws SQLException {
            MqttConfig model = new MqttConfig();

            model.setUsername(rs.getString("user_name"));
            model.setPassword(rs.getString("password"));
            model.setPort(rs.getInt("port"));
            model.setServerIP(rs.getString("server_ip"));
            model.setServerName(rs.getString("server_name"));
            model.setMaxConn(rs.getInt("max_conn"));
             if(rs.getInt("is_ssl") == 0){
                model.setIsSsl(false);
             }else{
                 model.setIsSsl(true);
             }

            return model;
        }
    }
}
