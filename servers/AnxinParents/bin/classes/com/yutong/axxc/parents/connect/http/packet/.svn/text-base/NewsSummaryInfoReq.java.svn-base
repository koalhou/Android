package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 新闻概要信息请求类
 * 
 * @author zhangzhia 2013-9-9 下午4:58:49
 */
public class NewsSummaryInfoReq extends AbstractReq
{

    private String news_type;

    /** 开始时间，格式yyyymmddhh24miss */
    private String startTime;

    /** 结束时间，格式yyyymmddhh24miss */
    private String endTime;

    /** 数量 */
    private String num;

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            req.put("news_type", news_type);
            req.put("start_time", startTime);
            req.put("end_time", endTime);
            req.put("num", num);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(NewsSummaryInfoReq.class, "[新闻概要信息请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.neusoft.yt.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/news/summary";
    }

    public void setNews_type(String news_type)
    {
        this.news_type = news_type;
    }

    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }

    public void setNum(String num)
    {
        this.num = num;
    }
    
    
}
