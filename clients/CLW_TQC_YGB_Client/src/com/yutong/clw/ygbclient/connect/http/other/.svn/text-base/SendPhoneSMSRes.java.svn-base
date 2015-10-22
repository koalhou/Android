/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:33:01
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.other;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 手机短信验证响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:33:01
 */
public class SendPhoneSMSRes extends AbstractRes
{
    // 服务端生成的验证码
    private String code;

    // 服务端生成的验证码
    public String getCode()
    {
        return code;
    }

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonObject);
            code = res.optString("code");
            
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[手机短信验证响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }
}
