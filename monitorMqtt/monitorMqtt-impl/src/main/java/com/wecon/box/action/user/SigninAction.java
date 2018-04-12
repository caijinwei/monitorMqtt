package com.wecon.box.action.user;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.constant.ConstKey;
import com.wecon.box.entity.Account;
import com.wecon.box.enums.ErrorCodeOption;
import com.wecon.box.enums.OpTypeOption;
import com.wecon.box.enums.ResTypeOption;
import com.wecon.box.param.SigninParam;
import com.wecon.common.redis.RedisManager;
import com.wecon.restful.annotation.WebApi;
import com.wecon.restful.core.AppContext;
import com.wecon.restful.core.BusinessException;
import com.wecon.restful.core.Client;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by zengzhipeng on 2017/8/2.
 */
@Label("登录")
@RestController
public class SigninAction extends UserBaseAction {
    @RequestMapping("user/signin")
    @WebApi(forceAuth = false, master = true)
    public Output signin(@Valid SigninParam param) {
        Client client = AppContext.getSession().client;

        //<editor-fold desc="记录登录次数，如果登录成功就删除记录">
        String redisKey = String.format(ConstKey.REDIS_SIGNIN_ERROR_TIMES, param.alias);
        String times = RedisManager.get(ConstKey.REDIS_GROUP_NAME, redisKey);
        if (times != null) {
            if (Integer.valueOf(times) > 5) {
                throw new BusinessException(ErrorCodeOption.Login_Error_Many_Times.key, ErrorCodeOption.Login_Error_Many_Times.value);
            } else {
                times = String.valueOf(Integer.valueOf(times) + 1);
            }
        } else {
            times = "1";
        }
        RedisManager.set(ConstKey.REDIS_GROUP_NAME, redisKey, times, 1800);//保存半小时

        //</editor-fold>

        Account user = accountApi.getAccount(param.alias);
        if (user == null) {
            throw new BusinessException(ErrorCodeOption.Login_NotAccount.key, ErrorCodeOption.Login_NotAccount.value);
        }
        if (user.state == -1) {
            throw new BusinessException(ErrorCodeOption.Login_NotActive.key, ErrorCodeOption.Login_NotActive.value);
        } else if (user.state == 0) {
            throw new BusinessException(ErrorCodeOption.Login_NotAllow.key, ErrorCodeOption.Login_NotAllow.value);
        }
        String pwd = DigestUtils.md5Hex(param.password + user.secret_key);
        if (!pwd.equals(user.password)) {
            throw new BusinessException(ErrorCodeOption.Login_PwdError.key, ErrorCodeOption.Login_PwdError.value);
        }

        int expire = ConstKey.SESSION_EXPIRE_TIME;
        if (param.isremeber != null && param.isremeber == 1) {
            expire = 3600 * 24 * 30;
        }
        String sid = accountApi.createSession(user, client.appid, client.fuid, client.ip, client.timestamp, expire);
        JSONObject data = new JSONObject();
        data.put("sid", sid);
        data.put("utype", user.type);

        //<editor-fold desc="操作日志">
        dbLogUtil.addOperateLog(OpTypeOption.Signin, ResTypeOption.Account, user.account_id, null);
        //</editor-fold>
        RedisManager.del(ConstKey.REDIS_GROUP_NAME, redisKey);

        return new Output(data);
    }
}

