/*******************************************************************************
 * @(#)ActivityManager.java 2012-6-21
 *
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.common;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Process;

import com.yutong.axxc.parents.business.logout.LogoutBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.view.login.LoginActivity;
import com.yutong.axxc.parents.view.settings.account.SettingAccountHomeActivity;

/**
 * Activity管理类
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2012-6-21 下午12:20:35
 */
public final class ActivityManager {

    private static final List<WeakReference<Activity>> references = new Vector<WeakReference<Activity>>();

    private ActivityManager() {

    }

    /**
     * 添加Activity到容器中[/color]
     * @param activity
     */
    public static void addActivity(WeakReference<Activity> reference) {
        references.add(reference);
    }

    /**
     * 添加Activity到容器中[/color]
     * @param activity
     */
    public static Activity getOneActivity() {
        for (WeakReference<Activity> reference : references) {
            if (reference.get() != null) {
                return reference.get();
            }
        }
        return null;
    }

    /**
     * 遍历所有Activity并finish[/color]
     */
    public static void exit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                releaseAllResource();
            }
        }).start();

    }

    /**
     * 遍历所有Activity并finish[/color]
     */
    public static void exitApp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                closeAllActivity();
                clearReciverAndNotification();
                //new LogoutBiz().logout();
                killProcess();
            }
        }).start();
    }

    /**
     * 释放所有资源
     */
    public static void releaseAllResource() {
        closeAllActivity();
        clearReciverAndNotification();
        killProcess();
    }

    /**
     * 杀进行
     */
    private static void killProcess() {
        //MessageManager.getInstance().stopService(YtApplication.getInstance());
        Process.killProcess(Process.myPid());
    }

    /**
     * 清理广播接收和提示
     */
    public static void clearReciverAndNotification() {
       // PushNotificationUtil.clearAllNotification();
        //ReceiverUtil.unRegisterPushReceiver();
    }

    /**
     * 关闭所有的activity
     */
    private static void closeAllActivity() {
        try {
            Logger.i(Logger.class, "[Activity管理类]:references size:", references.size());
            for (WeakReference<Activity> reference : references) {
                if (reference.get() != null) {
                    Logger.i(Logger.class, "[Activity管理类]:finsh activity class:", reference.get().getClass());
                    reference.get().finish();
                }
            }
        } catch (Exception e) {
            Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :", e);
        }

    }

    /**
     * 关闭所有的activity
     */
    public static void closeOtherActivityExpectSettingAccountHome() {
        try {
            Logger.i(Logger.class, "[Activity管理类]:references size:", references.size());
            for (WeakReference<Activity> reference : references) {
                if (reference.get() != null && !(reference.get() instanceof SettingAccountHomeActivity)) {
                    Logger.i(Logger.class, "[Activity管理类]:finsh activity class:", reference.get().getClass());
                    reference.get().finish();
                }
            }
        } catch (Exception e) {
            Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :", e);
        }

    }

 
    public static void closeSettingAccountHomeActivity() {
        try {
            Logger.i(Logger.class, "[Activity管理类]:references size:", references.size());
            for (WeakReference<Activity> reference : references) {
                if (reference.get() != null && reference.get() instanceof SettingAccountHomeActivity) {
                    Logger.i(Logger.class, "[Activity管理类]:finsh activity class:", reference.get().getClass());
                    reference.get().finish();
                }
            }
        } catch (Exception e) {
            Logger.d(Logger.class, "[Activity管理类]:finsh activity exception :", e);
        }

    }

    /**
     * 输出所有的activity
     */
    public static void outputAllActivity() {
        Logger.i(Logger.class, "[Activity管理类]:references size:", references.size());
        for (WeakReference<Activity> reference : references) {
            if (reference.get() != null) {
                Logger.i(Logger.class, "[Activity管理类]:alive activity class:", reference.get().getClass());
            }
        }
    }

    /**
     * 释放所有资源回到登录界面
     */
    public static void releaseAllResourceAndGoLogin() {
        closeAllActivity();
        new LogoutBiz(YtApplication.getInstance().getApplicationContext()).clearCurrentAcountLoginInfo();
        Intent intent = new Intent(YtApplication.getInstance().getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        YtApplication.getInstance().startActivity(intent);

    }
}
