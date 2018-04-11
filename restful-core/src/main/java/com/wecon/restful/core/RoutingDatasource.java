package com.wecon.restful.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDatasource extends AbstractRoutingDataSource
{
	@Override
	protected Object determineCurrentLookupKey()
	{
		if(AppContext.getSession() != null)
		{
			// 获取读写分离标记
			if (AppContext.getSession().master)
			{
				return "master";
			}
			else
			{
				// TODO slave 选择算法
				return "slave1";
			}	
		}
		return "master";
	}
}
