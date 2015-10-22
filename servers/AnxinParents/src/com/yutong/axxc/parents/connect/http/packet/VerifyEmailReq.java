package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 邮箱验证请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VerifyEmailReq extends AbstractReq {

    private String usr_email;

    public void setUsr_email(String usr_email)
    {
        this.usr_email = usr_email;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        return null;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/account/email/" + this.usr_email;
    }
}
