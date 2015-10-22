/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:32:48
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.other;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取二维码响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:32:48
 */
public class QRCodeRes extends AbstractRes
{
    // e二维码
    private String qr_code;

    // e二维码
    public String getQr_code()
    {
        return qr_code;
    }

    @Override
    public boolean parseCorrectMsg(String jsonObject)
    {
        JSONObject res;
        try
        {
            res = new JSONObject(jsonObject);
            qr_code = res.optString("qr_code");
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取二维码响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
        // TODO Auto-generated method stub
    }
}
