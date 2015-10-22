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
 * @version $Revision 1.0 $ 2013-4-9 上午10:15:01
 */
public class AlarmInfo {

	/**
	 * 告警消息ID
	 */
	@XmlElement(name = "alarm_id")
	private String alarmId;

	/**
	 * 告警类型
	 */
	@XmlElement(name = "alarm_type")
	private String alarmType;

	/**
	 * 告警内容，超速告警为超速发生时车辆的时速，超载为照片的URI
	 */
	@XmlElement(name = "alarm_cont")
	private String alarmCont;

	/**
	 * 告警发生时间。格式：yyyymmddhh24miss
	 */
	@XmlElement(name = "alarm_time")
	private String alarmTime;

	/**
	 * 告警结束时间。格式：yyyymmddhh24miss
	 */
	@XmlElement(name = "alarm_end")
	private String alarmEnd;

	/**
	 * 告警确认时间。格式：yyyymmddhh24miss
	 */
	@XmlElement(name = "conf_time")
	private String confTime;

	/**
	 * 告警确认用户编码
	 */
	@XmlElement(name = "conf_usr_id")
	private String confUsrName;

	/**
	 * 处理意见
	 */
	@XmlElement(name = "conf_info")
	private String confInfo;

	/**
	 * 告警车辆vin
	 */
	private String vin;

	/**
	 * 车牌号
	 */
	private String ln;

	/**
	 * 司机姓名
	 */
	private String driver;

	/**
	 * 车队
	 */
	@XmlElement(name = "orga_id")
	private String orgaId;

	/**
	 * 告警标志（是否为已处理）0– 未处理 1– 已处理 2– 处理中
	 */
	@XmlElement(name = "deal_flag")
	private String dealFlag;

	/**
	 * 告警发生时的时速，可能为空
	 */
	@JsonIgnore
	private String speeding;

	/**
	 * 照片ID，可能为空
	 */
	@JsonIgnore
	private String photoId;

	/**
	 * @return Returns the orgaId.
	 */
	public String getOrgaId() {
		return orgaId;
	}

	/**
	 * @param orgaId
	 *            The orgaId to set.
	 */
	public void setOrgaId(String orgaId) {
		this.orgaId = orgaId;
	}

	/**
	 * @return Returns the ln.
	 */
	public String getLn() {
		return ln;
	}

	/**
	 * @param ln
	 *            The ln to set.
	 */
	public void setLn(String ln) {
		this.ln = ln;
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
	 * @return Returns the alarmType.
	 */
	public String getAlarmType() {
		return alarmType;
	}

	/**
	 * @param alarmType
	 *            The alarmType to set.
	 */
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	/**
	 * @return Returns the alarmCont.
	 */
	public String getAlarmCont() {
		return alarmCont;
	}

	/**
	 * @param alarmCont
	 *            The alarmCont to set.
	 */
	public void setAlarmCont(String alarmCont) {
		this.alarmCont = alarmCont;
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

	/**
	 * @return Returns the alarmEnd.
	 */
	public String getAlarmEnd() {
		return alarmEnd;
	}

	/**
	 * @param alarmEnd
	 *            The alarmEnd to set.
	 */
	public void setAlarmEnd(String alarmEnd) {
		this.alarmEnd = alarmEnd;
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
	 * @return Returns the confUsrName.
	 */
	public String getConfUsrName() {
		return confUsrName;
	}

	/**
	 * @param confUsrName
	 *            The confUsrName to set.
	 */
	public void setConfUsrName(String confUsrName) {
		this.confUsrName = confUsrName;
	}

	/**
	 * @return Returns the confInfo.
	 */
	public String getConfInfo() {
		return confInfo;
	}

	/**
	 * @param confInfo
	 *            The confInfo to set.
	 */
	public void setConfInfo(String confInfo) {
		this.confInfo = confInfo;
	}

	/**
	 * @return Returns the vin.
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin
	 *            The vin to set.
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return Returns the driver.
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            The driver to set.
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return Returns the dealFlag.
	 */
	public String getDealFlag() {
		return dealFlag;
	}

	/**
	 * @param dealFlag
	 *            The dealFlag to set.
	 */
	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	/**
	 * @return Returns the speeding.
	 */
	public String getSpeeding() {
		return speeding;
	}

	/**
	 * @param speeding
	 *            The speeding to set.
	 */
	public void setSpeeding(String speeding) {
		this.speeding = speeding;
	}

	/**
	 * @return Returns the photoId.
	 */
	public String getPhotoId() {
		return photoId;
	}

	/**
	 * @param photoId
	 *            The photoId to set.
	 */
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AlarmInfo [alarmId=" + alarmId + ", alarmType=" + alarmType
				+ ", alarmCont=" + alarmCont + ", alarmTime=" + alarmTime
				+ ", alarmEnd=" + alarmEnd + ", confTime=" + confTime
				+ ", confUsrName=" + confUsrName + ", confInfo=" + confInfo
				+ ", vin=" + vin + ", ln=" + ln + ", driver=" + driver
				+ ", orgaId=" + orgaId + ", dealFlag=" + dealFlag
				+ ", speeding=" + speeding + ", photoId=" + photoId + "]";
	}
}
