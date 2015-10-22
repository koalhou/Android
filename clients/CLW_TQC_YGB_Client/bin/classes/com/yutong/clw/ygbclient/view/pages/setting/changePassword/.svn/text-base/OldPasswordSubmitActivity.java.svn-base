package com.yutong.clw.ygbclient.view.pages.setting.changePassword;

import java.util.Locale;

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

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.beans.VerifyAccountInfo;
import com.yutong.clw.ygbclient.common.utils.EncryptUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.changePassWord.BizModifyPwd;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.DataValidationUtil;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.pages.RemindAccessActivity;
import com.yutong.clw.ygbclient.view.pages.login.findPassword.DefaultPasswordActivity;
import com.yutong.clw.ygbclient.view.pages.login.findPassword.EmployeeIDSubmitActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.ExEditText;

/**
 * 旧密码输入界面
 * 
 * @author zhouzc
 */
public class OldPasswordSubmitActivity extends BaseActivity implements OnClickListener
{
    private final String ValidStringPassword = "[$NoChar(' ',<@不能有空格@>)$][$NotEmpty(<@请输入旧密码@>)$]";
    
    /**
     * 输入旧密码
     */
    private ExEditText et_old_pwd;

    /**
     * 提交
     */
    private Button subimtBtn;

    /**
     * 自定义控件内置编辑框
     */
    private EditText editText;

    /**
     * 旧密码验证处理
     */
    private BizModifyPwd biz = new BizModifyPwd(this);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_changepassword_old);
        initViews();
        initListener();
        initData();
    }

    private void initData()
    {
        // TODO Auto-generated method stub

    }

    private void initListener()
    {
        editText = et_old_pwd.getEditText();
        editText.addTextChangedListener(new MyTextWatcher(et_old_pwd, inputHandler));
        et_old_pwd.addOnValidateListener(new ExEditText.OnValidateListener()
        {

            @Override
            public String onValidate(String text)
            {
                String result = DataValidationUtil.validate(ValidStringPassword, text);

//                if (StringUtil.isNotBlank(result))
//                {
//                    result = "密码：" + result;
//                }

                // if (StringUtil.isEmpty(text))
                // return "请输入密码！";

                // LoignPasswordValidString
                return result;
            }
        });
    }

    /**
     * 输入未改变
     */
    private static final int TEXT_NOT_CHANGED = 0;

    /**
     * 输入改变
     */
    private static final int TEXT_CHANGED = 1;

    /**
     * 输入监听处理
     */
    private Handler inputHandler = new Handler()
    {

        // 旧手机号输入
        private boolean isOldPwdChanged = false;

        public void handleMessage(android.os.Message msg)
        {
            if (msg == null || msg.obj == null)
            {
                return;
            }
            switch (msg.what)
            {
            case TEXT_CHANGED:
                if (msg.obj.equals(et_old_pwd))
                {
                    isOldPwdChanged = true;
                }
                break;
            case TEXT_NOT_CHANGED:
                if (msg.obj.equals(et_old_pwd))
                {
                    isOldPwdChanged = false;
                }
            default:
                break;
            }
            if (isOldPwdChanged)
            {
                subimtBtn.setEnabled(true);
                subimtBtn.setTextColor(getResources().getColor(R.color.white));
            }
            else
            {
                subimtBtn.setEnabled(false);
                subimtBtn.setTextColor(getResources().getColor(R.color.password_confirm));
            }
        };
    };

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
     * 提交
     * 
     * @author lizyi 2013-11-8 下午2:20:14
     */
    private void doSubmit()
    {
        if (!et_old_pwd.validate())
        {
            ToastMessage(et_old_pwd.getErrMsg());
            return;
        }
        showLoading("正在验证密码...", 0);
        dovalidate();

    }

    /**
     * 验证旧密码
     * 
     * @author lizyi 2013-11-8 下午3:35:06
     */
    private void dovalidate()
    {
        final String oldPwd = et_old_pwd.getText();
        biz.verifyOldPwd(EncryptUtils.getMD5Str(oldPwd).toUpperCase(Locale.US), new BizResultProcess<Boolean>()
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
                            OldPasswordSubmitActivity.this.ToastMessage("密码输入有误！");
                            return;
                        }
                        else
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString("old_pwd", oldPwd);
                            ActivityUtils.changeActivity(OldPasswordSubmitActivity.this, NewPasswordSubmitActivity.class, bundle);
                            OldPasswordSubmitActivity.this.finish();
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
                        OldPasswordSubmitActivity.this.dismissLoading(0);
                        // TO DO:5秒钟
                        // OldPasswordSubmitActivity.this.ToastMessage("旧密码输入错误！");
                        HandleLogicErrorInfo(exception);
                        et_old_pwd.setText("");
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
        et_old_pwd = (ExEditText) findViewById(R.id.et_old_pwd);
        et_old_pwd.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_old_pwd.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(12) });
        
        et_old_pwd.getEditText().setFilters(new InputFilter[] { 
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
        btn_right.setText("提交");
        subimtBtn = btn_right;
        btn_right.setEnabled(false);
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
