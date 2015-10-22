package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;

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
import com.yutong.axxc.parents.business.other.SendPhoneBiz;
import com.yutong.axxc.parents.business.register.VerifyPhoneBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserBehaviorBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * 
 * 
 * @author zhangyongn
 * 
 */
public class RegisterActivity_InputPhone extends YtAbstractActivity implements
		OnClickListener {
	private final String VERIFYREGISTERED = "1";// 已注册
	private final String VERIFYNOTREGISTER = "0";// 未注册

	private final String VERIFYCODETYPE = "0";// 验证码类型，0：注册.

	private YtAsyncTask verifyPhoneBiz;
	private YtAsyncTask sendCodeBiz;

	private ExEditText register_phone_ET;
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_inputphone);
		initViews();
		initListener();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		register_phone_ET
				.addOnValidateListener(new ExEditText.OnValidateListener() {

					@Override
					public String onValidate(String text) {
						if (StringUtil.isNull(text))
							return "请输入手机号！";
						if (!StringUtil.isMobileNO(text)) {
							return "请输入正确的手机号!";
						} else
							return "";
					}
				});
		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {
						cancelTask();
						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});
	}

	private void initViews() {
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		register_phone_ET = (ExEditText) findViewById(R.id.register_phone_ET);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(R.string.next);
		tv_top_title.setText(R.string.registerTitle);

		EditText et = register_phone_ET.getEditText();
		et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });

	}

	@Override
	public void onBackPressed() {
		if (loadingoverlay.getVisibility() != View.VISIBLE) {
			super.onBackPressed();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			if (!register_phone_ET.validate()) {
				Toast.makeText(getApplicationContext(),
						register_phone_ET.getErrMsg(), Toast.LENGTH_SHORT)
						.show();
				break;
			}
			loadingoverlay.setLoadingTip("正在验证手机号...");
			loadingoverlay.setVisibility(View.VISIBLE);
			
			 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
	        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0003);
	        
			startVerifyPhoneTask();
			break;
		case R.id.btn_title_left:
			// 返回登录

			this.finish();
			break;

		default:
			break;
		}
	}

	private void startVerifyPhoneTask() {
		if (verifyPhoneBiz != null)
			verifyPhoneBiz.cancel();
		
		verifyPhoneBiz = new VerifyPhoneBiz(getApplicationContext(),
				new RegisterHandler(RegisterActivity_InputPhone.this),
				register_phone_ET.getText().toString());

		verifyPhoneBiz.execute();

	}

	private void cancelTask() {
		if (sendCodeBiz != null)
			sendCodeBiz.cancel();
		if (verifyPhoneBiz != null)
			verifyPhoneBiz.cancel();
	}

	private void startSendValidCode() {

		if (sendCodeBiz != null)
			sendCodeBiz.cancel();
		sendCodeBiz = new SendPhoneBiz(getApplicationContext(),
				new SendVerifyCodeHandler(RegisterActivity_InputPhone.this),
				register_phone_ET.getText().toString(), VERIFYCODETYPE);

		sendCodeBiz.execute();
	}

	private static class RegisterHandler extends YtHandler {
		private final WeakReference<RegisterActivity_InputPhone> activity;

		private String result;

		public RegisterHandler(RegisterActivity_InputPhone activity) {
			this.activity = new WeakReference<RegisterActivity_InputPhone>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(LoginActivity.class, "[注册页面-输入手机号码handler]:msg.what:",
					msg.what);
			final RegisterActivity_InputPhone ac = activity.get();
			if (activity != null) {
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;
					if (ac.VERIFYREGISTERED.equals(result)) {
						Toast.makeText(ac.getApplicationContext(), "该手机已注册!",
								Toast.LENGTH_SHORT).show();
						ac.loadingoverlay.setVisibility(View.INVISIBLE);
						break;
					}

					ac.loadingoverlay.setLoadingTip("手机验证通过，正在发送验证码...");
					ac.startSendValidCode();

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.loadingoverlay.setVisibility(View.INVISIBLE);

					break;

				default:
				    Toast.makeText(ac.getApplicationContext(),
                            (String) msg.obj, Toast.LENGTH_SHORT).show();
                    ac.loadingoverlay.setVisibility(View.INVISIBLE);
					break;
				}
			}
		}

	}

	private static class SendVerifyCodeHandler extends YtHandler {
		private final WeakReference<RegisterActivity_InputPhone> activity;

		private String result;

		public SendVerifyCodeHandler(RegisterActivity_InputPhone activity) {
			this.activity = new WeakReference<RegisterActivity_InputPhone>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(RegisterActivity_InputPhone.class, "[注册页面-发送验证码handler]:msg.what:",
					msg.what);
			final RegisterActivity_InputPhone ac = activity.get();
			if (activity != null) {
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;

					// 保存收到的验证码
					YtApplication.getInstance().setRegisterCode(result);
					YtApplication.getInstance().setReceiveRegisterCodeTime(
							Tools.getCurDate());
					YtApplication.getInstance().setRegisterPhone(
							ac.register_phone_ET.getText().toString());

					ac.startActivity(new Intent(ac.getApplication(),
							RegisterActivity_InputCode.class));
					ac.overridePendingTransition(R.anim.enter_righttoleft,
							R.anim.exit_righttoleft);
					ac.finish();

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.loadingoverlay.setVisibility(View.INVISIBLE);

					break;

				default:
					break;
				}
			}
		}

	}

}
