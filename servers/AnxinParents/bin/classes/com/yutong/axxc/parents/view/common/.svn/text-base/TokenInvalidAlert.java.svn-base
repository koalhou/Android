package com.yutong.axxc.parents.view.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.logout.LogoutBiz;
import com.yutong.axxc.parents.common.Logger;


/**
 * Token失效 确认退出？
 * @author maoky
 */
public class TokenInvalidAlert {

    private static volatile Boolean SHOW = true;

    public static void showConfirm(final Activity context) {
        new LogoutBiz(context).clearCurrentAcountLoginInfo();
        Logger.w(TokenInvalidAlert.class, "运行Token失效提示.");
        if (SHOW) {
            SHOW = false;
        } else {
            Logger.w(TokenInvalidAlert.class, "Token失效提示不能弹出.");
            return;
        }
        ActivityManager.clearReciverAndNotification();

        final Dialog dialog = new Dialog(context) {

            @Override
            public void onBackPressed() {
                super.onBackPressed();
                ActivityManager.exit();
                SHOW = true;
            }

        };
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_temp);
        TextView titleTx = (TextView) dialog.findViewById(R.id.titleTx);
        TextView messageTx = (TextView) dialog.findViewById(R.id.messageTx);
        Button firstBt = (Button) dialog.findViewById(R.id.firstBt);
        Button secondBt = (Button) dialog.findViewById(R.id.secondBt);
        titleTx.setText("错误");
        messageTx.setText("登录已失效，是否重新登录？");
        firstBt.setText("退出");
        secondBt.setText("重新登录");
        firstBt.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.onBackPressed();
            }
        });
        secondBt.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                SHOW = true;
                dialog.dismiss();
                ActivityManager.releaseAllResourceAndGoLogin();
            }
        });
        if (!context.isFinishing()) dialog.show();

    }

    /**
     * 不需要任何参数的，试试。。效果一样否
     */
    @Deprecated
    public static void showConfirm() {
        if (SHOW) {
            SHOW = false;
        } else {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityManager.getOneActivity());
        builder.setTitle("错误");
        builder.setMessage("登录已失效，是否重新登录？");
        builder.setCancelable(false);
        builder.setPositiveButton("登录", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityManager.releaseAllResourceAndGoLogin();
                SHOW = true;
            }
        });
        builder.setNeutralButton("取消", new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SHOW = true;
            }
        });
        builder.create().show();
    }
}
