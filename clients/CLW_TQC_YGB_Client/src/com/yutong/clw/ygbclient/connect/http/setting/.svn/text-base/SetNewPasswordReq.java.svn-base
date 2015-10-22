/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:26
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 用户密码修改请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:26
 * 
 */
public class SetNewPasswordReq extends AbstractReq
{
	// 旧密码
	private String old_pwd;
	// 新密码
	private String new_pwd;

	// 旧密码
	public void setOld_pwd(String old_pwd)
	{
		this.old_pwd = old_pwd;
	}

	// / 新密码
	public void setNew_pwd(String new_pwd)
	{
		this.new_pwd = new_pwd;
	}

	@Override
	public String packetMsgBody()
	{
		try
		{
			JSONObject req = new JSONObject();
			req.put("old_pwd", old_pwd);

			req.put("new_pwd", new_pwd);
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[用户密码修改请求类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}
}
