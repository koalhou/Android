/**
 * @公司名称：YUTONG
 * @文件名：GetStationBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:55:27
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetStationReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetStationRes;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;

/**
 * 获取站点列表信息业务逻辑类【只获取场外所有站点，不区分厂区，场内，场外，厂间】
 * 
 * @author zhangzhia 2013-10-22 上午9:55:27
 */
public class GetStationBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetStationBiz(Context context)
    {
        this.context = context;

        logTypeName = "[获取站点列表信息业务逻辑类]:";
        setActionURI(ActionURI.GET_STATIONS);
    }

    public List<StationInfo> getStationInfosFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "从本地获取数据");

        List<StationInfo> stationInfos = new StationInfoDao(context).getStations(ProxyManager.getInstance(context).getUserCode());
        // cacheItem =
        // CahceDataManager.getInstance(context).getCacheData(actionURI,
        // cacheKey);
        // if (cacheItem != null && StringUtil.isNotBlank(cacheItem.CacheData))
        // {
        // Gson gson = new Gson();
        // Type type = new TypeToken<List<StationInfo>>()
        // {
        // }.getType();
        // return gson.fromJson(cacheItem.CacheData, type);
        // }
        // else
        // {
        // return null;
        // }

        return stationInfos;
    }

    public List<StationInfo> getStationInfosFromSever() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetStationReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        List<StationInfo> stations = null;
        if (httpRes.isSuccess())
        {
            GetStationRes res = new GetStationRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    stations = res.getStationInfos();
                    // GsonBuilder builder = new GsonBuilder();
                    // builder.serializeSpecialFloatingPointValues();
                    // Gson gson = builder.create();
                    // Type type = new TypeToken<List<StationInfo>>()
                    // {
                    // }.getType();
                    // CahceDataManager.getInstance(context).putCacheConfig(actionURI,
                    // cacheKey, httpRes.getETag(),
                    // gson.toJson(stations, type));
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                    new StationInfoDao(context).batchUpdate(stations, ProxyManager.getInstance(context).getUserCode());
                    Logger.i(this.getClass(), logTypeName + "有新数据");
                    return stations;
                }
                else
                {
                    Logger.i(this.getClass(), logTypeName + "从本地数据库获取");
                    return getStationInfosFromLocal();
                }

            }
            else
            {
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());

            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }
        else if (httpRes.isException())
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }
        else
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }

    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private GetStationReq buildReq()
    {
        GetStationReq req = new GetStationReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        return req;
    }
}
