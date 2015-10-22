package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.StationRemindInfoBean;

/**
 * 设置站点提醒信息请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SetSationRemindInfoReq extends AbstractReq
{
    private String cld_id;

    private String station_id;

    /** 站点提示信息 */
    private StationRemindInfoBean stationRemindInfoBean;

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
            req.put("station_id", station_id);

            JSONObject station_remind_infoObject = new JSONObject();
            station_remind_infoObject.put("remind_id", stationRemindInfoBean.getRemind_id());
            station_remind_infoObject.put("remind_alias", stationRemindInfoBean.getRemind_alias());
            station_remind_infoObject.put("remind_type", stationRemindInfoBean.getRemind_type());
            station_remind_infoObject.put("remind_value", stationRemindInfoBean.getRemind_value());
            station_remind_infoObject.put("valid", stationRemindInfoBean.getValid());
            req.put("station_remind_info", station_remind_infoObject);

            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[设置站点提醒信息请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

    public void setStation_id(String station_id)
    {
        this.station_id = station_id;
    }

    public void setStationRemindInfoBean(StationRemindInfoBean stationRemindInfoBean)
    {
        this.stationRemindInfoBean = stationRemindInfoBean;
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/station/remind";
    }
}
