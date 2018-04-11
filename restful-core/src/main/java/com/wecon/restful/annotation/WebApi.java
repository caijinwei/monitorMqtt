package com.wecon.restful.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * restful 接口配置
 * @author sean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WebApi
{
	/**
	 * 是否强制登录认证， 默认false
	 * ps: 
	 * true表示强制对客户端进行认证，认证成功初始化用户上下文，认证失败返回Denied
	 * false表示对客户端进行弱认证，认证成功初始化用户上下文信息， 认证失败继续执行业务方法
	 * @return
	 */
	boolean forceAuth() default false;

	/**
	 * 数据库读写分离， 默认true
	 * ps:
	 * true表示该接口的数据访问全部导向master
	 * false表示该接口的数据访问全部导向slave集群
	 * @return
	 */
	boolean master() default true;

	/**
	 * 接口权限， 一旦绑定了权限系统的权限， 用户就必须有权限才可访问该接口
	 * ps:
	 * 字符串数组， 没个元素表示权限系统的权限id, 默认表示没有权限限制
	 * 否则返回restful 403
	 * @return
	 */
	String[] authority() default {};

	/**
	 * 是否跳过签名
	 */
	boolean skipSign() default false;
}
