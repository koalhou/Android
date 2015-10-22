package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 乘车协议手机号码验证请求
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class VerifyProtocolPhoneReq extends AbstractReq
{

    private String phone;

    private String name;

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setName(String name)
    {
        this.name = name;
    }

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
            req.put("phone", phone);
            req.put("name", name);
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
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/bind/student/verify";
    }
}
