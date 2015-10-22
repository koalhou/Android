
package com.yutong.axxc.parents.business.logout;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.yutong.axxc.parents.business.behaviour.UserBehaviorUploadBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.LogoutReq;
import com.yutong.axxc.parents.dao.login.LoginInfoDao;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 退出登录业务逻辑类
 * @author zhangzhia 2013-8-22 上午10:20:08
 *
 */
public class LogoutBiz {
    
    /** Context对象 */
    private Context context;
    
    private String logTypeName = "[退出登录业务逻辑类]:";
    
    public LogoutBiz(Context context)
    {
        this.context = context;
        Logger.i(this.getClass(), logTypeName + "清除当前用户登录信息");
        
    }
    
    /**
     * 清除当前用户登录信息
     * @param context
     */
    public void clearCurrentAcountLoginInfo() {
        new LoginInfoDao(context).delLoginInfo();
    }

    /**
     * 退出登录
     * @param context
     */
    public void logout() {
        
      //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0002);
        
        UploadLogBiz.uploadUserBehavior();
        
        Logger.i(this.getClass(), logTypeName + "退出登录");
        LogoutReq logoutReq = new LogoutReq();
        logoutReq.setAccessToken(YtApplication.getInstance().getAccessToken());
        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(logoutReq);
        clearCurrentAcountLoginInfo();
        Logger.i(this.getClass(), logTypeName + "退出登录,StatusCode:", httpRes.getStatusCode());

    }
}
