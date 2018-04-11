/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 * 手机适配设备类型
 *
 * @author zengzhipeng
 */
public enum PadTypeOption {

    PHONE("只支持手机", 1),
    PHONE_PAD("同时支持手机和Pad(完美支持)", 2),
    PAD("只支持Pad", 3);

    public int value;
    public String key;

    PadTypeOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static PadTypeOption valueOf(int value) {
        switch (value) {
            case 1:
                return PHONE;
            case 2:
                return PHONE_PAD;
            case 3:
                return PAD;
            default:
                return PHONE;
        }
    }
}
