package com.yutong.axxc.parents.business.other;

import org.apache.commons.lang3.StringUtils;

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
import com.yutong.axxc.parents.connect.http.packet.ResourceDownloadReq;
import com.yutong.axxc.parents.connect.http.packet.ResourceDownloadRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 资源文件下载业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ResDownloadBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /**
     * 资源id
     */
    private String res_id;

    private static ResMemoryCache memoryCache;

    private static ResFileCache fileCache;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     */
    public ResDownloadBiz(Context context, Handler handler, String res_id)
    {
        this.context = context;
        this.handler = handler;
        this.res_id = res_id;

        if (memoryCache == null)
        {
            memoryCache = new ResMemoryCache(context);
        }
        if (fileCache == null)
        {
            fileCache = new ResFileCache();
        }
        logTypeName = "[资源文件下载业务逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        if(StringUtils.isBlank(res_id))
        {
            return;
        }
        // 获取资源
        getInfoFromLocal();
    }

    private void getInfoFromLocal()
    {
        String jsonString = memoryCache.getResFromCache(res_id);
        if (jsonString == null)
        {
            jsonString = fileCache.getRes(res_id);
            if (jsonString == null)
            {
                // 从服务器获取资源
                handleProcess();
            }
            else
            {
                // 加入内存缓存
                memoryCache.addResToCache(res_id, jsonString);

                // resInfoBean = new ResourceInfoBean();
                // resInfoBean.parseJsonString(jsonString);
                ResourceDownloadRes res = new ResourceDownloadRes();
                res.parse(jsonString);
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getResourceInfoBean());
            }
        }
        else
        {
            // resInfoBean = new ResourceInfoBean();
            // resInfoBean.parseJsonString(jsonString);

            ResourceDownloadRes res = new ResourceDownloadRes();
            res.parse(jsonString);
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getResourceInfoBean());
        }

    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        ResourceDownloadReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);

        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);

        }
        else if (httpRes.isTokenExpire())
        {
            Logger.i(this.getClass(), logTypeName + "Token失效");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        }
        else if (httpRes.isException())
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes)
    {
        ResourceDownloadRes res = new ResourceDownloadRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");
            // resInfoBean = res.getResourceInfoBean();
            // resInfoBean.setRes_id(res_id);

            // String res_json = resInfoBean.toJsonString();

            // 更新缓存
            // fileCache.saveRes(res_id, res_json);
            // memoryCache.addResToCache(res_id, res_json);

            // 更新缓存
            fileCache.saveRes(res_id, httpRes.getContent());
            memoryCache.addResToCache(res_id, httpRes.getContent());

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getResourceInfoBean());

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private ResourceDownloadReq buildReq()
    {
        ResourceDownloadReq req = new ResourceDownloadReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setRes_id(res_id);
        return req;
    }

}
