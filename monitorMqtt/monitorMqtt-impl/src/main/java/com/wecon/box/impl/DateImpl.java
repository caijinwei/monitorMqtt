package com.wecon.box.impl;

import com.wecon.box.api.DataApi;
import com.wecon.box.entity.AlarmData;
import com.wecon.box.entity.ConnData;
import com.wecon.box.entity.Page;
import com.wecon.common.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai95 on 2018/5/2.
 */
@Component
public class DateImpl implements DataApi {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insertConnData(AlarmData alarmData) {
        Object[] args = new Object[]{alarmData.serverId, alarmData.data, 0};
        jdbcTemplate.update("INSERT INTO data (server_id, data, type, create_date) VALUES (?, ?, ?,CURRENT_TIMESTAMP())", args);
        System.out.println("日常记录成功！");
    }

    @Override
    public Page<ConnData> getAllserverData(Long serverId, String start_date,String end_date , int pageIndex, int pageSize) {


            String sqlCount = "select count(0) from data where 1=1 ";
            String sql = "select * from data where 1=1";
            StringBuffer condition = new StringBuffer("");
            List<Object> params = new ArrayList<Object>();
            if (serverId > 0) {
                condition.append(" and server_id = ? ");
                params.add(serverId);
            }

            // 操作时间起
            if (!CommonUtils.isNullOrEmpty(start_date)) {
                condition.append(" and date_format(data.create_date,'%Y-%m-%d %H:%i') >= ");
                condition.append(" date_format(str_to_date(?,'%Y-%m-%d %H:%i'),'%Y-%m-%d %H:%i') ");
                params.add(CommonUtils.trim(start_date));

            }
            // 操作时间止
            if (!CommonUtils.isNullOrEmpty(end_date)) {
                condition.append(" and date_format(data.create_date,'%Y-%m-%d %H:%i') <=  ");
                condition.append(" date_format(str_to_date(?,'%Y-%m-%d %H:%i'),'%Y-%m-%d %H:%i') ");
                params.add(CommonUtils.trim(end_date));

            }
            sqlCount += condition;
            int totalRecord = jdbcTemplate.queryForObject(sqlCount, params.toArray(), Integer.class);
            Page<ConnData> page = new Page<ConnData>(pageIndex, pageSize, totalRecord);
//		String sort = " order by real_his_cfg_id desc";
            sql += condition +  " limit " + page.getStartIndex() + "," + page.getPageSize();
            List<ConnData> list = jdbcTemplate.query(sql, params.toArray(), new DefaultDataRowMapper());
            page.setList(list);
            return page;

        }


    @Override
    public List<ConnData> getAllserverData(Long serverId, String start_date,String end_date) {

        String sqlCount = "select count(0) from data where 1=1 ";
        String sql = "select * from data where 1=1";
        StringBuffer condition = new StringBuffer("");
        List<Object> params = new ArrayList<Object>();
        if (serverId > 0) {
            condition.append(" and server_id = ? ");
            params.add(serverId);
        }

        // 操作时间起
        if (!CommonUtils.isNullOrEmpty(start_date)) {
            condition.append(" and date_format(data.create_date,'%Y-%m-%d %H:%i') >= ");
            condition.append(" date_format(str_to_date(?,'%Y-%m-%d %H:%i'),'%Y-%m-%d %H:%i') ");
            params.add(CommonUtils.trim(start_date));

        }
        // 操作时间止
        if (!CommonUtils.isNullOrEmpty(end_date)) {
            condition.append(" and date_format(data.create_date,'%Y-%m-%d %H:%i') <=  ");
            condition.append(" date_format(str_to_date(?,'%Y-%m-%d %H:%i'),'%Y-%m-%d %H:%i') ");
            params.add(CommonUtils.trim(end_date));

        }
        sqlCount += condition;
        int totalRecord = jdbcTemplate.queryForObject(sqlCount, params.toArray(), Integer.class);
//		String sort = " order by real_his_cfg_id desc";
        sql += condition ;
        List<ConnData> list = jdbcTemplate.query(sql, params.toArray(), new DefaultDataRowMapper());
        return list;

    }





public static final class DefaultDataRowMapper implements RowMapper<ConnData> {

    @Override
    public ConnData mapRow(ResultSet rs, int i) throws SQLException {
        ConnData model = new ConnData();
        model.id = rs.getLong("data_id");
        model.createTime = rs.getString("create_date");
        model.data = rs.getInt("data");
        model.serviceId = rs.getLong("server_id");
        return model;
    }
}


}
