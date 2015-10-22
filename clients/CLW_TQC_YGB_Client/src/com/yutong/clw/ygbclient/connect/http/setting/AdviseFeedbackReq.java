/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:33:33
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 意见建议反馈请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:33:33
 * 
 */
public class AdviseFeedbackReq extends AbstractReq
{
	// 意见内容
	private String content;
	// 意见内容
	private String msg_id;

	@Override
	public String packetMsgBody()
	{
		try
		{
			JSONObject req = new JSONObject();
			req.put("content", content);
			req.put("msg_id", msg_id);
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[ 意见建议反馈请求类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}

	}
	
	// 意见内容
	public void setContent(String content)
	{
		this.content = content;
	}

	//意见反馈使用，客户端唯一识别码
	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
}
