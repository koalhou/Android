package com.yutong.axxc.parents.connect.http.packet;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.PunchCardDateBean;

/**
 * 获取学生打卡日期响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentPunchCardDateRes extends AbstractRes
{
    private PunchCardDateBean punchCardDateBean = new PunchCardDateBean();

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

            JSONArray jsonArray = res.getJSONArray("date_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {// 遍历JSONArray
                    punchCardDateBean.getPunchCardDates().add(jsonArray.optString(i));
                }
            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "获取学生打卡日期响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public PunchCardDateBean getPunchCardDateBean()
    {
        return punchCardDateBean;
    }

    
    
}
