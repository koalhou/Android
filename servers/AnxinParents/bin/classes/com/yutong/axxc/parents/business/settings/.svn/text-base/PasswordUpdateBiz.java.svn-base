package com.yutong.axxc.parents.business.settings;

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
import com.yutong.axxc.parents.connect.http.packet.PasswordUpdateReq;
import com.yutong.axxc.parents.connect.http.packet.PasswordUpdateRes;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 密码修改业务类
 * 
 * @author zhangzhia 2013-8-22 上午10:25:11
 */
public class PasswordUpdateBiz extends YtAsyncTask
{
    /** Context对象 */
    // private Context context;

    /** Handler对象，用于与UI通信 */
    private Handler handler;

    /** 用户输入的旧密码 */
    private String md5OldPwd;

    /** 用户输入的新密码 */
    private String md5NewPwd;

    /**
     * <默认构造函数>
     */
    public PasswordUpdateBiz(Context context, Handler handler, String md5OldPwd, String md5NewPwd)
    {
        // this.context = context;
        this.handler = handler;
        this.md5OldPwd = md5OldPwd;
        this.md5NewPwd = md5NewPwd;
        logTypeName = "[密码修改业务类]:";
    }

    @Override
    protected void doInBackground()
    {
        Logger.d(this.getClass(), logTypeName + "发送请求");
        // 组装报文请求
        PasswordUpdateReq pwdUpdateReq = new PasswordUpdateReq();
        pwdUpdateReq.setAccessToken(YtApplication.getInstance().getAccessToken());
        pwdUpdateReq.setOldPwd(md5OldPwd);
        pwdUpdateReq.setNewPwd(md5NewPwd);

        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(pwdUpdateReq);
        if (httpRes.isSuccess())
        {
            PasswordUpdateRes res = new PasswordUpdateRes();
            res.parse(httpRes.getContent());
            if (httpRes.getStatusCode() == 200)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.PWD_UPDATE_SUCCESS);
            }
            else
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
            }
        }
        else if (httpRes.isTokenExpire())
        {
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "密码修改失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.PWD_UPDATE_FAILED, httpRes.getFailInfo());
        }
        
        //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0011);
        

    }

}
