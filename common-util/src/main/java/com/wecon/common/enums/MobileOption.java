package com.wecon.common.enums;

/**
 * 手机平台 Created by fengbing on 2015/12/3.
 */
public enum MobileOption {

    IPHONE("IPHONE", 1),
    MOVE_PHONE("MOVE_PHONE", 2), //功能机
    ANDROID("ANDROID", 4),
    IPAD("IPAD", 7),
    ANDROID_PAD("ANDROID_PAD", 9),
    PC("PC", 255);

    public int value;
    public String key;

    MobileOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static MobileOption valueOf(int value) {
        switch (value) {
            case 1:
                return IPHONE;
            case 2:
                return MOVE_PHONE;
            case 4:
                return ANDROID;
            case 7:
                return IPAD;
            case 9:
                return ANDROID_PAD;
            case 255:
                return PC;
            default:
                return ANDROID;
        }
    }
}
