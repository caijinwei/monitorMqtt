package com.wecon.box.enums;

import com.wecon.common.enums.EnumVal;

/**
 * Created by zengzhipeng on 2017/8/30.
 */
public enum ResTypeOption implements EnumVal {
    Unknown("未定义", 0),
    Account("帐号", 100),
    Dir("分组", 200),
    Device("设备", 300),
    ViewAccount("视图帐号", 400),
    Plc("通讯口", 500),
    Act("实时数据", 600),
    His("历史数据", 700),
    Alarm("报警数据", 800),
    File("文件", 900),
    Firm("固件", 1000),
    Write("操作实时数据", 1100),
    Driver("驱动", 1200);
    private String key;

    private int value;

    ResTypeOption(String _key, int _value) {
        key = _key;
        value = _value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getKey() {
        return key;
    }
}
