/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:18:51
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.enums.MsgPushType;
import com.yutong.clw.ygbclient.common.push.AbstractPush;
import com.yutong.clw.ygbclient.view.util.NotificationUtil;

/**
 * 推送消息工具类
 * 
 * @author zhangzhia 2013-09-11 上午9:52:20
 */
public final class PushMsgUtil
{
    private static List<AbstractPush> pushMsgs = new ArrayList<AbstractPush>();
    private static String logTypeName = "[推送消息工具类]";
    
    public static void addPush(AbstractPush push)
    {
        if (!pushMsgs.contains(push))
        {
            Logger.i(PushMsgUtil.class, logTypeName + "添加推送消息:" + push.getMsgPushType().toString() + ",id:" + push.id);
            pushMsgs.add(push);
        }
    }

    public static List<AbstractPush> getPushs()
    {
        return pushMsgs;
    }

    /*-------------------------------新闻start----------------------------------------------

    /**
     * 新闻通知数
     * 
     * @author zhangzhia 2013-9-11 下午2:38:16
     * @return
     */
    public static int getNewsNotificationCount()
    {
        return getNotificationCount(MsgPushType.News);
    }

    /**
     * 删除新闻记录
     * 
     * @author zhangzhia 2013-9-11 下午2:38:16
     * @return
     */
    public static void deleteNewsNotificationInfo()
    {
        deleteNotificationInfo(MsgPushType.News);
        NotificationUtil.removeNotification(NotificationUtil.NOTIFICATIONID_ANNOUNCE);
    }

    /*-------------------------------新闻end----------------------------------------------

    
    /*-------------------------------通用方法start----------------------------------------------
    
    /**
     * 通用通知数
     * 
     * @author zhangzhia 2013-9-11 下午2:39:19
     * @param msgPushType
     * @return
     */
    private static int getNotificationCount(MsgPushType msgPushType)
    {
        int count = 0;
        if (pushMsgs == null || pushMsgs.size() == 0)
        {
            return count;
        }

        for (AbstractPush item : pushMsgs)
        {
            if (msgPushType.equals(item.getMsgPushType()))
            {
                count++;
            }
        }

        return count;
    }

    /**
     * 通用删除记录
     * 
     * @author zhangzhia 2013-9-11 下午2:39:19
     * @param msgPushType
     * @return
     */
    private static void deleteNotificationInfo(MsgPushType msgPushType)
    {
        if (pushMsgs == null || pushMsgs.size() == 0)
        {
            return;
        }

        Iterator<AbstractPush> iter = pushMsgs.iterator();
        while (iter.hasNext())
        {
            AbstractPush item = iter.next();

            if (msgPushType.equals(item.getMsgPushType()))
            {
                iter.remove();
            }
        }
    }

    /**
     * 根据推送id获取推送
     * 
     * @author zhangzhia 2013-11-21 上午10:07:19
     * @param msg_id
     * @return
     */
    public static AbstractPush getNotification(String msg_id)
    {
        int count = 0;
        if (pushMsgs == null || pushMsgs.size() == 0)
        {
            return null;
        }

        for (AbstractPush item : pushMsgs)
        {
            if (msg_id.equals(item.id))
            {
                return item;
            }
        }

        return null;
    }

    /**
     * 根据推送id删除推送
     * 
     * @author zhangzhia 2013-11-21 上午10:07:47
     * @param msg_id
     */
    public static void deleteNotification(String msg_id)
    {
        if (pushMsgs == null || pushMsgs.size() == 0)
        {
            return;
        }

        Iterator<AbstractPush> iter = pushMsgs.iterator();
        while (iter.hasNext())
        {
            AbstractPush item = iter.next();

            if (msg_id.equals(item.id))
            {
                iter.remove();
            }
        }
    }
}
