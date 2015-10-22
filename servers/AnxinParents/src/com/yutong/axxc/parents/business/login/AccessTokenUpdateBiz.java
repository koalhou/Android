package com.yutong.axxc.parents.business.login;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.common.SysInfoGetUtil;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.AccessTokenUpdateReq;
import com.yutong.axxc.parents.connect.http.packet.AccessTokenUpdateRes;
import com.yutong.axxc.parents.connect.http.packet.VerifyProtocolPhoneReq;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * AccessToken更新业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class AccessTokenUpdateBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** refresh_token */
    private String refresh_token;

    public AccessTokenUpdateBiz(Context context, Handler handler, String refresh_token)
    {
        this.context = context;
        this.handler = handler;
        this.refresh_token = refresh_token;
        logTypeName = "[AccessToken更新业务逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        AccessTokenUpdateReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);

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
        AccessTokenUpdateRes res = new AccessTokenUpdateRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS);

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private AccessTokenUpdateReq buildReq()
    {
        AccessTokenUpdateReq req = new AccessTokenUpdateReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setRefresh_token(refresh_token);

        return req;
    }

}
