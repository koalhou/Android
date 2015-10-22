
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;

/**
 * 密码更新请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class PasswordUpdateReq extends AbstractReq{
    /** 用户输入的旧密码 */
    private String oldPwd;
    
    /** 用户输入的新密码 */
    private String newPwd;

    /**
     * @param oldPwd The oldPwd to set.
     */
    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    /**
     * @param newPwd The newPwd to set.
     */
    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();
            req.put("old_pwd", oldPwd);
            req.put("new_pwd", newPwd);
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[密码修改请求类]:组包生成密码修改请求时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/user/pwd";
    }

}
