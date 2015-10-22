package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.VehicleInfoBean;

/**
 * 获取车辆信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class VehicleInfoRes extends AbstractRes
{
    /** 车辆信息 */
    private VehicleInfoBean vehicleInfoBean;

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

            JSONObject jsonObject = res.getJSONObject("vehicle_info");
            if(jsonObject != null)
            {
                
                vehicleInfoBean = new VehicleInfoBean();

                vehicleInfoBean.setVehicle_vin(jsonObject.optString("vehicle_vin"));
                vehicleInfoBean.setVehicle_code(jsonObject.optString("vehicle_code"));
                vehicleInfoBean.setVehicle_plate(jsonObject.optString("vehicle_plate"));
                vehicleInfoBean.setVehicle_driver(jsonObject.optString("vehicle_driver"));
                vehicleInfoBean.setVehicle_color(jsonObject.optString("vehicle_color"));
 

            }



            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取车辆信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public VehicleInfoBean getVehicleInfoBean()
    {
        return vehicleInfoBean;
    }


}
