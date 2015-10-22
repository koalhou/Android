package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;

/**
 * 获取最新提醒信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetNewRemindInfoRes extends AbstractRes
{

    /** ETag */
    private String ETag;

    /** 站点提示信息 */
    private StationRemindInfoBean stationRemindInfoBean;

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

            ETag = res.optString("ETag");

            JSONObject jsonObject = res.getJSONObject("remind_info");
            
            if(jsonObject != null)
            {
                stationRemindInfoBean = new StationRemindInfoBean();

                stationRemindInfoBean.setStation_id(jsonObject.optString("station_id"));
                stationRemindInfoBean.setRemind_alias(jsonObject.optString("remind_alias"));
                stationRemindInfoBean.setRemind_type(jsonObject.optString("remind_type"));
                stationRemindInfoBean.setRemind_value(jsonObject.optString("remind_value"));
            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取最新提醒信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getETag()
    {
        return ETag;
    }

    public StationRemindInfoBean getStationRemindInfoBean()
    {
        return stationRemindInfoBean;
    }

  

}
