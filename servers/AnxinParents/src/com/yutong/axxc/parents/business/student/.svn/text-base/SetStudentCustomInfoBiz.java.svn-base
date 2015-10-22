package com.yutong.axxc.parents.business.student;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.SetStudentCustomInfoReq;
import com.yutong.axxc.parents.connect.http.packet.SetStudentCustomInfoRes;
import com.yutong.axxc.parents.connect.http.packet.SetUserSalutationReq;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 设置学生个性信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class SetStudentCustomInfoBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    private StudentCustomInfoBean studentCustomInfoBean;

    public SetStudentCustomInfoBiz(Context context, Handler handler, String cld_id, StudentCustomInfoBean studentCustomInfoBean)
    {
        this.context = context;
        this.handler = handler;
        this.studentCustomInfoBean = studentCustomInfoBean;
        logTypeName = "[设置学生个性信息逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetStudentCustomInfoReq req = buildReq();

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

    private void parseAndProcessRes(HttpRes httpRes)
    {
        SetStudentCustomInfoRes res = new SetStudentCustomInfoRes();
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

    private SetStudentCustomInfoReq buildReq()
    {
        SetStudentCustomInfoReq req = new SetStudentCustomInfoReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setStudentCustomInfoBean(studentCustomInfoBean);
        return req;
    }

}
