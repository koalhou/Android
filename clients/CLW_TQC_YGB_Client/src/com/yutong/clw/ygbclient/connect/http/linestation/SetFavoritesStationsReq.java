/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:37:13
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.CollectionStation;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 设置收藏站点请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:37:13
 * 
 */
public class SetFavoritesStationsReq extends AbstractReq
{
	/**
	 * 站点信息
	 */
	private List<CollectionStation> stations;

	@Override
	public String packetMsgBody()
	{
		try
		{

			JSONArray req = new JSONArray();
			for (int i = 0; i < stations.size(); i++)
			{
				JSONObject temp = new JSONObject();
				temp.put("favorites", DataTypeConvertUtils.boolean2Int(stations
						.get(i).isFavorites()));
				temp.put("station_id", stations.get(i).getStation_id());
				req.put(temp);
			}
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[ 设置收藏站点请求信息类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}

	public void setStations(List<CollectionStation> stations)
	{
		this.stations = stations;
	}

}
