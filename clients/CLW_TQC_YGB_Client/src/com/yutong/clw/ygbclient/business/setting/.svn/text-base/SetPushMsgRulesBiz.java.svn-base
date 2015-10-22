/**
 * @公司名称：YUTONG
 * @文件名：SetPushMsgRulesBiz.java
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午9:39:13
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
import com.yutong.clw.ygbclient.connect.http.setting.SetPushMsgRulesReq;
import com.yutong.clw.ygbclient.connect.http.setting.SetPushMsgRulesRes;
import com.yutong.clw.ygbclient.dao.setting.PushMsgRuleDao;

/**
 * 设置推送规则业务逻辑类
 * 
 * @author zhangzhia 2013-10-22 上午9:39:13
 */
public class SetPushMsgRulesBiz extends AbstractDataControl
{
    /** Context对象 */
    private Context context;

    /**
     * 推送规则
     */
    private List<PushMsgRule> rules;

    /**
     * 构造函数
     * 
     * @param context Context对象
     */
    public SetPushMsgRulesBiz(Context context, List<PushMsgRule> rules)
    {
        this.context = context;
        this.rules = rules;
        
        logTypeName = "[设置推送规则业务逻辑类]:";
        setActionURI(ActionURI.SET_PUSH_MSG_RULES);

    }

    public boolean updateRules() throws CommonException
    {
        Logger.i(this.getClass(), logTypeName + "发送请求");
        SetPushMsgRulesReq req = buildReq();

        HttpRes httpRes = getHttpRes(req);

        if (httpRes.isSuccess())
        {
            SetPushMsgRulesRes res = new SetPushMsgRulesRes();
            res.parse(httpRes.getContent());
            if (!res.isError())
            {
                Logger.i(this.getClass(), logTypeName + "成功");
                // 受影响缓存需要设置超时
                CahceDataManager.getInstance(context).removeCache(ActionURI.GET_PUSH_MSG_RULES);
                return true;
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
     * 信息推送设置检测是否改变
     * 
     * @throws CommonException
     */
    @SuppressWarnings("unused")
    private void checkRuleChange() throws CommonException
    {
        Logger.d(this.getClass(), logTypeName + "设置内容：", rules);
        boolean diff = false;
        List<PushMsgRule> beans = getRulesFromLocal();

        beans = null;
        if (beans != null && beans.size() > 0)
        {
            for (PushMsgRule newPush : rules)
            {
                for (PushMsgRule oldPush : beans)
                {
                    if (newPush.on_off == oldPush.on_off)
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
            updateRules();
        }

    }

    /**
     * 从本地获取信息
     */
    private List<PushMsgRule> getRulesFromLocal()
    {
        List<PushMsgRule> rules = new PushMsgRuleDao(context).getPushMsgRules(ProxyManager.getInstance(context).getUserCode());
        if (rules != null && rules.size() > 0)
        {
            Logger.i(this.getClass(), logTypeName + "读取本读缓存数据");
            
        }
        else
        {
            Logger.i(this.getClass(), logTypeName + "没有缓存数据");
        }

        return rules;
    }

    /**
     * 组装请求对象
     * 
     * @param context
     * @return
     */
    private SetPushMsgRulesReq buildReq()
    {
        SetPushMsgRulesReq req = new SetPushMsgRulesReq();
        req.setAccessToken(ProxyManager.getInstance(context).getAccessToken());
        req.setPushMsgRules(rules);

        return req;
    }

}
