/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 *
 * @author zengzhipeng
 */
public enum PicRankOption {

    Unknown("未知", 0),
    Quality("优质", 1),
    Sexy("色情露骨", 2);
    
    public int value;
    public String key;

    PicRankOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }
    
    public static PicRankOption valueOf(int value) {
        switch (value) {
            case 1:
                return Quality;
            case 2:
                return Sexy;
            default:
                return Unknown;
        }
    }
}
