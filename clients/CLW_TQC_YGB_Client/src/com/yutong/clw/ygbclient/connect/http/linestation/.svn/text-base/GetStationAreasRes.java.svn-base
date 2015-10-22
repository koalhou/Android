/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:37:50
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取站点区域信息响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:37:50
 * 
 */
public class GetStationAreasRes extends AbstractRes
{
	/**
	 * 站点区域信息集合
	 */
	private List<StationAreaInfo> staionAreaInfos;

	@Override
	public boolean parseCorrectMsg(String jsonObject)
	{
		try
		{
			JSONArray jsonArray = new JSONArray(jsonObject);
			int length = jsonArray.length();
			staionAreaInfos = new ArrayList<StationAreaInfo>();
			StationAreaInfo staionAreaInfo = null;
			for (int i = 0; i < length; i++)
			{
				staionAreaInfo = new StationAreaInfo();
				staionAreaInfo.parse(jsonArray.optJSONObject(i));
				staionAreaInfos.add(staionAreaInfo);
			}
			return true;
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[获取站点区域信息响应类]:解析 响应消息出错，详细信息：", e);
			return false;
		}
	}

	/**
	 * 站点区域信息集合
	 */
	public List<StationAreaInfo> getStaionAreaInfos()
	{
		return staionAreaInfos;
	}

}
