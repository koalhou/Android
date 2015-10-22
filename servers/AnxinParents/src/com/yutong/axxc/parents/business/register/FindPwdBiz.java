package com.yutong.axxc.parents.business.register;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.FindPwdReq;
import com.yutong.axxc.parents.connect.http.packet.FindPwdRes;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;

/**
 * 通过账号找回密码业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class FindPwdBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    private String usr_account;

    /** 账号类型 0:手机号码，1:邮箱 */
    private String account_type;

    /** MD5加密的新密码 */
    private String reset_pwd;

    public FindPwdBiz(Context context, Handler handler, String usr_account, String account_type, String reset_pwd)
    {
        this.context = context;
        this.handler = handler;
        this.usr_account = usr_account;
        this.account_type = account_type;
        this.reset_pwd = reset_pwd;

        logTypeName = "[通过账号找回密码业务逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
        
      //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0026);
    }

    private void handleProcess()
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        FindPwdReq req = buildReq();

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
        FindPwdRes res = new FindPwdRes();

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

    private FindPwdReq buildReq()
    {
        FindPwdReq req = new FindPwdReq();
        req.setUsr_account(usr_account);
        req.setAccount_type(account_type);
        req.setReset_pwd(reset_pwd);

        return req;
    }

}
