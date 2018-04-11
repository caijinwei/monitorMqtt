package com.wecon.common.test;

import com.wecon.common.util.TimeUtil;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.loader.CacheLoader;

import java.util.Collection;
import java.util.Map;

/**
 * Created by fengbing on 2015/12/8.
 */
public class TestCacheLoader implements CacheLoader
{

    @Override
    public Object load(Object o) throws CacheException
    {
        return String.valueOf(TimeUtil.getYYYYMMDDHHMMSSTime());
    }

    @Override
    public Map loadAll(Collection collection)
    {
        return null;
    }

    @Override
    public Object load(Object o, Object o1)
    {
        return null;
    }

    @Override
    public Map loadAll(Collection collection, Object o)
    {
        return null;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public CacheLoader clone(Ehcache ehcache) throws CloneNotSupportedException
    {
        return null;
    }

    @Override
    public void init()
    {

    }

    @Override
    public void dispose() throws CacheException
    {

    }

    @Override
    public Status getStatus()
    {
        return null;
    }
}
