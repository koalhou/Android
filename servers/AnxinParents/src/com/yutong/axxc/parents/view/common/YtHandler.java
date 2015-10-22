/*******************************************************************************
 * @(#)YtHandler.java 2013-5-6
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.common;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.yutong.axxc.parents.common.ThreadCommStateCode;

/**
 * Handler 父类，处理公共事件
 * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
 * @version $Revision 1.1 $ 2013-5-6 下午3:36:32
 */
public class YtHandler extends Handler {

    /**
     * 处理公共事件
     * @param msg
     * @param activity
     */
    public void handleMessage(Message msg, Activity activity) {
        switch (msg.what) {
        case ThreadCommStateCode.TOKEN_INVALID:
            TokenInvalidAlert.showConfirm(activity);
            break;
        default:
            break;
        }
    }

}
