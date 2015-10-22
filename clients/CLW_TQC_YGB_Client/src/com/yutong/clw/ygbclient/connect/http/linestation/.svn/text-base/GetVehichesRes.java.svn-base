/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:43
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
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取车辆信息响应
 * 
 * @author zhangzhia 2013-10-23 上午9:36:43
 * 
 */
public class GetVehichesRes extends AbstractRes
{
	/**
	 * 车辆信息集合类
	 */
	private List<VehicheInfo> vehicheInfos;

	@Override
	public boolean parseCorrectMsg(String jsonObject)
	{
		try
		{
			JSONArray res = new JSONArray(jsonObject);
			int length = res.length();
			vehicheInfos = new ArrayList<VehicheInfo>();
			VehicheInfo vehicheInfo = null;
			for (int i = 0; i < length; i++)
			{
				vehicheInfo = new VehicheInfo();
				vehicheInfo.parse(res.optJSONObject(i));
				vehicheInfos.add(vehicheInfo);
			}
			return true;
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[获取车辆信息响应类]:解析 响应消息出错，详细信息：", e);
			return false;
		}
	}

	/**
	 * 车辆信息集合类
	 */
	public List<VehicheInfo> getVehicheInfos()
	{
		return vehicheInfos;
	}

}
