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
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * @author zhangyongn
 */
public class FindPWDActivity_InputPhone extends YtAbstractActivity implements
		OnClickListener {
	private final String VERIFYCODETYPE = "1";// 验证码类型，0：注册.1:找回密码

	private ExEditText findpwd_phone_ET;

	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	private YtAsyncTask sendCodeBiz;

	private YtAsyncTask verifyPhoneBiz;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpwd_inputphone);
		initViews();
		initListener();
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		findpwd_phone_ET
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
		findpwd_phone_ET = (ExEditText) findViewById(R.id.findpwd_phone_ET);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(R.string.next);
		tv_top_title.setText(R.string.findpwdtitle);
		EditText et = findpwd_phone_ET.getEditText();
		et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			//
			if (!findpwd_phone_ET.validate()) {
				Toast.makeText(getApplicationContext(),
						findpwd_phone_ET.getErrMsg(), Toast.LENGTH_SHORT)
						.show();
				break;
			}
			startSendValidPhone();
			break;
		case R.id.btn_title_left:
			// 返回登录

			this.finish();
			break;
		default:
			break;
		}
	}

	private void cancelTask() {
		if (sendCodeBiz != null)
			sendCodeBiz.cancel();

	}

	private void startSendValidPhone() {
		if (sendCodeBiz != null)
			sendCodeBiz.cancel();

		loadingoverlay.setLoadingTip("正在验证手机...");
		loadingoverlay.setVisibility(View.VISIBLE);

		verifyPhoneBiz = new VerifyPhoneBiz(getApplicationContext(),
				new SendVerifyPhoneHandler(FindPWDActivity_InputPhone.this),
				findpwd_phone_ET.getText().toString());

		verifyPhoneBiz.execute();
	}

	private static class SendVerifyPhoneHandler extends YtHandler {
		private final WeakReference<FindPWDActivity_InputPhone> activity;

		private String result;

		public SendVerifyPhoneHandler(FindPWDActivity_InputPhone activity) {
			this.activity = new WeakReference<FindPWDActivity_InputPhone>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(FindPWDActivity_InputPhone.class,
					"[找回密码页面-验证手机handler]:msg.what:", msg.what);
			FindPWDActivity_InputPhone ac = activity.get();

			if (activity != null) {
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					String validCode = (String) msg.obj;
					if ("1".equals(validCode)) {
						ac.startSendValidCode();
					} else {
						ac.loadingoverlay.setVisibility(View.INVISIBLE);
						Toast.makeText(ac.getApplicationContext(), "手机号未注册！",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case ThreadCommStateCode.COMMON_FAILED:
				default:
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}

	}

	private void startSendValidCode() {

		if (sendCodeBiz != null)
			sendCodeBiz.cancel();
		loadingoverlay.setLoadingTip("正在发送验证码...");
		loadingoverlay.setVisibility(View.VISIBLE);
		sendCodeBiz = new SendPhoneBiz(getApplicationContext(),
				new SendVerifyCodeHandler(FindPWDActivity_InputPhone.this),
				findpwd_phone_ET.getText().toString(), VERIFYCODETYPE);

		sendCodeBiz.execute();
	}

	private static class SendVerifyCodeHandler extends YtHandler {
		private final WeakReference<FindPWDActivity_InputPhone> activity;

		private String result;

		public SendVerifyCodeHandler(FindPWDActivity_InputPhone activity) {
			this.activity = new WeakReference<FindPWDActivity_InputPhone>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(FindPWDActivity_InputPhone.class,
					"[找回密码页面-发送验证码handler]:msg.what:", msg.what);
			FindPWDActivity_InputPhone ac = activity.get();
			if (activity != null) {
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;

					// 保存收到的验证码
					YtApplication.getInstance().setFindpwdCode(result);
					YtApplication.getInstance().setReceiveFindpwdCodeTime(
							Tools.getCurDate());
					YtApplication.getInstance().setFindpwdPhone(
							ac.findpwd_phone_ET.getText().toString());

					ac.startActivity(new Intent(ac.getApplication(),
							FindPWDActivity_InputCode.class));
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
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					break;
				}
			}
		}

	}
}
