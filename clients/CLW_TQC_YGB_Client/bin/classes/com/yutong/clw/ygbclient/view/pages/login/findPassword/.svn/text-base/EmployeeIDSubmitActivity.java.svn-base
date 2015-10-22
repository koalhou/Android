package com.yutong.clw.ygbclient.view.pages.login.findPassword;

import android.content.Intent;
import android.os.Bundle;
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

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.beans.VerifyAccountInfo;
import com.yutong.clw.ygbclient.common.enums.ErrorInfoType;
import com.yutong.clw.ygbclient.common.enums.SMSVerifyType;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.login.BizValidEmpNumber;
import com.yutong.clw.ygbclient.view.bizAccess.validate.BizSendValidCode;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.ExEditText;

/**
 * 员工号提交页面
 * 
 * @author zhouzc
 */
public class EmployeeIDSubmitActivity extends BaseActivity implements OnClickListener
{
    private ExEditText findpwd_empnumber_ET;

    private BizValidEmpNumber biz = new BizValidEmpNumber(this);

    private BizSendValidCode sendcodebiz = new BizSendValidCode(this);

    private final String ValidStringAccount = "[$NotEmpty(<@请输入员工号@>)$][$Num(<@员工号为8位数字@>)$][$Len(8,8,<@员工号为8位数字@>)$]";

    private Button rightBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
    	
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpass_eidsubmit);
        initViews();
        initListener();

    }

    private void initData(){
    	
        Intent intent = getIntent();
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        String empnumber = (String) bundle.getSerializable(ActivityCommConstant.EMP_NUMBER);
        findpwd_empnumber_ET.setText(empnumber);
    }

    public void initViews(){
    	
    	rightBtn  =(Button) findViewById(R.id.btn_it_right);
        rightBtn.setEnabled(false);
        rightBtn.setTextColor(getResources().getColor(R.color.password_confirm));
        
        findpwd_empnumber_ET = (ExEditText) findViewById(R.id.findpwd_empnumber_ET);
        findpwd_empnumber_ET.getEditText().addTextChangedListener(new MyTextWatcher());
        EditText usernameinneret = findpwd_empnumber_ET.getEditText();
        int pl = usernameinneret.getPaddingLeft();
        usernameinneret.setInputType(InputType.TYPE_CLASS_NUMBER);

        usernameinneret.setPadding(pl, 0, 0, 0);
        usernameinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(8) });

        initData();
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
			
			String empNumberStr = findpwd_empnumber_ET.getText();
			if(empNumberStr!=null && empNumberStr.length()>0){
				rightBtn.setEnabled(true);
				rightBtn.setTextColor(getResources().getColor(R.color.white));
			}else{
				rightBtn.setEnabled(false);
				rightBtn.setTextColor(getResources().getColor(R.color.password_confirm));
			}
		}
    	
    }
    
    private void initListener()
    {

        findpwd_empnumber_ET.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringAccount, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "员工号：" + result;
//                }

                return result;

                // if (StringUtil.isEmpty(text))
                // return "请输入员工号！";
                // else
                // return "";
            }
        });

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
        if (!findpwd_empnumber_ET.validate())
        {
            ToastMessage(findpwd_empnumber_ET.getErrMsg());
            return;
        }
        
        EmployeeIDSubmitActivity.this.showLoading("正在验证员工信息...", 0);
        dovalidate();

    }

    private void dovalidate()
    {
        String number = findpwd_empnumber_ET.getText();
        YtApplication.getInstance().addDatas(ActivityCommConstant.EMP_NUMBER, number);
        biz.valid(number, new BizResultProcess<VerifyAccountInfo>()
        {

            @Override
            public void onBizExecuteError(final Exception exception, Error error)
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        EmployeeIDSubmitActivity.this.dismissLoading(0);
                        HandleLogicErrorInfo(exception);
                       /*if(exception != null && exception instanceof CommonException)
                       {
                           CommonException commonException = (CommonException)exception;
                           if(commonException.errorInfoType == ErrorInfoType._10012)
                           {
                               ToastMessage("输入的员工号不存在");
                           }
                       }*/
                       

                    }
                });

            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, VerifyAccountInfo t)
            {
                final VerifyAccountInfo info = t;
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        if (info == null)
                        {
                            EmployeeIDSubmitActivity.this.ToastMessage("验证员工号失败！");
                            return;
                        }
                        if (info.phonebind)
                        {
                            EmployeeIDSubmitActivity.this.showLoading("员工信息验证通过，正在发送验证码...", 0);
                            EmployeeIDSubmitActivity.this.sendValidCode(info.phone);

                        }
                        else
                        {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(ActivityCommConstant.EMP_NUMBER, info.emp_code);
                            ActivityUtils.changeActivity(EmployeeIDSubmitActivity.this, DefaultPasswordActivity.class, bundle);
                            EmployeeIDSubmitActivity.this.finish();
                        }
                    }

                });

            }
        });

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
                EmployeeIDSubmitActivity.this.dismissLoading(0);
                EmployeeIDSubmitActivity.this.ToastMessage("发送验证码失败！");
            }

            @Override
            public void onBizExecuteEnd(BizDataTypeEnum datatype, String t)
            {
                final String ret_code = t;
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        EmployeeIDSubmitActivity.this.dismissLoading(0);
                        if (ret_code == null)
                        {
                            EmployeeIDSubmitActivity.this.ToastMessage("发生错误！");
                            return;
                        }
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_FINDPWD_CODE, ret_code);
                        YtApplication.getInstance().addDatas(AppDataKeyConstant.KEY_FINDPWD_CODE_TIME, DateUtils.getCurDate());

                        String number = findpwd_empnumber_ET.getText();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ActivityCommConstant.PHONE, p_phone);
                        bundle.putSerializable(ActivityCommConstant.EMP_NUMBER, number);
                        ActivityUtils.changeActivity(EmployeeIDSubmitActivity.this, SMSCodeCheckActivity.class, bundle);
                        EmployeeIDSubmitActivity.this.finish();
                    }
                });

            }
        });

    }

//    private boolean checkInput()
//    {
//        boolean v = findpwd_empnumber_ET.validate();
//        return v;
//    }
}
