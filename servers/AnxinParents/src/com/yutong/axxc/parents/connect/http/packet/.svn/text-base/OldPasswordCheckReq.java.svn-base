
package com.yutong.axxc.parents.connect.http.packet;

import com.yutong.axxc.parents.common.Tools;

/**
 * 旧密码验证请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class OldPasswordCheckReq extends AbstractReq{
    /** 用户输入的旧密码 */
    private String uiOldPwd;

    /**
     * @param uiOldPwd The uiOldPwd to set.
     */
    public void setUiOldPwd(String uiOldPwd) {
        this.uiOldPwd = uiOldPwd;
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
        reuqestUri = reuqestUri + "/user/check/" + uiOldPwd;
    }

}
