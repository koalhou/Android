/**
 * @公司名称：YUTONG
 * @文件名：GetIdsLinesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:45:24
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.cache.CacheItem;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetIdsLinesReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetIdsLinesRes;
import com.yutong.clw.ygbclient.dao.linestation.LineInfoDao;
import com.yutong.clw.ygbclient.dao.linestation.StationInfoDao;

/**
 * 获取编号集合的线路信息业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:45:24
 */
public class GetIdsLinesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 查询线路ids
     */
    private List<String> line_ids;

    /**
     * 实际查询线路ids
     */
    private List<String> req_line_ids;
    
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetIdsLinesBiz(Context context, List<String> line_ids)
    {
        this.context = context;
        this.line_ids = line_ids;

        logTypeName = "[获取编号集合的线路信息业务逻辑类]:";
        setActionURI(ActionURI.GET_IDS_LINES);
    }

    @Override
    public boolean IsCacheExpires()
    {
        boolean isExpires = false;
        LineInfoDao lineInfodao = new LineInfoDao(context);
        for (String line_id : line_ids)
        {
            LineInfo lineInfo = lineInfodao.getInfoFromId(line_id, ProxyManager.getInstance(context).getUserCode());
            if (isLineExpire(lineInfo))
            {
                isExpires = true;
                break;
            }
        }
        return isExpires;

    }

    /**
     * 暂时不缓存
     * 
     * @author zhangzhia 2013-11-5 下午1:40:21
     * @return
     */
    public List<LineInfo> getLinesFromLocal()
    {
        List<LineInfo> lineinfos = new LineInfoDao(context).getInfoFromIds(line_ids, ProxyManager.getInstance(context).getUserCode());

        if (lineinfos != null && lineinfos.size() > 0)
        {
            StationInfoDao dao = new StationInfoDao(context);
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

    public List<LineInfo> getLines() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");

        List<String> needSearchIds = new ArrayList<String>();

        LineInfoDao lineInfodao = new LineInfoDao(context);
        for (String line_id : line_ids)
        {
            LineInfo lineInfo = lineInfodao.getInfoFromId(line_id, ProxyManager.getInstance(context).getUserCode());
            if (isLineExpire(lineInfo))
            {
                needSearchIds.add(line_id);
            }
        }
        
        StringBuilder ids = new StringBuilder("查询ids线路ids：");
        for(String id:line_ids)
        {
            ids.append(id + ",");
        }
        Logger.w(this.getClass(), logTypeName + ids.toString());
        
        StringBuilder needids = new StringBuilder("本地没有缓存或超时的线路ids：");
        for(String id:line_ids)
        {
            needids.append(id + ",");
        }
        Logger.w(this.getClass(), logTypeName + needids.toString());
        
        this.req_line_ids = needSearchIds;

        GetIdsLinesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        List<LineInfo> lineInfos = null;
        if (httpRes.isSuccess())
        {
            GetIdsLinesRes res = new GetIdsLinesRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    lineInfos = res.getLineInfos();
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());

                    new LineInfoDao(context).insertLineInfo(lineInfos, null, ProxyManager.getInstance(context).getUserCode());
                    Logger.i(this.getClass(), logTypeName + "从网络返回数据,更新数据库");

                    return getLinesFromLocal();
                }
                else
                {
                    Logger.e(this.getClass(), logTypeName + "从本地返回数据");
                    return getLinesFromLocal();
                }
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "失败");
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
     * 查询缓存路线是否超时,
     * 
     * @author zhangzhia 2013-11-27 上午9:52:02
     * @param line_id
     * @return
     */
    private boolean isLineExpire(LineInfo lineInfo)
    {
        try
        {
            if (lineInfo == null)
            {
                return true;
            }

            CacheItem cacheItem = CahceDataManager.getInstance(context).getCacheData(actionURI, cacheKey);

            // 如果没有当前模块没有配置缓存配置,或者当前模块不需要缓存超时机制，直接返回
            if (cacheItem == null || !cacheItem.NeedCacheMechanism)
            {
                return true;
            }

            Date nowTime = new Date();

            Long validTime = 0L;
            if (NetworkCheckUitl.is3GOnline(context))
            {
                validTime = cacheItem.ValidTime_3G;
            }
            else
            {
                validTime = cacheItem.ValidTime_WIFI;
            }

            boolean isTimeout = false;

            validTime += lineInfo.creatTime.getTime();
            if (nowTime.getTime() > validTime)
            {
                isTimeout = true;
            }
            else
            {
                isTimeout = false;
            }

            switch (cacheItem.Method)
            {
            case NaturalDay:
                if (!DateUtils.dateToStr(nowTime, "yyyy-MM-dd").equals(DateUtils.dateToStr(cacheItem.DownloadTime, "yyyy-MM-dd")))
                {
                    isTimeout = true;
                }
                else
                {
                    isTimeout = false;
                }
                break;
            case NaturalMonth:
                // 不等于当月超时
                if (!DateUtils.dateToStr(nowTime, "yyyy-MM").equals(DateUtils.dateToStr(cacheItem.DownloadTime, "yyyy-MM")))
                {
                    isTimeout = true;
                }
                else
                {
                    isTimeout = false;
                }
                break;
            default:
                break;
            }

            return isTimeout;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "判断缓存是否超时出错");
            return true;
        }
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private GetIdsLinesReq buildReq()
    {
        GetIdsLinesReq req = new GetIdsLinesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        // req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI,
        // cacheKey));

        req.setLine_ids(req_line_ids);

        return req;
    }

}
