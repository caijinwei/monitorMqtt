package com.wecon.restful.persist;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wecon.restful.core.ClassUtil;

public class EntityHolder
{
	private final static Map<String, Field[]> fields = new HashMap<>();

	public static Field[] reflect(Class<? extends Entity> cls)
	{
		String clazz = cls.getName();
		Field[] fs = fields.get(clazz);
		if (fs == null)
		{
			List<Field> list = new ArrayList<>();
			for (Field it : ClassUtil.getFields(cls))
			{
				// 过滤static/final字段
				if (!Modifier.isStatic(it.getModifiers()) && !Modifier.isFinal(it.getModifiers()))
				{
					list.add(it);
				}
			}
			fs = list.toArray(new Field[list.size()]);
			fields.put(clazz, fs);
		}
		return fs;
	}
}
