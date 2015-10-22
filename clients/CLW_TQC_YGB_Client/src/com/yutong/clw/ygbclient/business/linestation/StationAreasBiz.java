/**
 * @公司名称：YUTONG
 * @文件名：StationAreasBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:56:53
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.lang.reflect.Type;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationAreaInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetStationAreasReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetStationAreasRes;
import com.yutong.clw.ygbclient.dao.linestation.StationAreaDao;

/**
 * 站点区域信息业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:56:53
 */
public class StationAreasBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public StationAreasBiz(Context context)
    {
        this.context = context;

        logTypeName = "[站点区域信息业务逻辑类]:";
        setActionURI(ActionURI.GET_STATION_AREAS);
        
        cacheKey = actionURI.toString();

    }

    public List<StationAreaInfo> getStationAreasFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "从本地获取数据");
        // cacheItem =
        // CahceDataManager.getInstance(context).getCacheData(actionURI,
        // cacheKey);
        List<StationAreaInfo> stationAreas = null;
        // if (cacheItem != null && StringUtil.isNotBlank(cacheItem.CacheData))
        // {
        // Gson gson = new Gson();
        // Type type = new TypeToken<List<StationAreaInfo>>()
        // {
        // }.getType();
        //
        // stationAreas = gson.fromJson(cacheItem.CacheData, type);
        // }

        stationAreas = new StationAreaDao(context).getStationAreas();
        return stationAreas;

    }

    public List<StationAreaInfo> getStationAreasFromSever() throws CommonException
    {
        GetStationAreasReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            GetStationAreasRes res = new GetStationAreasRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                if (httpRes.needCached())
                {
                    // Gson gson = new Gson();
                    // Type type = new TypeToken<List<StationAreaInfo>>()
                    // {
                    // }.getType();
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                    // gson.toJson(res.getStaionAreaInfos(), type));
                    new StationAreaDao(context).batchUpdate(res.getStaionAreaInfos());

                    return res.getStaionAreaInfos();
                }
                else
                {
                    return getStationAreasFromLocal();
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
    private GetStationAreasReq buildReq()
    {
        GetStationAreasReq req = new GetStationAreasReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));
        return req;
    }

}
