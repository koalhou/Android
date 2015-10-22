/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:33:07
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.other;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 手机短信验证请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:33:07
 * 
 */
public class SendPhoneSMSReq extends AbstractReq
{

	// 手机号
	private String phone;
	// 工号
	private String code;
	// 短信验证类型
	private SMSVerifyType type;

	// 手机号
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	// 工号
	public void setCode(String code)
	{
		this.code = code;
	}

	// 短信验证类型
	public void setType(SMSVerifyType type)
	{
		this.type = type;
	}

	@Override
	public String packetMsgBody()
	{
		try
		{
			JSONObject req = new JSONObject();
			req.put("phone", phone);
			req.put("code", code);
			req.put("type", type.value());
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[手机短信验证请求信息类]:组包请求消息时失败，详细信息：", e);
			return null;
		}
	}

}
