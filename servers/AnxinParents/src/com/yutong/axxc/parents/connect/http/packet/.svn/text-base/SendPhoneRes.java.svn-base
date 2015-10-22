package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 向手机发送内容响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SendPhoneRes extends AbstractRes
{
    /** 返回的验证码 */
    private String verrify_code;
    
    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try {
            res = new JSONObject(jsonString);
            verrify_code = res.optString("verify_code");

            return true;
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[向手机发送内容响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getVerrify_code()
    {
        return verrify_code;
    }


    
}
