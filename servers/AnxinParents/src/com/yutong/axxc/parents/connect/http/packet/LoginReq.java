
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.ClientInfoBean;


/**
 * 登录请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class LoginReq extends AbstractReq {

    /** 登录用户名 */
    private String usr_account;

    /** MD5加密的登录用户密码 */
    private String usr_pwd;

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            
            JSONObject req = new JSONObject();
            req.put("usr_account", usr_account);
            req.put("usr_pwd", usr_pwd);
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[登录请求信息类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }


    public void setUsr_account(String usr_account)
    {
        this.usr_account = usr_account;
    }

    public void setUsr_pwd(String usr_pwd)
    {
        this.usr_pwd = usr_pwd;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/account/login";
    }
}
