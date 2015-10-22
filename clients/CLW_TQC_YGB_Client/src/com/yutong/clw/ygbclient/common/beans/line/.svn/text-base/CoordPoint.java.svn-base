/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 下午3:53:56
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.beans.line;

import org.json.JSONObject;

/**
 * 地理坐标实体类
 * 
 * @author zhangzhia 2013-10-22 下午3:53:56
 * 
 */
public class CoordPoint
{
	public CoordPoint (){}
	
	public CoordPoint (double lat,double lon){
		this.gps_lat=lat;
		this.gps_lon=lon;
	}
	
	/** 经度 */
    public Double gps_lon;
	/** 纬度 */
    public Double gps_lat;

	/**
	 * 
	 * @param optJSONObject
	 */
	public void parse(JSONObject optJSONObject)
	{
		gps_lon = optJSONObject.optDouble("gps_lon");
		gps_lat = optJSONObject.optDouble("gps_lat");

	}

}
