package com.wecon.restful.authcompany;

import com.wecon.restful.core.RequestLimitCfg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zengzhipeng on 2017/11/29.
 */
public class AuthComHelper {
    private static Map<Integer, String> comMap = new HashMap<>();
    private static String proxyHost;

    public static void setProxyHost(String proxyHost) {
        AuthComHelper.proxyHost = proxyHost;
    }

    public static void setComMap(Map<Integer, String> comMap) {
        AuthComHelper.comMap = comMap;
    }

    public static boolean isRight(Integer comid, String compvtkey) {
        //未配置comid和compvtkey的项目可不做验证
        if (comMap.size() == 0) {
            return true;
        }
        if (comMap.containsKey(comid) && comMap.get(comid).equals(compvtkey)) {
            //同comid/compvtkey 请求频率限制
            RequestLimitCfg.comLimit(comid, compvtkey);
            return true;
        } else {
            return false;
        }
    }
}
