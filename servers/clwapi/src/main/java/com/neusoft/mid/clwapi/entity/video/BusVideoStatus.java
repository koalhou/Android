package com.neusoft.mid.clwapi.entity.video;

import java.io.Serializable;
/**
 * @author wyg
 */
public class BusVideoStatus implements Serializable {

	private static final long serialVersionUID = -3742903282731382217L;

	public String vin;
	public String roadCondition; //路况视频状态
	public String wholeBus; //整车视频状态
	public String busDriver;//司机视频状态
	public String carDoor;//车门视频状态
	public String accFlag;//点火状态。1：开；0：关
	public String brand;//摄像头品牌   HI-海康  DA-大华
	public String vehicleNum;//车牌号
	public String is3GOnline;//3G设备状态：0 可用 1-不可用
	public String vehicleOnline;//车辆是否在线：0-不在线，1-在线
	public String realServer;//3G视频实时播放服务器地址
	public String authServer;//3G视频认证服务器地址
	
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getRoadCondition() {
		return roadCondition;
	}
	public void setRoadCondition(String roadCondition) {
		this.roadCondition = roadCondition;
	}
	public String getWholeBus() {
		return wholeBus;
	}
	public void setWholeBus(String wholeBus) {
		this.wholeBus = wholeBus;
	}
	public String getBusDriver() {
		return busDriver;
	}
	public void setBusDriver(String busDriver) {
		this.busDriver = busDriver;
	}
	public String getCarDoor() {
		return carDoor;
	}
	public void setCarDoor(String carDoor) {
		this.carDoor = carDoor;
	}
	public String getAccFlag() {
		return accFlag;
	}
	public void setAccFlag(String accFlag) {
		this.accFlag = accFlag;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getVehicleNum() {
		return vehicleNum;
	}
	public void setVehicleNum(String vehicleNum) {
		this.vehicleNum = vehicleNum;
	}
	
	public String getIs3GOnline() {
		return is3GOnline;
	}
	public void setIs3GOnline(String is3gOnline) {
		is3GOnline = is3gOnline;
	}
	public String getVehicleOnline() {
		return vehicleOnline;
	}
	public void setVehicleOnline(String vehicleOnline) {
		this.vehicleOnline = vehicleOnline;
	}
	public String getRealServer() {
		return realServer;
	}
	public void setRealServer(String realServer) {
		this.realServer = realServer;
	}
	public String getAuthServer() {
		return authServer;
	}
	public void setAuthServer(String authServer) {
		this.authServer = authServer;
	}
	
}
