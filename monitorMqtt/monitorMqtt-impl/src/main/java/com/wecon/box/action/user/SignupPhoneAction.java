package com.wecon.box.action.user;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.constant.ConstKey;
import com.wecon.box.entity.Account;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import com.wecon.box.util.EmailUtil;
import com.wecon.box.util.SmsUtil;
import com.wecon.box.util.VerifyUtil;
import com.wecon.common.redis.RedisManager;
import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.core.Client;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by zengzhipeng on 2017/8/15.
 */
@Label("手机号码注册")
@RestController
public class SignupPhoneAction extends UserBaseAction {
    /**
     * 发送验证码 -- 将邮箱的验证码也整合到此接口 20170911
     *
     * @return
     */
    @RequestMapping("user/sendvercode")
    @WebApi(forceAuth = false, master = true)
    public Output sendVercode(@RequestParam("phonenum") String phonenum) throws Exception {
        if (!VerifyUtil.isChinaPhone(phonenum) && !VerifyUtil.isValidEmail(phonenum)) {
            throw new BusinessException(ErrorCodeOption.PhonenumAndEmailError.key, ErrorCodeOption.PhonenumAndEmailError.value);
        }
        //生成短信验证码
        int vercode = (int) ((Math.random() * 9 + 1) * 100000);
        //redis上如果有值，不重复发
        String redisKey = String.format(ConstKey.REDIS_PHONE_SIGNIN_VERCODE, phonenum);
        String vercodeRedis = RedisManager.get(ConstKey.REDIS_GROUP_NAME, redisKey);
        if (vercodeRedis == null) {
            //保存到redis
            RedisManager.set(ConstKey.REDIS_GROUP_NAME, redisKey, String.valueOf(vercode), 60 * 3);//验证码保存3分钟
            if (VerifyUtil.isChinaPhone(phonenum)) {
                SmsUtil.sendSMS(phonenum, String.valueOf(vercode));
            } else if (VerifyUtil.isValidEmail(phonenum)) {
                //发送到邮箱
                EmailUtil.send(phonenum, "MQTT-Mosquitto - 验证码", String.valueOf(vercode));
            }
        }

        return new Output();
    }

    /**
     * 手机号码注册
     *
     * @param param
     * @return
     */
    @RequestMapping("user/signupphone")
    @WebApi(forceAuth = false, master = true)
    public Output signupPhone(@Valid SignupPhoneParam param) {
        if (!VerifyUtil.isValidUserName(param.username)) {
            throw new BusinessException(ErrorCodeOption.UserNameFormatError.key, ErrorCodeOption.UserNameFormatError.value);
        }
        if (!VerifyUtil.isChinaPhone(param.phonenum)) {
            throw new BusinessException(ErrorCodeOption.PhonenumError.key, ErrorCodeOption.PhonenumError.value);
        }
        //验证码是否有效
        String redisKey = String.format(ConstKey.REDIS_PHONE_SIGNIN_VERCODE, param.phonenum);
        String vercode = RedisManager.get(ConstKey.REDIS_GROUP_NAME, redisKey);
        if (vercode == null || !vercode.equals(param.vercode)) {
            throw new BusinessException(ErrorCodeOption.SmsVercodeError.key, ErrorCodeOption.SmsVercodeError.value);
        }
        Account user = accountApi.signupByPhone(param.username, param.phonenum, param.password);
        //直接登录
        Client client = AppContext.getSession().client;
        String sid = accountApi.createSession(user, client.appid, client.fuid, client.ip, client.timestamp, ConstKey.SESSION_EXPIRE_TIME);
        JSONObject data = new JSONObject();
        data.put("sid", sid);
        //<editor-fold desc="操作日志">
        dbLogUtil.addOperateLog(OpTypeOption.SignupPhone, ResTypeOption.Account, user.account_id, user);
        //</editor-fold>
        return new Output(data);
    }
}

class SignupPhoneParam {
    @Label("用户名")
    @NotNull
    @Length(max = 32, min = 1)
    public String username;
    @Label("手机号码")
    @NotNull
    @Length(max = 16, min = 1)
    public String phonenum;
    @Label("密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String password;
    @Label("短信验证码")
    @NotNull
    public String vercode;

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
