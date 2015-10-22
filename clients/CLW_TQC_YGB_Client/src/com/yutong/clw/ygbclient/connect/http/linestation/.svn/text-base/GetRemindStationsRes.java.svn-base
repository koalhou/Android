/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:18
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取站点提醒设置响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:18
 * 
 */
public class GetRemindStationsRes extends AbstractRes
{
	/**
	 * 站点提醒信息列表
	 */
	private List<RemindInfo> remindInfos;

	@Override
	public boolean parseCorrectMsg(String jsonObject)
	{
		try
		{
			JSONArray jsonArray = new JSONArray(jsonObject);
			remindInfos = new ArrayList<RemindInfo>();
			RemindInfo remindInfo = null;
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject temp = jsonArray.optJSONObject(i);
				remindInfo = new RemindInfo();
				remindInfo.parse(temp);
				remindInfos.add(remindInfo);
			}
			return true;
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[获取站点提醒设置响应类]:解析 响应消息出错，详细信息：", e);
			return false;
		}
	}

	/**
	 * 站点提醒信息列表
	 */
	public List<RemindInfo> getRemindInfos()
	{
		return remindInfos;
	}

}
