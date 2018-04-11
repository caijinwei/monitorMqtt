package com.wecon.box.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wecon.box.api.LogAccountApi;
import com.wecon.box.entity.Account;
import com.wecon.box.entity.LogAccount;
import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zengzhipeng on 2017/8/29.
 */
@Component
public class DbLogUtil {
    private static final Logger logger = LogManager.getLogger(DbLogUtil.class.getName());

    @Autowired
    protected LogAccountApi logAccountApi;

    /**
     * 新增、删除操作
     *
     * @param op_type
     * @param res_type
     * @param res_id
     * @param object
     */
    public void addOperateLog(OpTypeOption op_type, ResTypeOption res_type, long res_id, Object object) {
        try {
            LogAccount log = logAccountApi.getLogInit();
            log.op_type = op_type;
            log.res_type = res_type;
            log.res_id = res_id;
            log.message = JSON.toJSONString(object);
            logAccountApi.addLog(log);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    /**
     * 更新操作
     *
     * @param op_type
     * @param res_type
     * @param res_id
     * @param oldObject
     * @param newObject
     */
    public void updOperateLog(OpTypeOption op_type, ResTypeOption res_type, long res_id, Object oldObject, Object newObject) {
        try {
            JSONObject object = new JSONObject();
            JSONObject oldJsonObject = JSONObject.parseObject(JSON.toJSONString(oldObject));
            JSONObject newsJsonObject = JSONObject.parseObject(JSON.toJSONString(newObject));
            for (Map.Entry<String, Object> entry : oldJsonObject.entrySet()) {
                if (newsJsonObject.containsKey(entry.getKey())) {
                    if (newsJsonObject.get(entry.getKey()) == null || entry.getValue() == null) {
//                        object.put(entry.getKey(), entry.getValue() + " ::: " + newsJsonObject.get(entry.getKey()));
                    }else if(!newsJsonObject.get(entry.getKey()).equals(entry.getValue())) {
                        object.put(entry.getKey(), entry.getValue() + " ::: " + newsJsonObject.get(entry.getKey()));
                    }
                }
            }
            addOperateLog(op_type, res_type, res_id, object);
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public static void main(String[] arg) {
        Account user = new Account();
        user.account_id = 100;
        user.username = "123";
        Account newuser = new Account();
        newuser.account_id = 100;
        newuser.username = "2222";

        JSONObject object = new JSONObject();
        JSONObject oldJsonObject = JSONObject.parseObject(JSON.toJSONString(user));
        JSONObject newsJsonObject = JSONObject.parseObject(JSON.toJSONString(newuser));
        for (Map.Entry<String, Object> entry : oldJsonObject.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
            System.out.println(entry.getKey() + ":" + newsJsonObject.get(entry.getKey()));
            if (!newsJsonObject.get(entry.getKey()).equals(entry.getValue())) {
                object.put(entry.getKey(), entry.getValue() + ":::" + newsJsonObject.get(entry.getKey()));
            }
        }
        System.out.println(JSON.toJSONString(object));
    }
}
