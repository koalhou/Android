/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:38:35
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取车辆司机信息请求类
 * 
 * @author houjunhu 2014-07-08 上午9:38:30
 * 
 */
public class VehicheDriverDataReq extends AbstractReq
{
	// 车辆id信息集合
	private List<String> vehicle_vins;
	
	private List<String> line_ids;

	@Override
	public String packetMsgBody()
	{
		try
		{

			JSONArray req = new JSONArray();
			int size = vehicle_vins.size();
			for(int i = 0; i < size; i++)
			{
			    JSONObject obj = new JSONObject();
			    obj.put("vehicle_vin", vehicle_vins.get(i));
			    obj.put("line_id", line_ids.get(i));
			    req.put(obj);
			}
			return req.toString();
		}
		catch (Exception e)
		{
			Logger.e(this.getClass(), "[ 获取车辆司机信息请求类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}

	// 车辆id信息集合
	public void setVehicle_vins(List<String> vehicle_vins)
	{
		this.vehicle_vins = vehicle_vins;
	}

    public void setLine_ids(List<String> line_ids)
    {
        this.line_ids = line_ids;
    }
	
	
}
