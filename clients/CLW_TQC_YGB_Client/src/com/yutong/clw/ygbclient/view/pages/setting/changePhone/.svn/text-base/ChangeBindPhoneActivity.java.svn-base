package com.yutong.clw.ygbclient.view.pages.setting.changePhone;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.login.BizBindPhone;
import com.yutong.clw.ygbclient.view.bizAccess.validate.BizSendValidCode;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.widget.ExEditText;
import com.yutong.clw.ygbclient.view.widget.ExEditText.OnValidateListener;

/**
 * 修改绑定手机界面
 * 
 * @author zhouzc
 */
public class ChangeBindPhoneActivity extends RemindAccessActivity implements OnClickListener
{
    private final String ValidStringPhone = "[$Phone(<@手机号码格式错误@>)$]";

    private final String ValidStringCode = "[$NotEmpty(<@请输入6位数验证码@>)$][$Len(6,6,<@验证码为6位数字@>)$]";

    /**
     * 旧手机号
     */
    private ExEditText et_old_phone;

    /**
     * 新手机号
     */
    private ExEditText et_new_phone;

    /**
     * 输入验证码
     */
    private ExEditText et_phone_verify_code;

    /**
     * 获取验证码
     */
    private Button btn_get_verify_code;

    /**
     * 点击提交
     */
    private Button btn_subimt;

    /**
     * 错误提示信息
     */
    private String errorMsg;

    /**
     * 发送验证码
     */
    private BizSendValidCode sendcodebiz = new BizSendValidCode(this);

    /**
     * 绑定手机号
     */
    private BizBindPhone bindphonebiz = new BizBindPhone(this);

    /**
     * 计时器
     */
    private TimeCount time;

    /**
     * 输入未改变
     */
    private static final int TEXT_NOT_CHANGED = 0;

    /**
     * 输入改变
     */
    private static final int TEXT_CHANGED = 1;

    // 是否在计时
    private boolean isKeepingCount;

    /**
     * 输入监听处理
     */
    private Handler inputHandler = new Handler()
    {
        // 旧手机号输入
        private boolean isOldPhoneChanged;

        // 新手机号输入
        private boolean isNewPhoneChanged;

        // 验证码输入
        private boolean isVerfiyCodeChanged;

        public void handleMessage(android.os.Message msg)
        {

            switch (msg.what)
            {
//            不为空
            case TEXT_CHANGED:
                if (msg.obj.equals(et_old_phone))
                {
                    isOldPhoneChanged = true;
                }
                else if (msg.obj.equals(et_new_phone))
                {
                    isNewPhoneChanged = true;
                }
                else if (msg.obj.equals(et_phone_verify_code))
                {
                    isVerfiyCodeChanged = true;
                }
                break;
            case TEXT_NOT_CHANGED:
                if (msg.obj.equals(et_old_phone))
                {
                    isOldPhoneChanged = false;
                }
                else if (msg.obj.equals(et_new_phone))
                {
                    isNewPhoneChanged = false;
                }
                else if (msg.obj.equals(et_phone_verify_code))
                {
                    isVerfiyCodeChanged = false;
                }
            default:
                break;
            }
            if (isOldPhoneChanged && isNewPhoneChanged)
            {

                if (!isKeepingCount)
                {
                    btn_get_verify_code.setEnabled(true);
                    btn_get_verify_code.setTextColor(getResources().getColor(R.color.white));
                }
            }
            else
            {
            	btn_get_verify_code.setEnabled(false);
                btn_get_verify_code.setTextColor(getResources().getColor(R.color.password_confirm));
                
                btn_subimt.setEnabled(false);
            }
            
            btn_subimt.setEnabled(isVerfiyCodeChanged);
            btn_subimt.setTextColor(isVerfiyCodeChanged ? getResources().getColor(R.color.white):getResources().getColor(R.color.password_confirm));
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_changephone);
        initViews();
        initParam();
        initData();
        initListener();
        YtApplication.getInstance().removeDatas(
				AppDataKeyConstant.KEY_BINDPHONE_CODE);
    }

    
    @Override
    protected void onStop() {
    	
    	super.onStop();
    }
    
    @Override
    protected void onDestroy() {

    	YtApplication.getInstance().removeDatas(
				AppDataKeyConstant.KEY_BINDPHONE_CODE);
    	
    	super.onDestroy();
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
    
    /**
     * 加载监听
     * 
     * @author lizyi 2013-11-9 上午8:37:01
     */
    private void initListener()
    {
        /**
         * 加载计时器
         */
        time = new TimeCount(BTN_EXPIRE_SECONDS * 1000, 1000);//
        // 构造CountDownTimer对象
        /**
         * 旧手机号码输入之前，获取验证码按钮不可操作
         */
        et_old_phone.addOnValidateListener(new OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringPhone, text);

                if (StringUtil.isNotBlank(result))
                {
                    result = "原手机号码：" + result;
                    return result;
                }

                // 验证旧手机号是否和缓存信息是否一致
                UserInfo loginInfo = (UserInfo) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_USER);
                if (loginInfo != null)
                {
                    // TO DO 判断手机号是否已绑定
                    if (!text.equals(loginInfo.Phone))
                    {
                        return "原手机号码输入不正确";
                    }
                }
                else
                {
                    return "原手机号码未绑定，请先绑定！";
                }
                return "";
            }
        });
        et_old_phone.getEditText().addTextChangedListener(new MyTextWatcher(et_old_phone, inputHandler));
        /**
         * 未输入手机号码时，获取验证码按钮不可操作
         */
        et_new_phone.addOnValidateListener(new OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringPhone, text);

                if (StringUtil.isNotBlank(result))
                {
                    result = "新手机号码：" + result;
                }
                return result;
            }
        });
        et_new_phone.getEditText().addTextChangedListener(new MyTextWatcher(et_new_phone, inputHandler));
        btn_get_verify_code.setOnClickListener(this);
        /**
         * 验证码输入监听
         */
        et_phone_verify_code.addOnValidateListener(new OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringCode, text);

                if (StringUtil.isNotBlank(result))
                {
                    result = "验证码：" + result;
                }
                return result;
            }
        });
        et_phone_verify_code.getEditText().addTextChangedListener(new MyTextWatcher(et_phone_verify_code, inputHandler));
        et_phone_verify_code.getEditText().setOnClickListener(new VerifyCodeListener());
        /**
         * 提交按钮
         */
        btn_subimt.setOnClickListener(this);
    }

    /**
     * 点击验证码监听
     */
    private class VerifyCodeListener implements OnClickListener
    {

        @Override
        public void onClick(View view)
        {
            if (StringUtil.isBlank(et_old_phone.getText()) || StringUtil.isBlank(et_new_phone.getText()))
            {
                ToastMessage("请先输入手机号！");
                et_phone_verify_code.setText("");
            }

        }

    }

    /**
     * 输入监听
     */
    private class MyTextWatcher implements TextWatcher
    {
        // 被监听的输入框
        private ExEditText exEditText;

        /**
         * 输入监听消息处理
         */
        private Handler inputHandler;

        /**
         * 处理多个输入框
         * 
         * @param exEditText
         * @param subimt
         */
        public MyTextWatcher(ExEditText exEditText, Handler inputHandler)
        {
            super();
            this.exEditText = exEditText;
            this.inputHandler = inputHandler;
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (!exEditText.isShowRightIcon())
                return;
            Message obtainMessage = null;
            exEditText.getAlertIV().setVisibility(ImageView.INVISIBLE);
            String userName = StringUtil.parseStr(exEditText.getText());
            if (userName == null || "".equals(userName))
            {
                exEditText.getAlertIV().setVisibility(ImageView.INVISIBLE);
                obtainMessage = inputHandler.obtainMessage(TEXT_NOT_CHANGED, exEditText);
            }
            else
            {
                exEditText.getClearIV().setVisibility(ImageView.VISIBLE);
                obtainMessage = inputHandler.obtainMessage(TEXT_CHANGED, exEditText);

            }
            inputHandler.sendMessage(obtainMessage);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {

        }

    }

    /**
     * 加载数据
     * 
     * @author lizyi 2013-11-9 上午8:36:53
     */
    private void initData()
    {
        // TODO Auto-generated method stub

    }

    /**
     * 加载视图
     * 
     * @author lizyi 2013-11-9 上午8:33:56
     */
    public void initViews()
    {
        setAllowNetworkCheck(true);
        et_old_phone = (ExEditText) findViewById(R.id.et_old_phone);
        // test
        // et_old_phone.setText("15903611759");
        et_new_phone = (ExEditText) findViewById(R.id.et_new_phone);
        et_phone_verify_code = (ExEditText) findViewById(R.id.et_phone_verify_code);
        btn_get_verify_code = (Button) findViewById(R.id.btn_get_verify_code);
        btn_subimt = (Button) findViewById(R.id.btn_subimt);
        // 设置输入为数字类型
        et_old_phone.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        et_new_phone.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        et_phone_verify_code.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        et_old_phone.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
        et_new_phone.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
        et_phone_verify_code.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
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
        tv_large.setText("手机号码修改");
        tv_small.setVisibility(View.GONE);
        iv_tri.setVisibility(View.GONE);

        btn_left.setOnClickListener(this);
    }

    /**
     * 检查输入
     * 
     * @author lizyi 2013-11-8 下午2:20:04
     * @return
     */
    private boolean checkInput(ExEditText exEditText)
    {
        if (exEditText == null)
        {
            return false;
        }
        if (!exEditText.validate())
        {
            return false;
        }
        return true;
    }

    /**
     * 提交
     * 
     * @author lizyi 2013-11-8 下午2:20:14
     */
    private void doSendCode()
    {
        /**
         * 手机号校验
         */
        if (!checkInput(et_old_phone))
        {
            ToastMessage(et_old_phone.getErrMsg());
            return;
        }
        if (!checkInput(et_new_phone))
        {
            ToastMessage(et_new_phone.getErrMsg());
            return;
        }

        if (et_old_phone.getText().equals(et_new_phone.getText()))
        {
            ToastMessage("新手机不能与原手机相同！");
            return;
        }
        
        String code = (String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
       final String newPhone = et_new_phone.getText();
        // this.startPhoneValidTask();
        showLoading("正在发送验证码", 0);
        sendcodebiz.SendCode(newPhone, code, SMSVerifyType.CHANGEBINDPHONE, new BizResultProcess<String>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                dismissLoading(0);
                // ToastMessage("发送验证码失败！");
                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, String t)
            {

                final String code = t;
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        ChangeBindPhoneActivity.this.dismissLoading(0);
                        if (code == null)
                        {
                            ChangeBindPhoneActivity.this.ToastMessage("发生错误！");
                            return;
                        }
                        // 存储验证码和验证码日期
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE, newPhone);
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE, code);
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE_TIME, DateUtils.getCurDate());
                        ChangeBindPhoneActivity.this.time.start();// 开始计时

                        ToastMessage("已经发送验证码，请注意查收");
                        // 设置为可用
                        ChangeBindPhoneActivity.this.btn_get_verify_code.setEnabled(false);
                        ChangeBindPhoneActivity.this.btn_get_verify_code.setClickable(false);

                        // validcodetest
//                        ChangeBindPhoneActivity.this.et_phone_verify_code.setText(code);
                    }
                });

            }
        });
    }

    private int CODE_EXPIRE_SECONDS = 3600;
    private int BTN_EXPIRE_SECONDS =120;
    /**
     * 获取验证码
     * 
     * @author lizyi 2013-11-9 上午10:52:28
     */
    private void doBindPhone()
    {
        /**
         * 手机号校验
         */
        if (!checkInput(et_old_phone))
        {
            ToastMessage(et_old_phone.getErrMsg());
            return;
        }
        if (!checkInput(et_new_phone))
        {
            ToastMessage(et_new_phone.getErrMsg());
            return;
        }
        if (!checkInput(et_phone_verify_code))
        {
            ToastMessage(et_phone_verify_code.getErrMsg());
            return;
        }
        //
        // 还要判断验证码是否过期
        Object phoneobj=YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE);
        if(phoneobj!=null&&!phoneobj.toString().equals(et_new_phone.getText())){
        	 ToastMessage("手机号和验证码不匹配，请检查或重新获取验证码！");
             return;
        }
        Object codeobj=YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
        final String code = null==codeobj?"":String.valueOf(codeobj);
        Date dt = (Date) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE_TIME);
        
        if (StringUtils.isEmpty(code) || DateUtils.checkTimeExpired(dt, CODE_EXPIRE_SECONDS))
        {
            ToastMessage("验证码已失效，请重新获取验证码！");
            return;
        }
        if (!StringUtil.equals(code, et_phone_verify_code.getText()))
        {
            ToastMessage("手机号和验证码不匹配，请检查或重新获取验证码！");
            return;
        }

        showLoading("正在提交...", 0);

        bindphonebiz.bind(phoneobj.toString(), new BizResultProcess<Boolean>()
        {

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                dismissLoading(0);
                // ToastMessage("绑定失败！");

                HandleLogicErrorInfo(exception);
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
            {
                dismissLoading(0);
                if (t)
                {
                    ToastMessage("手机号码修改成功！");
                    /*ActivityUtils.changeActivity(ChangeBindPhoneActivity.this, SettingActivity.class);*/
                    YtApplication.getInstance().removeDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE);
                    YtApplication.getInstance().removeDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
                    UserInfo user = (UserInfo) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_USER);
                    user.Phone = et_new_phone.getText();

                    YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_USER, user);
                    finish();
                }
                else
                {
                    ToastMessage("手机号码修改失败！");
                }

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.btn_it_left:
            // 验证码失效
            // YtApplication.getInstance().removeDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
            finish();
            break;
        case R.id.btn_get_verify_code:
            doSendCode();
            break;
        case R.id.btn_subimt:
            doBindPhone();
            
            
          //用户行为统计[设置中心修改手机号]     
            new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_SET_XGSJH).reportBehavior(null);
            	
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
        {// 计时完毕时触发
         // YtApplication.getInstance().setBindStudentCode(null);
            isKeepingCount = false;
            btn_get_verify_code.setText("重新验证");
            btn_get_verify_code.setClickable(true);
            btn_get_verify_code.setEnabled(true);
            et_new_phone.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {// 计时过程显示
            isKeepingCount = true;
            btn_get_verify_code.setClickable(false);
            btn_get_verify_code.setEnabled(false);
            et_new_phone.setEnabled(false);
            String str = "短信验证中(%s)";
            String text = String.format(str, (int) (millisUntilFinished / 1000));
            btn_get_verify_code.setText(text);
        }
    }
    class PhoneTextWatcher implements TextWatcher{

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
			
		}
    	
    }
}
