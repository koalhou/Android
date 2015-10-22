
package com.yutong.axxc.parents.business.other;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.SendPhoneReq;
import com.yutong.axxc.parents.connect.http.packet.SendPhoneRes;


/**
 *  向手机发送短信业务逻辑类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class SendPhoneBiz extends YtAsyncTask {

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;
    
    /** 手机号码 */
    private String phone;
    
    /** 验证码类型 */
    private String verify_type;


    /**
     * 构造函数
     * @param context Context对象
     * @param handler Handler对象
     * @param phone 手机号码
     * @param verify_type 验证码类型
     */
    public SendPhoneBiz(Context context, Handler handler, String phone, String verify_type) {
        this.context = context;
        this.handler = handler;
        this.verify_type = verify_type;
        this.phone = phone;
        logTypeName = "[向手机发送短信业务逻辑类]:";
    }

    @Override
    protected void doInBackground() {
        handleProcess();
    }

    private void handleProcess() {
    	//zyong test
    	//ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, "0000");

        Logger.i(this.getClass(), logTypeName + "发送请求");
        SendPhoneReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);

        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }
        
        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
            
        } else if (httpRes.isException()) {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        } else {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes) {
        SendPhoneRes res = new SendPhoneRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");
            
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getVerrify_code());
 
        } else {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }
    

    private SendPhoneReq buildReq() {
        SendPhoneReq req = new SendPhoneReq();
        req.setPhone(phone);
        req.setVerify_type(verify_type);
        
        return req;
    }

}
