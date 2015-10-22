package com.yutong.axxc.parents.common.beans;

import com.yutong.axxc.parents.common.context.StringUtil;

/**
 * 站点实时提醒信息实体类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 * 
 */
public class StationRealTimeInfoBean {
	/**
	 * 按时间提醒,单位：分钟
	 */
public final static String REMIND_TYPE_BY_TIME="0";
/**
 * 按距离提醒，单位：米
 */
public final static String REMIND_TYPE_BY_DISTANT="1";
/**
 * 按站点个数提醒，单位：个
 */
public final static String REMIND_TYPE_BY_STATION="2";
	/** 车牌号码 */
	private String vehicle_plate;
	/** 站点id */
	private String station_id;
	/** 站点名称 */
	private String station_name;
	/** 备注名称 */
	private String remind_alias;
	/** 备注类型 */
	private String remind_type;
	/** 备注值 */
	private String remind_value;

	public String getStationFullName() {
		if (StringUtil.isNull(this.remind_alias))
			return this.station_name;
		return station_name + "(" + remind_alias + ")";
	}

	public String getVehicle_plate() {
		return vehicle_plate;
	}

	public void setVehicle_plate(String vehicle_plate) {
		this.vehicle_plate = vehicle_plate;
	}

	public String getStation_id() {
		return station_id;
	}

	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getRemind_alias() {
		return remind_alias;
	}

	public void setRemind_alias(String remind_alias) {
		this.remind_alias = remind_alias;
	}

	public String getRemind_type() {
		return remind_type;
	}

	public void setRemind_type(String remind_type) {
		this.remind_type = remind_type;
	}

	public String getRemind_value() {
		return remind_value;
	}

	public void setRemind_value(String remind_value) {
		this.remind_value = remind_value;
	}

}
