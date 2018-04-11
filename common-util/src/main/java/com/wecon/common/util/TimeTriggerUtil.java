package com.wecon.common.util;

import java.text.ParseException;
import java.util.Date;
import org.quartz.CronTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

public class TimeTriggerUtil {
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	/**
	 * @Description: 获取Quartz的Trigger(该方法只能是间隔型的周期)
	 * @Author: lph @Date: 2017-07-26
	 * @LastEditTime:
	 * @param jobName
	 *            AgentID_采集规则ID
	 * @param agentId
	 *            AgentID (暂时不用)
	 * @param cycletype
	 *            周期类型:1、秒；2、分；3、时
	 * @param cyclevalue
	 *            周期值
	 * @return
	 */
	public static Trigger getTrigger(String jobName, String cycletype, int cyclevalue) {
		return getTrigger(jobName, cycletype, cyclevalue, null);
	}

	public static Trigger getTrigger(String jobName, String cycletype, int cyclevalue, Date startTime) {
		Trigger trigger = null;
		if (cycletype.equals("1")) {
			trigger = TriggerUtils.makeSecondlyTrigger(cyclevalue);
		} else if (cycletype.equals("2")) {
			trigger = TriggerUtils.makeMinutelyTrigger(cyclevalue);
		} else if (cycletype.equals("3")) {
			trigger = TriggerUtils.makeHourlyTrigger(cyclevalue);
		}
		// Trigger分组命名
		if (trigger != null) {
			trigger.setName(jobName);
			trigger.setGroup(TRIGGER_GROUP_NAME);
		}
		if (startTime == null) {
			startTime = new Date();
		}
		trigger.setStartTime(startTime);
		// 忽略失败 job，等待下次触发；
		trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		return trigger;
	}

	/**
	 * @Description: 获取Quartz的Trigger(该方法只能是固定型的周期)
	 * @Author:lph @Date: 2017-07-26
	 * @LastEditTime:
	 * @param trigName
	 *            trigger名称
	 * @param group
	 *            trigger所属组名称
	 * @param cronExpression
	 *            crontab表达式
	 * @param startTime
	 *            开始时间(填null默认为立即执行)
	 * @param endTime
	 *            结束时间(可填null)
	 * @return CronTrigger类型的触发器
	 * @throws ParseException
	 *             cronExpression格式异常
	 */
	public static Trigger getTrigger(String trigName, String group, String cronExpression, Date startTime, Date endTime)
			throws ParseException {
		Trigger trigger = null;
		try {
			// Trigger分组命名
			if (group == null || "".equals(group)) {
				// 若组名未提供，则使用默认的组名
				group = TRIGGER_GROUP_NAME;
			}
			trigger = new CronTrigger(trigName, group, cronExpression);
			// 若开始时间未填写，则默认立即执行
			if (startTime == null) {
				startTime = new Date();
			}
			trigger.setStartTime(startTime);
			// 若有设置结束时间
			if (endTime != null) {
				trigger.setEndTime(endTime);
			}
			// 忽略失败 job，等待下次触发；
			trigger.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		} catch (ParseException e) {
			throw e;
		}
		return trigger;
	}
}
