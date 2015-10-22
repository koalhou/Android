/*******************************************************************************
 * @(#)ActivityUtils.java 2013-2-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/


package com.yutong.axxc.parents.view.common;


import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.GlobleConstants;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.context.StringUtil;

/**
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-2-25 下午03:20:19
 */
public final class ActivityUtils {
    private static Context context;

    private ActivityUtils() {

    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        ActivityUtils.context = context;
    }

    /**
     * 释放所有系统运行时占用资源（线程、服务等）
     * @param context
     */
    public static void stopAllThread(Context context) {
        ActivityManager.releaseAllResource();

    }

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static String getSoftwareVersion(Context context) {
        String version = null;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName + pInfo.versionCode;

        } catch (NameNotFoundException e) {
            Logger.e(ActivityUtils.class, "获取客户端当前版本时，未找到相关包信息，", e);
        }
        return version;
    }

    /**
     * activity跳转
     * @param from 源activity
     * @param to 目标activity
     * @param extras 需要传递的数据
     */
    public static void changeActivity(Activity from, Class<?> to, Bundle extras) {
        // Intent intent = setIntent(from, to, extras);
        Intent intent = new Intent();
        if (extras != null) intent.putExtras(extras);
        intent.setClass(from.getBaseContext(), to);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        from.startActivity(intent);
        //from.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        from.overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
    }

    /**
     * @param vMonitorBatchActivity TODO
     * @param from 源activity
     * @param to 目标biactivity
     * @param vBeanList 需要传递的列表数据
     */
    public static void changeActivityWithData(Activity from, Class<?> to, Intent mIntent) {
        if (mIntent != null) {
            mIntent.setClass(from, to);
            from.startActivity(mIntent);
        }
    }

    /**
     * 显示消息
     * @param context
     * @param msg
     */
    public static void toast(Context context, Object msg) {
        Toast.makeText(context, StringUtil.parseObj2Str(msg), Toast.LENGTH_SHORT).show();
    }

    /**
     * 电话呼叫指定号码
     * @param activity
     * @param telNumber
     */
    public static void call(Activity activity, String telNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telNumber));
        activity.startActivity(intent);
    }

    /**
     * 动态改变字体的颜色
     * @return
     */
    public static ColorStateList createSelector(Context context, int pressedColor, int defaultColor) {
        int statePressed = android.R.attr.state_pressed;
        int stateFocesed = android.R.attr.state_focused;
        int[][] state = { { statePressed }, { -statePressed }, { stateFocesed }, { -stateFocesed } };
        int color1 = context.getResources().getColor(pressedColor);
        int color2 = context.getResources().getColor(defaultColor);
        int color3 = context.getResources().getColor(pressedColor);
        int color4 = context.getResources().getColor(defaultColor);
        int[] color = { color1, color2, color3, color4 };
        ColorStateList colorStateList = new ColorStateList(state, color);
        return colorStateList;
    }

    public static String subString(String str) {
        if (str != null) {
            if (str.length() >= 7)
                return str.substring(str.length() - 7);
            else
                return str;
        } else {
            return GlobleConstants.EMPTY_STR;
        }
    }

    /**
     * 加粗数字
     * @param str
     * @return
     */
    public static SpannableStringBuilder str2Span(String str) {
        // str = qj2bj(str).replace(",", ", ");
        if (StringUtils.isBlank(str)) return new SpannableStringBuilder();
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        // 暂去除文字换行效果，查看小米机型问题
        // char[] chars = str.toCharArray();
        //
        // for (char ch : chars) {
        // if ((ch >= '0' && ch <= '9') || ch == '.') {
        // SpannableString string = new SpannableString(StringUtil.parseStr(ch));
        // string.setSpan(new RelativeSizeSpan(1.0f), 0, 1, 0);
        // string.setSpan(new StyleSpan(Typeface.BOLD), 0, 1, 0);
        // builder.append(string);
        // continue;
        // }
        // builder.append(ch);
        // }
        return builder;
    }

    /**
     * ASCII表中可见字符从!开始，偏移位值为33(Decimal)
     */
    private static final char DBC_CHAR_START = 33; // 半角!

    /**
     * ASCII表中可见字符到~结束，偏移位值为126(Decimal)
     */
    private static final char DBC_CHAR_END = 126; // 半角~

    /**
     * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
     */
    private static final char SBC_CHAR_START = 65281; // 全角！

    /**
     * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
     */
    private static final char SBC_CHAR_END = 65374; // 全角～

    /**
     * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
     */
    private static final int CONVERT_STEP = 65248; // 全角半角转换间隔

    /**
     * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
     */
    private static final char SBC_SPACE = 12288; // 全角空格 12288

    /**
     * 半角空格的值，在ASCII中为32(Decimal)
     */
    private static final char DBC_SPACE = ' '; // 半角空格

    /**
     * <PRE>
     * 全角字符->半角字符转换   
     * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
     * </PRE>
     */
    public static String qj2bj(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < src.length(); i++) {
            if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
                buf.append((char) (ca[i] - CONVERT_STEP));
            } else if (ca[i] == SBC_SPACE) { // 如果是全角空格
                buf.append(DBC_SPACE);
            } else { // 不处理全角空格，全角！到全角～区间外的字符
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * <PRE>
     * 半角字符->全角字符转换   
     * 只处理空格，!到&tilde;之间的字符，忽略其他
     * </PRE>
     */
    public static String bj2qj(String src) {
        if (src == null) {
            return src;
        }
        StringBuilder buf = new StringBuilder(src.length());
        char[] ca = src.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            if (ca[i] == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
                buf.append(SBC_SPACE);
            } else if ((ca[i] >= DBC_CHAR_START) && (ca[i] <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
                buf.append((char) (ca[i] + CONVERT_STEP));
            } else { // 不对空格以及ascii表中其他可见字符之外的字符做任何处理
                buf.append(ca[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 全角转半角
     * @param QJstr
     * @return
     */
    public static final String QBchange(String QJstr) {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;

        for (int i = 0; i < QJstr.length(); i++) {
            try {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + Tstr;
        }
        return outStr;
    }

    public static void clearCash(final Context acitvity, String server_org_id, String local_org_id) {
        // 如果本地ID和服务端返回ID一致，则什么都不做，否则清空本地照片、新闻、告警缓存
        Logger.i(acitvity.getClass(), "【 登录操作类】，服务端返回组织ID：", server_org_id, "本地保存的组织ID:", local_org_id);
        if (!server_org_id.equals(local_org_id)) {
            // TODO 清空本地缓存
            // 删除告警信息
            //AlertInfoDao alertInfoDao = new AlertInfoDao(acitvity);
           // alertInfoDao.delAlertInfos(YtApplication.getInstance().getUid());
            // 删除照片缓存
            //PhotoDao photoDao = new PhotoDao(acitvity);
           // photoDao.delAllPhotoInfos(YtApplication.getInstance().getUid());
            // 删除车辆缓存
            // VehicleDao vehicleDao = new VehicleDao(acitvity);
            // vehicleDao.delVehicleByUid(YtApplication.getInstance().getUid());
            // 删除所有新闻缓存
            // NewsInfoDao newsInfoDao = new NewsInfoDao(loginAcitvity);
            // newsInfoDao.delNewsInfos(userInfoBean.getUsrId());
        }
    }
}
