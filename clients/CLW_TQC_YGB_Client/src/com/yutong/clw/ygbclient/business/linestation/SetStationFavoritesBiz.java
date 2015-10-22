/**
 * @公司名称：YUTONG
 * @文件名：StationFavoritesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:00:24
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.CollectionStation;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.SetFavoritesStationsReq;
import com.yutong.clw.ygbclient.connect.http.linestation.SetFavoritesStationsRes;
import com.yutong.clw.ygbclient.dao.linestation.FavoritiesStationDao;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;

/**
 * 设置站点收藏业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:00:24
 */
public class SetStationFavoritesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private List<CollectionStation> stations;
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public SetStationFavoritesBiz(Context context, List<CollectionStation> stations)
    {
        this.context = context;
        this.stations = stations;

        logTypeName = "[设置站点收藏业务逻辑类]:";
        setActionURI(ActionURI.SET_FAVORITES);
    }

    /**
     * 设置收藏站点
     * 
     * @author lizyi 2013-11-1 下午3:36:09
     * @return 是否收藏成功
     * @throws CommonException
     */
    public boolean setStatinFavorites() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetFavoritesStationsReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            SetFavoritesStationsRes res = new SetFavoritesStationsRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                if (stations != null)
                {
                    StationInfoDao stationdao = new StationInfoDao(context);
                   
                    for (CollectionStation station : stations)
                    {
                        stationdao.setStationfavorites(station.station_id, station.favorites, ProxyManager.getInstance(context)
                                .getUserCode());

                        // #region by zz 增加本地存取缓存，减少网络交互，提供响应速度。
                        StationInfo info = stationdao.getStation(station.station_id, ProxyManager.getInstance(context).getUserCode());

                        if (info == null || StringUtil.isBlank(info.id))
                        {
                            new GetStationBiz(context).getStationInfosFromSever();
                        }

                        stationdao.setStationfavorites(station.station_id, station.favorites, ProxyManager.getInstance(context)
                                .getUserCode());
                        
                        FavoritiesStationDao favoritiesdao = new FavoritiesStationDao(context);
                        favoritiesdao.removeFavoritiesStation(station.station_id, ProxyManager.getInstance(context).getUserCode());
                        if (station.favorites)
                        {
                            
                            if(info.area_type == AreaType.AllFactory)
                            {
                                info.area_type = AreaType.FirstFactory;
                                favoritiesdao.addFavoritiesStation(info, ProxyManager.getInstance(context).getUserCode());
                                info.area_type = AreaType.SecondFactory;
                                favoritiesdao.addFavoritiesStation(info, ProxyManager.getInstance(context).getUserCode());
                            }
                            else
                            {
                                favoritiesdao.addFavoritiesStation(info, ProxyManager.getInstance(context).getUserCode());
                            }
                        }

                        // #endregion
                    }
                }

                // 受影响缓存需要设置超时
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_AREA_LINES);
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_IDS_LINES);
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_STATIONS);
                // CahceDataManager.getInstance(context).removeCache(ActionURI.GET_FAVORITES);

                return true;
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
    private SetFavoritesStationsReq buildReq()
    {
        SetFavoritesStationsReq req = new SetFavoritesStationsReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setStations(stations);

        return req;
    }

}
