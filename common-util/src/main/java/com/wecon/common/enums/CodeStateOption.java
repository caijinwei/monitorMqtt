package com.wecon.common.enums;

/**
 * 接口业务返回状态码定义
 */
public enum CodeStateOption {
    Common_Success("成功", 200),
    Common_ParamIllegal("非法参数值", 400),
    Common_SessionIdInvalid("SessionID(用户的会话标识)无效", 403),
    Common_ServerException("未知的服务器错误", 503),

    UserCenter_Login_NotAccount("该账号不存在", 1000),
    UserCenter_Login_PwdError("密码错误", 1001),
    UserCenter_Login_OperationFrequent("获取验证码操作频繁", 1003),

    UserCenter_Register_AccountExisted("用户名已经被注册", 1004),

    UserCenter_SignCode_GetError("获取验证码错误", 1005),
    UserCenter_SignCode_GetNumberOut("获取验证码超出限制次数", 1006),

    Parameter_PhoneNumberError("手机号不合法", 1007),

    UserCenter_Login_OldPwdError("旧密码错误", 1008),

    UserCenter_Bind_AccountHasBeenBoundError("该用户名已经被绑定", 1009),
    UserCenter_Bind_MobileHasBeenBoundError("该账号已经绑定手机", 1010),
    UserCenter_Bind_SocialChannelHasBeenBoundError("你已经绑定该社交渠道", 1011),

    UserCenter_Bind_MobileExistsError("手机已经被绑定", 1012),
    UserCenter_Bind_SocialAccountExistsError("社交账号已经被绑定", 1013),

    Parameter_EmailErorr("邮箱地址不合法", 1100),

    VersionUpdate_IsNewest("已经是最新版本", 1200);

    public int value;
    public String key;

    CodeStateOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

	/*
    static CodeStateOption valueOf(int value) {

		switch (value) {
			case 1000:
				return NotAccount;
			case 1001:
				return PwdError;
			case 1002:
				return NetworkFailure;
			case 1003:
				return OperationFrequent;
			case 1004:
				return AccountBeing;
			case 1005:
				return SignCodeError;
			case 1006:
				return SignCodeNumberOut;
			case 1007:
				return PhoneNumberError;
			default:
				return  NotAccount;
		}
	}*/
}
