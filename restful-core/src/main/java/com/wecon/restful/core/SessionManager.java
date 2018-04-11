package com.wecon.restful.core;

import com.wecon.common.redis.RedisManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * SessionManager
 */
public class SessionManager {
    private static final String RedisGroupName = "session";
    private static final String RedisKey = "UserSession:Guid:%s";
    private static final String UserSidGroupKey = "UserSession:UserID:%s";
    private static final Logger logger = LogManager.getLogger(SessionManager.class);

    /**
     * redis会话保持
     * <p>
     * Time complexity: O(1)
     *
     * @sessionId 会话ID
     * @userInfo 用户实体
     */
    public static boolean persistSession(String sessionId, SessionState.UserInfo userInfo, int seconds) {
        if (userInfo != null) {
            String userSidGroupRedisKey = String.format(UserSidGroupKey, userInfo.getUserID());

            // 先删除旧的sid信息，只让一个客户端在线
            //deleteUserSession(userInfo.getUserID());

            //手机端和网页端可以同时登录，手机端和网页分别只能只有一种设备登录
            //1.根据userid获取所有在线的sessionid
            Set<String> sessionids = RedisManager.smembers(RedisGroupName, userSidGroupRedisKey);
            //2.遍历sessionid。如果productid(对应客记端上传的pid，只有两种，1.pc,2.手机,3.opc)，相同就干掉session
            for (String it : sessionids) {
                byte[] value = RedisManager.get(RedisGroupName, it.getBytes());
                if (value != null) {
                    try {
                        SessionState.UserInfo tmpUserInfo = SessionState.UserInfo.parseFrom(value);
                        if (tmpUserInfo.getProductId() == userInfo.getProductId()) {
                            //测试阶段先注释
                            RedisManager.del(RedisGroupName, it);
                            break;
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }

            // 1. sid -> userID
            String sessionRedisKey = java.lang.String.format(RedisKey, sessionId);
            RedisManager.set(RedisGroupName, sessionRedisKey.getBytes(), userInfo.toByteArray(), seconds);
            // 2. userID - sid集合

            RedisManager.sadd(RedisGroupName, userSidGroupRedisKey, sessionRedisKey);
            return true;
        }
        return false;
    }

    /***
     * 刷新SID缓存时间
     *
     * @param sessionId
     * @param seconds
     * @return
     */
    public static boolean expireSid(String sessionId, int seconds) {
        if (!sessionId.isEmpty()) {
            String sessionRedisKey = java.lang.String.format(RedisKey, sessionId);
            return RedisManager.expire(RedisGroupName, sessionRedisKey, seconds);
        }
        return false;
    }

    /***
     * 删除单个会话信息
     *
     * @param sessionId 会话ID
     * @return
     */
    public static boolean deleteSession(String sessionId) {
        if (sessionId != null && !sessionId.isEmpty()) {
            String sessionRedisKey = java.lang.String.format(RedisKey, sessionId);
            return RedisManager.del(RedisGroupName, sessionRedisKey);
        }
        return false;
    }

    /***
     * 删除所有会话信息
     *
     * @param userId 用户ID
     * @return
     */
    public static boolean deleteUserSession(long userId) {
        if (userId > 0) {
            String userSidGroupRedisKey = String.format(UserSidGroupKey, userId);
            Set<String> sidset = RedisManager.smembers(RedisGroupName, userSidGroupRedisKey);
            if (sidset != null && !sidset.isEmpty()) {
                // 1.删除所有 sid -> user
                RedisManager.del(RedisGroupName, sidset.toArray(new String[sidset.size()]));
            }
            // 2. 删除userID - sid集合
            return RedisManager.del(RedisGroupName, userSidGroupRedisKey);
        }
        return false;
    }

    /**
     * 根据用户会话获取用户信息
     * <p>
     * Time complexity: O(1)
     *
     * @sessionId 会话ID
     */
    public static SessionState.UserInfo getUserInfo(String sessionId) {
        logger.debug("SessionManager getUserInfo,sessionid:" + sessionId);
        if (sessionId != null && !sessionId.isEmpty()) {
            String sessionRedisKey = java.lang.String.format(RedisKey, sessionId);
            byte[] value = RedisManager.get(RedisGroupName, sessionRedisKey.getBytes());
            logger.debug("SessionManager getUserInfo get value");
            if (value != null) {
                try {
                    logger.debug("SessionManager getUserInfo,value.length:" + value.length);
                    return SessionState.UserInfo.parseFrom(value);
                } catch (Exception ex) {
                    logger.error(ex);
                    throw new RuntimeException(ex);
                }
            }
        }
        return null;
    }

    /**
     * 新增当前请求（ip或者用户）的数量,并返回当前总量
     *
     * @param redisKey
     * @param seconds
     * @return
     */
    public static long addCurrentReqCount(String redisKey, int seconds) {
        return RedisManager.incrCount(RedisGroupName, redisKey, seconds);//数量加1
    }
}
