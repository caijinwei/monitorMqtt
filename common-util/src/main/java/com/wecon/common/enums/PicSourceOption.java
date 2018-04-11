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
public enum PicSourceOption {

    Unknown("未知", 0),
    Editor("编辑上传", 101),
    SpiderOgq("爬虫抓取(OGQ)", 201),
    SpiderFlickr("爬虫抓取(Flickr)", 202),
    Spider500Px("爬虫抓取(500px)", 203),
    SpiderIlike("爬虫抓取(Ilike)", 204),
    SpiderPexels("爬虫抓取(Pexels)", 205),
    DesktopImport("桌面导入", 301);

    public int value;
    public String key;

    PicSourceOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static PicSourceOption valueOf(int value) {
        switch (value) {
            case 101:
                return Editor;
            case 201:
                return SpiderOgq;
            case 202:
                return SpiderFlickr;
            case 203:
                return Spider500Px;
            case 204:
                return SpiderIlike;
            case 205:
                return SpiderPexels;
            case 301:
                return DesktopImport;
            default:
                return Unknown;
        }
    }
}
