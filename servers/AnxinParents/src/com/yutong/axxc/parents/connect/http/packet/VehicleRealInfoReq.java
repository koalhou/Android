package com.yutong.axxc.parents.connect.http.packet;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 获取车辆实时信息请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VehicleRealInfoReq extends AbstractReq {

    private List<String> vehicle_vins;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            
            if(vehicle_vins != null && vehicle_vins.size() > 0)
            for(String vehicle_id: vehicle_vins)
            {
                jsonArray.put(vehicle_id);
            }
            
            req.put("vehicle_vins", jsonArray);

            return req.toString();

            
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[获取车辆实时信息请求类]：组包请求时失败，详细信息：", e);
            return null;
        }
    }


    public void setVehicle_vins(List<String> vehicle_vins)
    {
        this.vehicle_vins = vehicle_vins;
    }


    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/vehicle/realinfo";
    }
}
