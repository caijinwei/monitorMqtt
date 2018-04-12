package com.wecon.box.action.user;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.constant.ConstKey;
import com.wecon.box.entity.Account;

import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import com.wecon.box.util.BoxWebConfigContext;
import com.wecon.box.util.EmailUtil;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zengzhipeng on 2017/8/5.
 */
@Label("修改用户信息")
@RestController
public class ChangeAction extends UserBaseAction {
    @Label("修改密码")
    @RequestMapping("user/chgpwd")
    @WebApi(forceAuth = true, master = true)
    public Output changePwd(@Valid ChgPwdParam param) {
        accountApi.updateAccountPwd(AppContext.getSession().client.userId, param.oldpwd, param.newpwd);
        //<editor-fold desc="操作日志">
        dbLogUtil.addOperateLog(OpTypeOption.ChgPwd, ResTypeOption.Account, AppContext.getSession().client.userId, null);
        //</editor-fold>
        return new Output();
    }

    @Label("忘记密码根据验证码重新修改密码")
    @RequestMapping("user/findpwd")
    @WebApi(forceAuth = false, master = true)
    public Output findPwd(@Valid FindPwdParam param) {
        if (!VerifyUtil.isChinaPhone(param.account) && !VerifyUtil.isValidEmail(param.account)) {
            throw new BusinessException(ErrorCodeOption.PhonenumAndEmailError.key, ErrorCodeOption.PhonenumAndEmailError.value);
        }
        //验证码是否有效
        checkVercode(param.vercode, param.account);
        Account model = accountApi.getAccount(param.account);

        accountApi.updateAccountPwd(model.account_id, "", param.newpwd);
        //<editor-fold desc="操作日志">
        dbLogUtil.addOperateLog(OpTypeOption.FindPwd, ResTypeOption.Account, model.account_id, null);
        //</editor-fold>
        return new Output();
    }

    @Label("更换邮箱")
    @RequestMapping("user/chgemail")
    @WebApi(forceAuth = true, master = true)
    public Output changeEmail(@Valid ChgEmailParam param) {
        if (!VerifyUtil.isValidEmail(param.email)) {
            throw new BusinessException(ErrorCodeOption.EmailError.key, ErrorCodeOption.EmailError.value);
        }
        Client client = AppContext.getSession().client;
        Account user = accountApi.getAccount(client.userId);
        if (user.email != null && user.email.equalsIgnoreCase(param.email)) {
            throw new BusinessException(ErrorCodeOption.EmailIsSame.key, ErrorCodeOption.EmailIsSame.value);
        }
        //发送验证邮箱。等验证过后才更新到db
        String token = UUID.randomUUID().toString().replace("-", "");
        Map<String, String> map = new HashMap<String, String>();
        map.put(token, param.email);
        String redisKey = String.format(ConstKey.REDIS_EMAIL_CHANGE_TOKEN, user.account_id);
        RedisManager.hmset(ConstKey.REDIS_GROUP_NAME, redisKey, map, 3600);
        String url = BoxWebConfigContext.boxWebConfig.getEmailActiveUrl() + "?t=2&u=" + user.account_id + "&token=" + token;
        String content = "<h1>请点击下面链接完成激活操作！</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
        EmailUtil.send(param.email, "邮箱更改激活邮件", content);
        //<editor-fold desc="操作日志">
        Account newUser = accountApi.getAccount(client.userId);
        newUser.email = param.email;
        dbLogUtil.updOperateLog(OpTypeOption.ChgEmail, ResTypeOption.Account, user.account_id, user, newUser);
        //</editor-fold>
        return new Output();
    }

    @Label("更换手机号码")
    @RequestMapping("user/chgphonenum")
    @WebApi(forceAuth = true, master = true)
    public Output changePhonenum(@Valid ChgPhoneParam param) {
        if (!VerifyUtil.isChinaPhone(param.phonenum)) {
            throw new BusinessException(ErrorCodeOption.PhonenumError.key, ErrorCodeOption.PhonenumError.value);
        }
        //验证码是否有效
        checkVercode(param.vercode, param.phonenum);
        Client client = AppContext.getSession().client;
        Account user = accountApi.getAccount(client.userId);
        Account oldUser = accountApi.getAccount(client.userId);
        user.phonenum = param.phonenum;
        accountApi.updateAccountPhone(user);
        JSONObject data = new JSONObject();
        data.put("phonenum", user.phonenum);
        //<editor-fold desc="操作日志">
        dbLogUtil.updOperateLog(OpTypeOption.ChgPhone, ResTypeOption.Account, user.account_id, oldUser, user);
        //</editor-fold>
        return new Output(data);
    }



    /**
     * 验证码是否有效
     *
     * @param vercodeparam
     * @param phonenum
     * @return
     */
    private boolean checkVercode(String vercodeparam, String phonenum) {
        String redisKey = String.format(ConstKey.REDIS_PHONE_SIGNIN_VERCODE, phonenum);
        String vercode = RedisManager.get(ConstKey.REDIS_GROUP_NAME, redisKey);
        if (vercode == null || !vercode.equals(vercodeparam)) {
            throw new BusinessException(ErrorCodeOption.SmsVercodeError.key, ErrorCodeOption.SmsVercodeError.value);
        }
        return true;
    }
}

class ChgPwdParam {
    @Label("旧密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String oldpwd;

    @Label("新密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String newpwd;

    public void setOldpwd(String oldpwd) {
        this.oldpwd = oldpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }
}

class FindPwdParam {
    @Label("手机码、邮箱")
    @NotNull
    public String account;

    @Label("验证码")
    @NotNull
    @Length(max = 6, min = 6)
    public String vercode;

    @Label("新密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String newpwd;

    public void setAccount(String account) {
        this.account = account;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }
}

class ChgEmailParam {
    @Label("邮箱地址")
    @NotNull
    @Length(max = 32, min = 1)
    public String email;

    public void setEmail(String email) {
        this.email = email;
    }
}

class ChgPhoneParam {
    @Label("手机号码")
    @NotNull
    @Length(max = 16, min = 1)
    public String phonenum;

    @Label("短信验证码")
    @NotNull
    public String vercode;

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }
}


