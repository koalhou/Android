package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 获取待授权人请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SearchCertigierUserReq extends AbstractReq
{
    private String account_name;

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
            req.put("account_name", account_name);
            req.put("cld_id", cld_id);
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(PasswordUpdateReq.class, "[获取待授权人请求类]:组包请求时失败，详细信息：", e);
            return null;
        }
    }

    public void setAccount_name(String account_name)
    {
        this.account_name = account_name;
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
        reuqestUri = reuqestUri + "/user/certigier/search";
    }
}
