
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 自动登录请求信息类
 * @author zhangzhia 2013-8-29 上午10:44:43
 *
 */
public class AutoLoginReq extends AbstractReq {

    /** 服务端分配终端用户的最新access_token信息 */
    private String accessToken;

    /** 终端软件版本号 */
    private String softVersionId;

    /**
     * (non-Javadoc)
     * @see com.neusoft.yt.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        return null;
    }

    /**
     * @return Returns the accessToken.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken The accessToken to set.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return Returns the softVersionId.
     */
    public String getSoftVersionId() {
        return softVersionId;
    }

    /**
     * @param softVersionId The softVersionId to set.
     */
    public void setSoftVersionId(String softVersionId) {
        this.softVersionId = softVersionId;
    }

    /**
     * (non-Javadoc)
     * @see com.neusoft.yt.connect.http.packet.AbstractReq#setReuqestUri(java.lang.String)
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/user/autologin";
    }
}
