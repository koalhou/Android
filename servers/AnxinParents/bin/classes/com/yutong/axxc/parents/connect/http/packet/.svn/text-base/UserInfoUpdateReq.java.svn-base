package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 用户信息更新请求
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class UserInfoUpdateReq extends AbstractReq
{

    /** 用户信息更新信息 */
    private UserInfoBean userInfoBean = new UserInfoBean();

    private static String logTypeName = "[用户信息更新请求信息类]:";

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

            // req.put("usr_id", userInfoBean.getUsr_id());
            // req.put("usr_no", userInfoBean.getUsr_no());
            req.put("usr_name", userInfoBean.getUsr_name());
            req.put("usr_sex", userInfoBean.getUsr_sex());
            req.put("usr_photo", userInfoBean.getUsr_photo());
            // req.put("usr_phone", userInfoBean.getUsr_phone());
            req.put("usr_addr", userInfoBean.getUsr_addr());
            //req.put("usr_email", userInfoBean.getUsr_email());

            return req.toString();
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), logTypeName + "组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    public void setUserInfoBean(UserInfoBean userInfoBean)
    {
        this.userInfoBean = userInfoBean;
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/user/info";
    }
}
