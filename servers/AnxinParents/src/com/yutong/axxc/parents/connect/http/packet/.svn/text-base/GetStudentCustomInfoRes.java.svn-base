package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;

/**
 * 获取学生个性信息响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetStudentCustomInfoRes extends AbstractRes
{
    /** 学生信息 */
    private StudentCustomInfoBean studentCustomInfoBean;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);
            JSONObject jsonObject = res.optJSONObject("child_custom_info");
            if (jsonObject != null)
            {
                studentCustomInfoBean = new StudentCustomInfoBean();
                studentCustomInfoBean.setCld_id(jsonObject.optString("cld_id"));

                studentCustomInfoBean.setCld_alias(jsonObject.optString("cld_alias"));
                studentCustomInfoBean.setCld_photo(jsonObject.optString("cld_photo"));
                studentCustomInfoBean.setCld_bg(jsonObject.optString("cld_bg"));
                studentCustomInfoBean.setCld_audio(jsonObject.optString("cld_audio"));
                studentCustomInfoBean.setCld_color(jsonObject.optString("cld_color"));
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生个性信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }


    public StudentCustomInfoBean getStudentCustomInfoBean()
    {
        return studentCustomInfoBean;
    }

}
