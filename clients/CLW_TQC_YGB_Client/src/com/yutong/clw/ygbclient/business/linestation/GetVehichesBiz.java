/**
 * @公司名称：YUTONG
 * @文件名：GetVehichesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:55:56
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.VehicheInfo;
import com.yutong.clw.ygbclient.common.cache.CacheItem;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetVehichesReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetVehichesRes;

/**
 * 获取车辆信息业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:55:56
 */
public class GetVehichesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private Date search_date;

    private List<String> line_ids;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetVehichesBiz(Context context, Date search_date, List<String> line_ids)
    {
        this.context = context;
        this.search_date = search_date;
        this.line_ids = line_ids;
        logTypeName = "[获取车辆信息业务逻辑类]:";
        setActionURI(ActionURI.GET_VEHICHES);
        cacheKey += search_date.getTime();
        for(String line_id: line_ids)
        {
            cacheKey += line_id;
        }
    }

    public List<VehicheInfo> getVehichesFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "从本地获取数据");
        cacheItem = CahceDataManager.getInstance(context).getCacheData(actionURI, cacheKey);
        List<VehicheInfo> vehicleINfos = new ArrayList<VehicheInfo>();
        if (cacheItem != null && StringUtil.isNotBlank(cacheItem.CacheData))
        {
            Gson gson = new Gson();
            Type type = new TypeToken<List<VehicheInfo>>()
            {
            }.getType();
            vehicleINfos = gson.fromJson(cacheItem.CacheData, type);
        }
        return vehicleINfos;

    }

    public List<VehicheInfo> getVehichesFromSever() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetVehichesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        // 线路上的车辆信息
        List<VehicheInfo> retVehiches = new ArrayList<VehicheInfo>();
        if (httpRes.isSuccess())
        {
            GetVehichesRes res = new GetVehichesRes();
            res.parse(httpRes.getContent());

            if (!res.isError())
            {

                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<VehicheInfo>>()
                    {
                    }.getType();
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag(),
                            gson.toJson(res.getVehicheInfos(), type));
                    retVehiches = res.getVehicheInfos();
                    return retVehiches;
                }
                else
                {
                    return getVehichesFromLocal();
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
    private GetVehichesReq buildReq()
    {
        GetVehichesReq req = new GetVehichesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        req.setSearch_date(DateUtils.dateToStr(search_date, "yyyyMMdd"));
        req.setLine_ids(line_ids);
        return req;
    }

}
