package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.behaviour.MobileEnvUploadBiz;
import com.yutong.axxc.parents.business.common.ReceiverUtil;
import com.yutong.axxc.parents.business.register.RegisterBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.GlobleConstants;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.MainActivity;

/**
 * 
 * 
 * @author zhangyongn
 * 
 */
public class RegisterActivity_InputNickName extends YtAbstractActivity
		implements OnClickListener {
	private YtAsyncTask regBiz;
	private ExEditText register_nickname_ET;
	private ExEditText register_pwd_ET;
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_inputnickname);
		initViews();
		initListener();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {

						if (regBiz != null)
							regBiz.cancel();
						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

		register_nickname_ET.addOnValidateListener(new ExEditText.OnValidateListener() {

			@Override
			public String onValidate(String text) {
				if (StringUtil.isNull(text))
					return "请输入用户名！";
				else
					return "";
			}
		});
		register_pwd_ET.addOnValidateListener(new ExEditText.OnValidateListener() {

			@Override
			public String onValidate(String text) {
				if (StringUtil.isNull(text))
					return "请输入密码！";
				return "";
			}
		});
	}

	private void initViews() {
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);
		register_nickname_ET = (ExEditText) findViewById(R.id.register_nickname_ET);
		register_pwd_ET = (ExEditText) findViewById(R.id.register_pwd_ET);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(R.string.next);
		tv_top_title.setText(R.string.registerTitle);

		EditText pwdinneret = register_pwd_ET.getEditText();
		int pl = pwdinneret.getPaddingLeft();
		pwdinneret.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		pwdinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				20) });

		EditText usernameinneret = register_nickname_ET.getEditText();

		usernameinneret
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}
	private boolean checkInputInfo() {
		boolean uservalid = register_nickname_ET.validate();
		boolean pwdvalid = register_pwd_ET.validate();
		return uservalid && pwdvalid;
	}
	private String getErrorMsg() {
		return register_nickname_ET.getErrMsg() + register_pwd_ET.getErrMsg();

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			UserInfoBean userbean = new UserInfoBean();
			if (checkInputInfo()) {
				String pwdmd5=Tools.getMD5Str(register_pwd_ET.getText()).toUpperCase(Locale.US);
				userbean.setUsr_pwd(pwdmd5);
				userbean.setUsr_login_name(register_nickname_ET.getText().toString());
				userbean.setUsr_phone(YtApplication.getInstance().getRegisterPhone());
				
				//用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0004);
		        
				loadingoverlay.setVisibility(View.VISIBLE);
				loadingoverlay.setLoadingTip("正在注册,请稍候...");
				startRegTask(userbean);

			} else {
				String errmsg = getErrorMsg();
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}
			

			break;
		case R.id.btn_title_left:
			// 返回登录

			this.finish();
			break;
		default:
			break;
		}
	}

	private void startRegTask(UserInfoBean userbean) {
		if (regBiz != null)
			regBiz.cancel();
		regBiz = new RegisterBiz(getApplicationContext(), new RegHandler(
				RegisterActivity_InputNickName.this), userbean);

		regBiz.execute();
		
	}
	private static class RegHandler extends YtHandler {
		private final WeakReference<RegisterActivity_InputNickName> activity;

		private UserInfoBean userInfoBean;

		public RegHandler(RegisterActivity_InputNickName activity) {
			this.activity = new WeakReference<RegisterActivity_InputNickName>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(RegisterActivity_InputNickName.class, "[注册页面-填写昵称密码注册handler]:msg.what:",
					msg.what);
			final RegisterActivity_InputNickName ac = activity.get();
			if (ac != null) {
				switch (msg.what) {
				case ThreadCommStateCode.REG_REGISTER_SUCCESS:
					userInfoBean = (UserInfoBean) msg.obj;
					ac.loadingoverlay.setVisibility(View.VISIBLE);
					Intent intent1 = new Intent(ac.getApplication(),
							RegisterActivity_Succ.class);

					//保存到application
					YtApplication.getInstance().setUserInfoCache(userInfoBean);
					intent1.putExtra(ActivityCommConstant.USER_INFO,
							userInfoBean);
					
					//上传手机环境信息
                    (new MobileEnvUploadBiz(ac, null)).execute();
                    
					//注册推送服务
                    YtApplication.getInstance().registerPushReceiver();
                    YtApplication.getInstance().registerNetworkReceiver();
                    
					ac.startActivity(intent1);
					ac.overridePendingTransition(R.anim.enter_righttoleft,
							R.anim.exit_righttoleft);
					ac.finish();
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.loadingoverlay.setVisibility(View.INVISIBLE);

					ac.register_pwd_ET.setText(GlobleConstants.EMPTY_STR);
					break;

				default:
					break;
				}
			}
		}
	}
}
