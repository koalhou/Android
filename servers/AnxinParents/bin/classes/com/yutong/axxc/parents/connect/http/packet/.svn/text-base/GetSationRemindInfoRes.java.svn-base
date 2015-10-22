package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;

/**
 * 获取站点提醒信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetSationRemindInfoRes extends AbstractRes
{
    /** 站点提示信息 */
    private List<StationRemindInfoBean> stationRemindInfoBeans;

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

            JSONArray jsonArray = res.getJSONArray("station_remind_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                stationRemindInfoBeans = new ArrayList<StationRemindInfoBean>();
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    
                    if (jsonObject != null)
                    {
                        StationRemindInfoBean obj = new StationRemindInfoBean();
                        obj.setRemind_id(jsonObject.optString("remind_id"));
                        obj.setStation_id(jsonObject.optString("station_id"));
                        obj.setRemind_alias(jsonObject.optString("remind_alias"));
                        obj.setRemind_type(jsonObject.optString("remind_type"));
                        obj.setRemind_value(jsonObject.optString("remind_value"));
                        obj.setValid(jsonObject.optString("valid"));
 
                        stationRemindInfoBeans.add(obj);
                    }
                }
            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取站点提醒信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public List<StationRemindInfoBean> getStationRemindInfoBeans()
    {
        return stationRemindInfoBeans;
    }


}
