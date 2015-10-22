package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.other.SendPhoneBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SysConfigUtil;
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
 * 
 * 
 * @author zhangyongn
 * 
 */
public class RegisterActivity_InputCode extends YtAbstractActivity implements
		OnClickListener {
	private final String VERIFYCODETYPE = "0";// 验证码类型，0：注册.
	private YtAsyncTask sendCodeBiz;
	private TimeCount time;
	private ExEditText register_code_ET;
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	private TextView reSendCodeTV;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;
	private int CODE_EXPIRE_SECONDS = 60;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_inputcode);
		initParam();
		time = new TimeCount(CODE_EXPIRE_SECONDS * 1000, 1000);// 构造CountDownTimer对象
		initViews();
		initListener();
	}

	private void initParam() {
		try {
			int delay = Integer.parseInt(SysConfigUtil.getProperty(
					"CODE_EXPIRE_SECONDS", "60"));
			CODE_EXPIRE_SECONDS = delay;
		} catch (Exception e) {
			CODE_EXPIRE_SECONDS = 60;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		reSendCodeTV.setOnClickListener(this);

		register_code_ET
				.addOnValidateListener(new ExEditText.OnValidateListener() {

					@Override
					public String onValidate(String text) {
						if (StringUtil.isNull(text))
							return "请输入验证码！";
						else
							return "";
					}
				});
		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {

						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});
	}

	private void initViews() {
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);
		register_code_ET = (ExEditText) findViewById(R.id.register_code_ET);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		reSendCodeTV = (TextView) findViewById(R.id.reSendCodeTV);

		btn_title_right.setText(R.string.next);
		tv_top_title.setText(R.string.registerTitle);

		EditText et = register_code_ET.getEditText();
		// et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });

		//test
		register_code_ET.setText(YtApplication.getInstance().getRegisterCode());
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			if (!register_code_ET.validate()) {
				Toast.makeText(getApplicationContext(),
						register_code_ET.getErrMsg(), Toast.LENGTH_SHORT)
						.show();
				break;
			}
			// 还要判断验证码是否过期
			if (YtApplication.getInstance().getRegisterCode() == null
					|| Tools.checkTimeExpired(YtApplication.getInstance()
							.getReceiveRegisterCodeTime(), CODE_EXPIRE_SECONDS)) {
				Toast.makeText(getApplicationContext(), "验证码已失效，请重发！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (!StringUtil.equals(YtApplication.getInstance()
					.getRegisterCode(), register_code_ET.getText())) {

				Toast.makeText(getApplicationContext(), "验证码不匹配",
						Toast.LENGTH_SHORT).show();
				break;
			}

			this.startActivity(new Intent(this.getApplication(),
					RegisterActivity_InputNickName.class));
			this.overridePendingTransition(R.anim.enter_righttoleft,
					R.anim.exit_righttoleft);
			this.finish();

			break;
		case R.id.btn_title_left:
			// 返回登录

			this.finish();
			break;
		case R.id.reSendCodeTV:
			loadingoverlay.setVisibility(View.VISIBLE);
			loadingoverlay.setLoadingTip("正在重发验证码...");
			startSendValidCode();

			break;
		default:
			break;
		}
	}

	private void startSendValidCode() {

		if (sendCodeBiz != null)
			sendCodeBiz.cancel();
		sendCodeBiz = new SendPhoneBiz(getApplicationContext(),
				new SendVerifyCodeHandler(RegisterActivity_InputCode.this),
				YtApplication.getInstance().getRegisterPhone(), VERIFYCODETYPE);

		sendCodeBiz.execute();
	}

	private static class SendVerifyCodeHandler extends YtHandler {
		private final WeakReference<RegisterActivity_InputCode> activity;

		private String result;

		public SendVerifyCodeHandler(RegisterActivity_InputCode activity) {
			this.activity = new WeakReference<RegisterActivity_InputCode>(
					activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(RegisterActivity_InputCode.class,
					"[注册页面-重发验证码handler]:msg.what:", msg.what);
			final RegisterActivity_InputCode ac = activity.get();
			if (activity != null) {
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					// 保存收到的验证码
					YtApplication.getInstance().setRegisterCode(result);
					YtApplication.getInstance().setReceiveRegisterCodeTime(
							Tools.getCurDate());

					ac.time.start();// 开始计时
					ac.reSendCodeTV.setClickable(false);
					Toast.makeText(ac.getApplicationContext(), "已经重发验证码，请注意查收",
							Toast.LENGTH_SHORT).show();
					//test
					ac.register_code_ET.setText(YtApplication.getInstance().getRegisterCode());

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

	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		private long totalTimeInterval;

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
			totalTimeInterval = millisInFuture;
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			YtApplication.getInstance().setRegisterCode(null);
			reSendCodeTV.setText("验证码已失效，点击此处重新发送验证码.");
			reSendCodeTV.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			reSendCodeTV.setClickable(false);
			String str = "验证码已发送(%s)";
			String text = String
					.format(str, (int) (millisUntilFinished / 1000));
			reSendCodeTV.setText(text);
		}
	}

}
