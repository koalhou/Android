package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 获取学生站点信息请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VehicleInfoReq extends AbstractReq {

    private String vehicle_vin;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        return null;
    }

    public void setVehicle_vin(String vehicle_vin)
    {
        this.vehicle_vin = vehicle_vin;
    }



    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/vehicle/" + vehicle_vin;
    }
}
