package com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic;

import java.util.concurrent.Callable;

import android.content.Context;

import com.yutong.clw.ygbclient.business.loginfo.UsageUploadBiz;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

public class BizBehaviorStatistic extends BizBase {

	public String module_son_id;
	private Context mContext;

	public BizBehaviorStatistic(Context context, String module_son_id) {
		super(context);
		this.module_son_id = module_son_id;
	}

	public void reportBehavior(BizResultProcess<Boolean> process) {

		final UsageUploadBiz biz = new UsageUploadBiz(mContext);

		Callable<Boolean> netWorkCallable = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				return biz.reportUsage(module_son_id);
			}

		};

		BizNetWork(netWorkCallable, process);
	}

}
