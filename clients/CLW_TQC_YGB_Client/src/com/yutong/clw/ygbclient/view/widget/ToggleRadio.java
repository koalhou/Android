/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-11-4 下午2:27:29
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.view.util.DensityUtil;

/**
 * @author zhangyongn 2013-11-4 下午2:27:29 切换单选控件。实现两个选项，互斥，左右平铺显示。主要用于：早班晚班、厂内厂外
 */
@SuppressLint("ResourceAsColor")
public class ToggleRadio extends LinearLayout implements OnClickListener
{
    private RelativeLayout leftRL;

    private RelativeLayout rightRL;

    private TextView tv_left;

    private TextView tv_right;

    private ImageView iv_baseline_left;

    private ImageView iv_baseline_right;

    private IToggleListener listener;

    private String[] optionTexts;

    /**
     * 当前选中项
     */
    private ToggleOption selectedOption;

    private LinearLayout baseLine_LL;
    
    public ToggleRadio(Context context)
    {
        super(context, null);
    }

    public ToggleRadio(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if (isInEditMode())
            return;
        LayoutInflater.from(context).inflate(R.layout.control_toggleradio, this, true);
        initViews();
        initImageLine();
        registerListener();
        setSelectedOption(ToggleOption.Left);
        

    }

    private void registerListener()
    {
        leftRL.setOnClickListener(this);
        rightRL.setOnClickListener(this);
    }

    private void initViews()
    {
        leftRL = (RelativeLayout) findViewById(R.id.leftRL);
        rightRL = (RelativeLayout) findViewById(R.id.rightRL);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right= (TextView) findViewById(R.id.tv_right);

        /*iv_baseline_left = (ImageView) findViewById(R.id.iv_baseline_left);
        iv_baseline_right = (ImageView) findViewById(R.id.iv_baseline_right);*/
        
        baseLine_LL = (LinearLayout) findViewById(R.id.baseLine_LL);
    }

    public void SetToggleListener(IToggleListener lis)
    {
        listener = lis;
    }

    /**
     * @return selectedWorkPlan
     */
    public ToggleOption getSelectedOption()
    {
        return selectedOption;
    }

    /**
     * @param selectedOption 要设置的 选项
     */
    public void setSelectedOption(ToggleOption selectedOption)
    {
        this.selectedOption = selectedOption;
        int maincolor = getResources().getColor(R.color.maincolor);
        int blackcolor = getResources().getColor(R.color.black);

        tv_left.setTextColor((selectedOption == ToggleOption.Left) ? maincolor : blackcolor);
        tv_right.setTextColor((selectedOption == ToggleOption.Right) ? maincolor : blackcolor);

        /*RelativeLayout.LayoutParams l = (RelativeLayout.LayoutParams) iv_baseline_right.getLayoutParams();
        l.height = DensityUtil.dip2px(YtApplication.getInstance(), (selectedOption == ToggleOption.Right) ? 3 : 1);
        iv_baseline_right.setLayoutParams(l);

        l = (RelativeLayout.LayoutParams) iv_baseline_left.getLayoutParams();
        l.height = DensityUtil.dip2px(YtApplication.getInstance(), (selectedOption == ToggleOption.Left) ? 3 : 1);
        iv_baseline_left.setLayoutParams(l);*/
        
        if(selectedOption == ToggleOption.Left) {
        	startLineAnimation(0,1);
        }else{
        	startLineAnimation(1,0);
        }
        
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.leftRL:
            if (selectedOption == ToggleOption.Right)
            {
                setSelectedOption(ToggleOption.Left);
                triggerSwitch();
                
                startLineAnimation(0,1);
            }
            break;
        case R.id.rightRL:
            if (selectedOption == ToggleOption.Left)
            {
                setSelectedOption(ToggleOption.Right);
                triggerSwitch();
                startLineAnimation(1,0);
            }
            break;
        }

    }

    private void triggerSwitch()
    {
        if (this.listener != null)
        {
            listener.onToggle(selectedOption);
        }

    }

    /**
     * @param optionTexts 要设置的 optionTexts
     */
    public void setOptionTexts(String[] optionTexts)
    {
        this.optionTexts = optionTexts;
        if (optionTexts == null && optionTexts.length < 2)
        {
            optionTexts = new String[] { "左", "右" };
        }
        tv_left.setText((selectedOption == ToggleOption.Left) ? optionTexts[0] : optionTexts[1]);
        tv_right.setText((selectedOption == ToggleOption.Right) ? optionTexts[0] : optionTexts[1]);
        
    }

    /**
     * 切换侦听器
     * 
     * @author zhangyongn 2013-11-5 下午1:38:31
     */
    public interface IToggleListener
    {
        /**
         * @author zhangyongn 2013-11-5 上午11:28:19
         * @param ToggleOption 切换后的选项
         */
        void onToggle(ToggleOption option);

    }

    /**
     * 切换项
     * 
     * @author zhangyongn 2013-11-8 上午10:43:52
     */
    public enum ToggleOption
    {
        Left, Right
    }
    
    private WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

    private int disPlayWidth = wm.getDefaultDisplay().getWidth();
    
    private ImageView line_image;
    
    private void initImageLine() {
		line_image = new ImageView(getContext());
		line_image.setBackgroundColor(Color.parseColor("#3399ff"));
		line_image.setLayoutParams(new LinearLayout.LayoutParams(disPlayWidth/ 2, LinearLayout.LayoutParams.FILL_PARENT));
	
		baseLine_LL.addView(line_image);
    }

	public void startLineAnimation(int index,int lastIndex) {
		float Spacing = disPlayWidth / 2;
		TranslateAnimation translate = new TranslateAnimation(lastIndex*Spacing,index*Spacing, 0, 0);
		translate.setDuration(100);
		translate.setFillAfter(true);
		line_image.startAnimation(translate);
	}
}
