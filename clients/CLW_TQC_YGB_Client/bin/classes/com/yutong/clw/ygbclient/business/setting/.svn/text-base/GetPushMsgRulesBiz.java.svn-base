/**
 * @公司名称：YUTONG
 * @文件名：GetPushMsgRulesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:38:21
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.setting;

import java.util.List;

import android.content.Context;

import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.business.common.AbstractDataControl;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.PushMsgRule;
import com.yutong.clw.ygbclient.common.cache.CahceDataManager;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.connect.http.common.HttpRes;
import com.yutong.clw.ygbclient.connect.http.setting.GetPushMsgRulesReq;
import com.yutong.clw.ygbclient.connect.http.setting.GetPushMsgRulesRes;
import com.yutong.clw.ygbclient.dao.linestation.LineInfoDao;
import com.yutong.clw.ygbclient.dao.setting.PushMsgRuleDao;

/**
 * 获取推送规则业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:38:21
 */
public class GetPushMsgRulesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public GetPushMsgRulesBiz(Context context)
    {
        this.context = context;
        logTypeName = "[获取推送规则业务逻辑类]:";
        setActionURI(ActionURI.GET_PUSH_MSG_RULES);

    }

    public List<PushMsgRule> getRulesFromLocal()
    {
        Logger.i(this.getClass(), logTypeName + "从本地返回数据");
        return new PushMsgRuleDao(context).getPushMsgRules(ProxyManager.getInstance(context).getUserCode());
    }

    public List<PushMsgRule> getRulesFromServer() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        GetPushMsgRulesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);
        List<PushMsgRule> pushMsgRules = null;
        if (httpRes.isSuccess())
        {
            GetPushMsgRulesRes res = new GetPushMsgRulesRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                if (httpRes.needCached())
                {
                    pushMsgRules = res.getPushMsgRules();
                    CahceDataManager.getInstance(context).putCacheConfig(actionURI, cacheKey, httpRes.getETag());
                    new PushMsgRuleDao(context).batchUpdateAllPushMsgRule(pushMsgRules, ProxyManager.getInstance(context).getUserCode());
                    Logger.i(this.getClass(), logTypeName + "从网络返回数据");
                    return res.getPushMsgRules();
                }
                else
                {
                    return getRulesFromLocal();
                }
            }
            else
            {
                Logger.e(this.getClass(), logTypeName + "失败");
                throw new CommonException(CommonNetStatus.Error_Info, res.getErrorCode(), res.getErrorDes());

            }
        }
        else if (httpRes.isTokenExpire())
        {
            Logger.e(this.getClass(), logTypeName + "Token失效");
            throw new CommonException(CommonNetStatus.Token_InValid);
        }
        else if (httpRes.isException())
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Exception);
        }
        else
        {
            Logger.e(this.getClass(), logTypeName + "失败：", httpRes.getFailInfo());
            throw new CommonException(CommonNetStatus.NetWork_Not_Stable);
        }
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private GetPushMsgRulesReq buildReq()
    {
        GetPushMsgRulesReq req = new GetPushMsgRulesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setIfNoneMatch(CahceDataManager.getInstance(context).getCacheETag(actionURI, cacheKey));

        return req;
    }

}