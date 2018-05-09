package com.wecon.monitorMqtt.console.task;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;

/**
 * Created by cai95 on 2018/5/6.
 */
public class Test {
    public static void main(String[] args){
        Jedis jedis = new Jedis("caijinwei.win");
        JSONObject data = new JSONObject();
        data.put("op_id","2");
        data.put("op_type","2");
        jedis.publish("upd_mosquitto_cfg",data.toJSONString());
    }
}
