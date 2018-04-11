package com.wecon.restful.core;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 类工具
 * @author sean
 */
public class ClassUtil
{
	/**
	 * 读取类的所有成员, 包括递归所有父类
	 * @param clazz
	 * @return
	 */
	public static Field[] getFields(Class<?> clazz)
	{
		Map<String, Field> container = new HashMap<>();
		getFieldInternal(clazz, container);
		Collection<Field> fields = container.values();
		return fields.toArray(new Field[fields.size()]);
	}

	private static void getFieldInternal(Class<?> clazz, Map<String, Field> container)
	{
		if (clazz != Object.class)
		{
			getFieldInternal(clazz.getSuperclass(), container);
			for (Field field : clazz.getDeclaredFields())
			{
				container.put(field.getName(), field);
			}
		}
	}
}
