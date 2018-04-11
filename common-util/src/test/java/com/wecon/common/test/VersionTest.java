package com.wecon.common.test;

import com.wecon.common.util.Version;
import org.junit.Test;

/**
 * Created by fengbing on 2016/6/14.
 */
public class VersionTest
{
    public static void main(String[] args)
    {
        String strVer = ""; //"2a.8.0";

        long startTime = System.currentTimeMillis();

        int num = 100000;
        for (int i = 0; i < num; i++)
        {
            try{
                Version.Parse(strVer);
            }catch (Exception ex){

            }

        }
        long endTime = System.currentTimeMillis();

        System.out.println("total use millsecond:" + (endTime - startTime));

        long validStartTime = System.currentTimeMillis();
        for (int i = 0; i < num; i++)
        {
            Version.validFormat(strVer);
        }
        long validEndTime = System.currentTimeMillis();
        System.out.println("total use millsecond:" + (validEndTime - validStartTime));
    }


    @Test
    public void validFormat()
    {
//        Assert.assertEquals(true, Version.validFormat("2.8"));
//        Assert.assertEquals(true, Version.validFormat("20.8"));
//        Assert.assertEquals(true, Version.validFormat("200.8"));
//        Assert.assertEquals(true, Version.validFormat("2000.8"));
//        Assert.assertEquals(true, Version.validFormat("2000000.8"));
//        Assert.assertEquals(true, Version.validFormat("2000000.8000000"));
//        Assert.assertEquals(true, Version.validFormat("2.8.1"));
//        Assert.assertEquals(true, Version.validFormat("2.8.1.1"));
//        Assert.assertEquals(true, Version.validFormat("2234567.8234567.1234567.1234567"));
//
//        //System.out.println("----------------");
//
//        Assert.assertEquals(false, Version.validFormat("2000000.80000000"));
//        Assert.assertEquals(false, Version.validFormat("20000000.8"));
//        Assert.assertEquals(false, Version.validFormat("2234567.8234567.12345671.1234567"));
//        Assert.assertEquals(false, Version.validFormat("2.8.1.12345678"));
//        Assert.assertEquals(false, Version.validFormat("2.8.1.1.1"));
//
//        Assert.assertEquals(false, Version.validFormat("2.8.a.1"));
    }
}
