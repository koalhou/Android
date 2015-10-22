/**
 * @公司名称：YUTONG
 * @文件名：GetRemindStationsBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:57:47
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.linestation;

import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.linestation.GetRemindStationsReq;
import com.yutong.clw.ygbclient.connect.http.linestation.GetRemindStationsRes;
import com.yutong.clw.ygbclient.dao.linestation.RemindStationInfoDao;

/**
 * 获取站点提醒业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:57:47
 */
public class GetRemindStationsBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private AreaType area_type;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetRemindStationsBiz(Context context, AreaType area_type)
    {
        this.context = context;
        this.area_type = area_type;
        logTypeName = "[获取站点提醒业务逻辑类]:";
        setActionURI(ActionURI.GET_REMIND_STATIONS);

        this.cacheKey += area_type.value();

    }

    public List<RemindInfo> getRemindsFromLocal()
    {
        return new RemindStationInfoDao(context).getRemindInfos(area_type, ProxyManager.getInstance(context).getUserCode());

    }

    public List<RemindInfo> getRemindsFromSever() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetRemindStationsReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        List<RemindInfo> remindInfos = null;
        if (httpRes.isSuccess())
        {
            GetRemindStationsRes res = new GetRemindStationsRes();
            res.parse(httpRes.getContent());

            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    remindInfos = res.getRemindInfos();
                    if (remindInfos == null || remindInfos.size() == 0)
                    {
                        return null;
                    }
                    RemindStationInfoDao remindStationInfoDao = new RemindStationInfoDao(context);
                    // 代码效率稍低,应该先返回给页面数据，之后再执行更新操作
//                    for (RemindInfo remindInfo : remindInfos)
//                    {
//                        remindStationInfoDao.updateRemindInfo(remindInfo, ProxyManager.getInstance(context).getUserCode());
//                    }
                    remindStationInfoDao.updateRemindInfo(area_type, remindInfos, ProxyManager.getInstance(context).getUserCode());
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                    Logger.i(this.getClass(), logTypeName + "从网络返回数据，并缓存到本地");
                    return getRemindsFromLocal();
                }
                else
                {
                    Logger.i(this.getClass(), logTypeName + "从本地返回数据");
                    return getRemindsFromLocal();
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
    private GetRemindStationsReq buildReq()
    {
        GetRemindStationsReq req = new GetRemindStationsReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        req.setArea_type(area_type);
        return req;
    }

}
