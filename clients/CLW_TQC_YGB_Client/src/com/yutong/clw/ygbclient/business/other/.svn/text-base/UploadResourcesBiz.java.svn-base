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
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.other.UploadResourcesReq;
import com.yutong.clw.ygbclient.connect.http.other.UploadResourcesRes;

/**
 * 资源上传业务逻辑类
 * @author zhangzhia 2013-10-22 上午10:08:53
 *
 */
public class UploadResourcesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;
    
    private ResMemoryCacheHepler memoryCache;

    private ResFileCacheHelper fileCache;

    /**
     * 资源信息
     */
    private ResourceInfo resInfo;
    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public UploadResourcesBiz(Context context, ResourceInfo resInfo)
    {
        this.context = context;
        this.resInfo = resInfo;
        logTypeName = "[资源上传业务逻辑类]:";
        setActionURI(ActionURI.UPLOAD_RESOURCES);
        
        if (memoryCache == null)
        {
            memoryCache = ResMemoryCacheHepler.getInstance(context);
        }
        if (fileCache == null)
        {
            fileCache = ResFileCacheHelper.getInstance(context);
        }
    }

    public String uploadResources() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        UploadResourcesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            UploadResourcesRes res = new UploadResourcesRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                
                ResRouterItem newRouter = new ResRouterItem(res.getRes_id(), ResourceType.Resource, resInfo.suffix);
                newRouter.setBytes(resInfo.getBytes());

                // 更新缓存
                memoryCache.addResToCache(res.getRes_id(), newRouter);
                fileCache.addResToCache(res.getRes_id(), newRouter);
                
                return res.getRes_id();
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
    private UploadResourcesReq buildReq()
    {
        UploadResourcesReq req = new UploadResourcesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setResourceInfo(resInfo);
        return req;
    }

}