package com.wecon.box.action.user;

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
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zengzhipeng on 2017/8/1.
 */
@Label("邮箱地址注册")
@RestController
public class SignupEmailAction extends UserBaseAction {
    @RequestMapping("user/signupemail")
    @WebApi(forceAuth = false, master = true)
    public Output signupByEmail(@Valid SignupEmailParam param) {
        if (!VerifyUtil.isValidUserName(param.username)) {
            throw new BusinessException(ErrorCodeOption.UserNameFormatError.key, ErrorCodeOption.UserNameFormatError.value);
        }
        if (!VerifyUtil.isValidEmail(param.email)) {
            throw new BusinessException(ErrorCodeOption.EmailError.key, ErrorCodeOption.EmailError.value);
        }
        Account user = accountApi.signupByEmail(param.username, param.email, param.password);
        //db状态为未激活
        String token = UUID.randomUUID().toString().replace("-", "");
        String redisKey = String.format(ConstKey.REDIS_EMAIL_SIGNUP_TOKEN, user.account_id);
        RedisManager.set(ConstKey.REDIS_GROUP_NAME, redisKey, token, 3600 * 24 * 7);
        String url = BoxWebConfigContext.boxWebConfig.getEmailActiveUrl() + "?t=1&u=" + user.account_id + "&token=" + token;
        String content = "<h1>请点击下面链接完成激活操作！</h1><h3><a href='" + url + "'>" + url + "</a></h3>";
        EmailUtil.send(param.email, "注册激活邮件", content);

        //<editor-fold desc="操作日志">
        dbLogUtil.addOperateLog(OpTypeOption.SignupEmail, ResTypeOption.Account, user.account_id, user);
        //</editor-fold>

        return new Output();
    }

    /**
     * 激活邮件接口
     *
     * @param param
     * @return
     */
    @RequestMapping("user/signupemailactive")
    @WebApi(forceAuth = false, master = true)
    public Output emailActive(@Valid EmailActiveParam param) {
        if (param.type == 1) {
            //验证token是否有效
            String redisKey = String.format(ConstKey.REDIS_EMAIL_SIGNUP_TOKEN, param.uid);
            String token = RedisManager.get(ConstKey.REDIS_GROUP_NAME, redisKey);
            if (token != null && token.equals(param.token)) {
                //验证成功，修改db状态，直接登录
//                Client client = AppContext.getSession().client;
                Account user = accountApi.getAccount(param.uid);
                Account oldUser = accountApi.getAccount(param.uid);
                user.state = 1;
                accountApi.updateAccountEmail(user);

//                String sid = accountApi.createSession(user, client.appid, client.fuid, client.ip, client.timestamp, ConstKey.SESSION_EXPIRE_TIME);
//                JSONObject data = new JSONObject();
//                data.put("sid", sid);
                //<editor-fold desc="操作日志">
                dbLogUtil.updOperateLog(OpTypeOption.EmailActive, ResTypeOption.Account, user.account_id, oldUser, user);
                //</editor-fold>
                return new Output();
            }
        } else if (param.type == 2) {
            //修改邮箱验证
            String redisKey = String.format(ConstKey.REDIS_EMAIL_CHANGE_TOKEN, param.uid);
            Map<String, String> map = RedisManager.hgetAll(ConstKey.REDIS_GROUP_NAME, redisKey);
            Account user = accountApi.getAccount(param.uid);
            Account oldUser = accountApi.getAccount(param.uid);
            user.email = map.get(param.token);
            accountApi.updateAccountEmail(user);
            //<editor-fold desc="操作日志">

            dbLogUtil.updOperateLog(OpTypeOption.EmailActive, ResTypeOption.Account, user.account_id, oldUser, user);
            //</editor-fold>
            return new Output();
        }
        throw new BusinessException(ErrorCodeOption.EmailActiveError.key, ErrorCodeOption.EmailActiveError.value);
    }
}

class SignupEmailParam {
    @Label("用户名")
    @NotNull
    @Length(max = 32, min = 1)
    public String username;
    @Label("邮箱地址")
    @NotNull
    @Length(max = 32, min = 1)
    public String email;
    @Label("密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class EmailActiveParam {
    /**
     * 1 注册激活，2邮箱变更激活
     */
    @Label("激活类型")
    @NotNull
    public Integer type;

    @Label("帐号Id")
    @NotNull
    public Long uid;

    @Label("token")
    @NotNull
    @Length(max = 32, min = 32)
    public String token;

    public void setType(Integer type) {
        this.type = type;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
