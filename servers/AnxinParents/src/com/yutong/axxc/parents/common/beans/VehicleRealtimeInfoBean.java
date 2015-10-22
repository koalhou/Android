
package com.yutong.axxc.parents.common.beans;


/**
 * 车辆实时信息实体类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class VehicleRealtimeInfoBean {

	/**
	 * 离线
	 */
	public final static  String STATUS_OFFLINE="0";
	/**
	 * 在线
	 */
	public final static  String STATUS_ONLINE="1";
	/**
	 * 停驶
	 */
	public final static  String STATUS_STOP="2";
	/**
	 * 行驶
	 */
	public final static  String STATUS_DRIVING="3";
	/**
	 * 点火
	 */
	public final static  String STATUS_FIRE="4";
    /** 车辆vin  */
    private String vehicle_vin;
    /**  经度 */
    private String gps_lon;
    /**  纬度 */
    private String gps_lat;
    /**  速度 单位：公里/小时*/
    private String speed;
    /** 方向角度， 0-360  （度）  */
    private String direction;
    /** 车辆状态，见文档  状态， 0：离线，1：在线，2：停驶，3：行驶，4：点火*/
    private String status;
    /** 更新时间  格式：yyyymmddhh24miss*/
    private String update_time;

    public String getVehicle_vin()
    {
        return vehicle_vin;
    }
    public void setVehicle_vin(String vehicle_vin)
    {
        this.vehicle_vin = vehicle_vin;
    }
    public String getGps_lon()
    {
        return gps_lon;
    }
    public void setGps_lon(String gps_lon)
    {
        this.gps_lon = gps_lon;
    }
    public String getGps_lat()
    {
        return gps_lat;
    }
    public void setGps_lat(String gps_lat)
    {
        this.gps_lat = gps_lat;
    }
    public String getSpeed()
    {
        return speed;
    }
    public void setSpeed(String speed)
    {
        this.speed = speed;
    }
    public String getDirection()
    {
        return direction;
    }
    public void setDirection(String direction)
    {
        this.direction = direction;
    }
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    public String getUpdate_time()
    {
        return update_time;
    }
    public void setUpdate_time(String update_time)
    {
        this.update_time = update_time;
    }
    
    

    
}
