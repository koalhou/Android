package com.yutong.axxc.parents.business.login;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.VerifyProtocolPhoneReq;
import com.yutong.axxc.parents.connect.http.packet.VerifyProtocolPhoneRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 验证乘车协议手机号码业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VerifyProtocolPhoneBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 验证号码 */
    private String phone;
    /** 协议填写的家长姓名 */
    private String name;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     * @param phone 验证号码
     */
    public VerifyProtocolPhoneBiz(Context context, Handler handler, String phone, String name)
    {
        this.context = context;
        this.handler = handler;
        this.phone = phone;
        this.name = name;
        logTypeName = "[验证乘车协议手机号码业务逻辑类]:";
    }

    @Override
    protected void doInBackground()
    {
        handleProcess();
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        VerifyProtocolPhoneReq req = buildReq();

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
        VerifyProtocolPhoneRes res = new VerifyProtocolPhoneRes();
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

    private VerifyProtocolPhoneReq buildReq()
    {
        VerifyProtocolPhoneReq req = new VerifyProtocolPhoneReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setPhone(phone);
        req.setName(name);
        return req;
    }

}
