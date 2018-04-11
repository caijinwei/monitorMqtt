package com.wecon.box.action;

import com.wecon.box.api.TestApi;
import com.wecon.restful.core.Output;
import com.wecon.restful.doc.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by cai95 on 2018/4/8.
 */
@RestController
@RequestMapping(value = "test")
public class TestAction {

    @Autowired
    TestApi testApi;

    @Label("jdbc连接测试")
    @RequestMapping(value = "testConnect")
    public Output testConnect(){
        System.out.println("------begin------");
//        testApi.testJdbcConn();
        System.out.println("---------------------------------------------");
        System.out.println("------end------");
        return new Output();
    }
}
