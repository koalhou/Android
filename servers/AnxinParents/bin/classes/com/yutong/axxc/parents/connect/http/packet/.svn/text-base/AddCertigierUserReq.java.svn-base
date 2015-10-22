package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 添加授权人请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class AddCertigierUserReq extends AbstractReq
{
    /** 授权家长id  */
    private String usr_id;

    /** 学生id  */
    private String cld_id;
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
            req.put("usr_id", usr_id);
            req.put("cld_id", cld_id);
            
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[添加授权人请求类]:组包请求消息时失败，详细信息：", e);
            return null;
        }
    }


    public void setUsr_id(String usr_id)
    {
        this.usr_id = usr_id;
    }
    
    

    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }


    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/user/certigier";
    }
}
