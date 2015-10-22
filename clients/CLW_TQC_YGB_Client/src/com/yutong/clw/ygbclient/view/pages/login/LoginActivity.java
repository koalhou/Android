package com.yutong.clw.ygbclient.view.pages.login;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.utils.EncryptUtils;
import com.yutong.clw.ygbclient.common.utils.PreferencesUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.login.BizLogin;
import com.yutong.clw.ygbclient.view.bizAccess.splash.BizSplash;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.pages.login.findPassword.EmployeeIDSubmitActivity;
import com.yutong.clw.ygbclient.view.pages.main.MainActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.ExEditText;
import com.yutong.clw.ygbclient.view.widget.LoginLinearLayout;

/**
 * 登录界面
 * 
 * @author zhouzc
 */
public class LoginActivity extends BaseActivity implements OnClickListener
{
	/*private String prefName = "LoginActivity";*/
	
	LoginLinearLayout rootLayout;

    ImageView im_logo;

    private TextView findpwdTV;

    /** 用户名 */
    private ExEditText userNameET;

    /** 密码 */
    public ExEditText passwordET;

    /** 登陆按钮 */
    private Button loginButton;

    private BizLogin biz = new BizLogin(this);

    // ---------------------------软键盘尺寸自适应--------------------
    private static final int BIGGER = 1;

    private static final int SMALLER = 2;

    private int softHeight = 0;// 软键盘的初始高度

    private final String ValidStringLoignEmpCode = "[$NotEmpty(<@请输入员工号@>)$][$Num(<@员工号为8位数字@>)$][$Len(8,8,<@员工号为8位数字@>)$]";

    private final String ValidStringLoignPassword = "[$NotEmpty(<@请输入密码@>)$]";

    private boolean isFocus = false;//是否已经弹出过软键盘
    
    private String userCodeStr = "";
    
    // 在handler里面重新布局
    Handler handler = new Handler(){
    	
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
            case 1:
                if (msg.arg1 == BIGGER){
                    // 恢复原来布局
                	handler.removeMessages(BIGGER);
                    im_logo.setVisibility(View.VISIBLE);
                    
                } else{
                	handler.removeMessages(SMALLER);
                    // 新的布局,比如我这里就是把账号输入框上面的logo给去掉
                    im_logo.setVisibility(View.GONE);
                }
                break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_login);
        initViews();
        initListener();
        
        /*UserInfo user = (UserInfo) YtApplication.getInstance().getDatas(AppDataKeyConstant.KEY_USER);
        ToastMessage("user.Id:"+user.Id);*/
        // test
        // userNameET.setText("90077777");
        // passwordET.setText("456");
        
        initFromIntent(getIntent());
    }
    
    @Override
    protected void onResume() {
    	
    	/*Object numobj = YtApplication.getInstance().getDatas(
				ActivityCommConstant.EMP_NUMBER);*/
    	
    	String userCodeStr = PreferencesUtils.getString(getBaseContext(), ActivityCommConstant.prefName, ActivityCommConstant.EMP_NUMBER);
    	if(userCodeStr!=null && userCodeStr.length()>0){
    	    
    	    userNameET.setText(userCodeStr);
    	    passwordET.getEditText().requestFocus();
    	}else{
    		userNameET.getEditText().requestFocus();
    	}
    	
    	/*if(userCodeStr!=null && userCodeStr.length()>0){
    	    
    	    Logger.e(this.getClass(), "设置 员工号：" + userCodeStr);
    	    userNameET.setText(userCodeStr);
    		
    	    Logger.e(this.getClass(), "原始密码值："+passwordET.getEditText().toString()+"  获取焦点" );
    	    passwordET.getEditText().requestFocus();
    	}else{
    		Logger.e(this.getClass(), "原始员工号："+userNameET.getEditText().toString()+"  获取焦点" );
    		userNameET.getEditText().requestFocus();
    	}*/
    	
    	super.onResume();
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
     	
	}
    
    @Override
    protected void onStop() {
    	super.onStop();
    	
    }
    
	private void initFromIntent(Intent intent){
    	
    	/*if(intent!=null && intent.getExtras()!=null){*/
		
		if(intent!=null){
    		
			Log.i("TAG", "intent!=null");
    		
			/*Bundle bundle = intent.getExtras();
    		UserInfo userInfo = (UserInfo) bundle.getSerializable(AppDataKeyConstant.KEY_USER);
    		userNameET.setText(userInfo.Code);*/
			
			String empNum = PreferencesUtils.getString(getBaseContext(), ActivityCommConstant.prefName,ActivityCommConstant.EMP_NUMBER);
			String empPassword = PreferencesUtils.getString(getBaseContext(), ActivityCommConstant.prefName,ActivityCommConstant.EMP_PASSWORD);
			
			Bundle bundle = intent.getExtras();
    		
			if(bundle!=null && bundle.containsKey(AppDataKeyConstant.EXIT_FLAG)){
				if(bundle.getBoolean(AppDataKeyConstant.EXIT_FLAG)){
					userNameET.setText(empNum);
				}
			}else{
				userNameET.setText(empNum);
				passwordET.setText(empPassword);
			}
    		
    		Logger.e(this.getClass(), "注销后跳转到 登陆界面" );
    		
    	}else{
    		Log.i("TAG", "intent=null");
    		String empNum = PreferencesUtils.getString(getBaseContext(), ActivityCommConstant.prefName,ActivityCommConstant.EMP_NUMBER);
    		String empPassword = PreferencesUtils.getString(getBaseContext(), ActivityCommConstant.prefName,ActivityCommConstant.EMP_PASSWORD);
   	     
   	     	if(StringUtil.isNotEmpty(empNum) && StringUtil.isNotEmpty(empPassword)){
   	    	 
   	     		userNameET.setText(empNum);
   	     		passwordET.setText(empPassword);
   	     	} 
    	}
    }
    
    private void initListener()
    {

        rootLayout.setOnResizeListener(new LoginLinearLayout.OnResizeListener(){
            @Override
            public void OnResize(int w, int h, int oldw, int oldh){
            	
                if (isFocus && softHeight == 0){
                    softHeight = oldh - h;
                }
                
                int change = BIGGER;
                
                if (h < oldh){
                    change = SMALLER;
                }else if ((h - oldh) < softHeight){
                    change = SMALLER;
                }
                
                Message msg = handler.obtainMessage();
                msg.what = 1;
                msg.arg1 = change;
                handler.sendMessage(msg);
            }
            	
        });
        findpwdTV.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        userNameET.addOnValidateListener(new ExEditText.OnValidateListener(){

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringLoignEmpCode, text);

//                if (StringUtil.isNotBlank(result)) /*验证失败*/
//                {
//                    result =  "员工号："+ result;
//                }
                
                return result;

                // if (StringUtil.isEmpty(text))
                // return "请输入员工号！";
                // else
                // return "";
            }
        });
        passwordET.addOnValidateListener(new ExEditText.OnValidateListener(){

            @Override
            public String onValidate(String text){
                String result = DataValidationUtil.validate(ValidStringLoignPassword, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result =  "密码:"+result;
//                }

                // if (StringUtil.isEmpty(text))
                // return "请输入密码！";

                // LoignPasswordValidString
                return result;
            }
        });
    }

    public void initViews(){
    	
        rootLayout = (LoginLinearLayout) findViewById(R.id.login_root_layout);
        im_logo = (ImageView) findViewById(R.id.im_logo);

        findpwdTV = (TextView) findViewById(R.id.findpwdTV);

        userNameET = (ExEditText) findViewById(R.id.userNameET);
        passwordET = (ExEditText) findViewById(R.id.passwordET);
        loginButton = (Button) findViewById(R.id.loginButton);

        EditText pwdinneret = passwordET.getEditText();
        int pl = pwdinneret.getPaddingLeft();
        pwdinneret.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        pwdinneret.setPadding(pl, 0, 0, 0);// 如果同时用，setPadding 将不会起作用，用的是
                                           // drawable里面自带的padding。setPadding要在setBackgroundDrawable之后执行才能生效
        pwdinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        pwdinneret.setFilters(new InputFilter[] { 
                new InputFilter() {   

                @Override
                public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {   
                if("".equals(src.toString().trim()))
                { 
                src="";  
                }
                return src;   
                   } 
                } }); 
        
        
        
        EditText usernameinneret = userNameET.getEditText();
        pl = usernameinneret.getPaddingLeft();
        usernameinneret.setInputType(InputType.TYPE_CLASS_NUMBER);

        usernameinneret.setPadding(pl, 0, 0, 0);
        usernameinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });

        // userNameET.setText("90070000");
        // passwordET.setText("123");
    }

    @Override
    public void onBackPressed(){
        // TODO Auto-generated method stub
        super.onBackPressed();
        YtApplication.getInstance().exit();
        finish();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){

        case R.id.findpwdTV:
            doGotoFindPwd();

            break;

        case R.id.loginButton:
            
            doLogin();
            break;
        default:
        }

    }
    
    
    
    private void doGotoFindPwd(){
        String userName = StringUtil.parseStr(userNameET.getText());
        userCodeStr = userName;
        Bundle bundle = new Bundle();
        bundle.putSerializable(ActivityCommConstant.EMP_NUMBER, userName);
        ActivityUtils.changeActivity(this, EmployeeIDSubmitActivity.class, bundle);
    }

    private void doLogin(){

        HideSoftKeyboard();

        if(!userNameET.validate()){

            ToastMessage(userNameET.getErrMsg());
            return;
        	
        }
        
        if(!passwordET.validate()){

            ToastMessage(passwordET.getErrMsg());
            return;
        	
        }
        
        String userName = StringUtil.parseStr(userNameET.getText());
        String pwdmd5 = EncryptUtils.getMD5Str(passwordET.getText()).toUpperCase(Locale.US);
        String password = StringUtil.parseStr(pwdmd5);

        
        // LoginInfoBean loginInfoBean = new LoginInfoBean();
//        if (!checkInputInfo(userName, password))
//        {
//            String errmsg = getErrorMsg();
//            ToastMessage(errmsg);
//            return;
//        }

        YtApplication.getInstance().addDatas(ActivityCommConstant.EMP_NUMBER, userName);
        YtApplication.getInstance().addDatas(ActivityCommConstant.EMP_PASSWORD, password);
        
        PreferencesUtils.putString(getBaseContext(), ActivityCommConstant.prefName, ActivityCommConstant.EMP_NUMBER, userName);
        PreferencesUtils.putString(getBaseContext(), ActivityCommConstant.prefName, ActivityCommConstant.EMP_PASSWORD, passwordET.getText().toString());
        
        showLoading("正在登录,请稍候...", 0);
        biz.login(userName, password, new BizResultProcess<UserInfo>()
        { 

            @Override
            public void onBizExecuteError(Exception exception, Error error)
            {
                final Exception ex = exception;
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        LoginActivity.this.dismissLoading(0);
                        HandleLogicErrorInfo(ex);
                    }
                });

            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, UserInfo t)
            {
                final UserInfo user = t;
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {

                        if (user == null)
                        {
                            LoginActivity.this.ToastMessage("登录发生错误！");
                            return;
                        }
                        
                        
                        BizSplash bizsplash = new BizSplash(LoginActivity.this);
                        bizsplash.splashInit(new BizResultProcess<String>()
                        {
                            @Override
                            public void onBizExecuteError(Exception exception, Error error)
                            {
                                
                                /*LoginActivity.this.dismissLoading(0);*/
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                        afterlogin();
                                    }
                                });

                            }

                            @Override
                            public void onBizExecuteEnd(BizDataTypeEnum datatype, String t)
                            {
                                /*LoginActivity.this.dismissLoading(0);*/
                                runOnUiThread(new Runnable()
                                {
                                    public void run()
                                    {
                                    	/*用户行为统计-*/
                                        new BizBehaviorStatistic(YtApplication.getInstance().getApplicationContext(), BehaviorStatisticConstants.M_DLBD_LOGIN).reportBehavior(null);
                                        afterlogin();
                                    }
                                });
                            }
                        });
                                          
                    }

                    private void afterlogin()
                    {
                        YtApplication.getInstance().onLoginSuccess();
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_USER, user);
                        PreferencesUtils.putString(getBaseContext(), ActivityCommConstant.prefName, ActivityCommConstant.PHONE, user.Phone);
                        
                        LoginActivity.this.dismissLoading(0);
                        
                        if (user.BindPhone){
                            ActivityUtils.changeActivity(LoginActivity.this, MainActivity.class);
                        } else{
                        	/* PhoneBindActivity */
                            ActivityUtils.changeActivity(LoginActivity.this, PhoneBindActivity.class);
                            passwordET.setText("");
                        }

                    }
                });

            }
        });

    }
    
    private long exitTime = 0;

	@Override
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
	}	
}
