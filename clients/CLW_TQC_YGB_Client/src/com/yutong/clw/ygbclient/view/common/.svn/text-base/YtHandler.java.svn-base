/*******************************************************************************
 * @(#)YtHandler.java 2013-5-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.clw.ygbclient.view.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.yutong.clw.ygbclient.common.constant.ThreadCommStateCode;

/**
 * Handler 父类，处理公共事件
 * 
 * @author zhouzc
 * 
 */
public class YtHandler extends Handler {

	/**
	 * 处理公共事件
	 * 
	 * @param msg
	 * @param activity
	 */
	public void handleMessage(Message msg, Activity activity) {
		switch (msg.what) {
		case ThreadCommStateCode.TOKEN_INVALID:
			// TODO 显示token过期
			// TokenInvalidAlert.showConfirm(activity);
			break;
		default:
			break;
		}
	}

}
