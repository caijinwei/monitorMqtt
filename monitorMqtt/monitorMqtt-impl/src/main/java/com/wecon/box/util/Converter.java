package com.wecon.box.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * Created by whp on 2017/8/29.
 */
public class Converter {
    /**
     * 将List中的对象转换成Map结构
     * @param list
     * @return
     */
    public static List<Map> convertListOjToMap(Object list){
        if(null == list){
            return null;
        }
        String json = JSON.toJSONString(list);
        try {
            List<Map> map = JSON.parseObject(json, new TypeReference<List<Map>>() {});
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
