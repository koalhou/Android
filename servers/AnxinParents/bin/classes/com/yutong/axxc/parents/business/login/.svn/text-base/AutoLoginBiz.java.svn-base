package com.yutong.axxc.parents.business.login;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.business.behaviour.UserBehaviorUploadBiz;
import com.yutong.axxc.parents.business.common.SysInfoGetUtil;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LoginInfoBean;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.AutoLoginReq;
import com.yutong.axxc.parents.connect.http.packet.AutoLoginRes;
import com.yutong.axxc.parents.connect.http.packet.LoginRes;
import com.yutong.axxc.parents.dao.beans.DaoLoginInfoBean;
import com.yutong.axxc.parents.dao.login.LoginInfoDao;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 自动登录业务逻辑类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class AutoLoginBiz extends YtAsyncTask
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
     * @param bizLoginInfoBean 登录信息对象
     */
    public AutoLoginBiz(Context context, Handler handler)
    {
        this.context = context;
        this.handler = handler;
        logTypeName = "[自动登录业务类]:";
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground()
    {
        login();
        
        //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0001);
        
        
    }

    /**
     * 登陆
     */
    private void login()
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        AutoLoginReq req = buildReq();

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
     * 解析并处理返回消息
     * 
     * @param httpRes
     */
    private void parseAndProcessRes(HttpRes httpRes)
    {
        AutoLoginRes res = new AutoLoginRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            YtApplication.getInstance().setAccessToken(res.getAccessToken());
            YtApplication.getInstance().setUid(res.getUserInfoBean().getUsr_id());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.AUTO_LOGIN_SUCCESS, res.getUserInfoBean());
            updateLoginInfo(context, res);
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    /**
     * 更新登录信息
     * 
     * @param context
     * @param loginRes
     */
    private void updateLoginInfo(Context context, AutoLoginRes loginRes)
    {
        DaoLoginInfoBean daoLoginInfoBean = new DaoLoginInfoBean();
        daoLoginInfoBean.setAccessToken(loginRes.getAccessToken());
        daoLoginInfoBean.setExpiresIn(loginRes.getExpiresIn());
        daoLoginInfoBean.setRefreshToken(loginRes.getRefreshToken());

        UserInfoBean userInfoBean = loginRes.getUserInfoBean();
        daoLoginInfoBean.setUsr_id(userInfoBean.getUsr_id());
        daoLoginInfoBean.setUsr_no(userInfoBean.getUsr_no());
        daoLoginInfoBean.setUsr_login_name(userInfoBean.getUsr_login_name());
        daoLoginInfoBean.setUsr_name(userInfoBean.getUsr_name());
        daoLoginInfoBean.setUsr_pwd(userInfoBean.getUsr_pwd());
        daoLoginInfoBean.setUsr_sex(userInfoBean.getUsr_sex());
        daoLoginInfoBean.setUsr_phone(userInfoBean.getUsr_phone());
        daoLoginInfoBean.setUsr_photo(userInfoBean.getUsr_photo());
        daoLoginInfoBean.setUsr_addr(userInfoBean.getUsr_addr());
        daoLoginInfoBean.setUsr_email(userInfoBean.getUsr_email());
        daoLoginInfoBean.setP_usr_no(userInfoBean.getP_usr_no());
        daoLoginInfoBean.setP_usr_login_name(userInfoBean.getP_usr_login_name());

        LoginInfoDao loginInfoDao = new LoginInfoDao(context);
        loginInfoDao.addLoginInfo(daoLoginInfoBean);
    }

    /**
     * 组装登录请求对象
     * 
     * @param context
     * @param loginInfoBean
     * @return
     */
    private AutoLoginReq buildReq()
    {
        AutoLoginReq req = new AutoLoginReq();
        req.setAccessToken(YtApplication.getInstance( ).getAccessToken());

        req.setClientInfo(SysInfoGetUtil.getClientInfoBean(context).getJsonString());

        return req;
    }

}
