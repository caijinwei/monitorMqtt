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
public enum DisplayStateOption {

    All("所有状态", -1),
    Show("显示", 0),
    Hidden("隐藏", 1),
    Deleted("逻辑删除", 2);

    public int value;
    public String key;

    DisplayStateOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static DisplayStateOption valueOf(int value) {
        switch (value) {
            case -1:
                return All;
            case 0:
                return Show;
            case 1:
                return Hidden;
            case 2:
                return Deleted;
            default:
                return All;
        }
    }
}
