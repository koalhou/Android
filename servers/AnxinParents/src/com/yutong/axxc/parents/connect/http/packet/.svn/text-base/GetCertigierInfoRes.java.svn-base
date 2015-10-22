package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 获取授权人响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class GetCertigierInfoRes extends AbstractRes
{
    /** 学生授权人信息 */
    private List<UserInfoBean> userInfoBeans;

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

            JSONArray jsonArray = res.getJSONArray("certigier_infos");// 获取JSONArray

            if (jsonArray != null)
            {
                userInfoBeans = new ArrayList<UserInfoBean>();
                for (int i = 0, length = jsonArray.length(); i < length; i++)
                {// 遍历JSONArray

                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject != null)
                    {
                        UserInfoBean userInfoBean = new UserInfoBean();

                        
                        userInfoBean.setUsr_id(jsonObject.optString("usr_id"));
                        userInfoBean.setUsr_name(jsonObject.optString("usr_name"));
                        userInfoBean.setUsr_phone(jsonObject.optString("usr_phone"));
                        userInfoBean.setUsr_photo(jsonObject.optString("usr_photo"));
                        userInfoBean.setUsr_login_name(jsonObject.optString("usr_login_name"));
                        userInfoBean.setUsr_main(jsonObject.optString("usr_main"));

                        userInfoBeans.add(userInfoBean);
                    }
                }
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取学生授权人响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    public List<UserInfoBean> getUserInfoBeans()
    {
        return userInfoBeans;
    }


}
