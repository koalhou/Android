package com.yutong.axxc.parents.business.settings;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.AdviseReq;
import com.yutong.axxc.parents.connect.http.packet.UserBindStudentRes;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 问题反馈业务逻辑类
 * 
 * @author zhangzhia 2013-8-27 上午10:44:40
 */
public class AdviseBiz extends YtAsyncTask
{
    /** Handler对象，用于与UI通信 */
    private Handler handler;

    /** Context对象 */
    private Context context;

    /** 问题反馈内容 */
    private String content;

    /**
     * <默认构造函数>
     */
    public AdviseBiz(Context context, Handler handler, String content)
    {
        this.handler = handler;
        this.context = context;
        this.content = content;

        logTypeName = "[问题反馈业务逻辑类]:";
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground()
    {
        handleProcess();
      //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0023);
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        AdviseReq req = buildReq();

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
        UserBindStudentRes res = new UserBindStudentRes();
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

    private AdviseReq buildReq()
    {
        AdviseReq req = new AdviseReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setContent(content);
        return req;
    }

}
