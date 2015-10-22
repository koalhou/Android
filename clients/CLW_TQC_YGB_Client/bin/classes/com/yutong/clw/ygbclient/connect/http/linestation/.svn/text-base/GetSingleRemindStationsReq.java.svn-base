/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:14
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取单个站点提醒设置请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:14
 */
public class GetSingleRemindStationsReq extends AbstractReq
{

    private AreaType area_type;

    private StatusRange status_range;

    private String vehiche_vin;

    private String station_id;

    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            req.put("area_type", area_type.value());
            req.put("station_id", station_id);
            req.put("status_range", status_range.value());
            req.put("vehicle_vin", vehiche_vin);

            return req.toString();
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), "[获取单个站点提醒设置请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setStation_id(String station_id)
    {
        this.station_id = station_id;
    }

    public void setArea_type(AreaType area_type)
    {
        this.area_type = area_type;
    }

    public void setStatus_range(StatusRange status_range)
    {
        this.status_range = status_range;
    }

    public void setVehiche_vin(String vehiche_vin)
    {
        this.vehiche_vin = vehiche_vin;
    }

}
