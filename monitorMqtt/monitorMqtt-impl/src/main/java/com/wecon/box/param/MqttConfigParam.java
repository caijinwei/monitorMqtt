package com.wecon.box.param;

import javax.validation.constraints.NotNull;

/**
 * Created by cai95 on 2018/4/9.
 */
public class MqttConfigParam {

    public long serverId;

    @NotNull
    public String serverName ;

    public String username;

    public String password;

    @NotNull
    public boolean isSsl;

    @NotNull
    public String serverIP;

    @NotNull
    public int port;

    @NotNull
    public int websocketPort;



    public int maxConn = 10000;



    public boolean isSsl() {
        return isSsl;
    }

    public void setSsl(boolean ssl) {
        isSsl = ssl;
    }

    public int getMaxConn() {
        return maxConn;
    }

    public void setMaxConn(int maxConn) {
        this.maxConn = maxConn;
    }

    public long getServerId() {
        return serverId;
    }

    public void setServerId(long serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsSsl() {
        return isSsl;
    }

    public void setIsSsl(boolean ssl) {
        isSsl = ssl;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWebsocketPort() {
        return websocketPort;
    }

    public void setWebsocketPort(int websocketPort) {
        this.websocketPort = websocketPort;
    }

    @Override
    public String toString() {
        return "MqttConfigParam{" +
                "serverId=" + serverId +
                ", serverName='" + serverName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isSsl=" + isSsl +
                ", serverIP='" + serverIP + '\'' +
                ", port=" + port +
                ", websocketPort=" + websocketPort +
                ", maxConn=" + maxConn +
                '}';
    }
}
