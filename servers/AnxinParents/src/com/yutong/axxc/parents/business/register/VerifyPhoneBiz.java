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
import com.yutong.axxc.parents.connect.http.packet.VerifyPhoneReq;
import com.yutong.axxc.parents.connect.http.packet.VerifyPhoneRes;

/**
 * 验证手机号码业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VerifyPhoneBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 用户手机号码 */
    private String usr_phone;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     * @param usr_phone 注册手机
     */
    public VerifyPhoneBiz(Context context, Handler handler, String usr_phone)
    {
        this.context = context;
        this.handler = handler;
        this.usr_phone = usr_phone;
        logTypeName = "[验证手机号码业务逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        verifyPhone();
    }

    /**
     * 验证手机是否已注册
     */
    private void verifyPhone()
    {

        // zyong test
        // ThreadCommUtil.sendMsgToUI(handler,
        // ThreadCommStateCode.COMMON_SUCCESS, "0");

        Logger.i(this.getClass(), logTypeName + "发送请求");
        VerifyPhoneReq req = buildReq(usr_phone);

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
        VerifyPhoneRes res = new VerifyPhoneRes();
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

    private VerifyPhoneReq buildReq(String usr_phone)
    {
        VerifyPhoneReq req = new VerifyPhoneReq();
        req.setUsr_phone(usr_phone);
        return req;
    }

}
