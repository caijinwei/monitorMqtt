package com.wecon.common.test;


import com.wecon.common.web.ImageUrlHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fengbing on 2015/12/23.
 */
public class ImageUrlHelperTest
{
    @Test
    public void getThumbnailUrl() throws Exception
    {
        System.out.println("getThumbnailUrl");
        String sucUrl = "http://thumbor.felinkapps.com/BQGTM2iFMqXEJAw8YABk9J2cm3I=/200x100/felinkagres/0165655.jpg";

        String computerUrl = "";
        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com/", "/200x100/felinkagres/0165655.jpg");
        Assert.assertEquals(sucUrl, computerUrl);

        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com/", "200x100/felinkagres/0165655.jpg");
        Assert.assertEquals(sucUrl, computerUrl);

        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com", "/200x100/felinkagres/0165655.jpg");
        Assert.assertEquals(sucUrl, computerUrl);

        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com", "200x100/felinkagres/0165655.jpg");
        Assert.assertEquals(sucUrl, computerUrl);

        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com", "200x100/felinkagres/0165655.jpg","hCqq5mb4YUxYU7WTLMTs6qiU-RdvgXKVESWURj" );
        Assert.assertEquals(sucUrl, computerUrl);

        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com", "200x100/felinkagres/0165655.jpg","hCqq5mb4YUxYU7WTLMTs6qiU-RdvgXKVESWURk" );
        Assert.assertNotEquals(sucUrl, computerUrl);

        //icon=http://thumbor.felinkapps.com/uhJroF0MYfoTTRGnkql0iPxB_Z8=/360x360/filters:format(webp)/res/pic/20160829/c4f0d24f9e404c2fb12f745321a0c44b.jpg
        computerUrl = ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com", "/gif/source/powered-by-giphy.jpg");
        System.out.println(computerUrl);

    }

    @Test
    public void getThumborUrl2() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException
    {

        String imgpath = "/drnews/img/2016/12/07/newsimg_90db7138dd63e05c56c9da689f1d6822.jpg";
        String  imgUrl =   imgpath; //"filters:max_bytes(40000)/" + "/center/top" +
        imgUrl = "300x250/filters:format(webp)" + imgUrl;
        //String imgUrl = imgpath;



        //imgUrl = "filters:format(gif)/" + imgpath;

        System.out.println(ImageUrlHelper.getThumbnailUrl("http://thumbor.felinkapps.com", imgUrl));
    }
    @Test
    public void checkHttp()
    {
        String  url = "Http://thumbor.felinkapps.com/kr29_3x7wSY7n5GkPpsMKyBwVME=/filters:format(webp)/drnews/img/2016/09/07/newsimg_69f85bde33fe568932e5f629697f4595.jpg";
        System.out.println(StringUtils.containsIgnoreCase(url, "http://"));
    }


}
