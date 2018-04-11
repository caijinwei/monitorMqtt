package com.wecon.common.test;

import com.wecon.common.util.TimeUtil;
import com.wecon.common.util.VersionUtil;
import com.wecon.common.web.WebHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengbing on 2015/12/2.
 */
public class WebHelperTest
{

    @Test
    public void testPostBody() throws Exception
    {
        System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate());
        try
        {
            final String url = "http://news.felinkapps.com/api/article/getArticleRecommendList/v1";
            String body = "{\"requestId\":\"1480924911924\",\"appId\":\"1\",\"count\":10,\"context\":{\"itemId\":0,\"placeId\":\"10000\",\"operType\":1},\"device\":{\"gaid\":\"\",\"timezone\":0,\"ip\":\"0.0.0.0\",\"idfa\":\"xxxxxxxxxxxxxxxxxxxxxxx\",\"platform\":1,\"mac\":\"\",\"osvCode\":\"\",\"pad\":\"\",\"sendtime\":1480924912037,\"androidId\":\"\"},\"sId\":\"100001\"}";
            System.out.println(WebHelper.postBody(url, body, null, "10.79.185.44", "utf-8", 100));
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }

        System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate());
    }

    @Test
    public void testWebHelperIP() throws Exception
    {
       /* List<NameValuePair> nvc = new ArrayList<>();

        String html = WebHelper.getPage("http://kakoi-operator.ru/908", nvc, "get", "", "windows-1251");
        System.out.println(html);*/
    }

    @Test
    public void testWebHelperProxy() throws Exception
    {
        /*System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate());
        try
        {
            List<NameValuePair> nvc = new ArrayList<>();

            String html = WebHelper.getPage("http://update.felinkapps.com/sjupdate-web/api/cache/refresh", nvc, "get", "172.17.156.62", 80, "utf-8", null, 6000);
            //String html = WebHelper.getPage("http://sjupdate.sj.91.com/t.aspx", nvc, "get", "10.79.133.56", "utf-8");

            System.out.println(html);
        }
        catch (Exception ex)
        {

        }
        System.out.println(TimeUtil.getYYYYMMDDHHMMSSDate());*/

    }

    @Test
    public void testZenDao() throws Exception
    {
//        List<NameValuePair> headers = new ArrayList<>();
//
//        headers.add(new BasicNameValuePair("Cookie", "pagerProductBrowse=100; keepLogin=on; za=fengbing; zp=e4f2d5c0b07e2294069b933935ce35c5c13d1d90; lastProject=8; projectTaskOrder=status%2Cid_desc; productStoryOrder=plan_asc; qaBugOrder=id_desc; lastProduct=9; lastBranch=9-0; lang=zh-cn; theme=default; windowWidth=1280; windowHeight=899; test=5D9C8F6E780D6E4B6C65E45AF1AC8A649F600ED13D37D912FFE61D2E2C986F9BD2776A7A6A55A3FC2B9BB6CB0F455F05FCD4F911791CB300E64B8D7D5901099CC2970B945E44370E8CA882B7C89BE4AA93885D87E0279E7964BBD8319403D0F07082B02B1065ED49D16702C447AD6245C0385BAA93DDAFFE5212468F9F258EC4F5039BADC907655D2FC26A5E6C6A1608; sid=qhlm8o6ni3189d76ntv8n6h0b2; windowWidth=1280; windowHeight=899"));
//
//        //Cookie: pagerProductBrowse=100; keepLogin=on; za=fengbing; zp=e4f2d5c0b07e2294069b933935ce35c5c13d1d90; lastProject=8; projectTaskOrder=status%2Cid_desc; productStoryOrder=plan_asc; qaBugOrder=id_desc; lastProduct=9; lastBranch=9-0; lang=zh-cn; theme=default; windowWidth=1280; windowHeight=899; test=5D9C8F6E780D6E4B6C65E45AF1AC8A649F600ED13D37D912FFE61D2E2C986F9BD2776A7A6A55A3FC2B9BB6CB0F455F05FCD4F911791CB300E64B8D7D5901099CC2970B945E44370E8CA882B7C89BE4AA93885D87E0279E7964BBD8319403D0F07082B02B1065ED49D16702C447AD6245C0385BAA93DDAFFE5212468F9F258EC4F5039BADC907655D2FC26A5E6C6A1608; sid=qhlm8o6ni3189d76ntv8n6h0b2; windowWidth=1280; windowHeight=899
//
//        String url = "http://pm.felink.com/zentao/bug-create-9-moduleID=333.json";
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("product=%s", "9"));
//        sb.append(String.format("&module=%s", "333"));
//        sb.append(String.format("&project=%s", ""));
//        sb.append(String.format("&openedBuild[]=%s", "trunk"));
//        sb.append(String.format("&assignedTo=%s", "fengbing"));
//        sb.append(String.format("&type=%s", "codeerror"));
//        sb.append(String.format("&os=%s", ""));
//        sb.append(String.format("&browser=%s", ""));
//        sb.append(String.format("&title=%s", "yyyyyyyyyyyyy"));
//        sb.append(String.format("&severity=%s", "3"));
//        sb.append(String.format("&pri=%s", "0"));
//        sb.append(String.format("&steps=%s", "<p>[步骤] </p><p>[结果] </p><p>[测试机型] </p>"));
//        sb.append(String.format("&story=%s", ""));
//        sb.append(String.format("&task=%s", "0"));
//        sb.append(String.format("&mailto[]=%s", ""));
//        sb.append(String.format("&keywords=%s", ""));
//        sb.append(String.format("&files[]=%s", ""));
//        sb.append(String.format("&labels[]=%s", ""));
//        sb.append(String.format("&case=%s", "0"));
//        sb.append(String.format("&caseVersion=%s", "0"));
//        sb.append(String.format("&result=%s", "0"));
//        sb.append(String.format("&testtask=%s", "0"));
//        String html = WebHelper.postBody(url, sb.toString(), headers, "", "utf-8");
//        System.out.println(html);
    }

    @Test
    public void testZenDao111() throws Exception
    {
//        List<NameValuePair> headers = new ArrayList<>();
//
//        headers.add(new BasicNameValuePair("Cookie", "pagerProductBrowse=100; keepLogin=on; za=fengbing; zp=e4f2d5c0b07e2294069b933935ce35c5c13d1d90; lastProject=8; projectTaskOrder=status%2Cid_desc; productStoryOrder=plan_asc; qaBugOrder=id_desc; lastProduct=9; lastBranch=9-0; lang=zh-cn; theme=default; windowWidth=1280; windowHeight=899; test=5D9C8F6E780D6E4B6C65E45AF1AC8A649F600ED13D37D912FFE61D2E2C986F9BD2776A7A6A55A3FC2B9BB6CB0F455F05FCD4F911791CB300E64B8D7D5901099CC2970B945E44370E8CA882B7C89BE4AA93885D87E0279E7964BBD8319403D0F07082B02B1065ED49D16702C447AD6245C0385BAA93DDAFFE5212468F9F258EC4F5039BADC907655D2FC26A5E6C6A1608; sid=qhlm8o6ni3189d76ntv8n6h0b2; windowWidth=1280; windowHeight=899"));
//
//        //Cookie: pagerProductBrowse=100; keepLogin=on; za=fengbing; zp=e4f2d5c0b07e2294069b933935ce35c5c13d1d90; lastProject=8; projectTaskOrder=status%2Cid_desc; productStoryOrder=plan_asc; qaBugOrder=id_desc; lastProduct=9; lastBranch=9-0; lang=zh-cn; theme=default; windowWidth=1280; windowHeight=899; test=5D9C8F6E780D6E4B6C65E45AF1AC8A649F600ED13D37D912FFE61D2E2C986F9BD2776A7A6A55A3FC2B9BB6CB0F455F05FCD4F911791CB300E64B8D7D5901099CC2970B945E44370E8CA882B7C89BE4AA93885D87E0279E7964BBD8319403D0F07082B02B1065ED49D16702C447AD6245C0385BAA93DDAFFE5212468F9F258EC4F5039BADC907655D2FC26A5E6C6A1608; sid=qhlm8o6ni3189d76ntv8n6h0b2; windowWidth=1280; windowHeight=899
//
//
//        String url = "http://pm.felink.com/zentao/bug-create-9-moduleID=333.json";
//
//        List<NameValuePair> params = new ArrayList<>();
//        params.add(new BasicNameValuePair("product", "9"));
//        params.add(new BasicNameValuePair("module", "333"));
//        params.add(new BasicNameValuePair("project", ""));
//        params.add(new BasicNameValuePair("openedBuild[]", "trunk"));
//        params.add(new BasicNameValuePair("assignedTo", "fengbing"));
//        params.add(new BasicNameValuePair("type", "codeerror"));
//        params.add(new BasicNameValuePair("os", ""));
//        params.add(new BasicNameValuePair("browser", ""));
//        params.add(new BasicNameValuePair("title", "kkkkkkkkkk"));
//        params.add(new BasicNameValuePair("severity", "3"));
//        params.add(new BasicNameValuePair("pri", "0"));
//        params.add(new BasicNameValuePair("steps", "<p>[步骤] 11111</p><p>[结果] 2222 </p><p>[测试机型] 333</p>"));
//        params.add(new BasicNameValuePair("story", ""));
//        params.add(new BasicNameValuePair("task", "0"));
//        params.add(new BasicNameValuePair("mailto[]", ""));
//        params.add(new BasicNameValuePair("keywords", ""));
//        params.add(new BasicNameValuePair("files[]", ""));
//        params.add(new BasicNameValuePair("labels[]", ""));
//        params.add(new BasicNameValuePair("case", "0"));
//        params.add(new BasicNameValuePair("caseVersion", "0"));
//        params.add(new BasicNameValuePair("result", "0"));
//        params.add(new BasicNameValuePair("testtask", "0"));
//        String html = WebHelper.getPage(url, params, "post", "", 80, "utf-8", headers);
//        System.out.println(html);
    }

    @Test
    public void testWebHelperProxy1() throws Exception
    {
        /*List<NameValuePair> nvc = new ArrayList<>();

        String html = WebHelper.getPage("http://www.numberingplans.com/?page=plans&sub=phonenr&alpha_2_input=IN&current_page=1", nvc, "get", "92.42.163.198", 3128, "utf-8");

        System.out.println(html);*/
    }


    public static void main(String[] args) throws Exception
    {
        //testVersionCompare();

        //testEhcache();

        testHttps();

        System.out.println(System.currentTimeMillis());

        //Version test = Version.Parse("2.0");
        try
        {
//            String html = WebHelper.getPage("http://www.numberingplans.com/?page=plans&sub=phonenr&alpha_2_input=IN&current_page=3196");
//            System.out.println(html);
//
//            aesEncrypt("testa7b94c7edfec5312b5960a8f21d0c83c", "0123456789012345");
//            aesEncrypt("testa7b94c7edfec5312b5960a8f21d0c83c", "0123456789012345");
//            aesEncrypt("testa7b94c7edfec5312b5960a8f21d0c83c", "0123456789012345");
        }
        catch (Exception ex)
        {

        }
    }

    private static void aesEncrypt(String str, String key) throws Exception
    {
        Cipher cipher = Cipher.getInstance(("AES/CBC/PKCS5Padding"));
        SecretKeySpec spec = new SecretKeySpec(key.getBytes("utf-8"), "AES");
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("utf-8"));//使用CBC模式，需要一个向量iv，可增加加密算法的强度

        cipher.init(Cipher.ENCRYPT_MODE, spec, iv);


        byte[] bytes = cipher.doFinal(str.getBytes("utf-8"));

        System.out.println(new Base64().encodeToString(bytes));

    }

    private static void testEhcache()
    {
        try
        {
            CacheManager cacheManager = CacheManager.create();

            System.out.println("testEhcache.createcache");

            Cache cache = cacheManager.getCache("sample");

            System.out.println("cache is " + (cache == null ? "null" : "not null"));

            Element element = cache.get("key");
            System.out.println("get 1 ->" + (element == null ? "is null" : String.valueOf(element.getObjectValue())));

			/*
            cache.put(new Element("key", "val", false,  2, 0));

			Thread.sleep(1500);

			Element element2 = cache.get("key");
			System.out.println("get 2 ->" + (element2 == null ? "is null": String.valueOf(element2.getObjectValue())));

			Thread.sleep(1500);
			Element element3 = cache.get("key");
			System.out.println("get 3 ->" + (element3 == null ? "is null": String.valueOf(element3.getObjectValue())));

			Thread.sleep(3000);
			Element element4 = cache.get("key");
			System.out.println("get 4 ->" + (element4 == null ? "is null": String.valueOf(element4.getObjectValue())));
			*/
            TestCacheLoader ccc = new TestCacheLoader();

            Element element_loader1 = cache.getWithLoader("key", ccc, "aaaaa");
            System.out.println("get element_loader1 ->" + (element_loader1 == null ? "is null" : String.valueOf(element_loader1.getObjectValue())));

            Thread.sleep(3000);

            Element element_loader2 = cache.getWithLoader("key", ccc, null);
            System.out.println("get element_loader2 ->" + (element_loader2 == null ? "is null" : String.valueOf(element_loader2.getObjectValue())));


        }
        catch (Exception e)
        {
            System.out.println("testEhcache.exception");
            e.printStackTrace();
        }

    }


    private static void testWebHelper()
    {
        /*
        String html = WebHelper.getPage("http://10.79.132.33/DataTransfers/");
		System.out.println(html);


		String url = "http://userad2.sj.91.com/Home.ashx/GetTagAdvert";

		List<NameValuePair> param = new ArrayList<NameValuePair>();

		param.add(new BasicNameValuePair("imei", "867739019981856"));
		param.add(new BasicNameValuePair("platform", "4"));
		param.add(new BasicNameValuePair("fw", ""));
		param.add(new BasicNameValuePair("pad", ""));
		param.add(new BasicNameValuePair("edittagid", "0"));
		param.add(new BasicNameValuePair("adtagid", "1012764"));
		param.add(new BasicNameValuePair("check", "19EBA880C6AE8FE030387817D9FC8449"));
		param.add(new BasicNameValuePair("clientIp", ""));
		param.add(new BasicNameValuePair("channel", ""));
		param.add(new BasicNameValuePair("filterPad", ""));
		param.add(new BasicNameValuePair("filterNoAuthorize", "undefined"));
		param.add(new BasicNameValuePair("rnd", "9425.755681004375"));


		System.out.println("===============================================================");
		html = WebHelper.getPage(url, param, "GET", "", "utf-8");
		System.out.println(html);

		System.out.println("===============================================================");
		html = WebHelper.getPage(url, param, "POST", "", "utf-8");
		System.out.println(html);
		*/
    }

    private static void testHttps() throws Exception
    {
        //String html =  WebHelper.post("https://uap.felinkapps.com/api/check/signcode?account=15005003682&phonecc=86&type=3", "", "utf-8");
        //System.out.println(html);
    }

    private static void testVersionCompare()
    {
        versionCompare("1.0", "2.0");
        versionCompare("2.0", "2.0");
        versionCompare("3.0", "2.0");

        versionCompare("1.0.5.6", "2.0");
        versionCompare("1.0.5.6", "1.0.5.10");
        versionCompare("1.0.5.06", "1.0.5.10");

        versionCompare("1.0.5.06", "1.0.6");

        versionCompare("1.0.7", "1.0.6.9");
    }

    private static void versionCompare(String version1, String version2)
    {
        int result = VersionUtil.compareTo(version1, version2);
        System.out.printf("%s, %s, %d", version1, version2, result).println();
        System.out.println();
    }

}
