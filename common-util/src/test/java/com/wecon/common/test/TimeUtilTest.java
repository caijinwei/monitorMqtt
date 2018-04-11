package com.wecon.common.test;

import com.wecon.common.util.TimeUtil;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by zengzhipeng
 */
public class TimeUtilTest
{
    public static void main(String[] args)
    {
        int timestampSecond = TimeUtil.getTimestampForSecond();
        System.out.printf("timestampSecond = %s", timestampSecond).println();

        int timestampSecondDate = TimeUtil.getTimestampForSecond(new Date());

        System.out.printf("timestampSecondDate = %s", timestampSecondDate).println();

        try
        {
            Date datefromTimestamp = TimeUtil.getDateFromTimestampForSecond(timestampSecond);

            System.out.printf("datefromTimestamp = %s", TimeUtil.getYYYYMMDDHHMMSSDate(datefromTimestamp)).println();

            Date datefromTimestampdate = TimeUtil.getDateFromTimestampForSecond(timestampSecond);
            System.out.printf("datefromTimestampdate = %s", TimeUtil.getYYYYMMDDHHMMSSDate(datefromTimestampdate)).println();


        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        testTimefrom("2015-1-1");
    }

    private static void testTimefrom(String str)
    {
        try
        {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date1 = df.parse("20151218090005");

            Calendar cal = Calendar.getInstance();
            TimeZone timeZone = cal.getTimeZone();
            timeZone = TimeZone.getDefault();

            System.out.println(timeZone.getID());
            System.out.println(timeZone.getDisplayName());
            System.out.println(timeZone.getDisplayName(Locale.SIMPLIFIED_CHINESE));

            Date date = TimeUtil.fromString(str, "yyyy-MM-dd");
            System.out.printf("%s; %s", str, TimeUtil.getYYYYMMDDHHMMSSDate(date)).println();
        }
        catch (ParseException pe)
        {
            System.out.printf("%s; %s", str, pe.getMessage()).println();

        }

    }

    @Test
    public void testParse() throws Exception
    {
        String val = "3/23/2016 3:43:00 AM";

        DateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a", Locale.US);
        Date dt = simpleDateFormat.parse(val);
        System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate(dt));
        val = "3/23/2016 3:43:00 PM";
        dt = simpleDateFormat.parse(val);
        System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate(dt));
    }
}
