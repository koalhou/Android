package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 家长绑定学生请求类
 * 
 * @author zhangzhia 2013-8-27 上午10:16:54
 */
public class UserBindStudentReq extends AbstractReq
{

    private String phone;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody()
    {
        return "";
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/bind/student/" + phone;
    }
}
