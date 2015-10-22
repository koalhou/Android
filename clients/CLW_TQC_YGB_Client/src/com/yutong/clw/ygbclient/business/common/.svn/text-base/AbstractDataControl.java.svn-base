/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:18:51
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.common;

import java.util.Date;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.cache.CacheItem;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.HttpAction;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.connect.http.common.AbstractReq;
import com.yutong.clw.ygbclient.connect.http.common.HttpMsgSendUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;

/**
 * 数据控制抽象类
 * 
 * @author zhangzhia 2013-9-11 下午5:18:33
 */
public abstract class AbstractDataControl
{
    /** Context对象 */
    protected Context context;

    /**
     * ActionURI
     */
    protected ActionURI actionURI;

    /**
     * 缓存key
     */
    protected String cacheKey;

    /**
     * 缓存值对象
     */
    protected CacheItem cacheItem;

    /**
     * http action提交方式,GET,POST,PUT,DELETE
     */
    protected HttpAction httpAction;

    public void setActionURI(ActionURI actionURI)
    {
        this.actionURI = actionURI;
        cacheKey = actionURI.toString();

        if (!StringUtil.isBlank(ProxyManager.getInstance(context).getUserCode()))
        {
            cacheKey += ProxyManager.getInstance(context).getUserCode();
        }
    }

    /**
     * 缓存是否过期
     */
    public boolean IsCacheExpires()
    {
        if(!SysConfigUtil.getCacheOpen())
        {
            return true;
        }
        try
        {
            cacheItem = CahceDataManager.getInstance(context).getCacheData(actionURI, cacheKey);
            
            
            // return false;

            // 如果没有当前模块没有配置缓存配置,或者当前模块不需要缓存超时机制，直接返回true
            if (cacheItem == null || !cacheItem.NeedCacheMechanism || cacheItem.DownloadTime == null)
            {
                return true;
            }

            // boolean result = cacheItem.ETag != null ||
            // "0".equals(cacheItem.ETag);
            // if (result)
            // {
            // return true;
            // }

            if (cacheItem.IsCacheExpires)
            {
                Logger.w(this.getClass(), logTypeName + "-----缓存超时:" + actionURI.toString());

                CahceDataManager.getInstance(context).removeCache(actionURI, cacheKey);
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

            validTime += cacheItem.DownloadTime.getTime();
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

            if (isTimeout)
            {
                Logger.w(this.getClass(), logTypeName + "-----缓存超时:" + actionURI.toString());
                CahceDataManager.getInstance(context).removeCache(actionURI, cacheKey);
            }

            return isTimeout;
        }
        catch (Exception e)
        {
            Logger.e(this.getClass(), logTypeName + "判断缓存是否超时出错" + actionURI.toString());
            return true;
        }
    }

    /** 实例对象日志名称 */
    public String logTypeName;

    /**
     * 获取HttpRes对象
     * 
     * @author zhangzhia 2013-10-22 下午2:57:41
     * @param req
     * @param action
     * @return
     */
    public HttpRes getHttpRes(AbstractReq req)
    {
        // 以下在调用HttpRes httpRes = getHttpRes(req, httpAction)之前必须调用
        // 这里获取url
        req.setUriAction(actionURI);
        // 获取Http Action方式
        httpAction = req.getHttpAction();

        HttpRes httpRes = null;
        switch (httpAction)
        {
        case Get:
            httpRes = HttpMsgSendUtil.sendGetMsg(req);
            break;
        case Post:
            httpRes = HttpMsgSendUtil.sendPostMsg(req);
            break;
        case Put:
            httpRes = HttpMsgSendUtil.sendPutMsg(req);
            break;
        case Delete:
            httpRes = HttpMsgSendUtil.sendDeleteMsg(req);
            break;
        }

        return httpRes;
    }

}
