package com.yutong.axxc.parents.business.student;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.SearchCertigierUserReq;
import com.yutong.axxc.parents.connect.http.packet.SearchCertigierUserRes;
import com.yutong.axxc.parents.view.common.YtApplication;


/**
 *  获取待授权用户信息逻辑类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class SearchCertigierUserBiz extends YtAsyncTask {

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;
    
    /** 待授权账号（安芯号或者注册手机号） */
    private String account_name;
    
    /** 学生id */
    private String cld_id;

    public SearchCertigierUserBiz(Context context, Handler handler, String account_name, String cld_id) {
        this.context = context;
        this.handler = handler;
        this.account_name = account_name;
        this.cld_id = cld_id;
        logTypeName = "[获取待授权用户信息逻辑类]:";
    }

    @Override
    protected void doInBackground() {
        handleProcess();
    }

    private void handleProcess() {
  
        Logger.i(this.getClass(), logTypeName + "发送请求");
        SearchCertigierUserReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPostMsg(req);
        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

  
    if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
        } else if (httpRes.isTokenExpire()) {
            Logger.i(this.getClass(), logTypeName + "Token失效");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.TOKEN_INVALID);
        } else if (httpRes.isException()) {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.NETWORK_ERROR, httpRes.getFailInfo());
        } else {
            Logger.w(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, httpRes.getFailInfo());
        }
    }

    private void parseAndProcessRes(HttpRes httpRes) {
        SearchCertigierUserRes res = new SearchCertigierUserRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");
            
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, res.getUserInfoBean());
 
        } else {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }
    

    private SearchCertigierUserReq buildReq() {
        SearchCertigierUserReq req = new SearchCertigierUserReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setAccount_name(account_name);
        req.setCld_id(cld_id);
        
        return req;
    }

}
