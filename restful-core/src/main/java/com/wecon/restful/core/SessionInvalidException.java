package com.wecon.restful.core;

/**
 * session无效
 * @author sean
 */
public final class SessionInvalidException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private String msg;
	private int code;

	public SessionInvalidException(String msg, int code)
	{
		this.msg = msg;
		this.code = code;
	}

	public SessionInvalidException(String msg)
	{
		this.msg = msg;
		this.code = 0;
	}

	public SessionInvalidException(String msg, Throwable e)
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
