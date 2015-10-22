
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 版本检查请求类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VersionChkReq extends AbstractReq {

    /** 终端软件版本号 */
    private String version;

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();
            req.put("version", version);
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[版本检查请求类]:组包生成版本检查请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * @param version The version to set.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/version";
    }
}
