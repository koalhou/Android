package com.yutong.axxc.parents.connect.http.packet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserInfoBean;

/**
 * 登录响应类
 * 
 * @author zhangzhia 2013-8-22 上午9:19:24
 */
public class LoginRes extends AbstractRes
{
    /** 要获取的Access Token */
    private String accessToken;

    /** Access Token的有效期，单位：秒 */
    private long expiresIn;

    /** 用于刷新Access Token的Refresh Token */
    private String refreshToken;

    private UserInfoBean userInfoBean;

    private UserInfoBean certigierUserInfoBean;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        JSONObject loginRes;
        try
        {
            loginRes = new JSONObject(jsonString);
            accessToken = loginRes.optString("access_token");
            expiresIn = loginRes.optInt("expires_in");
            refreshToken = loginRes.optString("refresh_token");

            JSONObject userInfoObject = loginRes.optJSONObject("usr_info");
            if (userInfoObject != null)
            {

                userInfoBean = new UserInfoBean();
                userInfoBean.setUsr_id(userInfoObject.optString("usr_id"));
                userInfoBean.setUsr_no(userInfoObject.optString("usr_no"));
                userInfoBean.setUsr_name(userInfoObject.optString("usr_name"));
                userInfoBean.setUsr_pwd(userInfoObject.optString("usr_pwd"));
                userInfoBean.setUsr_login_name(userInfoObject.optString("usr_login_name"));
                userInfoBean.setUsr_sex(userInfoObject.optString("usr_sex"));
               userInfoBean.setUsr_phone(userInfoObject.optString("usr_phone"));
                userInfoBean.setUsr_photo(userInfoObject.optString("usr_photo"));
                userInfoBean.setUsr_addr(userInfoObject.optString("usr_addr"));
                userInfoBean.setUsr_email(userInfoObject.optString("usr_email"));
                userInfoBean.seteTag(userInfoObject.optString("ETag"));
                userInfoBean.setPhone_bind(userInfoObject.optString("phone_bind"));

                JSONObject certigierUserInfoObject = userInfoObject.optJSONObject("certigier _usr_info");

                if (certigierUserInfoObject != null)
                {
                    certigierUserInfoBean = new UserInfoBean();

                    certigierUserInfoBean.setUsr_no(userInfoObject.optString("usr_no"));
                    certigierUserInfoBean.setUsr_login_name(userInfoObject.optString("usr_login_name"));

                }

            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[登录响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    /**
     * 获取Access Token
     * 
     * @return Returns the accessToken.
     */
    public String getAccessToken()
    {
        return accessToken;
    }

    /**
     * 获取Access Token的有效期，单位：秒
     * 
     * @return Returns the expiresIn.
     */
    public long getExpiresIn()
    {
        return expiresIn;
    }

    /**
     * 获取用于刷新Access Token的Refresh Token
     * 
     * @return Returns the refreshToken.
     */
    public String getRefreshToken()
    {
        return refreshToken;
    }

    /**
     * 获取用于UserInfoBean实体类
     * 
     * @return Returns the userInfoBean.
     */
    public UserInfoBean getUserInfoBean()
    {
        return userInfoBean;
    }

    public UserInfoBean getCertigierUserInfoBean()
    {
        return certigierUserInfoBean;
    }

    
}
