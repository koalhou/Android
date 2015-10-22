/**
 * @公司名称：YUTONG
 * @文件名：GetAreaLinesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:44:48
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetAreaLinesReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetAreaLinesRes;
import com.yutong.clw.ygbclient.dao.linestation.LineInfoDao;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;

/**
 * 获取厂区范围线路信息业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:44:48
 */
public class GetAreaLinesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 条件过滤
     */
    private List<String> filter;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetAreaLinesBiz(Context context, List<String> filter)
    {
        this.context = context;
        this.filter = filter;

        logTypeName = "[获取厂区范围线路信息业务逻辑类]:";
        setActionURI(ActionURI.GET_AREA_LINES);

        for (String item : filter)
        {
            cacheKey += item;
        }
    }

    public List<LineInfo> getLinesFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "从本地获取数据");
        List<LineInfo> lineinfos = new LineInfoDao(context).getInfoFromFilter(filter, ProxyManager.getInstance(context).getUserCode());
        StationInfoDao dao = new StationInfoDao(context);
        if (lineinfos != null)
        {
            for (LineInfo info : lineinfos)
            {
                if (info.getStations() != null)
                {
                    for (StationInfo station : info.getStations())
                    {

                        StationInfo item = dao.getStation(station.id, ProxyManager.getInstance(context).getUserCode());
                        if (item != null)
                        {
                            station.favorites = item.favorites;
                            station.status = item.status;
                        }
                        else
                        {
//                            station.favorites = false;
//                            station.status = false;
                            Logger.i(this.getClass(), logTypeName + "未找到路线"+info.line_id+"["+info.line_name+"]站点[" + station.id + "]" + station.name);
                        }

                    }
                }
            }

        }

        return lineinfos;

    }

    public List<LineInfo> getLinesFromSever() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetAreaLinesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        if (httpRes.isSuccess())
        {
            GetAreaLinesRes res = new GetAreaLinesRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    Logger.i(this.getClass(), logTypeName + "从网络返回最新数据，并缓存到本地");
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());

                    new LineInfoDao(context).insertLineInfo(res.getLineInfos(), filter, ProxyManager.getInstance(context).getUserCode());

//                    StationInfoDao dao = new StationInfoDao(context);
//                    List<LineInfo> lineinfos = res.getLineInfos();
//                    if (lineinfos != null)
//                    {
//                        for (LineInfo info : lineinfos)
//                        {
//                            if (info.getStations() != null)
//                            {
//                                for (StationInfo station : info.getStations())
//                                {
//                                    dao.setStationfavorites(station.id, station.favorites, ProxyManager.getInstance(context).getUserCode());
//                                    dao.setStationStatus(station.id, station.status, ProxyManager.getInstance(context).getUserCode());
//                                }
//                            }
//                        }
//
//                    }
                    
                    return getLinesFromLocal();
                }
                else
                {
                    return getLinesFromLocal();
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
    private GetAreaLinesReq buildReq()
    {
        GetAreaLinesReq req = new GetAreaLinesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        req.setFilter(filter);

        return req;
    }

}
