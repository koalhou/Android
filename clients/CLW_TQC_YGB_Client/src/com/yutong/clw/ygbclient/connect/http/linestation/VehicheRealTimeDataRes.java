/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:38:30
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VehicleRealtime;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取车辆实时信息响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:38:30
 */
public class VehicheRealTimeDataRes extends AbstractRes
{
    // 车辆实时信息，参见车辆实时信息
    private List<VehicleRealtime> realTimeInfos;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        try
        {
            JSONArray res = new JSONArray(jsonObject);
            realTimeInfos = new ArrayList<VehicleRealtime>();
            VehicleRealtime vehicleRealtime = null;
            JSONObject temp = null;
            for (int i = 0; i < res.length(); i++)
            {
                temp = res.optJSONObject(i);
                vehicleRealtime = new VehicleRealtime();
                vehicleRealtime.vehicle_vin = temp.optString("vehicle_vin");
                vehicleRealtime.vehicle_number = temp.optString("vehicle_number");
                vehicleRealtime.gps_lon = temp.optDouble("gps_lon");
                vehicleRealtime.gps_lat = temp.optDouble("gps_lat");
                vehicleRealtime.speed = temp.optInt("speed");
                vehicleRealtime.direction = temp.optInt("direction");
                vehicleRealtime.status = temp.optString("status");
                vehicleRealtime.update_time = DateUtils.strToDate(temp.optString("update_time"), DateUtils.LONG_TIME_FORMAT);
                realTimeInfos.add(vehicleRealtime);
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取车辆实时信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    // 车辆实时信息，参见车辆实时信息
    public List<VehicleRealtime> getRealTimeInfos()
    {
        return realTimeInfos;
    }

}
