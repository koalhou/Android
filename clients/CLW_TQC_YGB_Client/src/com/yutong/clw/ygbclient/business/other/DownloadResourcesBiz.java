/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:08:53
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.other;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.Router.ResRouterItem;
import com.yutong.clw.ygbclient.common.beans.ResourceInfo;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.ResourceType;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.rescache.ResFileCacheHelper;
import com.yutong.clw.ygbclient.common.rescache.ResMemoryCacheHepler;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.other.DownloadResourcesReq;
import com.yutong.clw.ygbclient.connect.http.other.DownloadResourcesRes;

/**
 * 资源下载业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午10:08:53
 */
public class DownloadResourcesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    private String res_id;

    private ResMemoryCacheHepler memoryCache;

    private ResFileCacheHelper fileCache;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public DownloadResourcesBiz(Context context, String res_id)
    {
        this.context = context;
        this.res_id = res_id;
        logTypeName = "[资源下载业务逻辑类]:";
        setActionURI(ActionURI.DOWNLOAD_RESOURCES);

        if (memoryCache == null)
        {
            memoryCache = ResMemoryCacheHepler.getInstance(context);
        }
        if (fileCache == null)
        {
            fileCache = ResFileCacheHelper.getInstance(context);
        }
    }

    /**
     * 从服务器获取资源数据
     * 
     * @author zhangzhia 2013-10-31 下午3:07:59
     * @return
     * @throws CommonException
     */
    public ResourceInfo getResourceInfoFromLocal() throws CommonException
    {
        ResourceInfo resInfo = null;
        ResRouterItem router = memoryCache.getResFromCache(res_id);
        if (router != null && router.getBytes() != null && router.getBytes().length > 0)
        {
            resInfo = new ResourceInfo();
            resInfo.suffix = router.suffix;
            resInfo.setBytes(router.bytes);
        }
        else
        {

            router = fileCache.getResFromCache(res_id);
            if (router != null && router.getBytes() != null && router.getBytes().length > 0)
            {
                // 加入内存缓存
                memoryCache.addResToCache(res_id, router);

                resInfo = new ResourceInfo();
                resInfo.suffix = router.suffix;
                resInfo.setBytes(router.bytes);
            }
            else
            {
                resInfo = getResourceInfoFromServer();

                if (resInfo == null)
                {
                    return null;
                }

                ResRouterItem newRouter = new ResRouterItem(res_id, ResourceType.NetURL, resInfo.suffix);
                newRouter.setBytes(resInfo.getBytes());

                // 更新缓存
                memoryCache.addResToCache(res_id, newRouter);
                fileCache.addResToCache(res_id, newRouter);
            }

        }

        return resInfo;
    }

    public ResourceInfo getResourceInfoFromServer() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        DownloadResourcesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            DownloadResourcesRes res = new DownloadResourcesRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");

                return res.getResourceInfo();
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
    private DownloadResourcesReq buildReq()
    {
        DownloadResourcesReq req = new DownloadResourcesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setUriParam(res_id);

        return req;
    }
}
