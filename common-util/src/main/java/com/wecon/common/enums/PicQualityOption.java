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
public enum PicQualityOption {

    Qualified("合格", 0),
    Good("良好", 10),
    Boutique("精品", 20);

    public int value;
    public String key;

    PicQualityOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static PicQualityOption valueOf(int value) {
        switch (value) {
            case 10:
                return Good;
            case 20:
                return Boutique;
            default:
                return Qualified;
        }
    }
}
