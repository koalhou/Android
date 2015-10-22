package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.settings.UserInfoUpdateBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.MainActivity;
import com.yutong.axxc.parents.view.home.MainNotBindActivity;

/**
 * @author zhangyongn
 */
public class RegisterActivity_FillProfile extends YtAbstractActivity implements OnClickListener
{

    private YtAsyncTask fillprofileBiz;

    private TextView sexclickoverlay;

    private ExEditText register_realname_ET;

    private ExEditText register_sex_ET;

    private ExEditText register_mail_ET;

    private ExEditText register_addr_ET;

    private Button submitButton;

    private YtAsyncTask biz;

    private Button btn_title_left;

    private TextView tv_top_title;

    private Button btn_title_right;

    // 加载弹出提示相关视图
    private LoadingOverlay loadingoverlay;

    private UserInfoBean userInfoBean = null;

    private static int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_fillprofile);
        initViews();
        initListener();
        fillData();
    }

    private void initListener()
    {
        btn_title_right.setOnClickListener(this);
        submitButton.setOnClickListener(this);

        sexclickoverlay.setOnClickListener(this);

        loadingoverlay.addOnCancelListener(new LoadingOverlay.OnCancelListener()
        {

            @Override
            public void onCancel()
            {

                // if (regBiz != null)
                // regBiz.cancel();
                loadingoverlay.setVisibility(View.INVISIBLE);

            }
        });

        // register_mail_ET
        // .addOnValidateListener(new ExEditText.OnValidateListener() {
        //
        // @Override
        // public String onValidate(String text) {
        // if (StringUtil.isNull(text))
        // return "";// 允许为空
        // else if (!StringUtil.isEmail(text)) {
        // return "请输入正确的邮件地址！";
        // } else
        // return "";
        // }
        // });

    }

    private void initViews()
    {
        sexclickoverlay = (TextView) findViewById(R.id.sexclickoverlay);
        loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

        register_realname_ET = (ExEditText) findViewById(R.id.register_realname_ET);
        register_sex_ET = (ExEditText) findViewById(R.id.register_sex_ET);
        register_mail_ET = (ExEditText) findViewById(R.id.register_mail_ET);
        register_addr_ET = (ExEditText) findViewById(R.id.register_addr_ET);
        submitButton = (Button) findViewById(R.id.submitButton);

        btn_title_left = (Button) findViewById(R.id.btn_title_left);
        tv_top_title = (TextView) findViewById(R.id.tv_top_title);
        btn_title_right = (Button) findViewById(R.id.btn_title_right);

        btn_title_right.setText(R.string.skip);
        tv_top_title.setText(R.string.registerfillprofiletoptitle);
        btn_title_left.setVisibility(View.INVISIBLE);

        register_sex_ET.setShowRightIcon(false);
        EditText sexet = register_sex_ET.getEditText();
        sexet.setKeyListener(null);

        register_addr_ET.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(80) });

        // register_mail_ET.getEditText().setFilters(
        // new InputFilter[] { new InputFilter.LengthFilter(20) });
        register_realname_ET.getEditText().setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });

    }

    private void fillData()
    {
        userInfoBean = YtApplication.getInstance().getUserInfoCache();

        if (userInfoBean != null)
        {
            register_realname_ET.setText(userInfoBean.getUsr_name());

            String sex = userInfoBean.getUsr_sex();
            if ("0".equals(sex))
            {
                register_sex_ET.setText("男");
            }
            else if ("1".equals(sex))
            {
                register_sex_ET.setText("女");
            }
            else
            {
                register_sex_ET.setText("未设置");
            }
            // register_mail_ET.setText(userInfoBean.getUsr_email());
            register_addr_ET.setText(userInfoBean.getUsr_addr());

        }
    }

    private boolean checkInputInfo()
    {
        boolean v1 = register_addr_ET.validate();
        boolean v2 = register_mail_ET.validate();
        boolean v3 = register_realname_ET.validate();
        return v1 && v2 && v3;
    }

    private String getErrorMsg()
    {
        return register_realname_ET.getErrMsg() + register_mail_ET.getErrMsg() + register_addr_ET.getErrMsg();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {

        case R.id.sexclickoverlay:

            // case R.id.register_sex_ET:
            //
            // startActivity(new Intent(getApplication(),
            // BusMapActivity.class));
            // Toast.makeText(getApplicationContext(), "性别", Toast.LENGTH_SHORT)
            // .show();
            // 数据列表
            String[] strList = new String[] { "男", "女" };

            AlertDialog mDialog = new AlertDialog.Builder(this).setTitle("请选择性别")
                    .setSingleChoiceItems(strList, 
                            "1".equals(userInfoBean.getUsr_sex()) ? 1 : 0,// 数据列表、第几个为选中
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            mSelectedItem = which;
                        }
                    }).setPositiveButton("确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    String sex = String.valueOf(mSelectedItem);

                    userInfoBean.setUsr_sex(sex);

                    if ("0".equals(sex))
                    {
                        register_sex_ET.setText("男");
                    }
                    else if ("1".equals(sex))
                    {
                        register_sex_ET.setText("女");
                    }
                    else
                    {
                        register_sex_ET.setText("未设置");
                    }
                    
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            }).create();

            mDialog.show();

            break;
        case R.id.submitButton:

            if (checkInputInfo())
            {

                loadingoverlay.setVisibility(View.VISIBLE);
                loadingoverlay.setLoadingTip("正在提交,请稍候...");
                startSubmitTask();

            }
            else
            {
                String errmsg = getErrorMsg();
                Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();
            }

            break;
        case R.id.btn_title_right:

            Intent intent1;
            if ("1".equals(YtApplication.getInstance().getUserInfoCache().getPhone_bind()))
            {
                intent1 = new Intent(getApplication(), MainActivity.class);

            }
            else
            {
                intent1 = new Intent(getApplication(), MainNotBindActivity.class);
            }
            startActivity(intent1);

            overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
            finish();

            break;
        default:
            break;
        }
    }

    private void startSubmitTask()
    {
        if (fillprofileBiz != null)
            fillprofileBiz.cancel();

        UserInfoBean info = YtApplication.getInstance().getUserInfoCache();
        info.setUsr_name(register_realname_ET.getText());
        info.setUsr_email(register_mail_ET.getText());
        info.setUsr_addr(register_addr_ET.getText());
        info.setUsr_sex(getSex());

        fillprofileBiz = new UserInfoUpdateBiz(getApplicationContext(), new FillProfileHandler(RegisterActivity_FillProfile.this), info);

        fillprofileBiz.execute();

    }

    private String getSex()
    {
        String t = register_sex_ET.getText();
        if (t.equals("男"))
            return "0";
        else if (t.equals("女"))
            return "1";
        else
            return "-1";
    }

    private static class FillProfileHandler extends YtHandler
    {
        private final WeakReference<RegisterActivity_FillProfile> activity;

        public FillProfileHandler(RegisterActivity_FillProfile activity)
        {
            this.activity = new WeakReference<RegisterActivity_FillProfile>(activity);
        }

        /**
         * (non-Javadoc)
         * 
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg)
        {
            Logger.i(RegisterActivity_FillProfile.class, "[注册页面-完善个人资料handler]:msg.what:", msg.what);
            final RegisterActivity_FillProfile ac = activity.get();
            if (ac != null)
            {
                switch (msg.what)
                {
                case ThreadCommStateCode.COMMON_SUCCESS:

                    ac.loadingoverlay.setVisibility(View.INVISIBLE);
                    Intent intent1;

                    if ("1".equals(YtApplication.getInstance().getUserInfoCache().getPhone_bind()))
                    {
                        intent1 = new Intent(ac.getApplication(), MainActivity.class);

                    }
                    else
                    {
                        intent1 = new Intent(ac.getApplication(), MainNotBindActivity.class);
                    }

                    ac.startActivity(intent1);
                    ac.overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
                    ac.finish();

                    break;
                case ThreadCommStateCode.COMMON_FAILED:
                    Toast.makeText(ac.getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    ac.loadingoverlay.setVisibility(View.INVISIBLE);

                    break;

                default:
                    Toast.makeText(ac.getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    ac.loadingoverlay.setVisibility(View.INVISIBLE);
                    break;
                }
            }
        }
    }

    private void startTask(UserInfoBean userInfoBean)
    {
        if (biz != null)
            biz.cancel();
        biz = new UserInfoUpdateBiz(getApplicationContext(), new ProcessHandler(RegisterActivity_FillProfile.this), userInfoBean);

        biz.execute();

    }

    private static class ProcessHandler extends YtHandler
    {
        private final WeakReference<RegisterActivity_FillProfile> weakActivity;

        public ProcessHandler(RegisterActivity_FillProfile activity)
        {
            this.weakActivity = new WeakReference<RegisterActivity_FillProfile>(activity);
        }

        /**
         * (non-Javadoc)
         * 
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg)
        {
            Logger.i(RegisterActivity_FillProfile.class, "[完善资料-handler]:msg.what:", msg.what);
            RegisterActivity_FillProfile activity = weakActivity.get();
            if (activity != null)
            {
                super.handleMessage(msg, activity);
                switch (msg.what)
                {
                case ThreadCommStateCode.COMMON_SUCCESS:

                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity.getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                    if (activity.userInfoBean != null)
                    {
                        YtApplication.getInstance().setUserInfoCache(activity.userInfoBean);
                    }
                    break;

                case ThreadCommStateCode.TOKEN_INVALID:
                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity.getApplicationContext(), "Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
                    break;

                case ThreadCommStateCode.COMMON_FAILED:

                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity.getApplicationContext(), "修改失败！请重试！", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    activity.loadingoverlay.setVisibility(View.INVISIBLE);
                    Toast.makeText(activity.getApplicationContext(), (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }
}
