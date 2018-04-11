package com.wecon.restful.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author sean
 */
@ControllerAdvice
public class SpringExceptionHandler
{
	private static final Logger logger = LogManager.getLogger(SpringExceptionHandler.class);

	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public Object business(Throwable ex)
	{
		Output output = new Output();

		// 业务异常
		if (ex instanceof BusinessException)
		{
			BusinessException be = (BusinessException) ex;
			output.code = be.getCode();
			output.msg = be.getMessage();
		}
		// 参数异常
		else if (ex instanceof BindException)
		{
			BindException be = (BindException) ex;
			output.code = 400;
			output.msg = be.getMessage();
			be.printStackTrace();
		}
		// 签名错误
		else if (ex instanceof SignErrorException)
		{
			SignErrorException be = (SignErrorException) ex;
			output.code = 400;
			output.msg = be.getMessage();
			be.printStackTrace();
		}
		// session过期
		else if (ex instanceof SessionInvalidException)
		{
			SessionInvalidException be = (SessionInvalidException) ex;
			output.code = 403;
			output.msg = be.getMessage();
		}
		// 无权访问
		else if (ex instanceof DeniedException)
		{
			DeniedException be = (DeniedException) ex;
			output.code = 405;
			output.msg = be.getMessage();
		}
		// 请求太频繁
		else if (ex instanceof RequestLimitException)
		{
			RequestLimitException be = (RequestLimitException) ex;
			output.code = 406;
			output.msg = be.getMessage();
		}
		// 系统异常
		else
		{
			ex.printStackTrace();
			output.code = 503;
			output.msg = "系统异常";

			// 生产和生产测试告警日志
			if (RestfulContextListener.isServer())
			{
				logger.error("系统异常503", ex);
			}
		}
		return output;
	}
}