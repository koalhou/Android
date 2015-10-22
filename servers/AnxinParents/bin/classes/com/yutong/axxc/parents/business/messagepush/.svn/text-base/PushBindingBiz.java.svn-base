
package com.yutong.axxc.parents.business.messagepush;

import android.content.Context;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.PushBindingReq;
import com.yutong.axxc.parents.view.common.YtApplication;


/**
 * 推送绑定业务逻辑类
 * @author zhangzhia 2013-8-27 上午10:14:43
 *
 */
public class PushBindingBiz extends YtAsyncTask {

    /** Context对象 */
    private Context context;

    /** 登录信息对象 */
    private String clientid;

    /**
     * 构造函数
     * @param context Context对象
     * @param handler Handler对象
     * @param clientid 客户端ID
     */
    public PushBindingBiz(Context context, String clientid) {
        this.context = context;
        this.clientid = clientid;
        
        logTypeName = "[推送绑定业务逻辑类]:";
    }


    /**
     * (non-Javadoc)
     * @see com.neusoft.yt.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground() {
        handleProcess();
    }

    /**
     * 绑定
     */
    private void handleProcess() {
        PushBindingReq req = buildReq();
        Logger.i(this.getClass(), logTypeName + "发送请求");
        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(req, 3);
        if (httpRes.isSuccess()) {
            Logger.i(this.getClass(), logTypeName + "成功");
        } else if (httpRes.isException()) {
            Logger.i(this.getClass(), logTypeName + "失败，网络异常");
        } else {
            Logger.i(this.getClass(), logTypeName + "失败，未知错误");
        }
    }

    private PushBindingReq buildReq() {
        PushBindingReq req = new PushBindingReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setClientid(clientid);
        return req;
    }

}
