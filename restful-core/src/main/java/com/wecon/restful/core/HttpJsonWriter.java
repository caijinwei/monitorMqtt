package com.wecon.restful.core;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class HttpJsonWriter extends AbstractHttpMessageConverter<Object>
{
	private static final Logger logger = LogManager.getLogger(HttpJsonWriter.class.getName());

	@Override
	protected boolean supports(Class<?> clazz)
	{
		return true;
	}

	@Override
	protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException
	{
		return null;
	}

	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException
	{
		String json = null;
		if (obj instanceof Output)
		{
			Output output = (Output) obj;
			json = JSON.toJSONString(output, SerializerFeature.WriteMapNullValue);
		}
		else
		{
			Output output = new Output();
			output.result = obj;
			json = JSON.toJSONString(output, SerializerFeature.WriteMapNullValue);
		}

		OutputStream out = outputMessage.getBody();

		byte[] bytes = json.getBytes("utf-8");
		out.write(bytes);

		logger.debug("write json " + json);
	}
}
