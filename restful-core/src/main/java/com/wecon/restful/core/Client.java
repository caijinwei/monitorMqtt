package com.wecon.restful.core;

import com.wecon.common.enums.FwVersionOption;
import com.wecon.common.enums.MobileOption;
import com.wecon.common.util.FwVersionHelper;
import com.wecon.common.web.HttpHeaderUtils;
import com.wecon.common.web.IpAddrHelper;
import com.wecon.common.web.IpUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 客户端信息
 *
 * @author sean
 */
public class Client {
    // sid
    public String sid;
    // 用户id
    public long userId;
    // 请求id
    public String reqid;
    // 上一次请求返回的请求id
    public String logid;
    // 全局会话id –>值由配制接口JSON里返回的reqid （云配制接口）
    public String globalId;
    // android平台对应取Android_ID, IOS平台对应值取UUID（openidfa）
    public String devid;
    // android平台对应取giad(google Advertising ID), IOS平台对应值取idfa(apple Advertising ID)
    public String adid;
    // 登录账号
    public String account;
    // 产品id
    public int appid;
    // 客户端唯一标识
    public String fuid;
    // 时间戳
    public long timestamp;
    // 平台
    public MobileOption platform;
    // app版本
    public String version;
    //app的versionCode
    public String versionCode;
    // 语言
    public String language;
    // 客户端内选择的语言
    public String languageOne;
    // imei
    public String imei;
    // 操作系统版本
    public FwVersionOption osVersion;
    // 客户端设备固件版本
    public String osv;
    // 网络连接类型
    public String netType;
    // imsi
    public String imsi;
    // 请求签名
    public String sign;
    // 机型
    public String device;
    // 国家
    public String country;
    // 用户信息
    public SessionState.UserInfo userInfo;
    // 请求ip
    public long ip;
    // 渠道值
    public String channel;
    // 原始渠道值
    public String orginchl;
    // cpu
    public String cpuRaw;
    // gpu
    public String gpuRaw;
    // 分辨率
    public String resolutionRaw;
    // 时区(正负区分东西区，单位为毫秒)
    public int timezone;
    //项目来源
    public int projectSource;
    //第三方接口请求公司id
    public int comid;
    //第三方接口请求公司私有key
    public String compvtkey;

    /**
     * 初始化
     *
     * @param session
     */
    protected void init(Session session) {
        this.sid = this.getString(session, "sid");
        this.appid = this.getInt(session, "pid");
        this.projectSource = this.getInt(session, "proj");

        this.reqid = this.getString(session, "reqid");
        if (reqid == null || reqid.isEmpty()) {
            this.reqid = System.currentTimeMillis() + "";
        }
        session.request.setAttribute("reqid", this.reqid);

        this.fuid = this.getString(session, "fuid");
        this.timestamp = this.getLong(session, "ts");
        this.platform = MobileOption.valueOf(this.getInt(session, "mt"));
        this.version = this.getString(session, "sv");
        this.versionCode = this.getString(session, "vc");
        this.language = this.getString(session, "lan");
        this.languageOne = this.getString(session, "lan1");
        this.imei = this.getString(session, "imei");
        this.imsi = this.getString(session, "imsi");
        this.osVersion = FwVersionHelper.getFwVersion(this.platform, this.getString(session, "osv"));
        this.osv = this.getString(session, "osv");
        this.netType = this.getString(session, "nt");
        this.sign = this.getString(session, "sign");
        this.device = this.getString(session, "dm");
        this.country = this.getString(session, "cc");
        this.channel = this.getString(session, "chl");
        this.orginchl = this.channel;
        if (!this.getString(session, "orginchl").isEmpty()) {
            this.orginchl = this.getString(session, "orginchl");
        }
        this.cpuRaw = this.getString(session, "cpu");
        this.gpuRaw = this.getString(session, "gpu");
        this.resolutionRaw = this.getString(session, "rslt");

        this.timezone = this.getInt(session, "tz");

        this.globalId = this.getString(session, "gid");
        this.logid = this.getString(session, "logid");
        this.devid = this.getString(session, "devid");
        this.adid = this.getString(session, "adid");

        this.ip = this.getIpAddr(session.request);

        this.comid = this.getInt(session, "comid");
        this.compvtkey = this.getString(session, "compvtkey");

        // 访问账号中心读取userId
        this.userInfo = SessionManager.getUserInfo(this.getString(session, "sid"));
        if (userInfo != null) {
            this.userId = userInfo.getUserID();
            this.account = userInfo.getAccount();
        }
    }

    private long getIpAddr(HttpServletRequest request) {
//		String ip = request.getHeader("x-forwarded-for");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
//		{
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
//		{
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
//		{
//			ip = request.getRemoteAddr();
//		}
        String ip = HttpHeaderUtils.getIpAddress(request);
        ip = IpUtils.formatIp(ip);
        try {
            return IpAddrHelper.convertIpToLong(ip);
        } catch (Exception e) {
            return 2130706433;
        }
    }

    private String getString(Session session, String key) {
        String[] val = session.params.get(key);
        if (val != null && val.length > 0) {
            return val[0];
        }
        return "";
    }

    private int getInt(Session session, String key) {
        String[] val = session.params.get(key);
        if (val != null && val.length > 0) {
            try {
                return Integer.parseInt(val[0]);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    private long getLong(Session session, String key) {
        String[] val = session.params.get(key);
        if (val != null && val.length > 0) {
            try {
                return Long.parseLong(val[0]);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
