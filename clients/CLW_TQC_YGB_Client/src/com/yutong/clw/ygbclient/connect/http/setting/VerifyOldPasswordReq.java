/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:52
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 验证旧密码请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:52
 * 
 */
public class VerifyOldPasswordReq extends AbstractReq
{

	@Override
	public String packetMsgBody()
	{
	    return null;
	}
	
	public void setUriParam(String old_pwd)
    {
        this.uriParam = old_pwd;
    }
}
