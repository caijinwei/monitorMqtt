package com.wecon.restful.persist;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * 单表读写工具
 * @author sean
 */
public class Dao
{
	/**
	 * 持久化实体
	 * @param dao
	 * @param tableName
	 * @param entity
	 * @return
	 */
	public static <E extends Entity> long persist(JdbcTemplate dao, final String tableName, final E entity)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		dao.update(new PreparedStatementCreator()
		{
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException
			{
				Field[] fields = EntityHolder.reflect(entity.getClass());

				StringBuilder sql = new StringBuilder();
				sql.append("insert into ").append(tableName).append(" (");
				for (Field it : fields)
				{
					Column col = it.getAnnotation(Column.class);
					if (col != null)
					{
						sql.append(col.value()).append(",");
					}
					else
					{
						sql.append(it.getName()).append(",");
					}
				}
				sql.setCharAt(sql.length() - 1, ')');

				List<Object> params = new LinkedList<>();
				sql.append(" values (");
				for (Field it : fields)
				{
					sql.append("?,");
					try
					{
						params.add(it.get(entity));
					}
					catch (Exception e)
					{
						throw new SQLException(e);
					}
				}
				sql.setCharAt(sql.length() - 1, ')');

				System.out.println(sql.toString());

				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				for (int i = 1; i <= params.size(); i++)
				{
					ps.setObject(i, params.get(i - 1));
				}
				return ps;
			}
		}, keyHolder);

		if (keyHolder.getKey() != null)
		{
			return keyHolder.getKey().longValue();
		}
		return 0L;
	}

	/**
	 * 读取一条记录
	 * @param dao
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <E extends Entity> E loadById(JdbcTemplate dao, final Class<E> entityClass, String sql, Object[] params)
	{
		List<E> list = getList(dao, entityClass, sql, params);
		if (!list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

	/**
	 * 读取列表
	 * @param dao
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <E extends Entity> List<E> getList(JdbcTemplate dao, final Class<E> entityClass, String sql, Object[] params)
	{
		List<E> list = dao.query(sql, params, new RowMapper<E>()
		{
			@Override
			public E mapRow(ResultSet set, int rowNo) throws SQLException
			{
				try
				{
					E item = entityClass.newInstance();
					item.fill(set);
					return item;
				}
				catch (Exception e)
				{
					throw new SQLException(e);
				}
			}
		});
		return list;
	}

	/**
	 * 读取列表
	 * @param dao
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <E extends Entity> Map<Object, E> getMap(JdbcTemplate dao, final Class<E> entityClass, String sql, Object[] params, String keyName)
	{
		final Map<Object, E> map = new HashMap<Object, E>();
		dao.query(sql, params, new RowMapper<E>()
		{
			@Override
			public E mapRow(ResultSet set, int rowNo) throws SQLException
			{
				try
				{
					E item = entityClass.newInstance();
					item.fill(set);
					map.put(set.getObject(keyName), item);
					return item;
				}
				catch (Exception e)
				{
					throw new SQLException(e);
				}
			}
		});
		return map;
	}

	/**
	 * 读取列表
	 * @param dao
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <E extends Entity> Map<Object, List<E>> getMapList(JdbcTemplate dao, final Class<E> entityClass, String sql, Object[] params, String keyName)
	{
		final Map<Object, List<E>> map = new HashMap<Object, List<E>>();
		dao.query(sql, params, new RowMapper<E>()
		{
			@Override
			public E mapRow(ResultSet set, int rowNo) throws SQLException
			{
				try
				{
					E item = entityClass.newInstance();
					item.fill(set);
					if (map.containsKey(set.getObject(keyName)))
					{
						map.get(set.getObject(keyName)).add(item);
					}
					else
					{
						List<E> liste = new ArrayList<E>();
						liste.add(item);
						map.put(set.getObject(keyName), liste);
					}
					return item;
				}
				catch (Exception e)
				{
					throw new SQLException(e);
				}
			}
		});
		return map;
	}
	/**
	 * 根据id读取列表
	 * @param dao
	 * @param entityClass
	 * @param tableName
	 * @param primaryKey
	 * @param idList
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <E extends Entity> List<E> getListByIdList(JdbcTemplate dao, final Class<E> entityClass, String tableName, String primaryKey,
			List<Object> idList)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(tableName).append(" where ").append(primaryKey).append(" in(");
		for (Object it : idList)
		{
			sql.append("?,");
		}
		sql.setCharAt(sql.length() - 1, ')');
		sql.append(" order by field(").append(primaryKey).append(',');
		for (Object it : idList)
		{
			sql.append("?,");
		}
		sql.setCharAt(sql.length() - 1, ')');
		idList.addAll(idList);

		List<E> list = dao.query(sql.toString(), idList.toArray(new Object[idList.size()]), new RowMapper<E>()
		{
			@Override
			public E mapRow(ResultSet set, int rowNo) throws SQLException
			{
				try
				{
					E item = entityClass.newInstance();
					item.fill(set);
					return item;
				}
				catch (Exception e)
				{
					throw new SQLException(e);
				}
			}
		});
		return list;
	}
}
