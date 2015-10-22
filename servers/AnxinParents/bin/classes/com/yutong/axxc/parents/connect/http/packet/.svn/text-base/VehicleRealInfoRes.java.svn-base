package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.LocationUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NeuGeoPoint;
import com.yutong.axxc.parents.common.beans.VehicleRealtimeInfoBean;

/**
 * 获取车辆信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class VehicleRealInfoRes extends AbstractRes
{
    /** 车辆信息 */
    private List<VehicleRealtimeInfoBean> vehicleRealInfoBeans;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);

            JSONArray jsonArray = res.getJSONArray("vehicle_real_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                vehicleRealInfoBeans = new ArrayList<VehicleRealtimeInfoBean>();
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject != null)
                    {
                        VehicleRealtimeInfoBean vehicleRealtimeInfoBean = new VehicleRealtimeInfoBean();

                        vehicleRealtimeInfoBean.setVehicle_vin(jsonObject.optString("vehicle_vin"));
                        
                      //直接转换为百度地图坐标
                        NeuGeoPoint bdGeoPoint = LocationUtils.fromWgs84ToBaidu(jsonObject.optString("longitude"), jsonObject.optString("latitude"));
                        vehicleRealtimeInfoBean.setGps_lon(String.valueOf(bdGeoPoint.getX()));
                        vehicleRealtimeInfoBean.setGps_lat(String.valueOf(bdGeoPoint.getY()));
                        
//                        vehicleRealtimeInfoBean.setGps_lon(jsonObject.optString("gps_lon"));
//                        vehicleRealtimeInfoBean.setGps_lat(jsonObject.optString("gps_lat"));
                        vehicleRealtimeInfoBean.setSpeed(jsonObject.optString("speed"));
                        vehicleRealtimeInfoBean.setDirection(jsonObject.optString("direction"));
                        vehicleRealtimeInfoBean.setStatus(jsonObject.optString("status"));
                        vehicleRealtimeInfoBean.setUpdate_time(jsonObject.optString("terminal_time"));

                        vehicleRealInfoBeans.add(vehicleRealtimeInfoBean);
                    }

                }
            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取车辆实时信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public List<VehicleRealtimeInfoBean> getVehicleRealInfoBeans()
    {
        return vehicleRealInfoBeans;
    }

}
