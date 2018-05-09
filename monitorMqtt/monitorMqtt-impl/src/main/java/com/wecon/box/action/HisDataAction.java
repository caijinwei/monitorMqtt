package com.wecon.box.action;

import com.alibaba.fastjson.JSONObject;
import com.wecon.box.api.DataApi;
import com.wecon.box.entity.ConnData;
import com.wecon.box.entity.Page;
import com.wecon.restful.core.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cai95 on 2018/5/7.
 */
@RestController
@RequestMapping(value = "hisData")
public class HisDataAction {
    @Autowired
    DataApi dataApi;

    @RequestMapping(value = "getHisData")
    public Output getHisData(@RequestParam("serverId") long serverId, @RequestParam("pageIndex") int pageIndex, @RequestParam("pageSize") int pageSize,
                             @RequestParam("start_date") String start_date, @RequestParam("end_date") String end_date) {
        Page<ConnData> page = dataApi.getAllserverData(serverId, start_date, end_date, pageIndex, pageSize);
        System.out.println("获取得到page的size-----------" + page.getList().size());
        for (ConnData data : page.getList()) {
            data.toString();
        }
        JSONObject data = new JSONObject();
        data.put("data", page);
        return new Output(data);
    }

    @RequestMapping(value = "getHisChartData")
    public Output getHisChartData(@RequestParam("serverId") long serverId,
                                  @RequestParam("start_date") String start_date, @RequestParam("end_date") String end_date) {
        List<ConnData> dataList = dataApi.getAllserverData(serverId, start_date, end_date);

        for(ConnData data : dataList ){
            System.out.println(data.toString());
        }

        List<String> timeList = new ArrayList<>();
        List<Integer> resultDatas = new ArrayList<>();
        JSONObject data = new JSONObject();
        for(int i =0 ;i< dataList.size();i++){

//            if(i==0||i==dataList.size()-1){
                timeList.add(dataList.get(i).createTime);
//            }else{
//                timeList.add("");
//            }
            resultDatas.add(dataList.get(i).data);
        }
        data.put("data", dataList);
        data.put("timeList",timeList);
        data.put("dataList",resultDatas);
        return new Output(data);
    }
}
