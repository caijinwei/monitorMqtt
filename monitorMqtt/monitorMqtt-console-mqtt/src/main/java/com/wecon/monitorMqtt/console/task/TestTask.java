package com.wecon.monitorMqtt.console.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * Created by zengzhipeng on 2017/7/25.
 */
public class TestTask extends Thread{
    private static final Logger logger = LogManager.getLogger(TestTask.class);
    private static ApplicationContext applicationContext;
    public void run() {
        logger.info("TestTask run start");
    }
}
