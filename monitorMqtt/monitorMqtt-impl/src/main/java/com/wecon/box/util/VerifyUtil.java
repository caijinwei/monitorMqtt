package com.wecon.box.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zengzhipeng on 2017/8/2.
 */
public class VerifyUtil {
    protected static Pattern regexValidEmail = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

    /**
     * 用户名，只能由字母数字下划线，1-20个字符
     */
    protected static Pattern regexValidUserName = Pattern.compile("^[0-9a-zA-Z_]{1,20}$");

    /**
     * 验证邮箱地址地址是否为有效的
     *
     * @param email 邮箱地址
     * @return 有效返回true；无效地址返回false
     */
    public static boolean isValidEmail(String email) {
        Matcher matcher = regexValidEmail.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证用户名是否合法
     *
     * @param userName
     * @return
     */
    public static boolean isValidUserName(String userName) {
        Matcher matcher = regexValidUserName.matcher(userName);
        Matcher matcherEmail = regexValidEmail.matcher(userName);
        return matcher.matches() || matcherEmail.matches();
    }

    /**
     * 中国手机号码
     *
     * @param str
     * @return
     */
    public static boolean isChinaPhone(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1]\\d{10}$"); // 验证手机号^[1][3,4,5,7,8][0-9]{9}$
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 是否是sql查询语句
     *
     * @param p
     * @return
     */
    public static boolean isSelStr(String p) {
        p = p.toUpperCase();

        if (p.indexOf("DELETE") >= 0 || p.indexOf("ASCII") >= 0
                || p.indexOf("UPDATE") >= 0 ||  p.indexOf("SUBSTR(") >= 0
                || p.indexOf("DROP") >= 0
                || p.indexOf("EXECUTE") >= 0 || p.indexOf("EXEC") >= 0
                || p.indexOf("TRUNCATE") >= 0 || p.indexOf("INTO") >= 0
                || p.indexOf("DECLARE") >= 0 || p.indexOf("MASTER") >= 0
                ) {
            return false;
        }
        if (p.indexOf("SELECT") >= 0 && p.indexOf("LIMIT") >= 0) {
            //必须使用LIMIT
            return true;
        }
        return false;
    }

    /*
    *
    * */
    public static String getVersionNum(String version) {
//        version = "sdsd2V4.0.0-P:A8 ";

        String result = "";
        //分割 前面的“-”
        Integer index = version.indexOf("-");
        String tempV = null;
        if (index != -1) {
            String s = version.substring(0, index);
            tempV = version.substring(0, index);
        } else {
            tempV = version;
        }


        //提取版本号
        String REGEX = "[[0-9]+\\.]+[0-9]+";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(tempV);
        while (m.find()) {
            result = (m.group());
        }
        System.out.println(result);
        return result;
    }

    public static void main(String[] args) {
//        getVersionNum("v5.0.0.0");
        System.out.println(isNumeric("1212"));

    }

    /*
    * localVsersion 本地版本
    * serverVsersion 服务器上面的版本
    *
    * return
    *        1)true
    *        serrverVersion > localVersion
    *        需要更新
    *
    *        2）flase
    *        serverVersion <= localVersion
    *        不需要更新
   * */
    public static boolean isNewVersion(String localVersion, String serverVersion) {
        if (localVersion.equals(serverVersion)) {
            return false;
        }
        String[] localArray = localVersion.split("\\.");
        String[] onlineArray = serverVersion.split("\\.");

        int length = localArray.length < onlineArray.length ? localArray.length : onlineArray.length;

        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(onlineArray[i]) > Integer.parseInt(localArray[i])) {
                return true;
            } else if (Integer.parseInt(onlineArray[i]) < Integer.parseInt(localArray[i])) {
                return false;
            }
            // 相等 比较下一组值
        }

        return true;
    }


    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
//    public static void main(String[] args){
//        System.out.println(isNewVersion(getVersionNum("sdsd2V4.0.0-P:A8"),getVersionNum("sdsd2V4.12.0-P:A8")));
//    }
}
