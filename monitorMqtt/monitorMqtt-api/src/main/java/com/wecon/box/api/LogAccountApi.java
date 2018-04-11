package com.wecon.box.api;

import com.alibaba.fastjson.JSONArray;
import com.wecon.box.entity.LogAccount;
import com.wecon.box.entity.LogAccountFilter;
import com.wecon.box.entity.Page;

/**
 * Created by zengzhipeng on 2017/8/1.
 */
public interface LogAccountApi {
    /**
     * 增加日志
     *
     * @param log
     * @return
     */
    long addLog(LogAccount log);

    /**
     * 日志列表
     *
     * @param filter
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<LogAccount> getLogList(LogAccountFilter filter, int pageIndex, int pageSize);

    /**
     * 获取日志的一些初始化数据
     *
     * @return
     */
    LogAccount getLogInit();

    /**
     * 查询结构转为jsonarray
     *
     * @param sqlStr
     * @return
     */
    JSONArray getSelData(String sqlStr);
}
