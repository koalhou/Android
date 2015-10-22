/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:07
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
 * 获取编号集合的线路信息请求类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:07
 * 
 */
public class GetIdsLinesReq extends AbstractReq
{

	/**
	 * 线路id集合
	 */
	private List<String> line_ids;

	@Override
	public String packetMsgBody()
	{
		try
		{
			if (line_ids != null && line_ids.size() > 0)
			{
				JSONArray jsonArray = new JSONArray(line_ids);
				return jsonArray.toString();
			}
			return null;
		}
		catch (Exception e)
		{
			Logger.e(this.getClass(), "[获取编号集合的线路信息请求类]:组包生成请求消息时失败，详细信息：", e);
			return null;
		}
	}

	/**
	 * 线路id集合
	 */
	public void setLine_ids(List<String> line_ids)
	{
		this.line_ids = line_ids;
	}

}
