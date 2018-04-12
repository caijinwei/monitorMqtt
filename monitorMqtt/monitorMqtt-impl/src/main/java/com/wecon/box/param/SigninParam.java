package com.wecon.box.param;

import com.wecon.restful.doc.Label;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by win7 on 2017/11/29.
 */
public class SigninParam {
    @Label("用户名、邮箱、电话")
    @NotNull
    @Length(max = 32, min = 1)
    public String alias;
    @Label("密码md5")
    @NotNull
    @Length(max = 32, min = 32)
    public String password;
    @Label("是否记住登录状态30天")
    @Range(min = 0, max = 1)
    public Integer isremeber;

    public void setIsremeber(Integer isremeber) {
        this.isremeber = isremeber;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
