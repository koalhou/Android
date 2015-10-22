package com.yutong.axxc.parents.business.settings;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.UserInfoUpdateReq;
import com.yutong.axxc.parents.connect.http.packet.UserInfoUpdateRes;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 用户信息更新业务类
 * 
 * @author zhangzhia 2013-8-22 上午10:26:05
 */
public class UserInfoUpdateBiz extends YtAsyncTask
{
    /** Context对象 */
    // private Context context;

    /** Handler对象，用于与UI通信 */
    private Handler handler;

    private UserInfoBean userInfoBean;

    /**
     * <默认构造函数>
     */
    public UserInfoUpdateBiz(Context context, Handler handler, UserInfoBean userInfoBean)
    {
        // this.context = context;
        this.handler = handler;
        this.userInfoBean = userInfoBean;
        logTypeName = "[用户信息更新业务类]:";
    }

    @Override
    protected void doInBackground()
    {
        updateUserInfo();
        //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0012);
        
    }

    private void updateUserInfo()
    {
        // ThreadCommUtil.sendMsgToUI(handler,
        // ThreadCommStateCode.COMMON_FAILED, "测试发生错误");

        Logger.i(UserInfoUpdateBiz.class, logTypeName + "发送请求");
        UserInfoUpdateReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);

        if (httpRes.isSuccess())
        {
            UserInfoUpdateRes res = new UserInfoUpdateRes();

            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, null);
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "失败");
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
            }
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

    private UserInfoUpdateReq buildReq()
    {
        UserInfoUpdateReq req = new UserInfoUpdateReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setUserInfoBean(userInfoBean);
        return req;
    }

}
