package com.yutong.axxc.parents.business.settings;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.PushMsgRuleBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetPushMsgRuleRes;
import com.yutong.axxc.parents.connect.http.packet.SetPushMsgRuleReq;
import com.yutong.axxc.parents.connect.http.packet.SetPushMsgRuleRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 设置信息推送设置业务类
 * 
 * @author zhangzhia 2013-9-3 下午3:36:02
 */
public class SetPushMsgSettingBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    private List<PushMsgRuleBean> pushMsgRuleBeans;

    private String belongEtag = EtagSettingsDao.PUSH_MSG_RULE_ETAG;

    private String belongCache = CahceSettingsDao.PUSH_MSG_RULE_ETAG;

    private String exkey;

    public SetPushMsgSettingBiz(Context context, Handler handler, List<PushMsgRuleBean> pushMsgRuleBeans)
    {
        this.context = context;
        this.handler = handler;
        this.pushMsgRuleBeans = pushMsgRuleBeans;

        logTypeName = "[设置信息推送设置业务类]:";

        exkey = YtApplication.getInstance().getUid();
    }

    @Override
    protected void doInBackground()
    {
        checkRuleChange();

    }

    /**
     * 信息推送设置检测是否改变
     */
    private void checkRuleChange()
    {
        Logger.d(this.getClass(), logTypeName + "设置内容：", pushMsgRuleBeans);
        boolean diff = false;
        List<PushMsgRuleBean> beans = getInfoFromLocal();
//        add by lizyi
        beans = null;
        if (beans != null && beans.size() > 0)
        {
            for (PushMsgRuleBean newPush : pushMsgRuleBeans)
            {
                for (PushMsgRuleBean oldPush : beans)
                {
                    if (!newPush.getOn_off().equals(oldPush.getOn_off()))
                    {
                        diff = true;
                        break;
                    }
                }
            }
        }
        else
        {
            diff = true;
        }

        if (diff)
        {
            handleProcess();
        }
        else
        {
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.PUSH_MSG_NEEDNOT_SAVE);
        }

    }

    /**
     * 从本地获取信息
     */
    private List<PushMsgRuleBean> getInfoFromLocal()
    {
        String jsonString = CahceSettingsDao.getCacheInfo(belongCache, exkey);
        if (StringUtils.isNotBlank(jsonString))
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            GetPushMsgRuleRes res = new GetPushMsgRuleRes();
            res.parse(jsonString);

            return res.getPushMsgRuleBeans();
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "没有缓存数据");
            return null;
        }
    }

    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetPushMsgRuleReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendPutMsg(req);
        // 用来UI取消操作
        if (isCanceled())
        {
            return;
        }

        if (httpRes.isSuccess())
        {
            parseAndProcessRes(httpRes);
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

    private void parseAndProcessRes(HttpRes httpRes)
    {
        SetPushMsgRuleRes res = new SetPushMsgRuleRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS);

        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private SetPushMsgRuleReq buildReq()
    {
        SetPushMsgRuleReq req = new SetPushMsgRuleReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setPushMsgRuleBeans(pushMsgRuleBeans);

        return req;
    }

}
