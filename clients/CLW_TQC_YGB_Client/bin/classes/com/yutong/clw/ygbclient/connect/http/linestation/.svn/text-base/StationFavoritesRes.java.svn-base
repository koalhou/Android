/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-23 上午9:38:04
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
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StationType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.connect.http.common.AbstractRes;

/**
 * 获取已收藏站点信息响应类
 * 
 * @author zhangzhia 2013-10-23 上午9:38:04
 */
public class StationFavoritesRes extends AbstractRes
{
    // 已收藏站点信息
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
                JSONObject temp = res.optJSONObject(i);

                stationInfo.parse(temp);
                stationInfos.add(stationInfo);
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(this.getClass(), "[获取已收藏站点信息响应类]:解析 响应消息出错，详细信息：", e);
            return false;
        }
    }

    // 已收藏站点信息
    public List<StationInfo> getStationInfos()
    {
        return stationInfos;
    }

}
