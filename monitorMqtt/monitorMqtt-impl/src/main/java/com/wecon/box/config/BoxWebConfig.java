package com.wecon.box.config;

/**
 * 站点的一些配置信息
 * Created by zengzhipeng on 2017/8/4.
 */
public class BoxWebConfig {
    /**
     * 邮箱激活帐号地址
     */
    private String emailActiveUrl;

    /**
     * 下载文件接口url
     */
    private String fileDownloadUrl;

    private String mqttHost;

    private String mqttUsername;

    private String mqttPwd;

    /**
     * 实时数据最大配置数，-1表示无限制
     */
    private Integer actSetNum;

    /**
     * 历史数据最大配置数，-1表示无限制
     */
    private Integer hisSetNum;

    /**
     * 告警数据最大配置数，-1表示无限制
     */
    private Integer alarmSetNum;

    public String getMqttHost() {
        return mqttHost;
    }

    public void setMqttHost(String mqttHost) {
        this.mqttHost = mqttHost;
    }

    public String getMqttUsername() {
        return mqttUsername;
    }

    public void setMqttUsername(String mqttUsername) {
        this.mqttUsername = mqttUsername;
    }

    public String getMqttPwd() {
        return mqttPwd;
    }

    public void setMqttPwd(String mqttPwd) {
        this.mqttPwd = mqttPwd;
    }

    public String getEmailActiveUrl() {
        return emailActiveUrl;
    }

    public void setEmailActiveUrl(String emailActiveUrl) {
        this.emailActiveUrl = emailActiveUrl;
    }

    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }

    public Integer getActSetNum() {
        return actSetNum;
    }

    public void setActSetNum(Integer actSetNum) {
        this.actSetNum = actSetNum;
    }

    public Integer getHisSetNum() {
        return hisSetNum;
    }

    public void setHisSetNum(Integer hisSetNum) {
        this.hisSetNum = hisSetNum;
    }

    public Integer getAlarmSetNum() {
        return alarmSetNum;
    }

    public void setAlarmSetNum(Integer alarmSetNum) {
        this.alarmSetNum = alarmSetNum;
    }
}
