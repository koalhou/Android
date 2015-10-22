package com.yutong.axxc.parents.connect.http.packet;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.beans.PushMsgRuleBean;

/**
 * 查询推送消息规则响应类
 * 
 * @author zhangzhia 2013-9-3 下午3:54:06
 */
public class GetPushMsgRuleRes extends AbstractRes
{
    /** 服务器返回列表 */
    private List<PushMsgRuleBean> pushMsgRuleBeans;

    @Override
    boolean parseCorrectMsg(String jsonString)
    {
        try
        {
            JSONTokener jsonTokener = new JSONTokener(jsonString);
            JSONObject jsonObj = (JSONObject) jsonTokener.nextValue();
            
            JSONArray jsonArray = jsonObj.optJSONArray("rule_contents");
            if (jsonArray != null && jsonArray.length() > 0)
            {
                pushMsgRuleBeans = new ArrayList<PushMsgRuleBean>();
                for (int i = 0, arrayLen = jsonArray.length(); i < arrayLen; i++)
                {
                    PushMsgRuleBean ruleSubRes = new PushMsgRuleBean();
                    ruleSubRes.parse(jsonArray.optJSONObject(i));

                    pushMsgRuleBeans.add(ruleSubRes);
                }
            }
            return true;
        }
        catch (JSONException e)
        {
            Logger.e(getClass(), "[查询推送消息规则响应类]:解析响应消息异常：", e);
            return false;
        }
    }

 

    public List<PushMsgRuleBean> getPushMsgRuleBeans()
    {
        return pushMsgRuleBeans;
    }


}
