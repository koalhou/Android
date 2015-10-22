package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.behaviour.MobileEnvUploadBiz;
import com.yutong.axxc.parents.business.login.LoginBiz;
import com.yutong.axxc.parents.business.view.ProxyBiz;
import com.yutong.axxc.parents.common.GlobleConstants;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.LoginInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.MainActivity;
import com.yutong.axxc.parents.view.home.MainNotBindActivity;

public class LoginActivity extends YtAbstractActivity implements
		android.view.View.OnClickListener {

	private RelativeLayout body;
	private LoadingOverlay loadingoverlay;
	private YtAsyncTask biz;

	private TextView registerTV;
	private TextView findpwdTV;
	/** 用户名 */
	private ExEditText userNameET;
	/** 密码 */
	public ExEditText passwordET;

	/** 登陆按钮 */
	private Button loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_login);
		initViews();
		registerListener();

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (biz != null)
			biz.cancel();
		loadingoverlay.setVisibility(View.INVISIBLE);
	}

	private void registerListener() {

		registerTV.setOnClickListener(this);
		findpwdTV.setOnClickListener(this);

		loginButton.setOnClickListener(this);

		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {

						if (biz != null)
							biz.cancel();
						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

		userNameET.addOnValidateListener(new ExEditText.OnValidateListener() {

			@Override
			public String onValidate(String text) {
				if (StringUtil.isNull(text))
					return "请输入登录账号！";
				else
					return "";
			}
		});
		passwordET.addOnValidateListener(new ExEditText.OnValidateListener() {

			@Override
			public String onValidate(String text) {
				if (StringUtil.isNull(text))
					return "请输入密码！";
				return "";
			}
		});
	}

	private void initViews() {
		body = (RelativeLayout) findViewById(R.id.body);
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		registerTV = (TextView) findViewById(R.id.registerTV);
		findpwdTV = (TextView) findViewById(R.id.findpwdTV);

		userNameET = (ExEditText) findViewById(R.id.userNameET);
		passwordET = (ExEditText) findViewById(R.id.passwordET);
		loginButton = (Button) findViewById(R.id.loginButton);

		EditText pwdinneret = passwordET.getEditText();
		int pl = pwdinneret.getPaddingLeft();
		pwdinneret.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		pwdinneret.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.blue_input_bg));
		pwdinneret.setPadding(pl, 0, 0, 0);// 如果同时用，setPadding 将不会起作用，用的是
											// drawable里面自带的padding。setPadding要在setBackgroundDrawable之后执行才能生效
		pwdinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				20) });

		EditText usernameinneret = userNameET.getEditText();
		pl = usernameinneret.getPaddingLeft();
		usernameinneret.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.blue_input_bg));
		usernameinneret.setPadding(pl, 0, 0, 0);
		usernameinneret
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });

	}

	@Override
	public void onBackPressed() {
		if (loadingoverlay.getVisibility() != View.VISIBLE) {
			super.onBackPressed();
			System.exit(0);
		} else {
			if (biz != null)
				biz.cancel();
			loadingoverlay.setVisibility(View.INVISIBLE);
		}

	}

	private boolean checkInputInfo(String userName, String password) {
		boolean uservalid = userNameET.validate();
		boolean pwdvalid = passwordET.validate();
		return uservalid && pwdvalid;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registerTV:
			Intent intent1 = new Intent(getApplication(),
					RegisterActivity_Protocol.class);

			startActivity(intent1);

			break;
		case R.id.findpwdTV:
			Intent intent2 = new Intent(getApplication(),
					FindPWDActivity_InputPhone.class);

			startActivity(intent2);
			break;

		case R.id.loginButton:
			String userName = StringUtil.parseStr(userNameET.getText());
			String pwdmd5 = Tools.getMD5Str(passwordET.getText()).toUpperCase(
					Locale.US);
			String password = StringUtil.parseStr(pwdmd5);

			LoginInfoBean loginInfoBean = new LoginInfoBean();
			if (checkInputInfo(userName, password)) {

				loginInfoBean.setUsr_pwd(password);
				loginInfoBean.setUsr_account(userName.trim());
				// YtApplication.getInstance().setUserName(userName.trim());
				loadingoverlay.setVisibility(View.VISIBLE);
				loadingoverlay.setLoadingTip("正在登录,请稍候...");
				startLoginTask(loginInfoBean);

			} else {
				String errmsg = getErrorMsg();
				Toast.makeText(getApplicationContext(), errmsg,
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
		}
	}

	private String getErrorMsg() {
		return userNameET.getErrMsg() + passwordET.getErrMsg();

	}

	private void startLoginTask(LoginInfoBean loginInfoBean) {
		if (biz != null)
			biz.cancel();
		biz = new LoginBiz(getApplicationContext(), new LoginHandler(
				LoginActivity.this), loginInfoBean);

		biz.execute();

	}

	private static class LoginHandler extends YtHandler {
		private final WeakReference<LoginActivity> activity;

		private UserInfoBean userInfoBean;

		public LoginHandler(LoginActivity activity) {
			this.activity = new WeakReference<LoginActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(LoginActivity.class, "[登录页面-登录handler]:msg.what:",
					msg.what);
			LoginActivity ac = activity.get();
			if (ac != null) {
				ac.loadingoverlay.setVisibility(View.INVISIBLE);
				switch (msg.what) {
				case ThreadCommStateCode.LOGIN_SUCCESS:
					userInfoBean = (UserInfoBean) msg.obj;
					if (userInfoBean == null) {
						Toast.makeText(ac.getApplicationContext(), "登录失败！",
								Toast.LENGTH_SHORT).show();
						break;
					}

					// 上传手机环境信息
					(new MobileEnvUploadBiz(ac, null)).execute();

					// 注册推送服务
					YtApplication.getInstance().registerPushReceiver();
					YtApplication.getInstance().registerNetworkReceiver();

					Intent intent1 = null;
					// zyong test
					// userInfoBean.setPhone_bind(UserInfoBean.PHONEBIND);
					// userInfoBean.setPhone_bind(UserInfoBean.PHONENOTBIND);

					if (userInfoBean.getPhone_bind() == null
							|| userInfoBean.getPhone_bind().equals(
									UserInfoBean.PHONENOTBIND)) {
						intent1 = new Intent(ac.getApplication(),
								MainNotBindActivity.class);
					} else {
						intent1 = new Intent(ac.getApplication(),
								MainActivity.class);
					}
					YtApplication.getInstance().setUserInfoCache(userInfoBean);
					intent1.putExtra(ActivityCommConstant.USER_INFO,
							userInfoBean);
					ac.startActivity(intent1);
					ac.finish();
					break;
				case ThreadCommStateCode.TOKEN_INVALID:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ProxyBiz.startCleanData();
					ac.passwordET.setText(GlobleConstants.EMPTY_STR);
					break;
				case ThreadCommStateCode.LOGIN_FAILED:
				case ThreadCommStateCode.COMMON_FAILED:
				default:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;

				}
			}
		}
	}
}
