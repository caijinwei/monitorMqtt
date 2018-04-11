package com.wecon.restful.core;

/**
 * 无权访问
 * @author sean
 */
public final class DeniedException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private String msg;
	private int code;

	public DeniedException(String msg)
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
