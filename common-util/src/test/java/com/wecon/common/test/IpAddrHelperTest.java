package com.wecon.common.test;

import com.wecon.common.web.IpAddrHelper;

/**
 * Created by fengbing on 2015/12/18.
 */
public class IpAddrHelperTest
{
    public static void main(String[] args)
    {
        iptest("1.46.78.237");
        iptest("1.47.11.32");
        iptest("1.47.72.125");
        iptest("101.109.103.97");
        iptest("103.25.12.31");
        iptest("105.192.111.148");
        iptest("106.0.204.114");
        iptest("106.216.182.209");
        iptest("106.219.39.63");
        iptest("109.224.17.108");
        iptest("112.198.83.178");
        iptest("112.5.236.216");
        iptest("117.248.196.103");
        iptest("121.54.44.129");
        iptest("125.25.248.54");
        iptest("149.255.214.15");
        iptest("151.236.172.87");
        iptest("176.241.87.39");
        iptest("182.56.98.232");
        iptest("185.95.204.102");
        iptest("186.108.181.101");
        iptest("188.247.75.110");
        iptest("203.81.71.22");
        iptest("217.132.25.20");
        iptest("223.225.154.125");
        iptest("36.37.192.125");
        iptest("37.236.104.24");
        iptest("37.236.228.18");
        iptest("37.237.108.7");
        iptest("37.238.176.94");
        iptest("37.238.180.95");
        iptest("37.239.113.59");
        iptest("39.54.73.202");
        iptest("41.220.68.137");
        iptest("41.239.22.193");
        iptest("49.49.229.97");
        iptest("5.1.110.159");
        iptest("5.149.103.203");
        iptest("5.22.75.239");
        iptest("91.251.114.245");
        iptest("91.76.158.177");
        iptest("94.252.157.191");
        iptest("99.229.199.100");
        iptest("1.0.212.211");
        iptest("1.39.85.198");
        iptest("10.16.155.156, 27.55.30.20");
        iptest("105.239.202.81");
        iptest("110.77.213.214");
        iptest("114.125.42.18");
        iptest("115.164.216.251");
        iptest("117.20.115.8");
        iptest("117.226.19.139");
        iptest("125.25.248.54");
        iptest("149.255.224.35");
        iptest("154.102.67.19");
        iptest("154.103.127.222");
        iptest("154.99.236.113");
        iptest("171.6.250.97");
        iptest("175.100.61.97");
        iptest("176.241.84.239");
        iptest("177.238.228.131");
        iptest("180.180.103.204");
        iptest("183.182.126.57");
        iptest("185.118.24.34, 185.118.24.10");
        iptest("191.103.213.184");
        iptest("197.134.255.79");
        iptest("197.239.6.92");
        iptest("197.246.241.250");
        iptest("202.67.42.46");
        iptest("223.207.249.232");
        iptest("27.55.20.19");
        iptest("27.77.11.231");
        iptest("31.173.243.109");
        iptest("36.37.192.64");
        iptest("37.236.188.61");
        iptest("37.239.152.63");
        iptest("37.255.141.227");
        iptest("39.251.59.179");
        iptest("39.41.109.76");
        iptest("39.54.173.84");
        iptest("41.190.2.200");
        iptest("41.244.241.93");
        iptest("41.252.161.65");
        iptest("42.115.123.164");
        iptest("49.229.11.58");
        iptest("49.229.33.100");
        iptest("49.48.13.173");
        iptest("78.166.251.85");
        iptest("82.114.168.158");
        iptest("82.114.168.158");

    }

    private static void iptest(String ipstr)
    {
        try
        {
            long ipvalue = IpAddrHelper.convertIpToLong(ipstr);

            String str = IpAddrHelper.convertLongToIP(ipvalue);

            System.out.printf("%s\t%d\t%s", ipstr, ipvalue, str).println();
        }
        catch (Exception ex)
        {
            System.out.printf("%s\t%s", ipstr, ex.getMessage()).println();
        }
    }
}
