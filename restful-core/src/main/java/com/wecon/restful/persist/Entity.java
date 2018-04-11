package com.wecon.restful.persist;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实体抽象类
 * @author sean
 */
public abstract class Entity
{
	public void fill(ResultSet set) throws SQLException
	{
		Field[] fields = EntityHolder.reflect(this.getClass());
		try
		{
			for (Field it : fields)
			{
				it.setAccessible(true);

				String key = null;
				Column col = it.getAnnotation(Column.class);
				if (col != null)
				{
					key = col.value();
				}
				else
				{
					key = it.getName();
				}

				if (set.getObject(key) != null)
				{
					it.set(this, set.getObject(key));
				}
			}
		}
		catch (Exception e)
		{
			throw new SQLException(e);
		}
	}
}
