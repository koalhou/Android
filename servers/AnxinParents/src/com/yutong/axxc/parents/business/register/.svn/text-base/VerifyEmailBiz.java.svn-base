package com.yutong.axxc.parents.business.register;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.VerifyEmailReq;
import com.yutong.axxc.parents.connect.http.packet.VerifyEmailRes;

/**
 * 验证邮箱业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VerifyEmailBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 用户邮箱 */
    private String usr_email;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     * @param usr_email 注册邮箱
     */
    public VerifyEmailBiz(Context context, Handler handler, String usr_email)
    {
        this.context = context;
        this.handler = handler;
        this.usr_email = usr_email;
        logTypeName = "[验证邮箱业务逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        VerifyEmailReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(req);

        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
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
        VerifyEmailRes res = new VerifyEmailRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getVerify_result());

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private VerifyEmailReq buildReq()
    {
        VerifyEmailReq req = new VerifyEmailReq();
        req.setUsr_email(usr_email);
        return req;
    }

}
