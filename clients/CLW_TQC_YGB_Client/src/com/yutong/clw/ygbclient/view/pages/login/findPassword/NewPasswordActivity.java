package com.yutong.clw.ygbclient.view.pages.login.findPassword;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
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
import com.yutong.clw.ygbclient.common.utils.EncryptUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BehaviorStatisticConstants;
import com.yutong.clw.ygbclient.view.bizAccess.behaviorstatistic.BizBehaviorStatistic;
import com.yutong.clw.ygbclient.view.bizAccess.login.BizChangePwd;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.pages.login.findPassword.EmployeeIDSubmitActivity.MyTextWatcher;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.ExEditText;

/**
 * 新密码输入界面
 * 
 * @author zhouzc
 */
public class NewPasswordActivity extends BaseActivity implements OnClickListener
{

    private ExEditText findpwd_pwd_ET;

    private ExEditText findpwd_pwd1_ET;

    private String emp_number;

    private BizChangePwd biz = new BizChangePwd(this);
//    [$NoChar('\\s*|\t|\r|\n',<@不能有空格@>)$]
    private final String ValidStringPassword = "[$NoChar(' ',<@不能有空格@>)$][$NotEmpty(<@请输入新密码@>)$][$Len(6,100,<@新密码长度必须至少6位@>)$][$Len(6,12,<@新密码长度必须最多12位@>)$]";
    private final String ValidStringPassword_Re = "[$NoChar(' ',<@不能有空格@>)$][$NotEmpty(<@请输入重复密码@>)$]";

    private Button submitBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpass_newpass);
        initData();
        initViews();
        initListener();
    }

    private void initData()
    {
        Intent intent = getIntent();
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;

        emp_number = (String) bundle.getSerializable(ActivityCommConstant.EMP_NUMBER);
    }

    private void initListener()
    {

        findpwd_pwd_ET.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
//                if (StringUtils.isEmpty(text))
//                    return "请输入密码！";
//                else
//                    return "";
                String result = DataValidationUtil.validate(ValidStringPassword, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "新密码：" + result;
//                }

                return result;
            }
        });
        findpwd_pwd1_ET.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
//                if (StringUtils.isEmpty(text))
//                    return "请输入密码！";
//                return "";
                String result = DataValidationUtil.validate(ValidStringPassword_Re, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "新密码：" + result;
//                }

                return result;
            }
        });
    }

    private void initViews()
    {
    	submitBtn = (Button) findViewById(R.id.btn_it_right);
    	submitBtn.setEnabled(false);
		submitBtn.setTextColor(getResources().getColor(R.color.password_confirm));
		
    	findpwd_pwd_ET = (ExEditText) findViewById(R.id.findpwd_pwd_ET);
        findpwd_pwd1_ET = (ExEditText) findViewById(R.id.findpwd_pwd1_ET);

        EditText pwd1inneret = findpwd_pwd1_ET.getEditText();
        pwd1inneret.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pwd1inneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        pwd1inneret.addTextChangedListener(new MyTextWatcher());
        
        pwd1inneret.setFilters(new InputFilter[] { 
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
        
        EditText pwdinneret = findpwd_pwd_ET.getEditText();
        pwdinneret.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        pwdinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        pwdinneret.addTextChangedListener(new MyTextWatcher());
        
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
        case R.id.btn_it_left:
            finish();
            break;
        case R.id.btn_it_right:
            doSubmit();
            break;
        default:
            break;
        }
    }

    private void doSubmit()
    {
        if (!findpwd_pwd_ET.validate())
        {
            ToastMessage(findpwd_pwd_ET.getErrMsg());
            return;

        }
        
        if (!findpwd_pwd1_ET.validate())
        {
            ToastMessage(findpwd_pwd1_ET.getErrMsg());
            return;

        }
        
        if (!findpwd_pwd1_ET.getText().toString().equals(findpwd_pwd_ET.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        
        showLoading("正在提交,请稍候...", 0);
        
        String pwdmd5 = EncryptUtils.getMD5Str(findpwd_pwd_ET.getText()).toUpperCase(Locale.US);
        String password = StringUtil.parseStr(pwdmd5);
        biz.changePwd(emp_number, password, new BizResultProcess<Boolean>()
       {
           
           @Override
           public void onBizExecuteError(Exception exception, Error error)
           {
               NewPasswordActivity.this.dismissLoading(0);
               HandleLogicErrorInfo(exception);
               
           }
           
           @Override
           public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
           {
               NewPasswordActivity.this.dismissLoading(0);
               ActivityUtils.changeActivity(NewPasswordActivity.this, SuccActivity.class);
               NewPasswordActivity.this.finish();
           }
       });

    }

    private boolean checkInputInfo()
    {
        boolean pwdvalid = findpwd_pwd_ET.validate();
        boolean pwd1valid = findpwd_pwd1_ET.validate();
        return pwdvalid && pwd1valid;
    }

    private String getErrorMsg()
    {
        return findpwd_pwd_ET.getErrMsg() + findpwd_pwd1_ET.getErrMsg();

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
			/*private ExEditText findpwd_pwd_ET;

		    private ExEditText findpwd_pwd1_ET;*/
			
			String oldPwdStr = findpwd_pwd_ET.getText();
			String newPwdStr = findpwd_pwd1_ET.getText();
			
			boolean oldPwdFlag = oldPwdStr!=null && oldPwdStr.length()>0;
			int oldPwdLen  = oldPwdStr.length();
			
			boolean newPwdFlag = newPwdStr!=null && newPwdStr.length()>0;
			int newPwdLen  = newPwdStr.length();
			
			if(oldPwdFlag && newPwdFlag && oldPwdLen==newPwdLen ){
				submitBtn.setEnabled(true);
				submitBtn.setTextColor(getResources().getColor(R.color.white));
			}else{
				submitBtn.setEnabled(false);
				submitBtn.setTextColor(getResources().getColor(R.color.password_confirm));
			}
		}
    	
    }
}
