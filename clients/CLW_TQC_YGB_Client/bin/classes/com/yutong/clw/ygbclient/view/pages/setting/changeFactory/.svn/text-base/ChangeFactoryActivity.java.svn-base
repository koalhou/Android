package com.yutong.clw.ygbclient.view.pages.setting.changeFactory;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;

/**
 * 厂区切换页面
 * 
 * @author zhouzc
 */
public class ChangeFactoryActivity extends RemindAccessActivity implements OnClickListener
{
    // 一厂
    private RelativeLayout firstFactoryRL;

    // 二厂
    private RelativeLayout secondFactoryRL;

    private ArrayList<ImageView> icons;

    private ImageView firstFactoryIV;

    private ImageView secondFactoryIV;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_changefactory);
        initViews();
        initData();
        initListener();
    }

    public void initViews()
    {
        firstFactoryRL = (RelativeLayout) findViewById(R.id.firstFactoryRL);
        secondFactoryRL = (RelativeLayout) findViewById(R.id.secondFactoryRL);

        firstFactoryIV = (ImageView) findViewById(R.id.firstFactoryIV);
        secondFactoryIV = (ImageView) findViewById(R.id.secondFactoryIV);
    }

    /**
     * 加载数据
     * 
     * @author lizyi 2013-11-9 下午2:58:23
     */
    private void initData()
    {
        initIcons();
        // 读取历史配置信息
        AreaType currentFactory = PrefDataUtil.getFactory(getApplicationContext());

        ImageView lastSelectedIcon = null;
        if (AreaType.FirstFactory.equals(currentFactory))
        {
            lastSelectedIcon = firstFactoryIV;
        }
        else if (AreaType.SecondFactory.equals(currentFactory))
        {
            lastSelectedIcon = secondFactoryIV;
        }
        else
        {
            lastSelectedIcon = firstFactoryIV;
        }
        manageIcons(lastSelectedIcon);
    }

    /**
     * 设置监听
     * 
     * @author lizyi 2013-11-9 下午2:58:09
     */
    private void initListener()
    {
        firstFactoryRL.setOnClickListener(this);
        secondFactoryRL.setOnClickListener(this);
    }

    /**
     * 管理图片
     */
    private void initIcons()
    {
        icons = new ArrayList<ImageView>();
        icons.add(firstFactoryIV);
        icons.add(secondFactoryIV);

    }

    /**
     * 管理图标的显示与隐藏
     * 
     * @author lizyi 2013-11-9 下午4:16:39
     * @param v
     */
    private void manageIcons(View v)
    {
        ImageView icon = (ImageView) v;
        if (icon == null)
        {
            return;
        }
        for (int i = 0; i < icons.size(); i++)
        {
            if (icon.equals(icons.get(i)))
            {
                icon.setVisibility(View.VISIBLE);
            }
            else
            {
                icons.get(i).setVisibility(View.INVISIBLE);
            }

        }

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
        btn_left.setBackgroundResource(R.drawable.bg_prevbtn);
        btn_right.setVisibility(View.INVISIBLE);
        tv_large.setText("厂区切换");
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

        btn_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
    	
    	
        switch (v.getId())
        {
        case R.id.btn_it_left:
            finish();
            break;
        case R.id.firstFactoryRL:
            
            PrefDataUtil.setFactory(this, AreaType.FirstFactory);
            finish();
            break;
        case R.id.secondFactoryRL:
            
            PrefDataUtil.setFactory(this, AreaType.SecondFactory);
            finish();
            break;

        default:
            break;
        }

    }

}