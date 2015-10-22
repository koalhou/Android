package com.yutong.axxc.parents.business.student;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.DeleteCertigierUserReq;
import com.yutong.axxc.parents.connect.http.packet.DeleteCertigierUserRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 删除授权用户信息逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class DeleteCertigierUserBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 授权家长id */
    private String usr_id;

    /** 学生id */
    private String cld_id;
    
    public DeleteCertigierUserBiz(Context context, Handler handler, String usr_id, String cld_id)
    {
        this.context = context;
        this.handler = handler;
        this.usr_id = usr_id;
        this.cld_id = cld_id;
        logTypeName = "[删除授权用户信息逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        DeleteCertigierUserReq req = buildReq();

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
        DeleteCertigierUserRes res = new DeleteCertigierUserRes();
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

    private DeleteCertigierUserReq buildReq()
    {
        DeleteCertigierUserReq req = new DeleteCertigierUserReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setUsr_id(usr_id);
        req.setCld_id(cld_id);
        return req;
    }

}
