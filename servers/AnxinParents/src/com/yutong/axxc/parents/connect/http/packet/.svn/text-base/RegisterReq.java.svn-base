
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.ClientInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;


/**
 * 用户注册请求信息类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class RegisterReq extends AbstractReq {

    /** 用户注册信息 */
    private UserInfoBean userInfoBean = new UserInfoBean();

    /** 客户端信息 */
    private ClientInfoBean clientInfoBean = new ClientInfoBean();
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            
            setClientInfo(clientInfoBean.getJsonString());
            
            JSONObject req = new JSONObject();

            JSONObject user_info = new JSONObject();

            user_info.put("usr_login_name", userInfoBean.getUsr_login_name());
            user_info.put("usr_pwd", userInfoBean.getUsr_pwd());
            user_info.put("usr_phone", userInfoBean.getUsr_phone());
 
            req.put("usr_info", user_info);
            
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[用户注册请求信息类]:组包生成用户注册请求消息时失败，详细信息：", e);
            return null;
        }
    }


    public void setUserInfoBean(UserInfoBean userInfoBean)
    {
        this.userInfoBean = userInfoBean;
    }

   
    public void setClientInfoBean(ClientInfoBean clientInfoBean)
    {
        this.clientInfoBean = clientInfoBean;
    }


    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/account/register";
    }
}
