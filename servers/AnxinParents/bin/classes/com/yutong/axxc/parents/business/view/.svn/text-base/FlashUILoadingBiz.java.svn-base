package com.yutong.axxc.parents.business.view;

import android.content.Context;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.view.common.MessageBuilder;

/**
 * 闪屏加载数据逻辑处理类
 * 
 * @author zhangyongn
 * 
 */
public class FlashUILoadingBiz extends YtAsyncTask {
	/** Context对象 */
	private Context context;

	/** Handler对象 */
	private Handler handler;

	public FlashUILoadingBiz(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;

	}

	@Override
	protected void doInBackground() {
		try {
			MessageBuilder.Load();
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_SUCCESS, null);
		} catch (Exception e) {
			Logger.w(this.getClass(), logTypeName + "失败：", e.getMessage());
			ThreadCommUtil.sendMsgToUI(handler,
					ThreadCommStateCode.COMMON_FAILED, e.getMessage());
		}
	}
}
