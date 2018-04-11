/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 * 壁纸标签类别 
 * @author zengzhipeng
 */
public enum PicTagPropertyOption {

    Unknown("未定义", 0),
    Collection("合辑", 1),
    UserTag("用户标签", 2);

    public int value;
    public String key;

    PicTagPropertyOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static PicTagPropertyOption valueOf(int value) {
        switch (value) {
            case 1:
                return Collection;
            case 2:
                return UserTag;
            default:
                return Unknown;
        }
    }
}
