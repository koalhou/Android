/**
 * @公司名称：YUTONG
 * @文件名：RecommendStationsBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:46:02
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
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.RecommendStationsReq;
import com.yutong.clw.ygbclient.connect.http.linestation.RecommendStationsRes;
import com.yutong.clw.ygbclient.dao.linestation.RecommendStationDao;

/**
 * 获取推荐站点列表信息业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:46:02
 */
public class RecommendStationsBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private AreaType area_type;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public RecommendStationsBiz(Context context, AreaType area_type)
    {
        this.context = context;
        this.area_type = area_type;

        logTypeName = "[获取推荐站点列表信息业务逻辑类]:";
        setActionURI(ActionURI.GET_RECOMMEND_STATIONS);

        cacheKey = actionURI.toString();
        cacheKey += area_type.value();
    }

    public List<StationInfo> getStationInfosFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "从本地获取数据");
        return new RecommendStationDao(context).getRecommendStations(area_type);

    }

    public List<StationInfo> getStationInfosFromSever() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        RecommendStationsReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        if (httpRes.isSuccess())
        {
            RecommendStationsRes res = new RecommendStationsRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {

                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                    new RecommendStationDao(context).batchUpdate(area_type, res.getStationInfos());
                    return res.getStationInfos();
                }
                else
                {
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
    private RecommendStationsReq buildReq()
    {
        RecommendStationsReq req = new RecommendStationsReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        req.setArea_type(area_type);
        return req;
    }

}
