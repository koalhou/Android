package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 得到学生最新状态响应类
 * @author zhangzhia 2013-8-22 上午9:19:24
 *
 */
public class GetStudentStatusRes extends AbstractRes {

    private String  status;
    
    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString) {
        JSONObject jsonObjRes;
        try {
            jsonObjRes = new JSONObject(jsonString);
            status = jsonObjRes.optString("status");
            return true;
        } catch (JSONException e) {
            Logger.e(this.getClass(), "[得到学生最新状态响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public String getStatus()
    {
        return status;
    }


}
