package com.yutong.clw.ygbclient.view.pages.login.findPassword;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;

/**
 * 重置密码成功界面
 * 
 * @author zhouzc
 */
public class SuccActivity extends BaseActivity implements OnClickListener
{
    private TimeCount time;

    private TextView loginTV;

    private TextView tiplabel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpass_succ);

        initViews();
        initLestener();

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (time == null)
            time = new TimeCount(60 * 1000, 1000);// 构造CountDownTimer对象
        time.start();// 开始计时
    }

    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        if (time != null)
        {
            time.cancel();// 开始计时
            time = null;
        }
    }

    private void initLestener()
    {

        loginTV.setOnClickListener(this);

    }

    public void initViews()
    {
        loginTV = (TextView) findViewById(R.id.loginTV);
        tiplabel = (TextView) findViewById(R.id.tiplabel);
    }

    @Override
    protected boolean hasTitle()
    {
        return true;
    }

    @Override
    protected void loadTitleByPageSetting(Button btn_left, Button btn_right, RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
            TextView tv_small)
    {
        btn_left.setVisibility(View.INVISIBLE);
        btn_right.setVisibility(View.INVISIBLE);
        btn_right.setText("确认");
        tv_large.setText("找回密码");
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.loginTV:
            if (time != null)
                time.cancel();
            this.finish();
            break;

        default:
            break;
        }
    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer
    {
        private long totalTimeInterval;

        public TimeCount(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
            totalTimeInterval = millisInFuture;
        }

        @Override
        public void onFinish()
        {
            // 计时完毕时触发
            gotoLogin();
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            // 计时过程显示

            String str = "密码设置成功，将在%s秒后";
            String text = String.format(str, (int) (millisUntilFinished / 1000));
            tiplabel.setText(text);
        }
    }

    public void gotoLogin()
    {
        this.finish();

    }
}
