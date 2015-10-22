package com.yutong.axxc.parents.business.behaviour;

import java.util.List;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.login.LoginBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserUsedDurationBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.UserUsedDurationReq;
import com.yutong.axxc.parents.connect.http.packet.UserUsedDurationRes;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 用户用时上传业务逻辑类
 * @author zhangzhia 2013-9-11 下午4:54:43
 *
 */
public class UserUsedDurationLogBiz extends YtAsyncTask
{
    /** Context对象 */
    private Context context;

    /** Handler对象，用于与UI通信 */
    private Handler handler;

    /** 行为日志上报实体类 */
    private List<UserUsedDurationBean> userUsedDurationBeans;

    /**
     * <默认构造函数>
     */
    public UserUsedDurationLogBiz(Context context, Handler handler, List<UserUsedDurationBean> userUsedDurationBeans)
    {
        this.context = context;
        this.handler = handler;
        this.userUsedDurationBeans = userUsedDurationBeans;

        logTypeName = "[用户使用时长上报业务类]：";
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
    }

    private void handleProcess()
    {

        Logger.d(this.getClass(), logTypeName + "发送请求");
        // 组装报文请求
        UserUsedDurationReq req = buildReq();
        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(req);

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

    }

    private UserUsedDurationReq buildReq()
    {
        UserUsedDurationReq req = new UserUsedDurationReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setUserUsedDurationBeans(userUsedDurationBeans);

        return req;
    }

}
