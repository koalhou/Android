/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:30:46
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.login;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 找回密码请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:30:46
 * 
 */
public class ForgotPasswordReq extends AbstractReq
{
	/**
	 * 员工号
	 */
	private String emp_code;
	/**
	 * MD5加密的新密码(新密码是否需要和旧密码命名区分开来？)
	 */
	private String passwordMD5;

	@Override
	public String packetMsgBody()
	{
		try
		{
			JSONObject req = new JSONObject();
			req.put("emp_code", emp_code);
			req.put("password", passwordMD5);
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[找回密码请求信息类]:组包生成找回密码请求消息时失败，详细信息：", e);
			return null;
		}
	}
	/**
	 * 员工号
	 */
	public void setEmp_code(String emp_code)
	{
		this.emp_code = emp_code;
	}
	/**
	 * MD5加密的新密码(新密码是否需要和旧密码命名区分开来？)
	 */
	public void setPasswordMD5(String passwordMD5)
	{
		this.passwordMD5 = passwordMD5;
	}

}
