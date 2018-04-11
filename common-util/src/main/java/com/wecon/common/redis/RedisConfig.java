package com.wecon.common.redis;

/**
 * Redis的连接配置信息
 */
public class RedisConfig {
    //redis服务器地址（单个ip）
    private String host;

    //连接的端口（单个端口）
    private String port;

    //redis服务器密码
    private String password;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
