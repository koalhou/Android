package com.yutong.axxc.parents.connect.push;

import org.json.JSONObject;

/**
 * Push内容公共类
 * 
 * @author zhangzhia 2013-9-3 下午3:16:09
 */
public class CommonPushMsg extends AbstractPushMsg
{

    /**
     * （非 Javadoc）
     * 
     * @see com.yutong.axxc.parents.connect.push.AbstractPush#parseContent(org.json.JSONObject)
     */
    @Override
    void parseContent(JSONObject jsonObject)
    {
    }

    public String getMsgType()
    {
        if (this.rule_id != null && this.rule_id.length() >= 2)
        {
            String rule = rule_id.substring(0, 2);
            if (CommonPushMsgConstant.RIDING_PUSH_MSG_TYPE.equals(rule))
            {
                return CommonPushMsgConstant.RIDING_PUSH_MSG_TYPE;
            }
            else if (CommonPushMsgConstant.NEWS_PUSH_MSG_TYPE.equals(rule))
            {
                return CommonPushMsgConstant.NEWS_PUSH_MSG_TYPE;
            }
            else
            {
                return CommonPushMsgConstant.UNKNOWN_PUSH_MSG_TYPE;
            }
        }
        else
        {
            return CommonPushMsgConstant.UNKNOWN_PUSH_MSG_TYPE;
        }
    }

}
