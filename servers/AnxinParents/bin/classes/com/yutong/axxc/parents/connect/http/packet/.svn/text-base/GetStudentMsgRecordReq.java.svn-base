package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 获取学生消息记录请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentMsgRecordReq extends AbstractReq
{

    private String cld_id;
    
    private String usr_id;
    
    private String record_date;

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
            req.put("usr_id", usr_id);
            req.put("record_date", record_date);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生消息记录请求类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }


    public void setRecord_date(String record_date)
    {
        this.record_date = record_date;
    }

    
    public void setUsr_id(String usr_id)
    {
        this.usr_id = usr_id;
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/msg/history";
    }
}
