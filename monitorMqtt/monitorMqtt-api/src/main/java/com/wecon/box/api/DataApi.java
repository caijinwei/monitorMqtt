package com.wecon.box.api;

import com.wecon.box.entity.AlarmData;
import com.wecon.box.entity.ConnData;
import com.wecon.box.entity.Page;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by cai95 on 2018/5/2.
 */
@Component
public interface DataApi {
    public void insertConnData(AlarmData alarmData);

    public Page<ConnData> getAllserverData(Long serverId, String start_date,String end_date , int pageIndex, int pageSize);
    public List<ConnData> getAllserverData(Long serverId, String start_date, String end_date);

}
