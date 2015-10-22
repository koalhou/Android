package com.yutong.axxc.parents.business.login;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.common.SysInfoGetUtil;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.VersionInfoBean;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.VersionChkReq;
import com.yutong.axxc.parents.connect.http.packet.VersionChkRes;

/**
 * 版本检查业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class VersionChkBiz extends YtAsyncTask
{
    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /**
     * 构造函数
     * 
     * @param context Context对象
     * @param handler Handler对象
     */
    public VersionChkBiz(Context context, Handler handler)
    {
        this.context = context;
        this.handler = handler;
        logTypeName = "[版本检查业务逻辑类]:";
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground()
    {
        versionChk();
    }

    /**
     * 登陆逻辑
     */
    private void versionChk()
    {
//         VersionInfoBean versionInfoBean = new VersionInfoBean();
//         versionInfoBean.setChangeDesc("1新版本描述TEST\n2新版本描述\r\n3新版本描述,新版本描述");
//         versionInfoBean.setTargetVersion("家长版TestV1.1");
//         versionInfoBean.setUri("http://10.8.3.217:8080/xcp/AnxinParents.apk");
//         versionInfoBean.setForce(false);
//         
//         ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NEED_UPGRADE,
//         versionInfoBean);
      
        long start = System.currentTimeMillis();
        VersionChkReq versionChkReq = buildVersionChkReq(context);
        Logger.i(this.getClass(), logTypeName + "发送版本检查请求");
        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(versionChkReq);
        

        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            VersionChkRes versionChkRes = new VersionChkRes();
            if (httpRes.getStatusCode() == 204)
            {
                long end = System.currentTimeMillis();
                if (end - start < 500)
                {
                    try
                    {
                        Thread.sleep(500 - (end - start), 0);
                    }
                    catch (InterruptedException e)
                    {
                        Logger.e(VersionChkBiz.class, e);
                    }
                }
                Logger.i(this.getClass(), logTypeName + "版本检查成功，不升级");
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NO_NEED_UPGRADE);
            }
            else if (versionChkRes.parse(httpRes.getContent()) && !versionChkRes.isError())
            {
                Logger.i(this.getClass(), logTypeName + "版本检查成功，需升级");
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NEED_UPGRADE, buildVersionInfo(versionChkRes));
            }
            else
            {
                Logger.w(this.getClass(), logTypeName + "版本检查失败");
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.CHK_VERSION_FAILED, httpRes.getFailInfo());
            }
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

    /**
     * @param loginRes
     * @return
     */
    private VersionInfoBean buildVersionInfo(VersionChkRes versionChkRes)
    {
        VersionInfoBean versionInfoBean = new VersionInfoBean();
        versionInfoBean.setChangeDesc(versionChkRes.getChangeDesc());
        versionInfoBean.setTargetVersion(versionChkRes.getTargetVersion());
        versionInfoBean.setUri(versionChkRes.getUri());
        versionInfoBean.setForce(versionChkRes.isForce());
        return versionInfoBean;
    }

    /**
     * 组装登录请求对象
     * 
     * @param context
     * @param bizLoginInfoBean
     * @return
     */
    private VersionChkReq buildVersionChkReq(Context context)
    {
        VersionChkReq versionChkReq = new VersionChkReq();
        versionChkReq.setVersion(SysInfoGetUtil.getPackageInfo(context));
        versionChkReq.setClientInfo(SysInfoGetUtil.getClientInfoBean(context).getJsonString());
        return versionChkReq;
    }
}
