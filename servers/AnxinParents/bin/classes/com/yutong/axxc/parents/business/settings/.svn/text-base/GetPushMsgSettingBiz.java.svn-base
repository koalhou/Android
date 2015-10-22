package com.yutong.axxc.parents.business.settings;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LineInfoBean;
import com.yutong.axxc.parents.common.beans.PushMsgRuleBean;
import com.yutong.axxc.parents.common.error.ErrorInfoUtil;
import com.yutong.axxc.parents.connect.http.HttpMsgSendUtil;
import com.yutong.axxc.parents.connect.http.HttpRes;
import com.yutong.axxc.parents.connect.http.packet.GetPushMsgRuleReq;
import com.yutong.axxc.parents.connect.http.packet.GetPushMsgRuleRes;
import com.yutong.axxc.parents.connect.http.packet.GetStudentLineInfoRes;
import com.yutong.axxc.parents.dao.common.CahceSettingsDao;
import com.yutong.axxc.parents.dao.common.EtagSettingsDao;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * 获取信息推送设置业务类
 * 
 * @author zhangzhia 2013-9-3 下午3:36:02
 */
public class GetPushMsgSettingBiz extends YtAsyncTask
{

    /** Context对象 */
    private Context context;

    /** Handler对象 */
    private Handler handler;

    /** 数据读取方式 */
    private ReadMethodEnum optype = ReadMethodEnum.OPTYPE_REMOTE;

    private String belongEtag = EtagSettingsDao.PUSH_MSG_RULE_ETAG;
    private String belongCache = CahceSettingsDao.PUSH_MSG_RULE_ETAG;
    
    private String exkey;

    public GetPushMsgSettingBiz(Context context, Handler handler)
    {
        this.context = context;
        this.handler = handler;

        logTypeName = "[获取信息推送设置业务类]:";

        exkey = YtApplication.getInstance().getUid();
    }

    public void setReadMethod(ReadMethodEnum optype)
    {
        this.optype = optype;
    }

    @Override
    protected void doInBackground()
    {
        switch (optype)
        {
        case OPTYPE_REMOTE:
            handleProcess();
            break;
        case OPTYPE_LOCAL:
            getInfoFromLocal();
            break;
        case OPTYPE_LOCAL_AND_REMOTE:
            getInfoFromLocal();
            break;
        default:
            break;
        }
    }

    /**
     * 从本地获取信息
     */
    private void getInfoFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "获取本地信息");
        List<PushMsgRuleBean> pushMsgRuleBeans = getLocalData();
        if (pushMsgRuleBeans != null)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");

            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_SUCCESS, pushMsgRuleBeans);
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "没有缓存数据");
            if(optype == ReadMethodEnum.OPTYPE_LOCAL)
            {
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.CACHE_NO_DATA);
            }
            else if(optype == ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE)
            {
                handleProcess();
            }
        }
    }

    private List<PushMsgRuleBean> getLocalData()
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
            return null;
        }
    }
    
    private void handleProcess()
    {

        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetPushMsgRuleReq req = buildReq();

        HttpRes httpRes = HttpMsgSendUtil.sendGetMsg(req);
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
        GetPushMsgRuleRes res = new GetPushMsgRuleRes();
        res.parse(httpRes.getContent());
        if (!res.isError())
        {
            Logger.i(this.getClass(), logTypeName + "成功");

            if (httpRes.needCached())
            {
                Logger.i(this.getClass(), logTypeName + "设置缓存");

                CahceSettingsDao.setCacheInfo(belongCache, exkey, httpRes.getContent());
                EtagSettingsDao.saveETag(belongEtag, exkey, httpRes.getETag());

                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_CHANGED, res.getPushMsgRuleBeans());
            }
            else
            {
                Logger.i(this.getClass(), logTypeName + "网络数据无变化");
                List<PushMsgRuleBean> pushMsgRuleBeans = getLocalData();
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.REMOTE_DATA_NO_CHANGED, pushMsgRuleBeans);
            }
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "失败");
            ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.COMMON_FAILED, ErrorInfoUtil.getInstance().get(res.getErrorCode()));
        }
    }

    private GetPushMsgRuleReq buildReq()
    {
        GetPushMsgRuleReq req = new GetPushMsgRuleReq();
        req.setAccessToken(YtApplication.getInstance().getAccessToken());
        req.setIfNoneMatch(EtagSettingsDao.getETag(belongEtag, exkey));
        return req;
    }

}
