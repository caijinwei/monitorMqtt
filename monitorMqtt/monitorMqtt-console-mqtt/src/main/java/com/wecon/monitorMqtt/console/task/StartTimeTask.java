/**
 * @功能说明:  启动定时任务
 * @创建人  : lph
 * @创建时间:  2017年07月26日 下午3:44:06
 * @修改人  : 
 * @修改时间: 
 * @修改描述: 
 * @Copyright (c)2017 福州富昌维控电子科技有限公司-版权所有
 */
package com.wecon.monitorMqtt.console.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartTimeTask {
	private static Logger logger = LoggerFactory.getLogger(StartTimeTask.class);
	private static final String MQTT_JOB = "MqttJob";
	private static final String BOX_NOTIFY_JOB = "BoxNotiryJob";
	public void init() {
		new Thread() {
			public void run() {
				try {
					// 延迟5秒
					Thread.sleep(5000);
					// 任务管理
					// 实时监控是否连接上mqtt代理服务器
//					Scheduler scheduler = QuartzManage.getScheduler();
//					Trigger trigger = TimeTriggerUtil.getTrigger(MQTT_JOB, "2", 5);

//					QuartzManage.createJob(scheduler, trigger, MQTT_JOB, null, null, MonitorTaskJob.class);

					//Trigger trigger2 = TimeTriggerUtil.getTrigger(BOX_NOTIFY_JOB, "1", 20);
					//QuartzManage.createJob(scheduler, trigger2, BOX_NOTIFY_JOB, null, null, BoxNotifyTaskJob.class);

//					Trigger trigger2 = TimeTriggerUtil.getTrigger(BOX_NOTIFY_JOB, "1", 20);
					//QuartzManage.createJob(scheduler, trigger, MQTT_JOB, null, null, MonitorTaskJob.class);
//					QuartzManage.createJob(scheduler, trigger2, BOX_NOTIFY_JOB, null, null, BoxNotifyTaskJob.class);
					

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("启动定时任务失败，原因是：" + e.getMessage(), e);
				}
			}
		}.start();
	}

}
