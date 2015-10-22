package com.yutong.clw.ygbclient.view.pages.login;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.login.BizBindPhone;
import com.yutong.clw.ygbclient.view.bizAccess.validate.BizSendValidCode;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.ExEditText;

/**
 * 手机绑定界面
 * 
 * @author zhouzc
 */
public class PhoneBindActivity extends BaseActivity implements OnClickListener
{
    private BizSendValidCode sendcodebiz = new BizSendValidCode(this);

    private BizBindPhone bindphonebiz = new BizBindPhone(this);

    private Button nextbtn;

    private Button sendcodebtn;

    private TimeCount time;

    private String phone;

    private ExEditText phone_ET;

    private ExEditText code_ET;

    private int CODE_EXPIRE_SECONDS = 60;

    private int BTN_EXPIRE_SECONDS= 120;
    
    private final String ValidStringBindPhone = "[$Phone(<@手机号码格式错误@>)$]";

   /* private final String ValidStringCode = "[$Len(1,10,<@长度在1到10之间@>)$]";*/
    private final String ValidStringCode = "[$NotEmpty(<@不能为空@>)$][$Len(6,6,<@请输入6位验证码@>)$]";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phonebind);
        initParam();
        time = new TimeCount(BTN_EXPIRE_SECONDS * 1000, 1000);// 构造CountDownTimer对象
        initViews();
        initLestner();
        YtApplication.getInstance().removeDatas(
				AppDataKeyConstant.KEY_BINDPHONE_CODE);
    }

    private void initParam()
    {
        try
        {
        	CODE_EXPIRE_SECONDS = Integer.parseInt(SysConfigUtil.getProperty("CODE_EXPIRE_SECONDS", "3600"));
        	
        	BTN_EXPIRE_SECONDS = Integer.parseInt(SysConfigUtil.getProperty(
					"BTN_EXPIRE_SECONDS", "120"));
        }
        catch (Exception e)
        {
            CODE_EXPIRE_SECONDS = 3600;
            BTN_EXPIRE_SECONDS=120;
        }
    }

    private void initLestner()
    {
        nextbtn.setOnClickListener(this);
        sendcodebtn.setOnClickListener(this);
        phone_ET.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringBindPhone, text);

                if (StringUtil.isNotBlank(result))
                {
                    result = "绑定手机：" + result;
                }

                return result;

                // if (StringUtils.isBlank(text))
                // return "请输入手机号！";
                // else if (!StringUtil.isMobileNO(text))
                // {
                // return "请输入正确的手机号！";
                // }
                // else
                // return "";
            }
        });
        code_ET.addOnValidateListener(new ExEditText.OnValidateListener()
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
                // if (StringUtils.isBlank(text))
                // return "请输入验证码！";
                // else
                // return "";
            }
        });
    }

    public void initViews()
    {
        nextbtn = (Button) findViewById(R.id.nextbtn);
        nextbtn.setEnabled(false);
        sendcodebtn = (Button) findViewById(R.id.sendcodebtn);
        phone_ET = (ExEditText) findViewById(R.id.phone_ET);
        phone_ET.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

				if (!phone_ET.isShowRightIcon())
					return;
				
				phone_ET.getAlertIV().setVisibility(ImageView.INVISIBLE);
				String userName = StringUtil.parseStr(phone_ET.getInputET().getText());
				
				if (userName == null || "".equals(userName)) {
					
					phone_ET.getClearIV().setVisibility(ImageView.INVISIBLE);
					if(sendcodebtn!=null){
						sendcodebtn.setEnabled(false);
					}
				} else {
					phone_ET.getClearIV().setVisibility(ImageView.VISIBLE);
					if(sendcodebtn!=null){
						sendcodebtn.setEnabled(true);
					}
				}
			}
		});
        
        code_ET = (ExEditText) findViewById(R.id.code_ET);
        code_ET.getEditText().addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {

				if (!code_ET.isShowRightIcon())
					return;
				code_ET.getAlertIV().setVisibility(ImageView.INVISIBLE);
				String userName = StringUtil.parseStr(code_ET.getInputET().getText());
				
				if (userName == null || "".equals(userName)) {
					code_ET.getClearIV().setVisibility(ImageView.INVISIBLE);
					if(nextbtn!=null){
						nextbtn.setEnabled(false);
					}
				} else {
					code_ET.getClearIV().setVisibility(ImageView.VISIBLE);
					if(nextbtn!=null){
						nextbtn.setEnabled(true);
					}
					
				}
			}
		});
        
        

        EditText et = code_ET.getEditText();
        // et.setInputType(InputType.TYPE_CLASS_PHONE);
        et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

        et = phone_ET.getEditText();
        et.setInputType(InputType.TYPE_CLASS_PHONE);
        et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });

        UserInfo user = (UserInfo) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_USER);
        if (user != null && StringUtil.isNotBlank(user.EipPhone))
        {
            phone_ET.setText(user.EipPhone);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
        case R.id.sendcodebtn:
            doSendCode();
            break;
        case R.id.nextbtn:
            doBindPhone();

            break;
        default:
        }

    }

    @Override
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        // 这里如果没有绑定直接返回，则下一次不应该自动登录，清楚登录信息
        YtApplication.getInstance().onLogout();

    }

    private void doSendCode()
    {
        String code = (String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
        if (!phone_ET.validate())
        {
            Toast.makeText(getApplicationContext(), phone_ET.getErrMsg(), Toast.LENGTH_SHORT).show();
            return;
        }
        phone = phone_ET.getText();
        phone_ET.setEnabled(false);
        // this.startPhoneValidTask();
        final  String fphone=phone;
        showLoading("正在发送验证码", 0);
        sendcodebiz.SendCode(fphone, code, SMSVerifyType.BINGPHONE, new BizResultProcess<String>()
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
                        PhoneBindActivity.this.dismissLoading(0);
                        if (code == null)
                        {
                            PhoneBindActivity.this.ToastMessage("发生错误！");
                            return;
                        }
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE, fphone);
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE, code);
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE_TIME, DateUtils.getCurDate());

                        PhoneBindActivity.this.time.start();// 开始计时

                        ToastMessage("已经发送验证码，请注意查收");
                        // 设置为可用
                        PhoneBindActivity.this.nextbtn.setEnabled(false);
                        
                        PhoneBindActivity.this.sendcodebtn.setEnabled(false);
                        PhoneBindActivity.this.sendcodebtn.setClickable(false);

                        // validcodetest
                        // PhoneBindActivity.this.code_ET.setText(code);
                    }
                });

            }
        });
    }

    private void doBindPhone()
    {
    	
    	String hasSendCode = (String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE );
    	if( hasSendCode==null ){
    		ToastMessage("请先获取验证码！");
    		return;
    	}
    	
    	String hasSendCodePhone = (String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE );
    	
    	String nowEditTextPhone = StringUtil.parseStr(phone_ET.getInputET().getText());
    	
    	if(!hasSendCodePhone.equals(nowEditTextPhone)){
    		ToastMessage("手机号和验证码不匹配，请检查或重新获取验证码！");
    		return;
    	}
        
    	if (!phone_ET.validate())
        {
            ToastMessage(phone_ET.getErrMsg());
            return;
        }
        
        // 还要判断验证码是否过期
        String code = (String) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
        Date dt = (Date) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE_TIME);
        
        /*int expireTime = 1 * 60 * 60;// 超时定位一小时*/       
        phone=phone_ET.getText();
        Object fphoneobj=YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE);
        if(fphoneobj==null||!phone.equals(fphoneobj.toString())){
            ToastMessage("手机号和验证码不匹配，请检查或重新获取验证码！");
            return;
        }
        if (StringUtils.isEmpty(code) || DateUtils.checkTimeExpired(dt, CODE_EXPIRE_SECONDS))
        {
            ToastMessage("验证码已失效，请重新获取验证码！");
            return;
        }
        if (!StringUtil.equals(code, code_ET.getText()))
        {
            ToastMessage("手机号和验证码不匹配，请检查或重新获取验证码");
            return;
        }

        showLoading("正在提交...", 0);

        bindphonebiz.bind(phone_ET.getText(), new BizResultProcess<Boolean>()
        {

            @Override
            public void onBizExecuteError(final Exception exception, Error error)
            {
                runOnUiThread(new Runnable()
                {

                    @Override
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
            	YtApplication.getInstance().removeDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
            	 YtApplication.getInstance().removeDatas(AppDataKeyConstant.KEY_BINDPHONE_PHONE);
                dismissLoading(0);
                
                ActivityUtils.changeActivity(PhoneBindActivity.this, FactorySelectActivity.class);
                
                if (StringUtil.isNotBlank(phone))
                {
                    UserInfo user = (UserInfo) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_USER);
                    user.Phone = phone;
                    user.BindPhone = t;
                    new LoginInfoDao(PhoneBindActivity.this).addLoginInfo(user);
                    
                    phone_ET.setEnabled(true);
                    YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_USER, user);
                }
               
                finish();
            }
        });

    }
    
    @Override
    protected void onStop() {
    	
    	super.onStop();
    }
    
    
    @Override
	protected void onDestroy() {
    	
    	YtApplication.getInstance().removeDatas(AppDataKeyConstant.KEY_BINDPHONE_CODE);
    	super.onDestroy();
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
            sendcodebtn.setText("重新验证");
            sendcodebtn.setClickable(true);
            sendcodebtn.setEnabled(true);
            phone_ET.setEnabled(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {// 计时过程显示
            sendcodebtn.setClickable(false);
            sendcodebtn.setEnabled(false);
            phone_ET.setEnabled(false);
            String str = "短信验证中(%s)";
            String text = String.format(str, (int) (millisUntilFinished / 1000));
            sendcodebtn.setText(text);
        }
    }
    
    private long exitTime = 0;

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN){
		    if((System.currentTimeMillis()-exitTime) > 2000){
		        Toast.makeText(getApplicationContext(), "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
		        exitTime = System.currentTimeMillis();
			} else {
				//退出代码
				onBackPressed();
			}
		    return true;
	    }
		return super.onKeyDown(keyCode, event);
	}	*/
	
}
