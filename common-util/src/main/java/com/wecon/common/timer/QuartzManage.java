package com.wecon.common.timer;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author lph
 * @description 定时任务管理类
 * @date 2017.07.27
 */
@SuppressWarnings("all")
public class QuartzManage {
	private static final Log LOG = LogFactory.getLog(QuartzManage.class);
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME"; // 触发器名
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME"; // 触发器组名
	private static StdSchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

	/**
	 * 创建Scheduler代理
	 */
	public static Scheduler getScheduler() {
		Scheduler sched = null;
		try {
			Properties props = new Properties();
			props.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
			// Scheduler线程数
			props.put("org.quartz.threadPool.threadCount", "50");

			gSchedulerFactory.initialize(props);
			
			sched = gSchedulerFactory.getScheduler();
			
			sched.start();
		} catch (Exception e) {
			LOG.error("创建Scheduler代理失败，原因是：" + e.getMessage(), e);
		}
		return sched;
	}

	/**
	 * @Description: 创建Scheduler代理
	 * @Author: ligang @Date: 2012-8-9
	 * @LastEditTime:
	 * @param schedulerName
	 *            Scheduler名称
	 * @param threadCount
	 *            线程数
	 * @return
	 * @throws SchedulerException
	 */
	public static Scheduler createScheduler(String schedulerName, String threadCount) throws SchedulerException {
		Scheduler scheduler = null;
		Properties properties = new Properties();
		properties.put(StdSchedulerFactory.PROP_THREAD_POOL_CLASS, "org.quartz.simpl.SimpleThreadPool");
		// Scheduler名称
		properties.put("org.quartz.scheduler.instanceName", schedulerName);
		// Scheduler线程数
		properties.put("org.quartz.threadPool.threadCount", threadCount);
		// Scheduler工厂初始化
		gSchedulerFactory.initialize(properties);
		// 获取Scheduler
		scheduler = gSchedulerFactory.getScheduler();
		return scheduler;
	}

	/**
	 * @Description: 启动Scheduler
	 * @Author: ligang @Date: 2012-8-9
	 * @LastEditTime:
	 * @param scheduler
	 *            Scheduler对象
	 * @throws SchedulerException
	 */
	public static void startScheduler(Scheduler scheduler) throws SchedulerException {
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}
	}

	/**
	 * @Description: 暂停Scheduler
	 * @Author: ligang @Date: 2012-8-9
	 * @LastEditTime:
	 * @param scheduler
	 *            Scheduler对象
	 * @throws SchedulerException
	 */
	public static void pauseScheduler(Scheduler scheduler) throws SchedulerException {
		// if (!scheduler.isInStandbyMode()) {
		scheduler.standby();
		// }
	}

	/**
	 * @Description: 关闭Scheduler
	 * @Author: ligang @Date: 2012-8-9
	 * @LastEditTime:
	 * @param scheduler
	 *            Scheduler对象
	 * @throws SchedulerException
	 */
	public static void shutdownScheduler(Scheduler scheduler) throws SchedulerException {
		if (!scheduler.isShutdown()) {
			scheduler.shutdown();
		}
	}

	/**
	 * @Description: 创建JOB
	 * @Author: ligang @Date: 2012-8-10
	 * @LastEditTime:
	 * @param scheduler
	 * @param trigger
	 * @param jobName
	 * @param groupName
	 *            (暂时不用)
	 * @param jobClass
	 * @throws SchedulerException
	 */
	public static void createJob(Scheduler scheduler, Trigger trigger, String jobName, String groupName, Object colBean,
			Class jobClass) throws SchedulerException {
		JobDetail job = scheduler.getJobDetail(jobName, JOB_GROUP_NAME);
		if (job == null) {
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, jobClass);
			
			jobDetail.getJobDataMap().put("key", colBean);
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			modifyJob(jobName, jobClass, trigger, colBean, scheduler);
		}
	}

	/**
	 * @Description: 修改Job
	 * @Author: ligang @Date: 2012-8-10
	 * @LastEditTime:
	 * @param jobName
	 *            Job名称
	 * @param jobClass
	 * @param trigger
	 * @param colBean
	 *            采集信息对象
	 * @param schel
	 *            Scheduler对象
	 * @throws SchedulerException
	 */
	public static void modifyJob(String jobName, Class jobClass, Trigger trigger, Object colBean, Scheduler schel)
			throws SchedulerException {
		removeJob(jobName, schel);
		createJob(schel, trigger, jobName, null, colBean, jobClass);
	}

	/**
	 * @Description: 移除Job
	 * @Author: ligang @Date: 2012-8-10
	 * @LastEditTime:
	 * @param jobName
	 *            任务名
	 * @param sched
	 *            Scheduler对象
	 * @throws SchedulerException
	 */
	public static void removeJob(String jobName, Scheduler sched) throws SchedulerException {
		// 停止触发器
		sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);
		// 移除触发器
		sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);
		// 删除任务
		sched.deleteJob(jobName, JOB_GROUP_NAME);
	}

	/**
	 * 停止一个任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param sched
	 *            Scheduler代理
	 * @throws Exception
	 */
	public static void disJob(String jobName, Scheduler sched) throws SchedulerException {
		// 停止触发器
		sched.pauseJob(jobName, JOB_GROUP_NAME);
	}

	/**
	 * 启动一个任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param sched
	 *            Scheduler代理
	 * @throws Exception
	 */
	public static void startJob(String jobName, Scheduler sched) throws SchedulerException {
		sched.resumeJob(jobName, JOB_GROUP_NAME);

	}

	/**
	 * 获取一个任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param sched
	 *            Scheduler代理
	 * @throws Exception
	 */
	public static JobDetail getJob(String jobName, Scheduler sched) throws SchedulerException {
		return sched.getJobDetail(jobName, JOB_GROUP_NAME);
	}

}
