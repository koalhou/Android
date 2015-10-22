/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:30:37
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 绑定手机请求类
 * @author zhangzhia 2013-10-23 上午9:30:37
 * 
 */
public class BindPhoneReq extends AbstractReq
{
	/**
	 * 绑定手机号码
	 */
	private String phone;

	@Override
	public String packetMsgBody()
	{
		try
		{
			JSONObject req = new JSONObject();
			req.put("phone", phone);
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[绑定手机请求信息类]:组包生成绑定手机请求消息时失败，详细信息：", e);
			return null;
		}
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
