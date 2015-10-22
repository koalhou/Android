package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StationRealTimeInfoBean;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;

/**
 * 获取站点提醒信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetRealTimeInfoRes extends AbstractRes
{
    /** 站点实时提示信息 */
    private StationRealTimeInfoBean stationRealTimeInfoBean;

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

            stationRealTimeInfoBean = new StationRealTimeInfoBean();

            stationRealTimeInfoBean.setStation_id(res.optString("station_id"));
            
            stationRealTimeInfoBean.setVehicle_plate(res.optString("vehicle_plate"));
            stationRealTimeInfoBean.setStation_name(res.optString("station_name"));
            stationRealTimeInfoBean.setRemind_alias(res.optString("remind_alias"));
            stationRealTimeInfoBean.setRemind_type(res.optString("remind_type"));
            stationRealTimeInfoBean.setRemind_value(res.optString("remind_value"));
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取站点提醒信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public StationRealTimeInfoBean getStationRealTimeInfoBean()
    {
        return stationRealTimeInfoBean;
    }


}
