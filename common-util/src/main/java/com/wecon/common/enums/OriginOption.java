/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 * 软件来源枚举
 *
 * @author zengzhipeng
 */
public enum OriginOption {

    Unknown("未知", 0),
    Editor("编辑上传", 1),
    MoboSync("Mobo同步", 2),
    SpiderGp("Google Play抓取", 3);

    public int value;
    public String key;

    OriginOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }
    
    public static OriginOption valueOf(int value) {
        switch (value) {
            case 1:
                return Editor;
            case 2:
                return MoboSync;
            case 3:
                return SpiderGp;
            default:
                return Unknown;
        }
    }
}
