/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-11-21 下午3:54:32
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.common;

import java.util.Arrays;
import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.business.linestation.GetSingleRemindStationsBiz;
import com.yutong.clw.ygbclient.business.linestation.SetRemindStationsBiz;
import com.yutong.clw.ygbclient.common.beans.RemindInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.LineRange;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindRange;
import com.yutong.clw.ygbclient.common.enums.remind.RemindStatus;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * @author zhouzc 2013-11-21 下午3:54:32
 * 
 */
public class BizRemind extends BizBase {

	public BizRemind(Context context) {
		super(context);
	}

	public BizRemind(Context context, int threadcount) {
		super(context, threadcount);
	}

	public void getStationRemind(final String stationid, AreaType areatype,
			StatusRange status_range, BizResultProcess<RemindInfo> process) {

		final GetSingleRemindStationsBiz biz = new GetSingleRemindStationsBiz(
				getmContext(), areatype, stationid, status_range, null);

		Callable<RemindInfo> networkcall = new Callable<RemindInfo>() {
			@Override
			public RemindInfo call() throws Exception {

				return biz.getRemind();
			}
		};
		BizNetWork(networkcall, process);
	}
	

	public void nolongerRemindToday(String  infoid,String stationid,
			BizResultProcess<Boolean> process) {
		RemindInfo info=new RemindInfo();
		info.setId(infoid);
		info.setStation_id(stationid);
		info.setRemind_status(RemindStatus.NoRemind);
		info.setLine_range(LineRange.FactoryOuter);
		info.setRemind_range(RemindRange.OnlyStation);//只针对站点的时候才有不再提醒
		final SetRemindStationsBiz biz = new SetRemindStationsBiz(mContext,
				Arrays.asList(info));
		Callable<Boolean> networkcall = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return biz.setRemindInfo();
			}
		};
		BizNetWork(networkcall, process);
	}
}
