package com.wecon.monitorMqtt.console.util;

import java.util.Date;

public abstract class AbstractTask {
	public long taskId;
	/* 产生时间 */
	public Date generateTime = null;
    /* 提交执行时间 */
    public Date submitTime = null;
    /* 开始执行时间 */
    public Date beginExceuteTime = null;
    /* 执行完成时间 */
    public Date finishTime = null;

	public int type;
    
    public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public Date getGenerateTime() {
		return generateTime;
	}
	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getBeginExceuteTime() {
		return beginExceuteTime;
	}

	public void setBeginExceuteTime(Date beginExceuteTime) {
		this.beginExceuteTime = beginExceuteTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void run(){}
	
}
