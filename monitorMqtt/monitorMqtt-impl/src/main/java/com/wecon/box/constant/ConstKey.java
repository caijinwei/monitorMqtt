package com.wecon.box.constant;

/**
 * Created by zengzhipeng on 2017/8/3.
 */
public class ConstKey {
	/**
	 * 保存实时数据的key，由machine_code组成
	 */
	public final static String REDIS_PIBOX_ACT_DATA_KEY = "pibox:actdata:%s";

	/**
	 * redis配置名
	 */
	public final static String REDIS_GROUP_NAME = "pibox";

	/**
	 * session默认过期时间 单位秒
	 */
	public final static int SESSION_EXPIRE_TIME = 3600 * 12;

	/**
	 * 文件下载默认过期时间 单位秒
	 */
	public final static int FILEDOWNLOAD_EXPIRE_TIME = 3600;

	/**
	 * 保存激活邮件的token的key,由帐号Id组成，值为随机生成的token,有设置过期时间
	 */
	public final static String REDIS_EMAIL_SIGNUP_TOKEN = "pibox:emailsignuptoken:%s";

	/**
	 * 更新邮件需要的token的key,由帐号Id组成, 保存值为map,由token和新邮箱组成
	 */
	public final static String REDIS_EMAIL_CHANGE_TOKEN = "pibox:emailchgtoken:%s";

	/**
	 * 保存手机验证码的key,由手机号组成，值为随机生成的六位数,有设置过期时间
	 */
	public final static String REDIS_PHONE_SIGNIN_VERCODE = "pibox:phonesignupvercode:%s";

	/**
	 * 保存文件下载的token的key,由文件Id组成，值为随机生成的token,有设置过期时间
	 */
	public final static String REDIS_FILE_DOWNLOAD_TOKEN = "pibox:filedownloadtoken:%s";

	/**
	 * mqtt服务端下发给盒子的主题
	 */
	public final static String MQTT_SERVER_TOPICE = "pibox/stc/%s";

	/**
	 * 保存帐号登陆失败次数，过期时间设置为30分钟，默认失败5次后，需要等待30分钟才能登陆
	 */
	public final static String REDIS_SIGNIN_ERROR_TIMES = "pibox:signinerror:%s";
	/*
	 * redis 提高配置同步的实时性的频道
	 */
	public final static String REDIS_CHANNEL_UPD_DEVICE_CFG = "upd_device_cfg";
}
