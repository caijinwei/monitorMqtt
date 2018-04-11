package com.wecon.box.util;

import com.wecon.common.util.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by whp on 2017/8/28.
 */
public class GroupOp {

    /**
     * 根据通讯口ID分组
     * @param cfgList
     * @param filterKeys
     * @return
     */
    public static Map<String, List<Map>> groupCfgFilterByCom(List<Map> cfgList, String... filterKeys){
        if(null == cfgList || cfgList.size() == 0){
            return null;
        }

        return groupCfgByKey("com", cfgList, filterKeys);
    }


    /**
     * 根据机器码分组
     * @param cfgList
     * @param filterKeys
     * @return
     */
    public static Map<String, List<Map>> groupCfgByMachineCode(List<Map> cfgList , String... filterKeys){
        if(null == cfgList || cfgList.size() == 0){
            return null;
        }

        return groupCfgByKey("machine_code", cfgList, filterKeys);
    }

    /**
     * 根据用户名分组
     * @param cfgList
     * @return
     */
    public static Map<String, List<Map>> groupCfgByUserName(List<Map> cfgList){
        if(null == cfgList || cfgList.size() == 0){
            return null;
        }

        return groupCfgByKey("username", cfgList);
    }

    private static Map<String, List<Map>> groupCfgByKey(String byKey, List<Map> cfgList , String... filterKeys){
        Map<String, List<Map>> gCfgMap = new HashMap<String, List<Map>>();
        for(Map m : cfgList){
            Object byKeyOj = m.get(byKey);
            if(CommonUtils.isNullOrEmpty(byKeyOj)) continue;
            List<Map> mCfgLst = gCfgMap.get(byKeyOj);
            if(null == mCfgLst){
                mCfgLst = new ArrayList<>();
            }
            if(filterKeys.length > 0){
                Map mr = new HashMap();
                for (String key : filterKeys){
                    mr.put(key, m.get(key));
                }
                mCfgLst.add(mr);
            }else{
                mCfgLst.add(m);
            }
            gCfgMap.put(byKeyOj.toString(), mCfgLst);
        }

        return gCfgMap;
    }
}
