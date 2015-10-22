package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 获取学生授权人响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class SearchCertigierUserRes extends AbstractRes
{

    /** 待授权用户信息 */
    private UserInfoBean userInfoBean = new UserInfoBean();


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

            JSONObject jsonObject = res.getJSONObject("usr_info");

            if (jsonObject != null)
            {
                userInfoBean.setUsr_id(jsonObject.optString("usr_id"));
                userInfoBean.setUsr_no(jsonObject.optString("usr_no"));
                userInfoBean.setUsr_name(jsonObject.optString("usr_name"));
                userInfoBean.setUsr_pwd(jsonObject.optString("usr_pwd"));
                userInfoBean.setUsr_login_name(jsonObject.optString("usr_login_name"));
                userInfoBean.setUsr_sex(jsonObject.optString("usr_sex"));
                userInfoBean.setUsr_phone(jsonObject.optString("usr_phone"));
                userInfoBean.setUsr_photo(jsonObject.optString("usr_photo"));
                userInfoBean.setUsr_addr(jsonObject.optString("usr_addr"));
                userInfoBean.setUsr_email(jsonObject.optString("usr_email"));

            }

            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生授权人响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }


    public UserInfoBean getUserInfoBean()
    {
        return userInfoBean;
    }



}
