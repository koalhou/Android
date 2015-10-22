
package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;


/**
 * 登录请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class LoginReq extends AbstractReq {

    /** 登录用户名 */
    private String account;

    /** MD5加密的登录用户密码 */
    private String passwordMD5;

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            
            

            JSONObject req = new JSONObject();
            req.put("account", account);
            req.put("password", passwordMD5);
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[登录请求信息类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }
    /** 登录用户名 */
    public void setAccount(String account)
    {
        this.account = account;
    }
    /** MD5加密的登录用户密码 */
    public void setPasswordMD5(String passwordMD5)
    {
        this.passwordMD5 = passwordMD5;
    }
    
}
