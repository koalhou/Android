package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;


/**
 * 获取授权人请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class GetCertigierInfoReq extends AbstractReq {

    private String cld_id;
    
    
    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

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
            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(PasswordUpdateReq.class, "[问题反馈请求类]:组包请求时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/user/certigier";
    }
}
