/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:25
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取站点列表信息响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:25
 * 
 */
public class GetStationRes extends AbstractRes
{
	/**
	 * 站点信获取站点列表列表信息响应类集合
	 */
	private List<StationInfo> stationInfos;

	@Override
	public boolean parseCorrectMsg(String jsonObject)
	{
		try
		{
			JSONArray res = new JSONArray(jsonObject);
			int length = res.length();
			stationInfos = new ArrayList<StationInfo>();
			StationInfo stationInfo = null;
			for (int i = 0; i < length; i++)
			{
				stationInfo = new StationInfo();
				stationInfo.parse(res.optJSONObject(i));
				stationInfos.add(stationInfo);
			}
			return true;
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[获取站点列表列表信息响应类]:解析 响应消息出错，详细信息：", e);
			return false;
		}
	}

	/**
	 * 站点信息集合
	 */
	public List<StationInfo> getStationInfos()
	{
		return stationInfos;
	}
}
