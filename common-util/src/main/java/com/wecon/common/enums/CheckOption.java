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
public enum CheckOption {

    All("所有状态", 15),
    NoCheck("草稿箱", 1),
    RefuseCheck("审核未通过", 2),
    SubmitCheck("待审核", 4),
    Checked("已审核通过并且上架", 8),
    ReSubmitCheck("上架修改后重新提交审核中", 16);

    public int value;
    public String key;

    CheckOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }
    
    public static CheckOption valueOf(int value) {
        switch (value) {
            case 15:
                return All;
            case 1:
                return NoCheck;
            case 2:
                return RefuseCheck;
            case 4:
                return SubmitCheck;
            case 8:
                return Checked;
            case 16:
                return ReSubmitCheck;
            default:
                return All;
        }
    }
    
}
