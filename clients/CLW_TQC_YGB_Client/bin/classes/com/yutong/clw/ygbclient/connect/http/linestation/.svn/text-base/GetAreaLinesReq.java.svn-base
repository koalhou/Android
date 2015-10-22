/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:35:50
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;

/**
 * 获取厂区范围线路请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:35:50
 * 
 */
public class GetAreaLinesReq extends AbstractReq
{
	/**
	 * 条件范围，区域类型+路线范围，格式：4个长度字符串，
	 * 0：未选中，1：选中，第一位代区域类型，1：一厂:2：二厂，后面3位从左到右代表厂内，厂外，厂间。 如1101，代表选择 一厂的厂内，厂间
	 */
	private List<String> filter;

	@Override
	public String packetMsgBody()
	{
		try
		{
			if (filter != null && filter.size() > 0)
			{
				JSONArray jsonArray = new JSONArray(filter);
				return jsonArray.toString();
			}
			
			return null;
		}
		catch (Exception e)
		{
			Logger.e(this.getClass(), "[获取厂区范围线路请求信息类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}

	/**
	 * 条件范围，区域类型+路线范围，格式：4个长度字符串，
	 * 0：未选中，1：选中，第一位代区域类型，1：一厂:2：二厂，后面3位从左到右代表厂内，厂外，厂间。 如1101，代表选择 一厂的厂内，厂间
	 */
	public void setFilter(List<String> filter)
	{
		this.filter = filter;
	}

}
