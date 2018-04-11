package com.wecon.restful.core;

/**
 * 签名错误
 * @author sean
 */
public final class SignErrorException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private String msg;
	private int code;

	public SignErrorException(String msg, int code)
	{
		this.msg = msg;
		this.code = code;
	}

	public SignErrorException(String msg)
	{
		this.msg = msg;
		this.code = 0;
	}

	public SignErrorException(String msg, Throwable e)
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
