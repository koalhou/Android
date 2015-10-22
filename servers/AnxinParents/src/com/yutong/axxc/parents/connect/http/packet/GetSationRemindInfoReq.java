package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 获取站点提醒信息请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class GetSationRemindInfoReq extends AbstractReq {

    private String cld_id;
    /** 线路类型 */
    private String line_type;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try
        {
            JSONObject req = new JSONObject();
            req.put("cld_id", cld_id);
            req.put("line_type", line_type);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取站点提醒信息请求类]:组包请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }


    public void setLine_type(String line_type)
    {
        this.line_type = line_type;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/station/remind";
    }
}
