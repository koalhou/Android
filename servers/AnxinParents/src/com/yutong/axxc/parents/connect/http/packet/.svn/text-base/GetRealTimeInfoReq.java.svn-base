package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 获取相对实时数据信息请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetRealTimeInfoReq extends AbstractReq
{
    /** 学生Id */
    private String cld_id;

    private String vehicle_vin;
    
    /** 站点Id */
    private String station_id;
    
    private String remind_type;
    
    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            req.put("cld_id", cld_id);
            req.put("vehicle_vin", vehicle_vin);
            req.put("station_id", station_id);
            req.put("remind_type", remind_type);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取相对实时数据信息请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

    public void setVehicle_vin(String vehicle_vin)
    {
        this.vehicle_vin = vehicle_vin;
    }

    public void setStation_id(String station_id)
    {
        this.station_id = station_id;
    }

    public void setRemind_type(String remind_type)
    {
        this.remind_type = remind_type;
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/station/msg/remind";
    }
}
