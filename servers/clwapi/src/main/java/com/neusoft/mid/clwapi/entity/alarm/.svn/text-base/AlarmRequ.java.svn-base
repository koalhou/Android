/**
 * @(#)AlarmInfo.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.alarm;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 上午9:36:45
 */
public class AlarmRequ {

	/**
	 * 0 – 查询全部 1 – 只查询未处理 2 – 只查询已处理
	 */
	private String operat;

	/**
	 * 每页元素的数量
	 */
	private String num;

	/**
	 * 开始时间，格式yyyyMMddHH24mmss
	 */
	@XmlElement(name = "start_time")
	private String startTime;

	/**
	 * 结束时间，格式yyyyMMddHH24mmss
	 */
	@XmlElement(name = "end_time")
	private String endTime;

	/**
	 * 0 – 查询全部 1 – 只查询超速 2 – 只查询超载
	 */
	private String type;

	/**
	 * 获取第几页
	 */
	private String page;

	/**
	 * 企业ID
	 */
	@JsonIgnore
	private String enId;

	/**
	 * 超速标志
	 */
	@JsonIgnore
	private Object speedFlag;

	/**
	 * 超载标志
	 */
	@JsonIgnore
	private Object overLoadFlag;

	/**
	 * 开始行
	 */
	@JsonIgnore
	private String startRow;

	/**
	 * 结束行
	 */
	@JsonIgnore
	private String endRow;

	/**
	 * 告警消息ID
	 */
	@XmlElement(name = "alarm_id")
	private String alarmId;

	/**
	 * 告警的处理时间，上传“0”代表没有处理时间，格式为yyyymmddhh24miss
	 */
	@XmlElement(name = "conf_time")
	private String confTime;

	/**
	 * 告警的发生时间
	 */
	@XmlElement(name = "alarm_time")
	private String alarmTime;
	
	/**
	 * 组织ID，限制查看权限
	 */
	private String organizationId;

	/**
	 * @return Returns the organizationId.
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId The organizationId to set.
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return Returns the alarmId.
	 */
	public String getAlarmId() {
		return alarmId;
	}

	/**
	 * @param alarmId
	 *            The alarmId to set.
	 */
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	/**
	 * @return Returns the confTime.
	 */
	public String getConfTime() {
		return confTime;
	}

	/**
	 * @param confTime
	 *            The confTime to set.
	 */
	public void setConfTime(String confTime) {
		this.confTime = confTime;
	}

	/**
	 * @return Returns the startRow.
	 */
	public String getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow
	 *            The startRow to set.
	 */
	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return Returns the endRow.
	 */
	public String getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow
	 *            The endRow to set.
	 */
	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return Returns the speedFlag.
	 */
	public Object getSpeedFlag() {
		return speedFlag;
	}

	/**
	 * @return Returns the overLoadFlag.
	 */
	public Object getOverLoadFlag() {
		return overLoadFlag;
	}

	/**
	 * @param speedFlag
	 *            The speedFlag to set.
	 */
	public void setSpeedFlag(Object speedFlag) {
		this.speedFlag = speedFlag;
	}

	/**
	 * @param overLoadFlag
	 *            The overLoadFlag to set.
	 */
	public void setOverLoadFlag(Object overLoadFlag) {
		this.overLoadFlag = overLoadFlag;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the enId.
	 */
	public String getEnId() {
		return enId;
	}

	/**
	 * @param enId
	 *            The enId to set.
	 */
	public void setEnId(String enId) {
		this.enId = enId;
	}

	/**
	 * @return Returns the operat.
	 */
	public String getOperat() {
		return operat;
	}

	/**
	 * @param operat
	 *            The operat to set.
	 */
	public void setOperat(String operat) {
		this.operat = operat;
	}

	/**
	 * @return Returns the num.
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num
	 *            The num to set.
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return Returns the startTime.
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            The startTime to set.
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return Returns the endTime.
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            The endTime to set.
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return Returns the alarmTime.
	 */
	public String getAlarmTime() {
		return alarmTime;
	}

	/**
	 * @param alarmTime
	 *            The alarmTime to set.
	 */
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

}
