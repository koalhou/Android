package com.yutong.clw.ygbclient.view.pages.setting.feedback;

import java.util.UUID;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.other.AdvicePushInfoDao;
import com.yutong.clw.ygbclient.view.bean.push.FeedBackPushBean;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.feedback.BizFeedBack;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.common.TimeUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.util.DensityUtil;

/**
 * 意见反馈界面
 * 
 * @author zhouzc
 */
public class FeedbackActivity extends RemindAccessActivity implements OnClickListener
{
//	[$NoChar('\\s*|\t|\r|\n',<@不能有空格@>)$]
	/*
	 * Custom("正则表达式", "验证不通过描述")
          	示例：Custom("\d+","必须为数字，且至少包含1个数字") 
    */
    
    private final String ValidStringFeedBack = "[$Len(5,500,<@字符串长度必须在5到500个之间@>)$][$IgnoreEnter([\n.],<@忽略回车@>)$]";

    private EditText inputET;
    
    private Button submitBtn;
    
    private BizFeedBack biz = new BizFeedBack(this);
    
    private int mTotalNum = 500;
    
    private TextView mLeftCharacterNum_TV;
    
    private Context mContext;
    
    private String mContent;
    
    private String msgId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_feedback);
        mContext = this;
        initViews();
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
        
    	btn_left.setBackgroundResource(R.drawable.bg_bluebtn);
    	btn_left.setText("取消");
    	btn_left.setTextColor(Color.WHITE);
    	btn_left.setPadding(DensityUtil.dip2px(FeedbackActivity.this, 15), 0, DensityUtil.dip2px(FeedbackActivity.this, 15), 0);
    	
    	tv_large.setText("发表意见");
    	
        btn_right.setText("提交");
        
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }
    
    public void initViews()
    {
    	submitBtn = (Button) findViewById(R.id.btn_it_right);
    	submitBtn.setEnabled(false);
    	
    	mLeftCharacterNum_TV = (TextView) findViewById(R.id.leftCharacterNum_TV);
    	
    	inputET = (EditText) findViewById(R.id.inputET);
        inputET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

				String inputET_Str = s.toString();
				if(inputET_Str!=null && inputET_Str.length()>0){
					submitBtn.setEnabled(true);
					submitBtn.setTextColor(getResources().getColor(R.color.white));
				}else{
					submitBtn.setEnabled(false);
					submitBtn.setTextColor(getResources().getColor(R.color.password_confirm));
				}
				setCharacterNum(inputET_Str);
			}
			
			
			private void setCharacterNum(String inputStr){
				if(!StringUtil.isEmpty(inputStr)){
					int number = mTotalNum - inputStr.length();
					mLeftCharacterNum_TV.setText(String.valueOf(number));
				}else{
					mLeftCharacterNum_TV.setText(String.valueOf(mTotalNum));
				}
			}
		});
    }

    

    @Override
    protected void onLoadingCanceled(int key)
    {
        super.onLoadingCanceled(key);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_it_left:
            finish();
            break;
        case R.id.btn_it_right:
            mContent = StringUtil.parseStr(inputET.getText());
            if (!checkInput(mContent))
            {
                break;
            }

            HideSoftKeyboard();
            this.showLoading("正在提交...", 0);
            
            msgId = UUID.randomUUID().toString();
            biz.SubmitFeedBack(msgId,mContent, new BizResultProcess<Boolean>()
            {

                @Override
                public void onBizExecuteError(final Exception exception, Error error)
                {
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                    
                            dismissLoading(0);
                            HandleLogicErrorInfo(exception);
                        }
                    });

                }

                @Override
                public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
                {
                    runOnUiThread(new Runnable()
                    {
                        public void run(){
                        	
                        	FeedBackPushBean announcementPushBean = new FeedBackPushBean();
                        	
                        	announcementPushBean.msgID = msgId;
                        	announcementPushBean.adviseTime = TimeUtil.getFormatDateTime("yyyy-MM-dd HH:mm:ss");
                        	announcementPushBean.advise = mContent;
                        	announcementPushBean.userCode = PreferencesUtils.getString(mContext,  ActivityCommConstant.prefName,  ActivityCommConstant.EMP_NUMBER);
                        	announcementPushBean.replyFlag = ActivityCommConstant.ADVICE_UN_REPLY;
                        	announcementPushBean.readFlag = ActivityCommConstant.ADVICE_UN_READ;
                        	
                        	new AdvicePushInfoDao(mContext).addAdviceInfo(announcementPushBean);
                        	
                            FeedbackActivity.this.dismissLoading(0);
                            FeedbackActivity.this.ToastMessage("提交成功！");
                            FeedbackActivity.this.finish();
                        }
                    });

                }
            });
            break;

        default:
            break;
        }

    }

    private boolean checkInput(String content)
    {
        if (content == null || content.length() == 0)
        {
            Toast.makeText(this.getApplicationContext(), "请您输入意见反馈内容!", Toast.LENGTH_SHORT).show();
            return false;
        }
        String result = DataValidationUtil.validate(ValidStringFeedBack, content);

        if (StringUtil.isNotBlank(result))
        {
            result = "意见反馈：" + result;
            Toast.makeText(this.getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
