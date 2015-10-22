package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 手机验证响应
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class VerifyPhoneRes extends AbstractRes {

    private String  verify_result;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        JSONObject jsonObjRes;
        try {
            jsonObjRes = new JSONObject(jsonString);
            verify_result = jsonObjRes.optString("verify_result");
            return true;
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[验证手机响应类]:解析 验证手机响应消息出错，详细信息：", e);
            return false;
        }

    }


    public String getVerify_result()
    {
        return verify_result;
    }

}
