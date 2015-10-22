/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:33:49
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.setting;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取推送规则响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:33:49
 * 
 */
public class GetPushMsgRulesRes extends AbstractRes
{
	//
	private List<PushMsgRule> pushMsgRules;

	// 获取所有的推送规则
	public List<PushMsgRule> getPushMsgRules()
	{
		return pushMsgRules;
	}

	@Override
	public boolean parseCorrectMsg(String jsonObject)
	{
		try
		{
			JSONArray res = new JSONArray(jsonObject);
			pushMsgRules = new ArrayList<PushMsgRule>();
			PushMsgRule rule = null;
			for (int i = 0; i < res.length(); i++)
			{
				JSONObject temp = res.optJSONObject(i);
				rule = new PushMsgRule();
				rule.parse(temp);
				pushMsgRules.add(rule);
			}
			return true;
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[获取推送规则响应类]:解析 响应消息出错，详细信息：", e);
			return false;
		}
	}
}
