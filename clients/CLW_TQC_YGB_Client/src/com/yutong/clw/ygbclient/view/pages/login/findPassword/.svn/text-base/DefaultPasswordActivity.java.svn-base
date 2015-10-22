package com.yutong.clw.ygbclient.view.pages.login.findPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;

/**
 * 首次进入找回密码反馈界面
 * 
 * @author zhouzc
 */
public class DefaultPasswordActivity extends BaseActivity implements OnClickListener
{
    private TextView loginTV;

    private TextView numberlabel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpass_defaultpass);

        initViews();
        initLestener();
    }

    private void initLestener()
    {

        loginTV.setOnClickListener(this);

    }

    public void initViews()
    {
        loginTV = (TextView) findViewById(R.id.loginTV);
        numberlabel = (TextView) findViewById(R.id.numberlabel);
        initData();
    }

    private void initData()
    {
        Intent intent = getIntent();
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        String empnumber = (String) bundle.getSerializable(ActivityCommConstant.EMP_NUMBER);

        numberlabel.setText("员工号:" + empnumber);
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

            this.finish();
            break;

        default:
            break;
        }

    }
}
