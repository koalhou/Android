
package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;

/**
 * 推送绑定请求类
 * @author zhangzhia 2013-8-27 上午10:16:54
 *
 */
public class PushBindingReq extends AbstractReq {

    /** 推送clientId */
    private String clientid;

   /**
    * （非 Javadoc）
    * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
    */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();
            req.put("clientid", clientid);
            return req.toString();
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[推送绑定请求信息类]:组包请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * @param clientid The clientid to set.
     */
    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/bind/msgpush";
    }
}
