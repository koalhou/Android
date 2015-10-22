/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:36:02
 * @功能描述：
 */
package com.yutong.clw.ygbclient.connect.http.linestation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.CoordPoint;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取编号集合的线路信息响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:36:02
 * 
 */
public class GetIdsLinesRes extends AbstractRes
{
	/**
	 * 编号集合的线路信息
	 */
	private List<LineInfo> lineInfos;

	@Override
	public boolean parseCorrectMsg(String jsonObject)
	{
		try
		{
			JSONArray jsonArray = new JSONArray(jsonObject);
			lineInfos = new ArrayList<LineInfo>();
			LineInfo lineInfo = null;
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject temp = jsonArray.optJSONObject(i);
				lineInfo = new LineInfo();
				lineInfo.parse(temp);
				lineInfos.add(lineInfo);
			}
			return true;
		}
		catch (JSONException e)
		{
			Logger.e(this.getClass(), "[获取编号集合的线路信息响应类]:解析 响应消息出错，详细信息：", e);
			return false;
		}
	}
	/**
	 * 编号集合的线路信息
	 */
	public List<LineInfo> getLineInfos()
	{
		return lineInfos;
	}
}
