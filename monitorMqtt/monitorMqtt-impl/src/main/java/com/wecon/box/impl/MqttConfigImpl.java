package com.wecon.box.impl;

import com.wecon.box.api.MqttConfigApi;
import com.wecon.box.entity.MqttConfig;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.restful.core.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
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
        if (serverId == null) {
            return null;
        }
        String sql = "SELECT * FROM mqttConfig where server_id = ?;";
        Object[] args = new Object[]{serverId};
        List<MqttConfig> resultList = jdbcTemplate.query(sql, args, new MqttConfigRowMapper());
        return resultList.get(0);
    }

    @Override
    public List<MqttConfig> getMqttConfigList() {
        String sql = "SELECT * FROM mqttConfig where 1=1;";
        Object[] args = new Object[]{};
        List<MqttConfig> resultList = jdbcTemplate.query(sql, args, new MqttConfigRowMapper());
        return resultList;
    }

    @Override
    public List<MqttConfig> getMqttConfigByAccoutId(Long accountId) {
        String sql = "SELECT * FROM mqttconfig a LEFT JOIN acc_ser_bind b ON a.server_id =b.server_id  LEFT JOIN account c ON b.account_id =c.account_id WHERE c.account_id = ?";
        Object[] args = new Object[]{accountId};
        return jdbcTemplate.query(sql, args, new MqttConfigRowMapper());
    }

    @Override
    public void setMqttConfig(MqttConfig mqttConfig) {

        String sql = "UPDATE mqttConfig  SET server_name = ?, `port` = ?, user_name = ?, `password` = ?, is_ssl = ? ,max_conn = ?,websocket_port=? ,update_date =CURRENT_TIMESTAMP() WHERE server_id = ?;";

        int isSsl = 0;
        if (mqttConfig.getIsSsl()) {
            isSsl = 1;
        } else {
            isSsl = 0;
        }
        Object[] args = new Object[]{mqttConfig.getServerName(), mqttConfig.getPort(), mqttConfig.getUsername(), mqttConfig.getPassword(), isSsl, mqttConfig.getMaxConn(),mqttConfig.getWebsocketPort(), mqttConfig.getServerId()};
        jdbcTemplate.update(sql, args);

    }

    @Override
    public Long addMqttConfig(final MqttConfig mqttConfig) {

        final String sql = "INSERT INTO `mqttConfig` (`server_name`, `password`, `port`, `is_ssl`,  `server_ip`, `user_name`, `max_conn`, `update_date`, `create_date`) VALUES (?, ?, ?,  ?, ?, ?, ?,current_timestamp(),current_timestamp());";


        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                int isSsl = 0;
                if (mqttConfig.getIsSsl()) {
                    isSsl = 1;
                } else {
                    isSsl = 0;
                }
                PreparedStatement preState = con.prepareStatement("INSERT INTO `mqttConfig` (`server_name`, `password`, `port`, `is_ssl`,  `server_ip`, `user_name`, `max_conn`, `websocket_port`,`update_date`, `create_date`) VALUES (?, ?, ?,  ?, ?, ?, ?,?,current_timestamp(),current_timestamp())",
                Statement.RETURN_GENERATED_KEYS);
                preState.setString(1,mqttConfig.getServerName());
                preState.setString(2,mqttConfig.getPassword());
                preState.setInt(3,mqttConfig.getPort());
                preState.setInt(4,isSsl);
                preState.setString(5,mqttConfig.getServerIP());
                preState.setString(6,mqttConfig.getUsername());
                preState.setInt(7,mqttConfig.getMaxConn());
                preState.setInt(8,mqttConfig.getWebsocketPort());
                return preState;
            }
        }, key);
        // 从主键持有者中获得主键值
        return key.getKey().longValue();

    }

    @Override
    public void deleteMqttConfig(Long serverId) {
        if (serverId == null) {
            throw new BusinessException(ErrorCodeOption.Fail_Delete_MqttConfig.key, ErrorCodeOption.Fail_Delete_MqttConfig.value);
        }
        String sql = "DELETE FROM mqttconfig WHERE server_id =?";
        Object[] args = new Object[]{serverId};
        jdbcTemplate.update(sql, args);
    }

    @Override
    public void bindAccountMqtt(Long serverId, Long accountId) {

        String sql = "INSERT INTO `acc_ser_bind` (`account_id`, `server_id`, `create_date`) VALUES (?, ?,current_timestamp())";
        Object[] args = new Object[]{accountId, serverId};
        jdbcTemplate.update(sql, args);
    }

    @Override
    public void delAccountMqtt(Long serverId) {
        String sql = "DELETE FROM `acc_ser_bind` WHERE `server_id`=?";
        Object[] args = new Object[]{serverId};
        jdbcTemplate.update(sql, args);
    }


    public static final class MqttConfigRowMapper implements RowMapper<MqttConfig> {
        @Override
        public MqttConfig mapRow(ResultSet rs, int i) throws SQLException {
            MqttConfig model = new MqttConfig();

            model.setUsername(rs.getString("user_name"));
            model.setServerId(rs.getLong("server_id"));
            model.setPassword(rs.getString("password"));
            model.setPort(rs.getInt("port"));
            model.setServerIP(rs.getString("server_ip"));
            model.setServerName(rs.getString("server_name"));
            model.setMaxConn(rs.getInt("max_conn"));
            model.setWebsocketPort(rs.getInt("websocket_port"));
            if (rs.getInt("is_ssl") == 0) {
                model.setIsSsl(false);
            } else {
                model.setIsSsl(true);
            }

            return model;
        }
    }
}
