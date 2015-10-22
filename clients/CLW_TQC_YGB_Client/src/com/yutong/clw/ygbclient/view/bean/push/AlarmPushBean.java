/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-19 下午4:54:59
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bean.push;

import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindType;

/**
 * @author zhouzc 2013-11-19 下午4:54:59
 * 
 */
public class AlarmPushBean extends PushBean {
	
	private String remindId;
	
	private AreaType areaType;

	private RemindRange remindRange;

	private RemindType remindType;

	private StatusRange statusRange;
	
	private int remindValue;

	private String stationId;

	private String stationName;

	private String stationAlias;

	private String vehicleVin;

	private String vehicleNumber;

	private Double vehicleLon;

	private Double vehicleLat;

	private String lineid;
	
	private String linename;
	
	private String ruleId;//02:厂内      03：厂外
	
	public String getLinename() {
		return linename;
	}

	public void setLinename(String linename) {
		this.linename = linename;
	}

	public String getLineid() {
		return lineid;
	}

	public void setLineid(String lineid) {
		this.lineid = lineid;
	}

	public String getRemindId() {
		return remindId;
	}

	public void setRemindId(String remindId) {
		this.remindId = remindId;
	}

	public AreaType getAreaType() {
		return areaType;
	}

	public void setAreaType(AreaType areaType) {
		this.areaType = areaType;
	}

	public RemindRange getRemindRange() {
		return remindRange;
	}

	public void setRemindRange(RemindRange remindRange) {
		this.remindRange = remindRange;
	}

	public RemindType getRemindType() {
		return remindType;
	}

	public void setRemindType(RemindType remindType) {
		this.remindType = remindType;
	}

	public int getRemindValue() {
		return remindValue;
	}

	public void setRemindValue(int remindValue) {
		this.remindValue = remindValue;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationAlias() {
		return stationAlias;
	}

	public void setStationAlias(String stationAlias) {
		this.stationAlias = stationAlias;
	}

	public String getVehicleVin() {
		return vehicleVin;
	}

	public void setVehicleVin(String vehicleVin) {
		this.vehicleVin = vehicleVin;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public Double getVehicleLon() {
		return vehicleLon;
	}

	public void setVehicleLon(Double vehicleLon) {
		this.vehicleLon = vehicleLon;
	}

	public Double getVehicleLat() {
		return vehicleLat;
	}

	public void setVehicleLat(Double vehicleLat) {
		this.vehicleLat = vehicleLat;
	}

	public StatusRange getStatusRange() {
		return statusRange;
	}

	public void setStatusRange(StatusRange statusRange) {
		this.statusRange = statusRange;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	
	
}
