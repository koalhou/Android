/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:31:23
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.beans.VerifyAccountInfo;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 验证账号响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:31:23
 */
public class VerifyAccountRes extends AbstractRes
{

    /**
     * 验证账号信息
     */
    private VerifyAccountInfo verifyInfo = new VerifyAccountInfo();

    @Override
    public boolean parseCorrectMsg(String jsonString)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonString);
            
            String emp_code = res.optString("code");
            boolean bind_phone = DataTypeConvertUtils.int2Boolean(res.optInt("bind_phone"));
            String phone = res.optString("phone");
            
            verifyInfo.emp_code = emp_code;
            verifyInfo.phonebind = bind_phone;
            verifyInfo.phone = phone;
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[验证账号响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    /**
     * 验证账号信息
     */
    public VerifyAccountInfo getVerifyInfo()
    {
        return verifyInfo;
    }

}
