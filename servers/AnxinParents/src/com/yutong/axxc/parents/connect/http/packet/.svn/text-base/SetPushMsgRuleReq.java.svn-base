package com.yutong.axxc.parents.connect.http.packet;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.PushMsgRuleBean;

/**
 * 设置推送消息规则请求
 * 
 * @author zhangzhia 2013-9-3 下午3:54:24
 */
public class SetPushMsgRuleReq extends AbstractReq
{

    private List<PushMsgRuleBean> pushMsgRuleBeans;

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#packetMsgBody()
     */
    @Override
    public String packetMsgBody()
    {
        try
        {
            JSONObject req = new JSONObject();
            if (pushMsgRuleBeans != null)
            {
                JSONArray ruleArray = new JSONArray();

                for (PushMsgRuleBean ruleItem : pushMsgRuleBeans)
                {
                    ruleArray.put(ruleItem.packaged());
                }

                req.put("rule_contents", ruleArray);
            }
            return req.toString();

        }
        catch (JSONException e)
        {
            Logger.e(SetPushMsgRuleReq.class, "[设置推送消息规则请求消息类]:组包生成设置推送消息规则请求消息时失败，详细信息：", e);
            return null;
        }
    }

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.http.packet.AbstractReq#setReuqestUri()
     */
    @Override
    public void setReuqestUri()
    {
        reuqestUri = reuqestUri + "/pushmsg/rule";

    }

    public void setPushMsgRuleBeans(List<PushMsgRuleBean> pushMsgRuleBeans)
    {
        this.pushMsgRuleBeans = pushMsgRuleBeans;
    }

}
