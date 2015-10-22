
package com.yutong.clw.ygbclient.common;

import java.util.List;
import java.util.Map;

import android.os.Handler;
import android.os.Message;


/**
 * 线程通讯工具类
 * @author zhangzhia 2013-9-11 下午5:18:13
 *
 */
public class ThreadCommUtil {
    private ThreadCommUtil() {
    }


    public static void sendMsgToUI(Handler handler, int msgWhat) {
        if (handler != null) {
            Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",threadid:", Thread.currentThread().getId());
            Message message = handler.obtainMessage();
            message.what = msgWhat;
            handler.sendMessage(message);
        }
    }


    public static void sendMsgToUI(Handler handler, int msgWhat, Object msgObj) {
        if (handler != null) {
            if (msgObj instanceof String) {
                Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",obj:", msgObj);
            } else if (msgObj instanceof List) {
                Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",list size:", ((List<?>) msgObj).size());
            } else if (msgObj instanceof Map) {
                Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",map size:", ((Map<?, ?>) msgObj).size());
            } else {
                Logger.i(ThreadCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",obj:", msgObj);
            }

            Message message = handler.obtainMessage();
            message.what = msgWhat;
            message.obj = msgObj;
            handler.sendMessage(message);
        }
    }
}
