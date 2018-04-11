package com.wecon.box.enums;

import com.wecon.common.enums.EnumVal;

/**
 * Created by zengzhipeng on 2017/8/30.
 */
public enum OpTypeOption implements EnumVal {
    Unknown("未定义", 0),
    //用户相关
    SignupEmail("邮箱注册", 100),
    EmailActive("邮箱激活", 101),
    SignupPhone("手机号码注册", 102),
    Signin("登录", 103),
    Signout("登出", 104),
    ChgPwd("修改密码", 105),
    ChgEmail("修改邮箱", 106),
    ChgPhone("修改手机号码", 107),
    AddViewUser("新增视图帐号", 108),
    UpdViewUser("修改视图帐号", 109),
    SetUserState("修改账户状态",110),
    FindPwd("找回密码", 111),
    SaveAccountExt("保存用户扩展信息", 112),

    //分组操作
    AddDir("新增分组", 200),
    UpdDir("修改分组", 201),
    DelDir("删除分组", 202),

    //盒子操作
    BindDevice("绑定盒子", 300),
    UnBindDevice("解除绑定盒子", 301),
    DragDeviceDir("盒子分组修改", 302),
    UpdateDeviceInfo("修改盒子基本信息", 303),

    //权限操作
    AddViewRole("添加视图帐号权限", 400),
    UpdViewRole("修改视图监控点权限", 401),
    DelViewRole("删除视图帐号权限", 402),

    //通讯口操作
    AddPlc("添加通讯口", 501),
    UpdatePlc("更新通讯口配置", 502),
    DelPlc("删除通讯口配置", 503),
    DelRealHis("删除通讯口下的实时历史监控点",504),
    DelAlram1("删除通讯口下的报警监控点",505),
    //实时监控点操作
    AddAct("新增实时数据配置", 600),
    UpdAct("修改实时数据配置", 601),
    DelAct("删除实时数据配置", 602),
    WriteAct("下发数据到盒子(Web端)", 603),
    WriteActPhone("下发数据到盒子(手机端)", 604),

    //历史监控点操作
    AddHis("新增历史数据配置", 700),
    UpdHis("修改历史数据配置", 701),
    DelHis("删除历史数据配置", 702),

    //报警监控点
    AddAlarm("新增报警数据配置", 800),
    UpdAlarm("修改报警数据配置", 801),
    DelAlarm("删除报警数据配置", 802),
    ConFirmAlarmData("确认报警数据", 803),

    //文件操作
    AddFile("新增文件", 900),

    //固件管理
    AddFirm("新增固件", 1000),
    UpdFirm("修改固件", 1001),
    DelFirm("删除固件", 1002),

    //驱动管理
    BatchDriver("批量新增驱动", 1200),
    UpdDriver("修改驱动", 1201),
    DelDriver("删除驱动", 1202),

    //后台操作类型
    UpdPlcInfo("通讯口更新", 10),
    DelPlcInfo("通讯口删除", 11),
    UpdRealHisCfg("实时历史监控点更新", 20),
    DelRealHisCfg("实时历史监控点删除", 21),
    UpdAlarmCfg("报警监控点更新", 30),
    DelAlarmCfg("报警监控点删除", 31);

    private String key;

    private int value;

    OpTypeOption(String _key, int _value) {
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
