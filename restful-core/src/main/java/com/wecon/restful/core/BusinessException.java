package com.wecon.restful.core;

/**
 * 业务异常
 * @author sean
 */
public final class BusinessException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private String msg;
	private int code;

	public BusinessException(String msg, int code)
	{
		this.msg = msg;
		this.code = code;
	}

	public BusinessException(String msg)
	{
		this.msg = msg;
		this.code = 0;
	}

	public BusinessException(String msg, Throwable e)
	{
		super(e);
		this.msg = msg;
		this.code = 0;
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
