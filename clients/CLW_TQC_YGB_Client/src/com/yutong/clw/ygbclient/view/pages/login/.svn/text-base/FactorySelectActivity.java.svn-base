package com.yutong.clw.ygbclient.view.pages.login;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.pages.main.MainActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;

/**
 * 厂区选择界面
 * 
 * @author zhouzc
 */
public class FactorySelectActivity extends BaseActivity implements OnClickListener
{
    private Button nextbtn;

    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_factoryselect);
        initViews();
        initLestner();
    }

    private void initLestner()
    {
        nextbtn.setOnClickListener(this);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                case R.id.factory1RB:

                    PrefDataUtil.setFactory(FactorySelectActivity.this, AreaType.FirstFactory);
                    break;
                case R.id.factory2RB:
                    PrefDataUtil.setFactory(FactorySelectActivity.this, AreaType.SecondFactory);
                    break;
                }

            }
        });
    }

    public void initViews()
    {
        nextbtn = (Button) findViewById(R.id.nextbtn);
        rg = (RadioGroup) findViewById(R.id.facradioRG);

        initFactorySet();

    }

    private void initFactorySet()
    {

        AreaType type = PrefDataUtil.getFactory(this);
        switch (type)
        {
        case FirstFactory:
            rg.check(R.id.factory1RB);
            break;
        case SecondFactory:
            rg.check(R.id.factory2RB);
            break;

        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

        case R.id.nextbtn:
            ActivityUtils.changeActivity(FactorySelectActivity.this, MainActivity.class);
            finish();
            
            break;
        default:
        }

    }

}
