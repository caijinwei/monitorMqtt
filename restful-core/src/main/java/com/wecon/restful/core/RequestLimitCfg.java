package com.wecon.restful.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 接口请求频率限制配置
 * Created by zengzhipeng on 2018/1/30.
 */
public class RequestLimitCfg {
    private static final Logger logger = LogManager.getLogger(RequestLimitCfg.class);
    /**
     * 时间范围，单位秒 （如10s内同一个用户最多请求N次）
     */
    private static Integer timeRange;

    /**
     * 同一用户请求限制数,-1表示没有限制
     */
    private static Long reqUserLimitNum;

    /**
     * 同一ip请求限制数,-1表示没有限制
     */
    private static Long reqIpLimitNum;

    /**
     * 同一comid/compvtkey请求限制数,-1表示没有限制
     */
    private static Long reqComLimitNum;

    public static Integer getTimeRange() {
        if (timeRange == null) {
            return 10;//默认10秒
        }
        return timeRange;
    }

    public static void setTimeRange(Integer timeRange) {
        RequestLimitCfg.timeRange = timeRange;
    }

    public static Long getReqUserLimitNum() {
        return reqUserLimitNum;
    }

    public static void setReqUserLimitNum(Long reqUserLimitNum) {
        RequestLimitCfg.reqUserLimitNum = reqUserLimitNum;
    }

    public static Long getReqIpLimitNum() {
        return reqIpLimitNum;
    }

    public static void setReqIpLimitNum(Long reqIpLimitNum) {
        RequestLimitCfg.reqIpLimitNum = reqIpLimitNum;
    }

    public static Long getReqComLimitNum() {
        return reqComLimitNum;
    }

    public static void setReqComLimitNum(Long reqComLimitNum) {
        RequestLimitCfg.reqComLimitNum = reqComLimitNum;
    }

    /**
     * 限制同一ip的请求频率
     *
     * @param ip
     */
    public static void ipLimit(String ip) {
        if (reqIpLimitNum == null) {
            return;
        }
        if (reqIpLimitNum > -1) {
            String redisKey = "reqLimitIp:" + ip;
            long count = SessionManager.addCurrentReqCount(redisKey, timeRange);
            // 如果redis中的count大于限制的次数，则报错
            if (count > reqIpLimitNum) {
                logger.info("单用户IP[" + ip + "]在[" + timeRange + "]秒内,请求次数[" + count + "]超过了限定的次数[" + reqIpLimitNum + "]");
                throw new RequestLimitException("the same ip request too many times");
            }
        }
    }

    /**
     * 限制同一comid/compvtkey的请求频率
     *
     * @param comid
     * @param compvtkey
     */
    public static void comLimit(Integer comid, String compvtkey) {
        if (reqComLimitNum == null) {
            return;
        }
        if (reqComLimitNum > -1) {
            String redisKey = "reqLimitCom:" + comid + "-" + compvtkey;
            long count = SessionManager.addCurrentReqCount(redisKey, timeRange);
            // 如果redis中的count大于限制的次数，则报错
            if (count > reqComLimitNum) {
                logger.info("单开发者ComId[" + comid + "]ComPvtKey[" + compvtkey + "]在[" + timeRange + "]秒内,请求次数[" + count + "]超过了限定的次数[" + reqComLimitNum + "]");
                throw new RequestLimitException("the same developer request too many times");
            }
        }
    }

    /**
     * 限制同一用户的请求频率
     * @param userId
     */
    public static void userLimit(long userId){
        if (reqUserLimitNum == null) {
            return;
        }
        if (reqUserLimitNum > -1) {
            String redisKey = "reqLimitUser:" + userId;
            long count = SessionManager.addCurrentReqCount(redisKey, timeRange);
            // 如果redis中的count大于限制的次数，则报错
            if (count > reqUserLimitNum) {
                logger.info("单用户UserId[" + userId + "]在[" + timeRange + "]秒内,请求次数[" + count + "]超过了限定的次数[" + reqUserLimitNum + "]");
                throw new RequestLimitException("the same user request too many times");
            }
        }
    }
}
