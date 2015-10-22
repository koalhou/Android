
package com.yutong.axxc.parents.common.context;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * 系统共享资源工具类
 * @author zhangzhia 2013-8-21 下午5:13:45
 *
 */
public final class ContextUtil {
    /**
     * 获取系统共享资源值，如果没有相应的key，则以defaltValue值为默认值创建相应的key
     * @param <T>
     * @param context
     * @param preName
     * @param mode
     * @param key
     * @param defaultV
     * @return
     */
    public static <T> T getPreference(Context context, String preName, int mode, String key, T defaultV) {
        SharedPreferences sharedPF = context.getSharedPreferences(preName, mode);
        Object result = null;
        if (defaultV instanceof Boolean)
            result = sharedPF.getBoolean(key, (Boolean) defaultV);
        else if (defaultV instanceof Float)
            result = sharedPF.getFloat(key, (Float) defaultV);
        else if (defaultV instanceof Long)
            result = sharedPF.getFloat(key, (Long) defaultV);
        else if (defaultV instanceof Integer)
            result = sharedPF.getInt(key, (Integer) defaultV);
        else
            result = sharedPF.getString(key, (String) defaultV);
        @SuppressWarnings("unchecked")
        T t = (T) result;
        return t;
    }

    /**
     * 设置系统共享资源值，如果没有相应的key，则以value值为默认值创建相应的key,如果有，则覆盖key值内容
     * @param <T>
     * @param context
     * @param preName SharedPreferences名称
     * @param mode SharedPreferences访问模式
     * @param key
     * @param value
     */
    public static <T> void setPreferences(Context context, String preName, int mode, String key, T value) {
        SharedPreferences sharedPF = context.getSharedPreferences(preName, mode);
        if (value instanceof Boolean)
            sharedPF.edit().putBoolean(key, (Boolean) value).commit();
        else if (value instanceof Float)
            sharedPF.edit().putFloat(key, (Float) value).commit();
        else if (value instanceof Long)
            sharedPF.edit().putFloat(key, (Long) value).commit();
        else if (value instanceof Integer)
            sharedPF.edit().putInt(key, (Integer) value).commit();
        else
            sharedPF.edit().putString(key, (String) value).commit();
    }

    /**
     * activity跳转
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     * @param enterAnim 进入界面渲染动画
     * @param exitAnim 退出界面渲染动画
     */
    public static void alterActivity(Activity from, Class<?> to, Bundle extras, int enterAnim, int exitAnim) {
        Intent intent = new Intent(from, to);
        if (extras != null) intent.putExtras(extras);
        from.startActivity(intent);
        from.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * activity跳转
     * @param from 源activity
     * @param to 目标activity
     */
    public static void alterActivity(Activity from, Class<?> to) {
        alterActivity(from, to, null, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * activity跳转
     * @param from 源activity
     * @param to 目标activity
     * @param enterAnim 进入界面渲染动画
     * @param exitAnim 退出界面渲染动画
     */
    public static void alterActivity(Activity from, Class<?> to, int enterAnim, int exitAnim) {
        alterActivity(from, to, null, enterAnim, exitAnim);
    }

    /**
     * activity跳转
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     */
    public static void alterActivity(Activity from, Class<?> to, Bundle extras) {
        alterActivity(from, to, extras, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 相关联的Activity调用
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     * @param resultCode 目标Activity返回响应码
     * @param enterAnim 进入界面渲染动画
     * @param exitAnim 退出界面渲染动画
     */
    public static void alterActivityForResult(Activity from, Class<?> to, Bundle extras, int resultCode, int enterAnim,
            int exitAnim) {
        Intent intent = new Intent(from, to);
        if (extras != null) intent.putExtras(extras);
        from.startActivityForResult(intent, resultCode);
        from.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 相关联的Activity调用
     * @param from 源activity
     * @param to 目标activity
     * @param resultCode 目标Activity返回响应码
     */
    public static void alterActivityForResult(Activity from, Class<?> to, int resultCode, int enterAnim, int exitAnim) {
        alterActivityForResult(from, to, null, resultCode, enterAnim, exitAnim);
    }

    /**
     * 相关联的Activity调用
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     * @param resultCode 目标Activity返回响应码
     */
    public static void alterActivityForResult(Activity from, Class<?> to, Bundle extras, int resultCode) {
        alterActivityForResult(from, to, extras, resultCode, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 相关联的Activity调用
     * @param from 源activity
     * @param to 目标activity
     * @param resultCode 目标Activity返回响应码
     */
    public static void alterActivityForResult(Activity from, Class<?> to, int resultCode) {
        alterActivityForResult(from, to, null, resultCode, android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 在当前context的指定view上隐藏软件盘
     * @param context 当前上下文
     * @param view 需要隐藏软键盘的View
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager im = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        IBinder windowToken = view.getWindowToken();
        im.hideSoftInputFromWindow(windowToken, 0);
    }

    /**
     * 设置当前context为无标题状态
     * @param context
     */
    public static void setNoTitle(Activity context) {
        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 获得当前context屏幕参数
     * @param context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetric = context.getResources().getDisplayMetrics();
        return displayMetric;
    }
}
