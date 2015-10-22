package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserUsedDurationBean;

/**
 * 设置家长称呼请求
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class SetUserSalutationReq extends AbstractReq {

    private String cld_id;

    private String usr_salutation;

    


    public void setCld_id(String cld_id)
    {
        this.cld_id = cld_id;
    }

    public void setUsr_salutation(String usr_salutation)
    {
        this.usr_salutation = usr_salutation;
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody() {
        try {
            JSONObject req = new JSONObject();

            req.put("cld_id", cld_id);
            req.put("usr_salutation", usr_salutation);
            

            return req.toString();

            
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[设置家长称呼请求]：组包请求时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri() {
        reuqestUri = reuqestUri + "/child/salutation";
    }
}
