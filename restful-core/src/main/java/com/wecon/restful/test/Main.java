package com.wecon.restful.test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Main
{
	public static void main(String[] arr) throws Exception
	{
		String html = FileUtils.readFileToString(new File("C:/Users/sean/Desktop/youtube.txt"));
		
		String script = null;
		Pattern scriptPattern = Pattern.compile(".*?(<script>var ytplayer.*?ytplayer.load.*?</script>).*?");
		Matcher scriptMatcher = scriptPattern.matcher(html);
		if (scriptMatcher.find())
		{
			script = scriptMatcher.group(1);
		}
		System.out.println(script);
		
		String args = null;
		Pattern argsPattern = Pattern.compile(".*?\"args\":(.*?),\"params\".*?");
		Matcher argsMatcher = argsPattern.matcher(script);
		if (argsMatcher.find())
		{
			args = argsMatcher.group(1);
		}
		System.out.println(args);
		
		/**
		 * url_encoded_fmt_stream_map
		 * adaptive_fmts
		 */
		JSONObject json = JSON.parseObject(args);
		String adaptive_fmts = json.getString("adaptive_fmts");
//		adaptive_fmts = URLDecoder.decode(adaptive_fmts);
		System.out.println(adaptive_fmts);
		String[] tmp = adaptive_fmts.split(",");
		for(String it : tmp)
		{
			System.out.println(it);
		}
	}
}
