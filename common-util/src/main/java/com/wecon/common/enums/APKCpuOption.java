/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wecon.common.enums;

/**
 * APK软件包支持的cpu类型枚举
 *
 * @author zengzhipeng
 */
public enum APKCpuOption {

    Unknown("未知", 0),
    General("通用", 1),
    Armeabi("armeabi", 2),
    Armeabi_v7a("armeabi_v7a", 4),
    Mips("mips", 8),
    X86("x86", 16);

    public int value;
    public String key;

    APKCpuOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static APKCpuOption valueOf(int value) {
        switch (value) {
            case 0:
                return Unknown;
            case 1:
                return General;
            case 2:
                return Armeabi;
            case 4:
                return Armeabi_v7a;
            case 8:
                return Mips;
            case 16:
                return X86;
            default:
                return Unknown;
        }
    }
    
    public static APKCpuOption keyOf(String key) {
        switch (key) {
            case "未知":
                return Unknown;
            case "通用":
                return General;
            case "armeabi":
                return Armeabi;
            case "armeabi_v7a":
                return Armeabi_v7a;
            case "mips":
                return Mips;
            case "x86":
                return X86;
            default:
                return Unknown;
        }
    }
}
