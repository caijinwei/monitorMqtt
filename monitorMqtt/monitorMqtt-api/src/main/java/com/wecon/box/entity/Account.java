package com.wecon.box.entity;

import java.sql.Timestamp;

/**
 * Created by zengzhipeng on 2017/7/31.
 */
public class Account {
    public long account_id;
    public String username;
    public String password;
    public String phonenum;
    public String email;
    public Timestamp create_date;
    /**
     * 帐号类型 0：超级管理员1：管理者帐号  2：视图帐号
     */
    public int type;
    /**
     * 状态 1：正常 0：禁止登录 -1:邮箱注册未激活
     */
    public int state;
    public Timestamp update_date;
    public String secret_key;
}
