/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-8 上午10:44:01
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.business.linestation.GetRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.SetRemindStationsBiz;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhouzc 2013-11-8 上午10:44:01
 */
public class BizSetting extends BizBase
{

    public BizSetting(Context context)
    {
        super(context);
    }

    public BizSetting(Context context, int threadcount)
    {
        super(context, threadcount);
    }

    public BizDataTypeEnum getReminds(final LineRange lineRange, AreaType area_type, BizResultProcess<List<RemindInfo>> process)
    {
        final GetRemindStationsBiz biz = new GetRemindStationsBiz(mContext, area_type);

        Callable<List<RemindInfo>> LocalCallable = new Callable<List<RemindInfo>>()
        {
            @Override
            public List<RemindInfo> call() throws Exception
            {
                List<RemindInfo> data = biz.getRemindsFromLocal();
                return Filter(data, lineRange);

            }
        };

        Callable<List<RemindInfo>> networkCallable = new Callable<List<RemindInfo>>()
        {
            @Override
            public List<RemindInfo> call() throws Exception
            {

                List<RemindInfo> data = biz.getRemindsFromSever();

                return Filter(data, lineRange);
            }
        };

        return BizCommonWork(!biz.IsCacheExpires(), LocalCallable, networkCallable, process);

    }

    protected List<RemindInfo> Filter(List<RemindInfo> data, LineRange lineRange)
    {
        if (data == null)
            return null;
        List<RemindInfo> ret = new ArrayList<RemindInfo>();
        for (int i = 0; i < data.size(); i++)
        {
            if (data.get(i).getLine_range() == lineRange)
            {
                ret.add(data.get(i));
            }
        }
        return ret;
    }

    public void setRemind(RemindInfo info, BizResultProcess<Boolean> process)
    {

        final SetRemindStationsBiz biz = new SetRemindStationsBiz(mContext, Arrays.asList(info));
        Callable<Boolean> networkcall = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {

                return biz.setRemindInfo();
            }
        };
        BizNetWork(networkcall, process);

    }

    public void deleteReminds(List<RemindInfo> reminds, BizResultProcess<Boolean> process)
    {
        for (RemindInfo remind : reminds)
        {
            remind.setRemind_status(RemindStatus.Delete);
        }
        final SetRemindStationsBiz biz = new SetRemindStationsBiz(mContext, reminds);
        Callable<Boolean> networkcall = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {

                return biz.setRemindInfo();
            }
        };
        BizNetWork(networkcall, process);
    }

    public BizDataTypeEnum getUnReadAdviceNum(BizResultProcess<Integer> process)
    {
		
    	
    	return null;
    	
    }
}
