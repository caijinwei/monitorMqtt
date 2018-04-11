/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 *
 * @author locky.xu
 */
public enum RingSourceOption {

    Unknown("未知", 0),
    Editor("编辑上传", 101),
    User("用户上传", 102),
    SpiderFreetone("爬虫抓取(freetone)", 201),
    SpiderPagal("爬虫抓取(pagalworld)", 202);

    public int value;
    public String key;

    RingSourceOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static RingSourceOption valueOf(int value) {
        switch (value) {
            case 101:
                return Editor;
            case 102:
                return User;
            case 201:
                return SpiderFreetone;
            case 202:
                return SpiderPagal;
            default:
                return Unknown;
        }
    }
}
