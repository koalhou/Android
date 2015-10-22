package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;

/**
 * 设置站点提醒信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SetSationRemindInfoRes extends AbstractRes
{

    /** ETag */
    private String ETag;


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

         
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[设置站点提醒信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getETag()
    {
        return ETag;
    }



}
