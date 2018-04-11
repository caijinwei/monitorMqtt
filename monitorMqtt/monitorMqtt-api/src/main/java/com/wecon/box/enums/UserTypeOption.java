package com.wecon.box.enums;

/**
 * Created by zengzp on 2017/7/18.
 */
public enum UserTypeOption {
    Host("超管", 0),
    AdminUser("管理者帐号", 1),
    ViewUser("视图帐号", 2);

    public int value;

    public String key;

    UserTypeOption(final String _key,final int _value) {
        this.key = _key;
        this.value = _value;
    }
}
