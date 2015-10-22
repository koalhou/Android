/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:34:39
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 
 * 设置推送规则请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:34:39
 * 
 */
public class SetPushMsgRulesReq extends AbstractReq
{
	/**
	 * { "id" : " FCF7FB77-9F62-40E3-9C58-E8CDFBB72C4C", "rule_id" : " 05",
	 * "on_off" : "1" },
	 */

	private List<PushMsgRule> pushMsgRules;

	// 设置推送规则
	public void setPushMsgRules(List<PushMsgRule> pushMsgRules)
	{
		this.pushMsgRules = pushMsgRules;
	}

	@Override
	public String packetMsgBody()
	{
		try
		{

			JSONArray jsonArray = new JSONArray();
			JSONObject req = null;
			PushMsgRule temp = null;
			for (int i = 0; i < pushMsgRules.size(); i++)
			{
				req = new JSONObject();
				temp = pushMsgRules.get(i);
				//req.put("id", temp.id);
				req.put("rule_id", temp.rule_id);
				req.put("on_off", DataTypeConvertUtils.boolean2Int(temp.on_off));
				jsonArray.put(req);
			}
			return jsonArray.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[设置推送规则请求类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}
}
