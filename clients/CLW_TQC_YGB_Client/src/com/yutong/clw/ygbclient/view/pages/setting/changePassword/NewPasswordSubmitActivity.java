package com.yutong.clw.ygbclient.view.pages.setting.changePassword;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yutong.clw.ygbclient.common.utils.EncryptUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.changePassWord.BizModifyPwd;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.ExEditText;

/**
 * 新密码输入界面
 * 
 * @author zhouzc
 */
public class NewPasswordSubmitActivity extends RemindAccessActivity implements OnClickListener
{
    /**
     * 输入新密码
     */
    private ExEditText et_new_pwd;

    /**
     * 确认新密码
     */
    private ExEditText et_confirm_new_pwd;

    /**
     * 确认
     */
    private Button confirmBtn;

    /**
     * 自定义控件内置编辑框--新密码
     */
    private EditText editTextNew;

    /**
     * 自定义控件内置编辑框--确认密码
     */
    private EditText editTextConfirm;

    /**
     * 输入内容没有改变
     */
    private static final int TEXT_NOT_CHANGED = 0;

    /**
     * 输入内容改变
     */
    private static final int TEXT_CHANGED = 1;

    private final String ValidStringPassword = "[$NoChar(' ',<@不能有空格@>)$][$NotEmpty(<@请输入新密码@>)$][$Len(6,100,<@新密码长度必须至少6位@>)$][$Len(6,12,<@新密码长度必须最多12位@>)$]";
    private final String ValidStringPassword_Re = "[$NoChar(' ',<@不能有空格@>)$][$NotEmpty(<@请输入重复密码@>)$]";

    /**
     * 输入监听处理
     */
    private Handler inputHandler = new Handler()
    {
        // 密码输入
        private boolean isPwdChanged = false;

        // 密码确认
        private boolean isConfirmChanged = false;

        public void handleMessage(android.os.Message msg)
        {
            if (msg == null || msg.obj == null)
            {
                return;
            }
            switch (msg.what)
            {
            case TEXT_CHANGED:
                if (msg.obj.equals(et_new_pwd))
                {
                    isPwdChanged = true;
                }
                else if (msg.obj.equals(et_confirm_new_pwd))
                {
                    isConfirmChanged = true;
                }
                break;
            case TEXT_NOT_CHANGED:
                if (msg.obj.equals(et_new_pwd))
                {
                    isPwdChanged = false;
                }
                else if (msg.obj.equals(et_confirm_new_pwd))
                {
                    isConfirmChanged = false;
                }
            default:
                break;
            }
            if (isPwdChanged && isConfirmChanged)
            {
                confirmBtn.setEnabled(true);
                confirmBtn.setTextColor(NewPasswordSubmitActivity.this.getResources().getColor(R.color.white));
            }
            else
            {
                confirmBtn.setEnabled(false);
                confirmBtn.setTextColor(NewPasswordSubmitActivity.this.getResources().getColor(R.color.password_confirm));
            }
        };
    };

    /**
     * 新用户密码修改
     */
    private BizModifyPwd biz = new BizModifyPwd(this);

    /**
     * 旧密码
     */
    private String old_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_changepassword_new);
        initViews();
        initListener();
        initData();
    }

    private void initData()
    {
        Intent intent = getIntent();
        if (intent == null)
            return;
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        old_pwd = (String) bundle.getString("old_pwd");
    }

    private void initListener()
    {
        /**
         * 输入密码
         */
        et_new_pwd.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringPassword, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "新密码：" + result;
//                }

                return result;

                // if (StringUtil.isEmpty(text))
                // {
                // return "请输入新密码！";
                // }
                // else
                // {
                // return "";
                // }
            }
        });
        editTextNew = et_new_pwd.getEditText();
        editTextNew.addTextChangedListener(new MyTextWatcher(et_new_pwd));
        /**
         * 确认密码
         */
        et_confirm_new_pwd.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringPassword_Re, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "确认新密码：" + result;
//                }

                return result;
                // if (StringUtil.isEmpty(text))
                // {
                // return "请确认新密码！";
                // }
                // else
                // {
                // return "";
                // }
            }
        });
        editTextConfirm = et_confirm_new_pwd.getEditText();
        editTextConfirm.addTextChangedListener(new MyTextWatcher(et_confirm_new_pwd));
    }

    /**
     * 输入监听
     */
    private class MyTextWatcher implements TextWatcher
    {

        private ExEditText exEditText;

        public MyTextWatcher(ExEditText exEditText)
        {
            super();
            this.exEditText = exEditText;
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            if (!exEditText.isShowRightIcon())
                return;
            exEditText.getAlertIV().setVisibility(ImageView.INVISIBLE);
            String userName = StringUtil.parseStr(exEditText.getEditText().getText());
            Message obtainMessage = null;
            if (userName == null || "".equals(userName))
            {
                exEditText.getAlertIV().setVisibility(ImageView.INVISIBLE);
                exEditText.getClearIV().setVisibility(ImageView.VISIBLE);
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
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            // TODO Auto-generated method stub

        }

    }

    /**
     * 检查输入
     * 
     * @author lizyi 2013-11-8 下午2:20:04
     * @return
     */
    private boolean checkInput()
    {

        boolean v = et_new_pwd.validate();
        boolean v1 = et_confirm_new_pwd.validate();
        return v && v1;
    }

    /**
     * 提交
     * 
     * @author lizyi 2013-11-8 下午2:20:14
     */
    private void doSubmit()
    {
        // TO DO
        // 正则表达式交验
//        String newPwd = et_new_pwd.getText();
//        String confirmPwd = et_confirm_new_pwd.getText();
//        if (StringUtil.isBlank(newPwd) || StringUtil.isBlank(confirmPwd))
//        {
//            ToastMessage("密码不能为空");
//            return;
//        }
//        if (!newPwd.equals(confirmPwd))
//        {
//            ToastMessage("两次密码输入不一致");
//            editTextConfirm.setText("");
//            return;
//        }
//        boolean b = checkInput();
//        if (!b)
//        {
//            ToastMessage(et_new_pwd.getErrMsg() + "\n" + et_confirm_new_pwd.getErrMsg());
//            return;
//        }
//        
        if (!et_new_pwd.validate())
        {
            ToastMessage(et_new_pwd.getErrMsg());
            return;

        }
        
        if (!et_confirm_new_pwd.validate())
        {
            ToastMessage(et_confirm_new_pwd.getErrMsg());
            return;

        }
        
        if (!et_new_pwd.getText().toString().equals(et_confirm_new_pwd.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        
        showLoading("正在提交新密码...", 0);
        dovalidate();

    }

    /**
     * 提交新密码
     * 
     * @author lizyi 2013-11-8 下午3:35:06
     */
    private void dovalidate()
    {
    	
        final String confirmPwd = et_confirm_new_pwd.getText();
        biz.modifyPwd(EncryptUtils.getMD5Str(old_pwd).toUpperCase(Locale.US), EncryptUtils.getMD5Str(confirmPwd).toUpperCase(Locale.US),
                new BizResultProcess<Boolean>()
                {

                    @Override
                    public void onBizExecuteEnd(BizDataTypeEnum datatype, Boolean t)
                    {
                        final boolean ret = t;
                        runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                            	
                            	
                            	
                            	
                            	
                                if (!ret)
                                {
                                    // TO DO:5秒钟
                                    NewPasswordSubmitActivity.this.ToastMessage("密码修改失败！");
                                    return;
                                }
                                else
                                {
                                    // 密码修改成功
                                    // TO DO:5秒钟
                                    NewPasswordSubmitActivity.this.ToastMessage("密码修改成功！");
                                    NewPasswordSubmitActivity.this.finish();
                                }
                            }
                        });

                    }

                    /**
                     * 根据错误码进行异常处理
                     * 
                     * @author lizyi 2013-11-8 下午3:53:46
                     * @see com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess#onBizExecuteError(java.lang.Exception,
                     *      java.lang.Error)
                     */
                    @Override
                    public void onBizExecuteError(final Exception exception, Error error)
                    {
                        runOnUiThread(new Runnable()
                        {
                            public void run()
                            {
                                NewPasswordSubmitActivity.this.dismissLoading(0);
                                // TO DO:5秒钟
//                                NewPasswordSubmitActivity.this.ToastMessage("新密码输入错误！");
                                HandleLogicErrorInfo(exception);
                                et_new_pwd.setText("");
                                et_confirm_new_pwd.setText("");
                            }
                        });

                    }

                });
    }

    /**
     * 初始化控件
     * 
     * @author lizyi 2013-11-8 下午2:15:14
     */
    public void initViews()
    {
        setAllowNetworkCheck(true);
        et_new_pwd = (ExEditText) findViewById(R.id.et_new_pwd);
        et_new_pwd.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_confirm_new_pwd = (ExEditText) findViewById(R.id.et_confirm_new_pwd);
        et_confirm_new_pwd.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_new_pwd.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) }); 	
        et_confirm_new_pwd.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        
        et_new_pwd.getEditText().setFilters(new InputFilter[] { 
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
        
        et_confirm_new_pwd.getEditText().setFilters(new InputFilter[] { 
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
        btn_right.setBackgroundResource(R.drawable.bg_bluebtn);
        btn_right.setWidth(DensityUtil.dip2px(getApplicationContext(), 72));
        btn_right.setHeight(DensityUtil.dip2px(getApplicationContext(), 36));
        btn_right.setText("确认");
        confirmBtn = btn_right;
        btn_right.setEnabled(false);
        btn_right.setTextColor(NewPasswordSubmitActivity.this.getResources().getColor(R.color.password_confirm));
        
        tv_large.setText("密码修改");
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

}
