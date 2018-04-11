package com.wecon.restful.authotiry;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.TransactionalMode;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by zengzhipeng
 * sean modified
 */
public class CacheUtil
{
    private static final Logger logger =  LogManager.getLogger(CacheUtil.class.getName());
    private static CacheManager manager = CacheManager.create();
    private final static int timeToIdleSeconds = 0;

    public static void put(String cacheName, String key, Serializable value)
    {
        put(cacheName, key, value, 0);
    }

    public static void put(String cacheName, String key, Serializable value, int timeToLiveSeconds)
    {
        Cache cache = getCache(cacheName);
        if (timeToLiveSeconds <= 0)
        {
            cache.put(new Element(key, value));
        }
        else
        {
            cache.put(new Element(key, value, false, timeToIdleSeconds, timeToLiveSeconds));
        }
    }

    public static boolean isExists(String cacheName, String key)
    {
        Object obj = get(cacheName, key);
        return obj != null;
    }

    public static Object get(String cacheName, String key)
    {
        Cache cache = getCache(cacheName);
        Element element = cache.get(key);
        if (element != null)
        {
            return element.getObjectValue();
        }
        return null;
    }

    /**
     * 移除一个缓存的key
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static boolean remove(String cacheName, String key)
    {
        Cache cache = getCache(cacheName);
        return cache.remove(key);
    }

    public static final Cache getCache(String cacheName)
    {
        Cache cache = manager.getCache(cacheName);
        if (cache == null)
        {
            CacheConfiguration cfg = new CacheConfiguration();
            cfg.name(cacheName);
            cfg.maxEntriesLocalHeap(3000);
            cfg.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU);
            cfg.eternal(false);
            cfg.timeToIdleSeconds(60 * 20);
            cfg.timeToLiveSeconds(60 * 60);
            cfg.overflowToOffHeap(false);
            cfg.statistics(false);
            cfg.transactionalMode(TransactionalMode.OFF);
            cfg.timeToIdleSeconds(timeToIdleSeconds);

            cache = new Cache(cfg);
            manager.addCache(cache);

            logger.warn("缓存的名称:" + cacheName + "不存在, 创建默认配置缓存, 如需配置请在classpath添加ehcache.xml");
        }
        return cache;
    }
}
