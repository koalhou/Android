package com.yutong.clw.ygbclient.view.pages.setting.userguide;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.pages.setting.ViewPagerAdapter;

public class UserGuideActivity extends Activity
{

    private String returnType = ActivityCommConstant.GUID_GOTO_LOGIN;

    private ViewPager vp;

    private ViewPagerAdapter vpAdapter;

    private List<View> views;

    // 记录当前选中位置
    private int currentIndex;

    private ImageView imageView;  
    
    private ImageView[] imageViews; 
    
    private ViewGroup main, group;  
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        String rtype = this.getIntent().getStringExtra(ActivityCommConstant.GUID_RETURN_TYPE);
        if (!StringUtil.isEmpty(rtype))
        {
            setReturnType(rtype);
        }
        // 初始化页面
        initViews();

    }

    private void initViews()
    {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.view_guide_1, null));
        views.add(inflater.inflate(R.layout.view_guide_2, null));
        views.add(inflater.inflate(R.layout.view_guide_3, null));

        View viewfour = inflater.inflate(R.layout.view_guide_4, null);
        views.add(viewfour);

        imageViews = new ImageView[views.size()];  
        group = (ViewGroup)findViewById(R.id.viewGroup);  
        for (int i = 0; i < views.size(); i++) {  
        	
            imageView = new ImageView(UserGuideActivity.this);  
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            lp.setMargins(15, 5, 15, 0);
            imageView.setLayoutParams(lp);  
            imageView.setPadding(20, 0, 20, 0);  
            imageViews[i] = imageView;  
            if (i == 0) {  
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.bullet_blue);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.bullet_purple);  
            }  
            group.addView(imageViews[i]);  
        }  
        
        Button btn = (Button) viewfour.findViewById(R.id.btn_return);
        if (StringUtil.equals(returnType, ActivityCommConstant.GUID_GOTO_LOGIN))
        {
            btn.setText("立即体验");
        }
        else
        {
            btn.setText("返\t\t回");
        }

        // 初始化Adapter
        vpAdapter = new ViewPagerAdapter(views, this, this.getReturnType());
        
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(new GuidePageChangeListener());
    }

    /**
     * @return the returnType
     */
    public String getReturnType()
    {
        return returnType;
    }

    /**
     * @param returnType the returnType to set
     */
    public void setReturnType(String returnType)
    {
        this.returnType = returnType;
    }
    
    
    
    /** 指引页面改监听器 */
    class GuidePageChangeListener implements OnPageChangeListener {  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0]  
                        .setBackgroundResource(R.drawable.bullet_blue);  
                if (arg0 != i) {  
                    imageViews[i]  
                            .setBackgroundResource(R.drawable.bullet_purple);  
                }  
            }
  
        }  
  
    }  
    
}
