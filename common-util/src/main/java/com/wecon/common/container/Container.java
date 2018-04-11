package com.wecon.common.container;

/**
 * Created by linkaixun on 2015/12/7.
 */
public class Container<T1, T2>
{
    public T1 V1;
    public T2 V2;

    public Container()
    {
    }

    public Container(T1 v1, T2 v2)
    {
        V1 = v1;
        V2 = v2;
    }
}
