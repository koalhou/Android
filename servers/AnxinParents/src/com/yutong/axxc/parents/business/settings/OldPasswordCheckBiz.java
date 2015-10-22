
package com.yutong.axxc.parents.business.settings;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.OldPasswordCheckReq;
import com.yutong.axxc.parents.connect.http.packet.OldPasswordCheckRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 旧密码验证业务类
 * @author zhangzhia 2013-8-22 上午10:24:39
 *
 */
public class OldPasswordCheckBiz extends YtAsyncTask {
    /** Context对象 */
    // private Context context;

    /** Handler对象，用于与UI通信 */
    private Handler handler;

    /** 用户输入的旧密码 */
    private String uiOldPwd;
    
    /**
     * <默认构造函数>
     */
    public OldPasswordCheckBiz(Context context, Handler handler, String uiOldPwd) {
        // this.context = context;
        this.handler = handler;
        this.uiOldPwd = uiOldPwd;
        logTypeName = "[旧密码验证业务类]:";
    }


    @Override
    protected void doInBackground() {
        //ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.OLD_PWD_CHECK_SUCCESS);
        
        // 组装报文请求
        OldPasswordCheckReq req = new OldPasswordCheckReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setUiOldPwd(uiOldPwd);

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
        if (httpRes.isSuccess()) {
            OldPasswordCheckRes res = new OldPasswordCheckRes();
            res.parse(httpRes.getContent());
            if (httpRes.getStatusCode() == 200) {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.OLD_PWD_CHECK_SUCCESS);
            } else {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
            }
        } else if (httpRes.isTokenExpire()) {
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        } else {
            Logger.w(this.getClass(), logTypeName + "旧密码验证失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.OLD_PWD_CHECK_FAILED, httpRes.getFailInfo());
        }
       
    }
}
