
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;


/**
 * 通过账号找回密码请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class FindPwdReq extends AbstractReq {

    /** 登录用户名 */
    private String usr_account;

    /** 账号类型 0:手机号码，1:邮箱*/
    private String account_type;
    
    /** MD5加密的新密码 */
    private String reset_pwd;
    
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            
            JSONObject req = new JSONObject();
            req.put("usr_account", usr_account);
            req.put("account_type", account_type);
            req.put("reset_pwd", reset_pwd);
            
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[通过账号找回密码请求类]:组包请求消息时失败，详细信息：", e);
            return null;
        }
    }


    public void setUsr_account(String usr_account)
    {
        this.usr_account = usr_account;
    }


    public void setAccount_type(String account_type)
    {
        this.account_type = account_type;
    }


    public void setReset_pwd(String reset_pwd)
    {
        this.reset_pwd = reset_pwd;
    }


    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/account/pwd";
    }
}
