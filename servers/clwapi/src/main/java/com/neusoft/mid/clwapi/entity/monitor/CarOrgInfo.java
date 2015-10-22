/**
 * @(#)RealTimeInfo.java 2013-3-26
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.monitor;

import javax.xml.bind.annotation.XmlElement;
/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-3-26 下午1:14:43
 */
public class CarOrgInfo {
	/**
	 * 节点类型。01：组织；02：车辆.
	 */
	@XmlElement(name = "data_type")
	private String dataType;
	
	/**
	 * 组织ID/车辆VIN.
	 */
	@XmlElement(name = "id")
	private String id;
	
	/**
	 * 组织名称/车牌号.
	 */
	@XmlElement(name = "name")
	private String name;
	
	/**
	 * 父组织ID.
	 */
	@XmlElement(name = "pid")
	private String pid;
	
	/**
	 * 当前状态,1-行驶；2-停驶；0-离线.
	 */
	@XmlElement(name = "car_status")
	private String carStatus;	
	
	/**
	 * 车辆维度.
	 */
	@XmlElement(name = "car_latitude")
	private String carLatitude;
	
	/**
	 * 车辆经度.
	 */
	@XmlElement(name = "car_longitude")
	private String carLongitude;
	
	/**
	 * 线路名称.
	 */
	@XmlElement(name = "route_name")
	private String routeName;
	
	/**
	 * 时速，单位km/h.
	 */
	@XmlElement(name = "speed")
	private String speed;
	
	/**
	 * 司机姓名.
	 */
	@XmlElement(name = "driver_name")
	private String driverName;
	
	/**
	 * 司机联系方式.
	 */
	@XmlElement(name = "driver_tel")
	private String driverTel;
	
	/**
	 * 跟车老师（司乘）姓名.
	 */
	@XmlElement(name = "attendant_name")
	private String attendantName;
	
	/**
	 * 跟车老师（司乘）联系方式.
	 */
	@XmlElement(name = "attendant_tel")
	private String attendantTel;
	
	/**
	 * 核载，车辆最大乘车人数.
	 */
	@XmlElement(name = "limit_number")
	private String limitNumber;
	
	/**
	 * 乘客人数.
	 */
	@XmlElement(name = "passenger_number")
	private String passengerNumber;
	
	/**
	 * 告警状态，0-无告警，1-有告警.
	 */
	@XmlElement(name = "alarm_status")
	private String alarmStatus;

	/**
	 * 提示告警状态，需要提示的告警种类：超速和超载
	 * alarm_notice_flag第一位表示超速标志
	 * alarm_notice_flag第一位表示超载标志.
	 */
	@XmlElement(name = "alarm_notice_flag")
	private String alarmNoticeFlag;

	/**
	 * @return Returns the dataType.
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType The dataType to set.
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the pid.
	 */
	public String getPid() {
		return pid;
	}

	/**
	 * @param pid The pid to set.
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}

	/**
	 * @return Returns the carStatus.
	 */
	public String getCarStatus() {
		return carStatus;
	}

	/**
	 * @param carStatus The carStatus to set.
	 */
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}

	/**
	 * @return Returns the carLatitude.
	 */
	public String getCarLatitude() {
		return carLatitude;
	}

	/**
	 * @param carLatitude The carLatitude to set.
	 */
	public void setCarLatitude(String carLatitude) {
		this.carLatitude = carLatitude;
	}

	/**
	 * @return Returns the carLongitude.
	 */
	public String getCarLongitude() {
		return carLongitude;
	}

	/**
	 * @param carLongitude The carLongitude to set.
	 */
	public void setCarLongitude(String carLongitude) {
		this.carLongitude = carLongitude;
	}

	/**
	 * @return Returns the routeName.
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * @param routeName The routeName to set.
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/**
	 * @return Returns the speed.
	 */
	public String getSpeed() {
		return speed;
	}

	/**
	 * @param speed The speed to set.
	 */
	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * @return Returns the driverName.
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName The driverName to set.
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return Returns the driverTel.
	 */
	public String getDriverTel() {
		return driverTel;
	}

	/**
	 * @param driverTel The driverTel to set.
	 */
	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	/**
	 * @return Returns the attendantName.
	 */
	public String getAttendantName() {
		return attendantName;
	}

	/**
	 * @param attendantName The attendantName to set.
	 */
	public void setAttendantName(String attendantName) {
		this.attendantName = attendantName;
	}

	/**
	 * @return Returns the attendantTel.
	 */
	public String getAttendantTel() {
		return attendantTel;
	}

	/**
	 * @param attendantTel The attendantTel to set.
	 */
	public void setAttendantTel(String attendantTel) {
		this.attendantTel = attendantTel;
	}

	/**
	 * @return Returns the limitNumber.
	 */
	public String getLimitNumber() {
		return limitNumber;
	}

	/**
	 * @param limitNumber The limitNumber to set.
	 */
	public void setLimitNumber(String limitNumber) {
		this.limitNumber = limitNumber;
	}

	/**
	 * @return Returns the passengerNumber.
	 */
	public String getPassengerNumber() {
		return passengerNumber;
	}

	/**
	 * @param passengerNumber The passengerNumber to set.
	 */
	public void setPassengerNumber(String passengerNumber) {
		this.passengerNumber = passengerNumber;
	}

	/**
	 * @return Returns the alarmStatus.
	 */
	public String getAlarmStatus() {
		return alarmStatus;
	}

	/**
	 * @param alarmStatus The alarmStatus to set.
	 */
	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	/**
	 * @return Returns the alarmNoticeFlag.
	 */
	public String getAlarmNoticeFlag() {
		return alarmNoticeFlag;
	}

	/**
	 * @param alarmNoticeFlag The alarmNoticeFlag to set.
	 */
	public void setAlarmNoticeFlag(String alarmNoticeFlag) {
		this.alarmNoticeFlag = alarmNoticeFlag;
	}
}
