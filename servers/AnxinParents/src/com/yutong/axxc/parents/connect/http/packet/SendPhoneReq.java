package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 向手机发送内容请求类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SendPhoneReq extends AbstractReq
{

    private String phone;

    private String verify_type;

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setVerify_type(String verify_type)
    {
        this.verify_type = verify_type;
    }

    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();

            req.put("phone", phone);
            req.put("verify_type", verify_type);

            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[向手机发送内容请求类]:组包生成请求消息时失败，详细信息：", e);
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
        reuqestUri = reuqestUri + "/phone/send";
    }

}
