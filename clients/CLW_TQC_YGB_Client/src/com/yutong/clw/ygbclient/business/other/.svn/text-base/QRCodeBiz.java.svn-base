/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:08:18
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.other;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.cache.CacheItem;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.rescache.ResFileCacheHelper;
import com.yutong.clw.ygbclient.common.rescache.ResMemoryCacheHepler;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.other.QRCodeReq;
import com.yutong.clw.ygbclient.connect.http.other.QRCodeRes;

/**
 * 获取二维码业务逻辑类[2013-11-18 不使用]
 * 
 * @author zhangzhia 2013-10-22 上午10:08:18
 */
public class QRCodeBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public QRCodeBiz(Context context)
    {
        this.context = context;
        logTypeName = "[获取二维码业务逻辑类]:";
        setActionURI(ActionURI.GET_QRCODE);

    }

    /**
     * 从本地获取数据
     * 
     * @author zhangzhia 2013-10-31 下午3:07:59
     * @return
     * @throws CommonException
     */
    public String getQRCodeFromLocal() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "从本地获取数据");
        cacheItem = CahceDataManager.getInstance(context).getCacheData(actionURI, cacheKey);

        if (cacheItem != null && StringUtil.isNotBlank(cacheItem.CacheData))
        {
            return cacheItem.CacheData;
        }
        else
        {
            return null;
        }

    }

    /**
     * 从网络获取数据
     * 
     * @author zhangzhia 2013-11-4 下午3:03:30
     * @return
     * @throws CommonException
     */
    public String getQRCodeFromServer() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        QRCodeReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            QRCodeRes res = new QRCodeRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                if (httpRes.needCached())
                {
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag(), res.getQr_code());
                    return res.getQr_code();
                }
                else
                {
                    return getQRCodeFromLocal();
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
    private QRCodeReq buildReq()
    {
        QRCodeReq req = new QRCodeReq();
        // 获取Etag
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        return req;
    }

}