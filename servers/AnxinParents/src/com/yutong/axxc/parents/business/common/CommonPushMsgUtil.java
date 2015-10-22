package com.yutong.axxc.parents.business.common;

import java.util.Iterator;
import java.util.List;

import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.connect.push.CommonPushMsgConstant;
import com.yutong.axxc.parents.view.common.YtApplication;

/**
 * CommonPushMsgUtil工具类
 * 
 * @author zhangzhia 2013-09-11 上午9:52:20
 */
public final class CommonPushMsgUtil
{
    /**
     * 乘车通知数
     * 
     * @author zhangzhia 2013-9-11 下午2:38:16
     * @return
     */
    public static int getRidingNotificationCount()
    {
        return getNotificationCount(CommonPushMsgConstant.RIDING_PUSH_MSG_TYPE);
    }

    /**
     * 系统新闻通知数
     * 
     * @author zhangzhia 2013-9-11 下午2:38:16
     * @return
     */
    public static int getNewsNotificationCount()
    {
        return getNotificationCount(CommonPushMsgConstant.NEWS_PUSH_MSG_TYPE);
    }

    /**
     * 通用通知数
     * 
     * @author zhangzhia 2013-9-11 下午2:39:19
     * @param pust_type
     * @return
     */
    public static int getNotificationCount(String pust_type)
    {

        List<CommonPushMsg> list = YtApplication.getInstance().getCommonPushMsgs();

        if(list == null)
        {
            return 0;
        }
        
        int count = 0;
        for (CommonPushMsg item : list)
        {
            if (pust_type.equals(item.getMsgType()))
            {
                count++;
            }
        }

        return count;
    }

    /**
     * 获取学生的乘车通知数
     * 
     * @author zhangzhia 2013-9-11 下午2:41:26
     * @param cld_id
     * @return
     */
    public static int getChildRidingNotificationCount(String cld_id)
    {

        List<CommonPushMsg> list = YtApplication.getInstance().getCommonPushMsgs();

        int count = 0;
        for (CommonPushMsg item : list)
        {
            if (cld_id.equals(item.getCld_id()))
            {
                count++;
            }
        }

        return count;
    }

    /**
     * 删除乘车记录
     * 
     * @author zhangzhia 2013-9-11 下午2:38:16
     * @return
     */
    public static void deleteRidingNotificationInfo()
    {
        deleteNotificationInfo(CommonPushMsgConstant.RIDING_PUSH_MSG_TYPE);
    }

    /**
     * 删除系统新闻记录
     * 
     * @author zhangzhia 2013-9-11 下午2:38:16
     * @return
     */
    public static void deleteNewsNotificationInfo()
    {
        deleteNotificationInfo(CommonPushMsgConstant.NEWS_PUSH_MSG_TYPE);
    }

    /**
     * 通用删除记录
     * 
     * @author zhangzhia 2013-9-11 下午2:39:19
     * @param pust_type
     * @return
     */
    public static void deleteNotificationInfo(String pust_type)
    {
        List<CommonPushMsg> list = YtApplication.getInstance().getCommonPushMsgs();

        if(list == null)
        {
            return;
        }
        
        Iterator<CommonPushMsg> iter = list.iterator();
        while (iter.hasNext())
        {
            CommonPushMsg item = iter.next();

            if (pust_type.equals(item.getMsgType()))
            {
                iter.remove();
            }
        }
    }

    /**
     * 删除学生乘车记录
     * 
     * @author zhangzhia 2013-9-11 下午2:39:19
     * @param pust_type
     * @return
     */
    public static void deleteChildNotificationCount(String cld_id)
    {
        List<CommonPushMsg> list = YtApplication.getInstance().getCommonPushMsgs();

        if(list == null)
        {
            return;
        }
        
        Iterator<CommonPushMsg> iter = list.iterator();
        while (iter.hasNext())
        {
            CommonPushMsg item = iter.next();

            if (cld_id.equals(item.getCld_id()))
            {
                iter.remove();
            }
        }
    }

}
