/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:29:55
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.push;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;

/**
 * 推送内容抽象类
 * 
 * @author zhangzhia 2013-9-3 上午10:36:19
 */
public abstract class AbstractPush {

	public MsgPushType msgPushType = MsgPushType.Unknow;

	public String id;

	public String rule_id;

	/** 用户code */
	public String user_code;

	public Date event_time;

	public JSONObject contentJsonObj;

	public MsgPushType getMsgPushType() {
		return msgPushType;
	}

	public boolean parse(String msg) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(msg);
			
		    id = jsonObject.optString("id");
			rule_id = jsonObject.optString("rule_id");
			user_code = jsonObject.optString("user_code");
			event_time = DateUtils.strToDate(jsonObject.optString("event_time"), "yyyyMMddHHmmss");

			contentJsonObj = jsonObject.optJSONObject("content");

			msgPushType = MsgPushType.myNameOf(rule_id);

			return parseContent();

		} catch (JSONException e) {
			Logger.e(this.getClass(), "[推送内容抽象类]:解析接受消息出错，详细信息：", e);
			return false;
		}

	}

	@Override
	public boolean equals(Object o) {
		if (null == o)
			return true;

		if (!(o instanceof AbstractPush)) {
			return true;
		}
		AbstractPush push = (AbstractPush) o;
		if (this.id.equals(push.id)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 解析内容
	 * 
	 * @param jsonObject
	 * @return
	 */
	abstract boolean parseContent();

	public String getRule_id() {
		return rule_id;
	}

	public void setRule_id(String rule_id) {
		this.rule_id = rule_id;
	}

	// /**
	// * 获取内容值
	// *
	// * @author zhangzhia 2013-9-10 上午11:26:46
	// * @param key key
	// */
	// public String getContent(String key)
	// {
	// try
	// {
	// String value = null;
	// if (contentJsonObj != null)
	// {
	// value = contentJsonObj.optString(key);
	//
	// }
	//
	// return value;
	// }
	// catch (Exception e)
	// {
	// return null;
	// }
	// }
	//
	// /**
	// * 获取内容值Map集合
	// *
	// * @author zhangzhia 2013-9-10 上午11:26:46
	// */
	// public Map<String, String> getContents()
	// {
	// try
	// {
	// Map<String, String> map = new HashMap<String, String>();
	// if (contentJsonObj != null)
	// {
	// // value = contentJsonObj.optString(key);
	// Iterator<?> it = contentJsonObj.keys();
	// while (it.hasNext())
	// {
	// String key = (String) it.next();
	// map.put(key, contentJsonObj.getString(key));
	//
	// }
	// }
	//
	// return map;
	// }
	// catch (Exception e)
	// {
	// return null;
	// }
	// }



}
