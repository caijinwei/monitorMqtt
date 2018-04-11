package com.wecon.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 *
 * @author Sean
 */
public class TimeUtil
{
    private static final SimpleDateFormat FORMAT_YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat FORMAT_MMDDHHMM = new SimpleDateFormat("MM-dd HH:mm");
    private static final SimpleDateFormat FORMAT_YYYYMMDDHHMMSSLong = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat FORMAT_YYYYMMDDLong = new SimpleDateFormat("yyyyMMdd");

    private static final SimpleDateFormat FORMAT_LongYYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat FORMAT_LongYYYYMMDD = new SimpleDateFormat("yyyyMMdd");

    /**
     * 按照格式读取时间字符串
     *
     * @param format 日期格式
     * @return 返回格式化后的日期字符串
     */
    public static String getDateByFormat(String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date());
    }

    /**
     * 获取yyyy-MM-dd hh:mm:ss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static String getYYYYMMDDHHMMSSDate()
    {
        return FORMAT_YYYYMMDDHHMMSS.format(new Date());
    }

    /**
     * 获取yyyy-MM-dd hh:mm:ss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static String getUtcYYYYMMDDHHMMSSDate()
    {
        return FORMAT_YYYYMMDDHHMMSS.format(new Date(getUtcCalendar().getTimeInMillis()));
    }

    /**
     * 获取utc的日期时间
     *
     * @return 返回utc的日期时间
     */
    public static Date getUtcDate()
    {
        return new Date(getUtcCalendar().getTimeInMillis());
    }

    /**
     * 获取对应的utc日期时间
     *
     * @param date 日期
     * @return 返回utc的日期时间
     */
    public static Date getUtcDate(Date date)
    {
        return new Date(getUtcCalendar(date).getTimeInMillis());
    }


    public static Calendar getUtcCalendar()
    {
        //1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        //System.out.print("UTC时间：" + cal.getTimeInMillis());

        return cal;
    }

    public static Calendar getUtcCalendar(Date date)
    {
        //1、取得本地时间：
        Calendar cal = Calendar.getInstance();

        if (date != null)
        {
            cal.setTime(date);
        }

        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

        //System.out.print("UTC时间：" + cal.getTimeInMillis());

        return cal;
    }

    /**
     * 获取yyyy-MM-dd hh:mm:ss格式日期
     *
     * @param date 所要格式化的日期值
     * @return 返回格式化后的日期字符串
     */
    public static String getYYYYMMDDHHMMSSDate(Date date)
    {
        return FORMAT_YYYYMMDDHHMMSS.format(date);
    }

    /**
     * 获取MM-dd hh:mm格式日期
     *
     * @param date 所要格式化的日期值
     * @return 返回格式化后的日期字符串
     */
    public static String getMMDDHHMMDate(Date date)
    {
        return FORMAT_MMDDHHMM.format(date);
    }

    /**
     * 获取yyyMMdd格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static int getYYYYMMDD()
    {
        return Integer.parseInt(FORMAT_YYYYMMDDLong.format(new Date()));
    }

    /**
     * 根据传入的日期获取yyyMMdd格式日期
     *
     * @param date 所要格式化的日期
     * @return 返回格式化后的日期字符串
     */
    public static int getYYYYMMDD(Date date)
    {
        return Integer.parseInt(FORMAT_YYYYMMDDLong.format(date));
    }

    /**
     * 根据传入的日期获取yyyMMdd格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static int getUtcYYYYMMDD()
    {
        return Integer.parseInt(FORMAT_YYYYMMDDLong.format(new Date(getUtcCalendar().getTimeInMillis())));
    }


    /**
     * 获取yyyyMMddhhmmss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static long getYYYYMMDDHHMMSSTime()
    {
        return Long.parseLong(FORMAT_YYYYMMDDHHMMSSLong.format(new Date()));
    }

    /**
     * 获取yyyyMMddhhmmss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static long getUtcYYYYMMDDHHMMSSTime()
    {
        return Long.parseLong(FORMAT_YYYYMMDDHHMMSSLong.format(new Date(getUtcCalendar().getTimeInMillis())));
    }

    /**
     * 将日期转化为yyyyMMddhhmmss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static long getYYYYMMDDHHMMSSTime(Date date)
    {
        return Long.parseLong(FORMAT_YYYYMMDDHHMMSSLong.format(date));
    }

    /**
     * 获取yyyyMMddhhmmss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static long getYYYYMMDDHHMMSSTime(int year, int month)
    {
        String monthStr = month < 10 ? "0" + month : month + "";
        return Long.parseLong(year + monthStr + "00000000");
    }

    /**
     * 获取yyyyMMddhhmmss格式日期
     *
     * @return 返回格式化后的日期字符串
     */
    public static long getYYYYMMDDHHMMSSTime(int year, int month, int day)
    {
        String monthStr = month < 10 ? "0" + month : month + "";
        String dayStr = day < 10 ? "0" + day : day + "";
        return Long.parseLong(year + monthStr + dayStr + "000000");
    }

    /**
     * 读取指定时间的下N天
     *
     * @param date   指定时间
     * @param offset 下N天
     * @return 返回日期
     */
    public static Date getNextDays(Date date, int offset)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset);
        date = calendar.getTime();
        return date;
    }

    /**
     * 读取一个月的天数
     *
     * @param year  年
     * @param month 月
     * @return 返回天数
     */
    public static int getDaysOfMonth(int year, int month)
    {
        Calendar calendar = new GregorianCalendar();
        calendar.set(year, month, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 读取唯一的字符串
     *
     * @return 返回唯一的字符串
     */
    public static synchronized String getUniqueStr()
    {
        return Long.toString(System.currentTimeMillis());
    }

    /**
     * 字符串转时间
     *
     * @param str    日期字符串
     * @param format 日期格式
     * @return 格式化后的日期
     */
    public static Date fromString(String str, String format) throws ParseException
    {
        DateFormat df = new SimpleDateFormat(format);
        return df.parse(str);
    }

    /**
     * 将yyyyMMddHHmmss的日期转化为Date类型
     *
     * @param value
     * @return
     */
    public static Date fromYYYYMMDDHHMMSS(long value) throws ParseException
    {
        return FORMAT_LongYYYYMMDDHHMMSS.parse(String.valueOf(value));
    }

    /**
     * 将yyyyMMdd的日期转化为Date类型
     *
     * @param value
     * @return
     * @throws ParseException
     */
    public static Date fromYYYYMMDD(int value) throws ParseException
    {
        return FORMAT_LongYYYYMMDD.parse(String.valueOf(value));
    }

    /**
     * 读取随机日期
     *
     * @param beginDate 起始日期
     * @param endDate   终止日期
     * @return 起始和终止日期之间的随即日期
     */
    public static Date randomDate(String beginDate, String endDate) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start = format.parse(beginDate);
        Date end = format.parse(endDate);
        int days = (int) ((end.getTime() - start.getTime()) / 86400000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        calendar.add(Calendar.DATE, new Random().nextInt(days));
        return calendar.getTime();

    }

    /**
     * 获取当前时间戳（单位：秒）
     *
     * @return 时间戳
     */
    public static int getTimestampForSecond()
    {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 返回指定日期的时间戳值（单位：秒）
     *
     * @param date 待计算时间戳的日期
     * @return 时间戳
     */
    public static int getTimestampForSecond(Date date)
    {
        return (int) (date.getTime() / 1000);
    }

    /**
     * 返回指定日期的时间戳值（单位：秒）
     *
     * @param date 待计算时间戳的日期
     * @return 时间戳
     */
    public static long getTimestampForMilliSecond(Date date)
    {
        return date.getTime();
    }

    /**
     * @param timestamp 待转化为日期的时间戳值，单位：秒
     * @return 转化后的时间
     * @throws ParseException 格式转化失败
     */
    public static Date getDateFromTimestampForSecond(int timestamp) throws ParseException
    {
        String d = FORMAT_YYYYMMDDHHMMSS.format((long) (timestamp) * 1000);
        return FORMAT_YYYYMMDDHHMMSS.parse(d);
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception
    {
//        //System.out.println(getYYYYMMDDHHMMSSTime());
//        System.out.println("|| utc: ");
//        System.out.println(getUtcYYYYMMDDHHMMSSTime());
////        System.out.println("||");
////        System.out.println(getYYYYMMDDHHMMSSDate(getUtcDate()));
////        System.out.println("||");
////        System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate(TimeUtil.fromYYYYMMDDHHMMSS(getUtcYYYYMMDDHHMMSSTime())));
////        System.out.println("||");
////        System.out.println(TimeUtil.getTimestampForMilliSecond(getUtcDate()));
//        System.out.println("||");

        Date dateUtc = getUtcDate();
        System.out.println(getYYYYMMDDHHMMSSDate(dateUtc));
        long tsUtc = getTimestampForMilliSecond(dateUtc);
        System.out.println(tsUtc);
        System.out.println("-------------");
        long ymd = getYYYYMMDDHHMMSSTime(dateUtc);


        Date date = TimeUtil.fromYYYYMMDDHHMMSS(ymd);
        long ts = TimeUtil.getTimestampForMilliSecond(date);
        System.out.println(getYYYYMMDDHHMMSSDate(date));
        System.out.println(ts);
        long tsTm = System.currentTimeMillis();
        System.out.println(tsTm);
        System.out.println(tsUtc - tsTm);


        System.out.println(getYYYYMMDDHHMMSSDate(getDateFromTimestampForSecond((int) (ts / 1000))));
        System.out.println(getYYYYMMDDHHMMSSDate(getDateFromTimestampForSecond((int) (tsTm / 1000))));
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.get)


        Calendar cal = Calendar.getInstance();

        if (date != null)
        {
            cal.setTime(date);
        }

        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        System.out.println("---------");
        System.out.println(zoneOffset);
        System.out.println(dstOffset);
        System.out.println("---------");

        long tsXX = getTimestampForMilliSecond(fromYYYYMMDDHHMMSS(20161228130103L));
        System.out.println(tsXX);
        System.out.println(tsXX + zoneOffset + dstOffset);
//        System.out.println("||");
//        Date date = TimeUtil.getDateFromTimestampForSecond((int) (TimeUtil.getTimestampForMilliSecond(TimeUtil.fromYYYYMMDDHHMMSS(getUtcYYYYMMDDHHMMSSTime())) / 1000));
//        System.out.println(getYYYYMMDDHHMMSSDate(date));
//        System.out.println("||");
//        System.out.println(TimeUtil.getTimestampForMilliSecond(TimeUtil.getUtcDate(TimeUtil.fromYYYYMMDDHHMMSS(19700101000000L))));
//        System.out.println("||");
        //Date date1 = TimeUtil.getDateFromTimestampForSecond(0);
        //System.out.println(getYYYYMMDDHHMMSSDate(date1));


        //long dd =  System.currentTimeMillis();
        //System.out.print();
    }

}
