/**
 * @公司名称：YUTONG
 * @文件名：StationFavoritesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:00:24
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
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.StationFavoritesReq;
import com.yutong.clw.ygbclient.connect.http.linestation.StationFavoritesRes;
import com.yutong.clw.ygbclient.dao.linestation.FavoritiesStationDao;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;

/**
 * 站点收藏业务逻辑类【只获取厂区，厂外收藏站点，不区分早班，晚班】
 * 
 * @author zhangzhia 2013-10-22 上午10:00:24
 */
public class GetStationFavoritesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private AreaType area_type;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetStationFavoritesBiz(Context context, AreaType area_type)
    {
        this.context = context;
        this.area_type = area_type;

        logTypeName = "[站点收藏业务逻辑类]:";
        setActionURI(ActionURI.GET_FAVORITES);

        cacheKey += area_type.value();
    }

    public List<StationInfo> getStationFavoritesFromLocal()
    {
        List<StationInfo> stationInfos = new FavoritiesStationDao(context).getFavoritesStations(area_type, ProxyManager
                .getInstance(context).getUserCode());

        StationInfoDao dao = new StationInfoDao(context);
        if (stationInfos != null)
        {
            for (StationInfo station : stationInfos)
            {
                StationInfo item = dao.getStation(station.id, ProxyManager.getInstance(context).getUserCode());
                if (item != null)
                {
                    station.favorites = item.favorites;
                    station.status = item.status;
                }
                else
                {
                    Logger.i(this.getClass(), logTypeName + "未找到站点[" + station.id + "]" + station.name);
                }

            }

        }

        return stationInfos;

    }

    public List<StationInfo> getStationFavoritesFromSever() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        StationFavoritesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        List<StationInfo> favoritiesStations = null;
        if (httpRes.isSuccess())
        {
            StationFavoritesRes res = new StationFavoritesRes();
            res.parse(httpRes.getContent());

            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    favoritiesStations = res.getStationInfos();
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                    new FavoritiesStationDao(context).batchUpdate(area_type, favoritiesStations, ProxyManager.getInstance(context).getUserCode());
                    Logger.i(this.getClass(), logTypeName + "有新数据");
                    return favoritiesStations;
                }
                else
                {
                    Logger.i(this.getClass(), logTypeName + "从本地数据库获取");
                    return getStationFavoritesFromLocal();
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
    private StationFavoritesReq buildReq()
    {
        StationFavoritesReq req = new StationFavoritesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        req.setArea_type(area_type);
        return req;
    }

}
