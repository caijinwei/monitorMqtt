package com.wecon.common.enums;

/**
 * 纯客户端动作定义
 * Created by fengbing on 2016/1/6.
 */
public enum ClientActOption
{
    NONE("无", 0),
    GOOGLE_PLAY("跳转GooglePlay", 1),
    //跳第三方网页(客户端直接打开系统浏览器展示)
    THIRD_WEB_URL("跳转外部页面", 2),
    //跳内嵌网页(带前进后退按钮，且客户端可接管页面里的事件例如sact)
    INNER_WEB_URL("跳转内嵌网页", 3),
    //跳转文章详情页面 注：S3后版本才支持
    JUMP_ARTICLE_DETAIL("跳转文章详情页面 ", 4),
    //跳蓝牙获取通讯录功能
    BLUETOOTH_ADDRESS_BOOK("蓝牙获取通讯录", 5),
    //跳通讯录备份功能
    BACKUP_MODULE("跳通讯录备份功能", 6),
    //跳通话记录备份功能 注：S3后版本才支持
    BACKUP_CALL_LOG("跳通话记录备份功能", 7),
    //跳短信份功能 注：S3后版本才支持
    BACKUP_SMS("跳短信份功能", 8),
    //推广告SDK里的内容 注：S3后版本才支持
    AD_SDK("推广告SDK里的内容", 9),
    //跳apps栏目列表 注：S3后版本才支持
    JUMP_ARTICLE_MODULE_APPS("跳apps栏目列表", 10),
    //跳games栏目列表 注：S3后版本才支持
    JUMP_ARTICLE_MODULE_GAMES("跳games栏目列表", 11),
    //跳资讯栏目列表 注：S3后版本才支持
    JUMP_ARTICLE_MODULE_INFOS("跳资讯栏目列表", 12),
    //跳壁纸栏目列表 注：S3后版本才支持
    JUMP_PIC_MODULE("跳壁纸栏目列表", 13),
    //跳壁纸详情页 注：S3后版本才支持
    JUMP_PIC_DETAIL("跳壁纸详情页 ", 14),
    //跳文件传输 注：S3后版本才支持
    JUMP_FILE_TRANS("跳文件传输", 15),
    OPEN_URL_LOGIN("打开URL前客户端必须要有登录状态", 16),
    JUMP_PIC_TAG("跳壁纸合辑",17),
    JUMP_PIC_CATE("跳某个壁纸分类", 18),
    JUMP("换机助手",19),
    JUMP_GENIUS_ATR_CATE("跳某分类",20),
    JUMP_GENIUS_ATR_TAG("跳某合辑",21),
    JUMP_GENIUS_APPS_RANK("跳Apps热门（榜单）",20),
    JUMP_GENIUS_GAMES_RANK("跳Games热门（榜单）",21),
    JUMP_SOFT_DETAIL("跳软件详情页",30);



    public int value;
    public String key;

    ClientActOption(String _key, int _value)
    {
        this.key = _key;
        this.value = _value;
    }
}
