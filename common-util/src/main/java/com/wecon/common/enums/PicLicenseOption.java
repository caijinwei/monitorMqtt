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
public enum PicLicenseOption {

    Unknown("未知", 0),
    //可商用版权
    PublicDomainWork("公共类型-DW", 101),
    PublicDomainDedication("公共类型-DD", 102),
    Attribution("署名", 201),
    AttributionShareAlike("署名-相同方式共享", 202),
    AttributionNoDerivatives("署名-禁止演绎", 203),
    CopyrightGOtrip("版权所有不得转载", 301),
    //版权限制
    CopyrightLimitAllReserved("对方拥有版权,仅上架到第三方渠道", 1101),
    CopyrightLimitCopyNc("对方拥有版权,未经授权不得用于盈利性目的", 1102),
    CopyrightLimitCopyPk("对方拥有版权", 1103),
    CopyrightLimitCcNc("非商业用途,不得用于盈利性目的", 1201),
    CopyrightLimitCcNcNd("非商业用途,不得用于盈利性目的,禁止演绎", 1202),
    CopyrightLimitCcNdSa("禁止演绎", 1203),
    CopyrightLimitNotCopyright("抓取来源没有标注版权信息", 1301);

    public int value;
    public String key;

    PicLicenseOption(String _key, int _value) {
        this.key = _key;
        this.value = _value;
    }

    public static PicLicenseOption valueOf(int value) {
        switch (value) {
            case 101:
                return PublicDomainWork;
            case 102:
                return PublicDomainDedication;
            case 201:
                return Attribution;
            case 202:
                return AttributionShareAlike;
            case 203:
                return AttributionNoDerivatives;
            case 301:
                return CopyrightGOtrip;
            case 1101:
                return CopyrightLimitAllReserved;
            case 1102:
                return CopyrightLimitCopyNc;
            case 1103:
                return CopyrightLimitCopyPk;
            case 1201:
                return CopyrightLimitCcNc;
            case 1202:
                return CopyrightLimitCcNcNd;
            case 1203:
                return CopyrightLimitCcNdSa;
            case 1301:
                return CopyrightLimitNotCopyright;
            default:
                return Unknown;
        }
    }
}
