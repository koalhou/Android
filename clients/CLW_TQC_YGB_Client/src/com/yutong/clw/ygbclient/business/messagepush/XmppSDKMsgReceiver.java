/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:08:53
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.messagepush;

import android.util.Log;

import com.yutong.axxc.xmpp.client.model.XmppInfo;
import com.yutong.axxc.xmpp.client.provider.InfoReceivedListener;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.business.common.PushMsgUtil;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.push.AbstractPush;
import com.yutong.clw.ygbclient.common.push.FeedBackPush;
import com.yutong.clw.ygbclient.common.push.FactoryInsidePush;
import com.yutong.clw.ygbclient.common.push.FactoryOuterPush;
import com.yutong.clw.ygbclient.common.push.NewsPush;
import com.yutong.clw.ygbclient.connect.push.PushMsgExpression;
import com.yutong.clw.ygbclient.view.bean.push.PushBean;

/**
 * 推送消息广播接收类
 * 
 * @author zhangzhia 2013-9-3 上午9:14:25
 */
public class XmppSDKMsgReceiver implements InfoReceivedListener
{
    /** 通知栏不提示 */
    private final String NON_PROMPT = "0";

    private String logTypeName = "[推送消息广播接收类]:";

    @Override
    public void Receive(XmppInfo msginfo)
    {
        Logger.i(this.getClass(), logTypeName + "收到推送消息" + msginfo == null ? "" : msginfo.getInfo());
        handleMsgData(msginfo.getInfo());
    }

    /**
     * 处理消息数据
     * @param context
     */
    private void handleMsgData(String pushJsonString)
    {
        MsgPushType msgPushType = PushMsgExpression.getMsgPushType(pushJsonString);
        Log.i("TAG", "pushJsonString = "+pushJsonString);
        AbstractPush msgPush = null;

        switch (msgPushType)
        {
        case News:
            msgPush = new NewsPush();
            break;
            
        case FactoryInside:
            msgPush = new FactoryInsidePush();
            break;
            
        case FactoryOuter:
            msgPush = new FactoryOuterPush();
            break;
            
        case Feedback:
        	msgPush = new FeedBackPush();
        	break;
        default:
            // Unknow
            Logger.e(this.getClass(), "[推送消息广播接收类]:未知消息推送类型，丢弃");
            break;
        }

        if (msgPush != null && msgPush.parse(pushJsonString)){
        	
            PushMsgUtil.addPush(msgPush);
            YtApplication.getInstance().getPushMessageRouter().push(PushBean.parseFromCommonMessage(msgPush));
        }
    }
}
