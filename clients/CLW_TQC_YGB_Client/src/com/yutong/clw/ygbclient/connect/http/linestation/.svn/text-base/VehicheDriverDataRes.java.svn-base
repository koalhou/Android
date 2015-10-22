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
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取车辆司机信息响应类
 * 
 * @author houjunhu 2014-07-08 上午9:38:30
 */
public class VehicheDriverDataRes extends AbstractRes
{
    // 车辆司机信息，参见车辆司机信息
    private List<VehicleDriver> DriverInfos;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        try
        {
            JSONArray res = new JSONArray(jsonObject);
            DriverInfos = new ArrayList<VehicleDriver>();
            VehicleDriver vehicleDriver = null;
            JSONObject temp = null;
            for (int i = 0; i < res.length(); i++)
            {
                temp = res.optJSONObject(i);
                vehicleDriver = new VehicleDriver();
                vehicleDriver.vehicle_vin = temp.optString("vehicle_vin");
                vehicleDriver.line_id = temp.optString("line_id");
                vehicleDriver.driver_name = temp.optString("driver_name");
                vehicleDriver.driver_tel = temp.optString("driver_tel");
                vehicleDriver.emp_code = temp.optString("emp_code");
                vehicleDriver.pic_url = temp.optString("url");
                DriverInfos.add(vehicleDriver);
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取车辆司机信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    // 车辆司机信息，参见车辆司机信息
    public List<VehicleDriver> getDriverInfos()
    {
        return DriverInfos;
    }

}
