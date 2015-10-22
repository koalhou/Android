package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * AccessToken更新请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class AccessTokenUpdateReq extends AbstractReq {

    private String refresh_token;

    public void setRefresh_token(String refresh_token)
    {
        this.refresh_token = refresh_token;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();
            req.put("refresh_token", refresh_token);
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[AccessToken更新请求信息类]:组包生成请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/user/refresh";
    }
}
