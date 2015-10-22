package com.yutong.axxc.parents.business.behaviour;

import java.util.List;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.UserBehaviorUploadReq;
import com.yutong.axxc.parents.connect.http.packet.UserBehaviorUploadRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 用户行为上传业务逻辑类
 * @author zhangzhia 2013-9-11 下午4:55:05
 *
 */
public class UserBehaviorUploadBiz extends YtAsyncTask
{
    /** Context对象 */
    private Context context;

    /** Handler对象，用于与UI通信 */
    private Handler handler;

    /** 行为日志上报实体类 */
    private List<UserBehaviorBean> userBehaviorBeans;

    /**
     * <默认构造函数>
     */
    public UserBehaviorUploadBiz(Context context, Handler handler, List<UserBehaviorBean> userBehaviorBeans)
    {
        this.context = context;
        this.handler = handler;
        this.userBehaviorBeans = userBehaviorBeans;

        logTypeName = "[用户行为上报业务类]：";
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground()
    {
        Logger.d(this.getClass(), logTypeName + "开始上报用户行为");
        // 组装报文请求
        UserBehaviorUploadReq behUploadReq = buildUserBehaviorUploadReq();
        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(behUploadReq);

        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess() && httpRes.getStatusCode() == 200)
        {
            Logger.i(this.getClass(), logTypeName + "成功");
        }
        else
        {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
        }

        
        userBehaviorBeans.clear();
    }


    private UserBehaviorUploadReq buildUserBehaviorUploadReq()
    {
        UserBehaviorUploadReq behUploadReq = new UserBehaviorUploadReq();
        behUploadReq.setAccessToken(YtApplication.getInstance().getAccessToken());
        behUploadReq.setUserBehaviorBeans(userBehaviorBeans);

        return behUploadReq;
    }

}
