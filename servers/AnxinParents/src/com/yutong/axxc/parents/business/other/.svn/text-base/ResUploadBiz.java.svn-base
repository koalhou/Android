
package com.yutong.axxc.parents.business.other;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.common.rescache.ResFileCache;
import com.yutong.axxc.parents.common.rescache.ResMemoryCache;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.ResourceUploadReq;
import com.yutong.axxc.parents.connect.http.packet.ResourceUploadRes;
import com.yutong.axxc.parents.view.common.YtApplication;


/**
 *  资源文件上传业务逻辑类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class ResUploadBiz extends YtAsyncTask {

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 资源信息 */
    private ResourceInfoBean resourceInfoBean;
    
    private static ResMemoryCache memoryCache;

    private static ResFileCache fileCache;

    /**
     * 构造函数
     * @param context Context对象
     * @param handler Handler对象

     */
    public ResUploadBiz(Context context, Handler handler, ResourceInfoBean resourceInfoBean) {
        this.context = context;
        this.handler = handler;
        this.resourceInfoBean = resourceInfoBean;

        if (memoryCache == null)
        {
            memoryCache = new ResMemoryCache(context);
        }
        if (fileCache == null)
        {
            fileCache = new ResFileCache();
        }
        
        logTypeName = "[资源文件上传业务逻辑类]:";
    }

    @Override
    protected void doInBackground() {
        handleProcess();
    }

    private void handleProcess() {
  
        Logger.i(this.getClass(), logTypeName + "发送请求");
        ResourceUploadReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(req);

        // 用来UI取消操作
        if (isCanceled())
        {
            return; 
        }
        
        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
            
        } else if (httpRes.isTokenExpire()) {
            Logger.i(this.getClass(), logTypeName + "Token失效");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        } else if (httpRes.isException()) {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        } else {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes) {
        ResourceUploadRes res = new ResourceUploadRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");
            
            // 更新缓存
            fileCache.saveRes(res.getRes_id(), resourceInfoBean.toJsonString());
            memoryCache.addResToCache(res.getRes_id(), resourceInfoBean.toJsonString());

            resourceInfoBean.setRes_id(res.getRes_id());

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, resourceInfoBean);
 
        } else {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }
    

    private ResourceUploadReq buildReq() {
        ResourceUploadReq req = new ResourceUploadReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setResourceInfoBean(resourceInfoBean);
        return req;
    }

}
