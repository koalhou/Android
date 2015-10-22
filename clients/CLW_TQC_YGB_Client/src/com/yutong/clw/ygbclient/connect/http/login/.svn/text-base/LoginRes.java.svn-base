package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.SexType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

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

    private UserInfo emp_info;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractRes#parseCorrectMsg(java.lang.String)
     */
    @Override
    public boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);
            accessToken = res.optString("access_token");
            expiresIn = res.optInt("expires_in");
            refreshToken = res.optString("refresh_token");

            JSONObject userInfoObject = res.optJSONObject("emp_info");
            if (userInfoObject != null)
            {

                emp_info = new UserInfo();

                emp_info.AccessToken = res.optString("access_token");
                emp_info.ExpiresIn = res.optInt("expires_in");
                emp_info.RefreshToken = res.optString("refresh_token");

                emp_info.Code = userInfoObject.optString("code");
                emp_info.Name = userInfoObject.optString("name");
                emp_info.NameShort = userInfoObject.optString("name_short");
                
                emp_info.Sex = SexType.myValueOf(userInfoObject.optInt("sex"));
                emp_info.Phone = userInfoObject.optString("phone");
                emp_info.Photo = userInfoObject.optString("photo");
                emp_info.Department = userInfoObject.optString("department");
                emp_info.EipPhone = userInfoObject.optString("eip_phone");
                emp_info.BindPhone = DataTypeConvertUtils.int2Boolean(userInfoObject.optInt("bind_phone"));
                emp_info.DefaultPassword = DataTypeConvertUtils.int2Boolean(userInfoObject.optInt("default_password"));
                emp_info.BelongArea = AreaType.myValueOf(userInfoObject.optInt("belong_area"));

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

    public UserInfo getEmp_info()
    {
        return emp_info;
    }

}
