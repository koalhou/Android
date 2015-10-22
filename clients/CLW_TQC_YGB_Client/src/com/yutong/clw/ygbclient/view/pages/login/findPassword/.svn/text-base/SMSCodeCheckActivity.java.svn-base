package com.yutong.clw.ygbclient.view.pages.login.findPassword;

import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.validate.BizSendValidCode;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.ExEditText;

/**
 * 短信验证码输入界面
 * 
 * @author zhouzc
 */
public class SMSCodeCheckActivity extends BaseActivity implements OnClickListener
{
    private TimeCount time;

    private ExEditText findpwd_code_ET;

    private TextView inputcodelabel;

    private TextView reSendCodeTV;

    private int CODE_EXPIRE_SECONDS = 3600;
    
    private int BTN_EXPIRE_SECONDS= 120;
    
    private BizSendValidCode sendcodebiz = new BizSendValidCode(this);

    private String phonenumber;

    private String emp_number;

    private final String ValidStringCode = "[$NotEmpty(<@请输入6位数验证码@>)$][$Len(6,6,<@验证码为6位数字@>)$]";
    
    private Button rightBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpass_smscheck);
        initData();
        initParam();
        initViews();
        initListener();
        startCheckTimer();
    }

	private void startCheckTimer() {
		if (time == null){
        	time = new TimeCount(BTN_EXPIRE_SECONDS * 1000, 1000);// 构造CountDownTimer对象
            time.start();// 开始计时
        }
	}

    private void initData()
    {
        Intent intent = getIntent();
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        phonenumber = (String) bundle.getSerializable(ActivityCommConstant.PHONE);
        emp_number = (String) bundle.getSerializable(ActivityCommConstant.EMP_NUMBER);
    }

    private void initParam()
    {
    	try {
			CODE_EXPIRE_SECONDS = Integer.parseInt(SysConfigUtil.getProperty(
					"CODE_EXPIRE_SECONDS", "3600"));
			BTN_EXPIRE_SECONDS = Integer.parseInt(SysConfigUtil.getProperty(
					"BTN_EXPIRE_SECONDS", "120"));
		} catch (Exception e) {
			e.printStackTrace();
			CODE_EXPIRE_SECONDS = 3600;
			BTN_EXPIRE_SECONDS=120;
		}
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        /*if (time != null)
        {
            time.cancel();
            time = null;
        }*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
       
    }

    @Override
    protected void onStop() {
    	super.onStop();
    }
    
    @Override
    protected void onDestroy() {
    	YtApplication.getInstance().removeDatas(
				AppDataKeyConstant.KEY_FINDPWD_CODE);
    	super.onDestroy();
    }
    
    private void initListener()
    {

        reSendCodeTV.setOnClickListener(this);

        findpwd_code_ET.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
            	
                String result = DataValidationUtil.validate(ValidStringCode, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "验证码：" + result;
//                }

                return result;
//                if (StringUtils.isEmpty(text))
//                    return "请输入验证码！";
//                else
//                    return "";
            }
        });

    }

    private void initViews()
    {
    	
    	rightBtn = (Button) findViewById(R.id.btn_it_right);
    	rightBtn.setEnabled(false);
    	rightBtn.setTextColor(getResources().getColor(R.color.password_confirm));
    	
        findpwd_code_ET = (ExEditText) findViewById(R.id.findpwd_code_ET);
        findpwd_code_ET.getEditText().addTextChangedListener(new MyTextWatcher());
        
        reSendCodeTV = (TextView) findViewById(R.id.reSendCodeTV);
        inputcodelabel = (TextView) findViewById(R.id.inputcodelabel);

        EditText et = findpwd_code_ET.getEditText();
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });

        // test
        //findpwd_code_ET.setText((String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_FINDPWD_CODE));

        String text = "已向您绑定的手机%s发送验证码\n请输入手机验证码";
        String maskphone = StringUtil.maskPhone(phonenumber);
        inputcodelabel.setText(String.format(text, maskphone));
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

        btn_right.setText("提交");
        tv_large.setText("找回密码");
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.reSendCodeTV:
            doResendCode();
            
            break;
        case R.id.btn_it_left:
            finish();
            break;
        case R.id.btn_it_right:
            doValidate();
            break;
        default:
            break;
        }
    }

    private void doResendCode()
    {
        showLoading("正在重发验证码...", 0);
        sendValidCode(phonenumber);

    }

    private void doValidate()
    {
        if (!findpwd_code_ET.validate())
        {
            ToastMessage(findpwd_code_ET.getErrMsg());
            return;

        }
        // 还要判断验证码是否过期
        Object codeobj=YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_FINDPWD_CODE);
        final String code = null==codeobj?"":(String) codeobj;
        Date dt = (Date) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_FINDPWD_CODE_TIME);
        if (StringUtil.isEmpty(code) || DateUtils.checkTimeExpired(dt, CODE_EXPIRE_SECONDS))
        {
            Toast.makeText(getApplicationContext(), "验证码已失效，请重发！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringUtil.equals(code, findpwd_code_ET.getText()))
        {

            Toast.makeText(getApplicationContext(), "验证码不匹配", Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ActivityCommConstant.EMP_NUMBER, emp_number);
        ActivityUtils.changeActivity(SMSCodeCheckActivity.this, NewPasswordActivity.class, bundle);
        this.finish();

    }

    private void sendValidCode(String phone)
    {    	
        final String p_phone = phone;
        String code = (String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_FINDPWD_CODE);

        sendcodebiz.SendCode(phone, code, SMSVerifyType.FINDPWD, new BizResultProcess<String>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                SMSCodeCheckActivity.this.dismissLoading(0);
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, String t)
            {
                final String ret_code = t;
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        SMSCodeCheckActivity.this.dismissLoading(0);
                        if (ret_code == null)
                        {
                            SMSCodeCheckActivity.this.ToastMessage("发生错误！");
                            return;
                        }
                        // 保存收到的验证码
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_FINDPWD_CODE, ret_code);
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_FINDPWD_CODE_TIME, DateUtils.getCurDate());

                        SMSCodeCheckActivity.this.time.start();// 开始计时
                        SMSCodeCheckActivity.this.reSendCodeTV.setClickable(false);
                        SMSCodeCheckActivity.this.ToastMessage("已经重发验证码，请注意查收");
                        // validcodetest 
//                        SMSCodeCheckActivity.this.findpwd_code_ET.setText(ret_code);
                    }
                });

            }
        });

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
        {// 计时完毕时触发

            reSendCodeTV.setText("点击此处重新发送验证码.");
            reSendCodeTV.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {// 计时过程显示
            reSendCodeTV.setClickable(false);
            String str = "验证码已发送(%s)";
            String text = String.format(str, (int) (millisUntilFinished / 1000));
            reSendCodeTV.setText(text);
        }
        
        
    }
    
    class MyTextWatcher implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			
			String pwd_codeStr = findpwd_code_ET.getText();
			if(pwd_codeStr!=null && pwd_codeStr.length()>0){
				rightBtn.setEnabled(true);
				rightBtn.setTextColor(getResources().getColor(R.color.white));
			}else{
				rightBtn.setEnabled(false);
				rightBtn.setTextColor(getResources().getColor(R.color.password_confirm));
			}
		}
    	
    }
}
