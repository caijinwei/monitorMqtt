package com.wecon.box.impl;

import com.wecon.box.api.TestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by cai95 on 2018/4/8.
 */
@Component
public class TestImpl implements TestApi {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void testJdbcConn() {

        String sql =new String("select * from mqttConfig where 1=1");
        Map<String, java.lang.Object>map= jdbcTemplate.queryForMap(sql);
            for(Object obj : map.keySet()){
            System.out.println(obj);
        }
    }
}
