/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:30:04
 * @功能描述：
 */
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
 * 自动登录响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:30:04
 */
public class AutoLoginRes extends AbstractRes
{

    /**
     * 要获取的Access Token
     */
    private String access_token;

    /**
     * Access Token的有效期，单位：秒
     */
    private long expires_in;

    /**
     * 用于刷新Access Token的令牌信息
     */
    private String refresh_token;

    /**
     * 员工信息。参见员工信息
     */
    private UserInfo emp_info;

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonObject);

            access_token = res.optString("access_token");
            expires_in = res.optLong("expires_in");
            refresh_token = res.optString("refresh_token");

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
            Logger.e(this.getClass(), "[自动登录响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }

    }

    /**
     * 要获取的Access Token
     */
    public String getAccessToken()
    {
        return access_token;
    }

    /**
     * Access Token的有效期，单位：秒
     */
    public long getExpires_in()
    {
        return expires_in;
    }

    /**
     * 用于刷新Access Token的令牌信息
     */
    public String getRefresh_token()
    {
        return refresh_token;
    }

    /**
     * 员工信息。参见员工信息
     */
    public UserInfo getEmp_info()
    {
        return emp_info;
    }

}
