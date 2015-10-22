package com.yutong.axxc.parents.view.home;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.yutong.axxc.parents.business.login.UserBindStudentBiz;
import com.yutong.axxc.parents.business.login.VerifyProtocolPhoneBiz;
import com.yutong.axxc.parents.business.other.SendPhoneBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
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
public class BindChildActivity extends YtAbstractActivity implements
		OnClickListener {
	private final String VERIFYCODETYPE = "2";// 验证码类型，0：注册.2,找回密码
	private YtAsyncTask sendCodeBiz;
	private YtAsyncTask bindBiz;
	private YtAsyncTask phoneValidBiz;

	private TimeCount time;
	private ExEditText parentname_ET;
	private ExEditText phone_ET;
	private ExEditText code_ET;
	private Button sendcodebtn;

	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;
	private int CODE_EXPIRE_SECONDS = 60;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bindchild);
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

		sendcodebtn.setOnClickListener(this);

		parentname_ET
				.addOnValidateListener(new ExEditText.OnValidateListener() {

					@Override
					public String onValidate(String text) {
						if (StringUtil.isNull(text))
							return "请输入家长姓名！";
						else
							return "";
					}
				});
		phone_ET.addOnValidateListener(new ExEditText.OnValidateListener() {

			@Override
			public String onValidate(String text) {
				if (StringUtils.isBlank(text))
					return "请输入手机号！";
				else if (!StringUtil.isMobileNO(text)) {
					return "请输入正确的手机号！";
				} else
					return "";
			}
		});
		code_ET.addOnValidateListener(new ExEditText.OnValidateListener() {

			@Override
			public String onValidate(String text) {
				if (StringUtils.isBlank(text))
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
		parentname_ET = (ExEditText) findViewById(R.id.parentname_ET);
		phone_ET = (ExEditText) findViewById(R.id.phone_ET);
		code_ET = (ExEditText) findViewById(R.id.code_ET);
		sendcodebtn = (Button) findViewById(R.id.sendcodebtn);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(R.string.submittext);
		tv_top_title.setText(R.string.bindchildtitle);
		// 初始状态不可点，等验证码发送后，才可以点击
		btn_title_right.setEnabled(false);

		EditText et = code_ET.getEditText();
		// et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
		et = parentname_ET.getEditText();
		// et.setInputType(InputType.TYPE_CLASS_PHONE);
		et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
		et = phone_ET.getEditText();
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
			if (!parentname_ET.validate()) {
				Toast.makeText(getApplicationContext(),
						parentname_ET.getErrMsg(), Toast.LENGTH_SHORT).show();
				break;
			}
			if (!phone_ET.validate()) {
				Toast.makeText(getApplicationContext(), phone_ET.getErrMsg(),
						Toast.LENGTH_SHORT).show();
				break;
			}
			// 还要判断验证码是否过期
			if (YtApplication.getInstance().getBindStudentCode() == null
					|| Tools.checkTimeExpired(YtApplication.getInstance()
							.getBindStudentCodeTime(), CODE_EXPIRE_SECONDS)) {
				Toast.makeText(getApplicationContext(), "验证码已失效，请重发！",
						Toast.LENGTH_SHORT).show();
				break;
			}
			if (!StringUtil.equals(YtApplication.getInstance()
					.getBindStudentCode(), code_ET.getText())) {
				Toast.makeText(getApplicationContext(), "验证码不匹配",
						Toast.LENGTH_SHORT).show();
				break;
			}
			
			 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
	        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0006);
	        
	        
			loadingoverlay.setVisibility(View.VISIBLE);
			loadingoverlay.setLoadingTip("正在验证相关信息...");

			startBindTask();

			break;
		case R.id.btn_title_left:
			Intent intent2 = new Intent(getApplication(),
					MainNotBindActivity.class);

			startActivity(intent2);
			this.finish();
			break;
		case R.id.sendcodebtn:
			if (!parentname_ET.validate()) {
				Toast.makeText(getApplicationContext(),
						parentname_ET.getErrMsg(), Toast.LENGTH_SHORT).show();
				break;
			}
			if (!phone_ET.validate()) {
				Toast.makeText(getApplicationContext(), phone_ET.getErrMsg(),
						Toast.LENGTH_SHORT).show();
				break;
			}

			this.startPhoneValidTask();

			break;
		default:
			break;
		}
	}

	private void startBindTask() {
		if (bindBiz != null)
			bindBiz.cancel();
		bindBiz = new UserBindStudentBiz(getApplicationContext(),
				new BindHandler(BindChildActivity.this), phone_ET.getText());

		bindBiz.execute();

	}

	private void startPhoneValidTask() {
		loadingoverlay.setVisibility(View.VISIBLE);
		loadingoverlay.setLoadingTip("正在验证手机号码...");
		if (phoneValidBiz != null)
			phoneValidBiz.cancel();
		phoneValidBiz = new VerifyProtocolPhoneBiz(getApplicationContext(),
				new PhoneValidHandler(BindChildActivity.this),
				phone_ET.getText(), parentname_ET.getText());

		phoneValidBiz.execute();

	}

	private void startSendValidCode() {

		if (sendCodeBiz != null)
			sendCodeBiz.cancel();
		sendCodeBiz = new SendPhoneBiz(getApplicationContext(),
				new SendVerifyCodeHandler(BindChildActivity.this),
				phone_ET.getText(), VERIFYCODETYPE);

		sendCodeBiz.execute();
	}

	/**
	 * 发送验证码handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class SendVerifyCodeHandler extends YtHandler {
		private final WeakReference<BindChildActivity> activity;

		private String result;

		public SendVerifyCodeHandler(BindChildActivity activity) {
			this.activity = new WeakReference<BindChildActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(BindChildActivity.class,
					"[关联学生页面-发送验证码handler]:msg.what:", msg.what);
			BindChildActivity ac = activity.get();
			if (activity != null) {
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					// 保存收到的验证码
					YtApplication.getInstance().setBindStudentCode(result);
					YtApplication.getInstance().setBindStudentCodeTime(
							Tools.getCurDate());

					ac.time.start();// 开始计时
					ac.sendcodebtn.setClickable(false);
					Toast.makeText(ac.getApplicationContext(), "已经发送验证码，请注意查收",
							Toast.LENGTH_SHORT).show();
					// 设置为可用
					ac.btn_title_right.setEnabled(true);
					// test
					ac.code_ET.setText(YtApplication.getInstance()
							.getBindStudentCode());
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

	/**
	 * 验证手机协议handler
	 * 
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class PhoneValidHandler extends YtHandler {
		private final WeakReference<BindChildActivity> activity;

		private String result;

		public PhoneValidHandler(BindChildActivity activity) {
			this.activity = new WeakReference<BindChildActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(BindChildActivity.class,
					"[关联学生页面-协议手机验证handler]:msg.what:", msg.what);
			BindChildActivity ac = activity.get();
			
			if (activity != null) {
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;
					if ("1".equals(result)) {
						ac.loadingoverlay.setVisibility(View.INVISIBLE);
						Toast.makeText(ac.getApplicationContext(), "协议手机验证失败!",
								Toast.LENGTH_SHORT).show();
					} else {
						ac.loadingoverlay.setLoadingTip("手机协议验证成功,正在发送验证码...");
						ac.startSendValidCode();
					}
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
			}
		}

	}

	/**
	 * 关联学生handler
	 * 
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class BindHandler extends YtHandler {
		private final WeakReference<BindChildActivity> activity;

		private String result;

		public BindHandler(BindChildActivity activity) {
			this.activity = new WeakReference<BindChildActivity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Logger.i(BindChildActivity.class, "[关联学生页面-关联学生handler]:msg.what:",
					msg.what);
			BindChildActivity ac = activity.get();
			if (activity != null) {
				ac.loadingoverlay.setVisibility(View.INVISIBLE);
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					result = (String) msg.obj;
					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					ac.startActivity(new Intent(ac.getApplication(),
							BindChildActivity_Succ.class));
					ac.overridePendingTransition(R.anim.enter_righttoleft,
							R.anim.exit_righttoleft);
					ac.finish();

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();

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
			YtApplication.getInstance().setBindStudentCode(null);
			sendcodebtn.setText("发送验证码");
			sendcodebtn.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			sendcodebtn.setClickable(false);
			String str = "验证码已发送(%s)";
			String text = String
					.format(str, (int) (millisUntilFinished / 1000));
			sendcodebtn.setText(text);
		}
	}

}
