package com.wecon.box.util;

import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wecon.box.enums.AlarmGradeOption;
import com.wecon.box.enums.DataTypeOption;

/**
 * Created by zengzhipeng on 2017/8/24.
 */
@Component
public class OptionUtil {

    public JSONArray getDataTypeOptionOptions() {
        JSONArray options = new JSONArray();
        for (DataTypeOption it : DataTypeOption.values()) {
            options.add(createOption(it.getValue(), it.getKey()));
        }
        return options;
    }
    public JSONArray getAlarmGradeOptionOptions() {
    	JSONArray options = new JSONArray();
    	for (AlarmGradeOption it : AlarmGradeOption.values()) {
    		options.add(createOption(it.getValue(), it.getKey()));
    	}
    	return options;
    }

    /**
     * 获取日志操作类型
     *
     * @return
     */
    public JSONArray getOpTypeOptions() {
        JSONArray options = new JSONArray();
        for (OpTypeOption it : OpTypeOption.values()) {
            options.add(createOption(it.getValue(), it.getKey()));
        }
        return options;
    }

    /**
     * 资源类型
     *
     * @return
     */
    public JSONArray getResTypeOptions() {
        JSONArray options = new JSONArray();
        for (ResTypeOption it : ResTypeOption.values()) {
            options.add(createOption(it.getValue(), it.getKey()));
        }
        return options;
    }

    private JSONObject createOption(int value, String text) {
        JSONObject option = new JSONObject();
        option.put("value", value);
        option.put("text", text);
        return option;
    }
}
