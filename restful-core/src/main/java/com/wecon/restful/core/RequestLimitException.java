package com.wecon.restful.core;

/**
 * Created by zengzhipeng on 2018/1/31.
 */
public class RequestLimitException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;
    private int code;

    public RequestLimitException(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String getMessage()
    {
        return msg;
    }

    public int getCode()
    {
        return code;
    }
}
