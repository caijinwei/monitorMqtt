package com.wecon.common.redis;

import com.wecon.common.util.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis管理
 */
public class RedisManager {
    private static Map<String, RedisConfig> redisConfig;
    private static Map<String, JedisPool> redisPool = new HashMap<>();

    /**
     * 获取redis链接
     *
     * @param group redis别名
     * @return
     */
    private static synchronized Jedis getJedis(String group) {
        JedisPool pool = redisPool.get(group);
        if (pool == null) {
            RedisConfig cfg = redisConfig.get(group);
            if (cfg == null) {
                throw new RuntimeException("redis配置" + group + "不存在");
            }

            JedisPoolConfig poolconfig = new JedisPoolConfig();
            //最大连接数，-1表示不限
            poolconfig.setMaxTotal(-1);
            //最大空闲连接数
            poolconfig.setMaxIdle(200);
            poolconfig.setMaxWaitMillis(100000L);
            poolconfig.setTestOnBorrow(true);

            if (StringUtil.isNullOrEmpty(cfg.getPassword())) {
                pool = new JedisPool(poolconfig, cfg.getHost(), Integer.parseInt(cfg.getPort()));
            } else {
                pool = new JedisPool(poolconfig, cfg.getHost(), Integer.parseInt(cfg.getPort()), 0, cfg.getPassword());
            }

            redisPool.put(group, pool);
        }
        return pool.getResource();
    }

    /**
     * 关闭jedis
     *
     * @param jedis
     */
    private static void returnJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public void setRedisConfig(Map<String, RedisConfig> redisConfig) {
        RedisManager.redisConfig = redisConfig;
    }

    public static List<byte[]> mget(String group, byte[]... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        Jedis jedis = getJedis(group);
        try {
            return jedis.mget(keys);
        } finally {
            returnJedis(jedis);
        }
    }


    public static String get(String group, String key) {
        String value = null;
        if (key == null || key.isEmpty()) {
            return value;
        }
        Jedis jedis = getJedis(group);
        try {
            return jedis.get(key);
        } finally {
            returnJedis(jedis);
        }
    }

    public static byte[] get(String group, byte[] key) {
        byte[] value = null;
        if (key == null) {
            return value;
        }
        Jedis jedis = getJedis(group);
        try {
            return jedis.get(key);
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean set(String group, String key, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.set(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean set(String group, byte[] key, byte[] value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.set(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean del(String group, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.del(key);
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean del(String group, String... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.del(keys);
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean rpush(String group, byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.rpush(key, value);
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean lpush(String group, byte[] key, int seconds, byte[]... value) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.lpush(key, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static List<String> lrange(String group, String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.lrange(key, start, end);
        } finally {
            returnJedis(jedis);
        }
    }


    public static byte[] lpop(String group, byte[] key) {
        // byte[] value = null;
        if (key == null) {
            return null;
        }

        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.lpop(key);
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean incr(String group, String key, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.incr(key);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 获取key的累计数，主要用于统计一段时间内相同key的数量
     *
     * @param group
     * @param key
     * @param seconds
     * @return
     */
    public static long incrCount(String group, String key, int seconds) {
        long count = 0L;
        Jedis jedis = null;
        jedis = getJedis(group);
        count = jedis.incr(key);
        // 如果该key不存在，则从0开始计算，并且当count为1的时候，设置过期时间
        if (count == 1 && seconds > 0) {
            jedis.expire(key, seconds);
        }
        return count;
    }

    public static boolean expire(String group, String key, int seconds) {
        Jedis jedis = null;
        try {
            if (seconds > 0) {
                jedis = getJedis(group);
                int remainingTime = Integer.valueOf(jedis.ttl(key).toString());
                jedis.expire(key, seconds + remainingTime);
            }
            return true;
        } finally {
            returnJedis(jedis);
        }
    }

    public static boolean sismember(String group, String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.sismember(key, member);
        } finally {
            returnJedis(jedis);
        }
    }

    public static long sadd(String group, String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.sadd(key, members);
        } finally {
            returnJedis(jedis);
        }
    }

    public static long srem(String group, String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.srem(key, members);
        } finally {
            returnJedis(jedis);
        }
    }

    //返回集合key中的所有成员
    public static Set<String> smembers(String group, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.smembers(key);
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 为哈希表 key 中的域 field 的值加上增量 increment 。
     * 增量也可以为负数，相当于对给定域进行减法操作。
     *
     * @param group
     * @param key
     * @param field
     * @param value
     * @param seconds
     * @return
     */
    public static boolean hincrby(String group, String key, String field, long value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.hincrBy(key, field, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return true;
        } finally {
            returnJedis(jedis);
        }
    }


    public static Long hset(String group, String key, String field, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            long redisValue = jedis.hset(key, field, value);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return redisValue;
        } finally {
            returnJedis(jedis);
        }
    }

    public static String hmset(String group, String key, Map<String, String> hash, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            String redisValue = jedis.hmset(key, hash);
            if (seconds > 0) {
                jedis.expire(key, seconds);
            }
            return redisValue;
        } finally {
            returnJedis(jedis);
        }
    }

    public static Long hdel(String group, String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.hdel(key, field);
        } finally {
            returnJedis(jedis);
        }
    }

    public static String hget(String group, String key, String field) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.hget(key, field);
        } finally {
            returnJedis(jedis);
        }
    }

    public static Map<String, String> hgetAll(String group, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.hgetAll(key);
        } finally {
            returnJedis(jedis);
        }
    }

    public static List<String> hmget(String group, String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.hmget(key, field);
        } finally {
            returnJedis(jedis);
        }
    }

    public static Set<String> hkeys(String group, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.hkeys(key);
        } finally {
            returnJedis(jedis);
        }
    }

    public static long zadd(String group, String key, String member, double score) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.zadd(key, score, member);
        } finally {
            returnJedis(jedis);
        }
    }

    public static long zrem(String group, String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.zrem(key, member);
        } finally {
            returnJedis(jedis);
        }
    }

    public static Set<String> zrange(String group, String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.zrange(key, start, end);
        } finally {
            returnJedis(jedis);
        }
    }

    public static Set<String> zrevrange(String group, String key, int start, int end) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.zrevrange(key, start, end);
        } finally {
            returnJedis(jedis);
        }
    }

    public static Long zrank(String group, String key, String member) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            return jedis.zrank(key, member);
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 订阅频道
     *
     * @param group
     * @param jedisPubSub
     * @param channel
     */
    public static void subscribe(String group, JedisPubSub jedisPubSub, String[] channel) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);

            jedis.subscribe(jedisPubSub, channel);
        } finally {
            returnJedis(jedis);
        }
    }

    /**
     * 发布订阅消息
     *
     * @param group
     * @param channel
     * @param message
     */
    public static void publish(String group, String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = getJedis(group);
            jedis.publish(channel, message);
        } finally {
            returnJedis(jedis);
        }
    }


    public static long ttl(String group, String key) {
        Jedis jedis = null;
        try {
            long time = 0;

            jedis = getJedis(group);
            time = jedis.ttl(key);

            return time;
        } finally {
            returnJedis(jedis);
        }
    }
}
