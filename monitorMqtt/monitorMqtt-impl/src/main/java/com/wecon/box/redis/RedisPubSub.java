package com.wecon.box.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by whp on 2017/9/12.
 */
public class RedisPubSub {
    private JedisPool jedisPool;
    private JedisPubSub jListener;
    private static final Logger logger = LogManager.getLogger(RedisPubSub.class.getName());

    public RedisPubSub(String host){
        jedisPool = new JedisPool(new JedisPoolConfig(), host);
    }

    public RedisPubSub(String host, int port){
        jedisPool = new JedisPool(new JedisPoolConfig(), host, port);
    }

    /**
     * 发布消息
     * @param channel
     * @param msg
     */
    public void publish(String channel, String msg){
        Jedis jedis = jedisPool.getResource();
        jedis.publish(channel, msg);
        logger.debug("channel:" + channel + "  publish message :" + msg);
    }

    /**
     * 订阅消息
     * @param listener
     * @param channel
     */
    public void subscribe(final JedisPubSub listener, final String... channel){
        this.jListener = listener;
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                Jedis jedis = jedisPool.getResource();
                jedis.subscribe(jListener, channel);
                logger.debug("subscribe channel:" + channel);
            }
        });
    }

    /**
     * 取消订阅
     */
    public void unsubscribe(){
        jListener.unsubscribe();
    }
}
