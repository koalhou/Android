/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:37:59
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取已收藏站点信息请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:37:59
 * 
 */
public class StationFavoritesReq extends AbstractReq
{
	/**
	 * 区域类型： 1 ：一厂，2 ：二厂
	 */
	private AreaType area_type;

	@Override
	public String packetMsgBody()
	{
		try
		{

			JSONObject req = new JSONObject();
			req.put("area_type", area_type.value());
			return req.toString();
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[ 获取已收藏站点信息请求类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}

	/**
	 * 区域类型： 1 ：一厂，2 ：二厂
	 */
	public void setArea_type(AreaType area_type)
	{
		this.area_type = area_type;
	}

}
