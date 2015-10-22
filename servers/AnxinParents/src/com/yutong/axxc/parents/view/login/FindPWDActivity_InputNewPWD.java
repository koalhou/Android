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
import com.yutong.axxc.parents.business.register.FindPwdBiz;
import com.yutong.axxc.parents.common.GlobleConstants;
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
 * 
 * 
 * @author zhangyongn
 * 
 */
public class FindPWDActivity_InputNewPWD extends YtAbstractActivity implements
		OnClickListener {
	private final String ACCOUTTYPE = "0";// 0:手机号码，1:邮箱
	private YtAsyncTask regBiz;
	private ExEditText findpwd_pwd_ET;
	private ExEditText findpwd_pwd1_ET;
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpwd_inputnewpwd);
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

		findpwd_pwd_ET
				.addOnValidateListener(new ExEditText.OnValidateListener() {

					@Override
					public String onValidate(String text) {
						if (StringUtil.isNull(text))
							return "请输入密码！";
						else
							return "";
					}
				});
		findpwd_pwd1_ET
				.addOnValidateListener(new ExEditText.OnValidateListener() {

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
		findpwd_pwd_ET = (ExEditText) findViewById(R.id.findpwd_pwd_ET);
		findpwd_pwd1_ET = (ExEditText) findViewById(R.id.findpwd_pwd1_ET);
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(R.string.next);
		tv_top_title.setText(R.string.findpwdtitle);

		EditText pwd1inneret = findpwd_pwd1_ET.getEditText();

		pwd1inneret.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		pwd1inneret
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });

		EditText pwdinneret = findpwd_pwd_ET.getEditText();
		pwdinneret.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		pwdinneret.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				20) });
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	private boolean checkInputInfo() {
		boolean pwdvalid = findpwd_pwd_ET.validate();
		boolean pwd1valid = findpwd_pwd1_ET.validate();
		return pwdvalid && pwd1valid;
	}

	private String getErrorMsg() {
		return findpwd_pwd_ET.getErrMsg() + findpwd_pwd1_ET.getErrMsg();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			if (checkInputInfo()) {
				if (!findpwd_pwd1_ET.getText().toString()
						.equals(findpwd_pwd_ET.getText().toString())) {
					Toast.makeText(getApplicationContext(), "两次输入密码不一致",
							Toast.LENGTH_SHORT).show();
					break;
				}

				loadingoverlay.setVisibility(View.VISIBLE);
				loadingoverlay.setLoadingTip("正在提交,请稍候...");
				startSubmitTask();

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

	private void startSubmitTask() {
		if (regBiz != null)
			regBiz.cancel();
		String pwdmd5=Tools.getMD5Str(findpwd_pwd_ET.getText()).toUpperCase(Locale.US);
		regBiz = new FindPwdBiz(getApplicationContext(), new FindpwdHandler(
				FindPWDActivity_InputNewPWD.this), YtApplication.getInstance()
				.getFindpwdPhone(), ACCOUTTYPE, pwdmd5);

		regBiz.execute();

	}

	private static class FindpwdHandler extends YtHandler {
		private final WeakReference<FindPWDActivity_InputNewPWD> activity;

		public FindpwdHandler(FindPWDActivity_InputNewPWD activity) {
			this.activity = new WeakReference<FindPWDActivity_InputNewPWD>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(FindPWDActivity_InputNewPWD.class,
					"[找回密码页面-输入新密码handler]:msg.what:", msg.what);
			 FindPWDActivity_InputNewPWD ac = activity.get();
			if (ac != null) {
				
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					ac.loadingoverlay.setVisibility(View.INVISIBLE);
					Intent intent1 = new Intent(ac.getApplication(),
							FindPWDActivity_Succ.class);

					ac.startActivity(intent1);
					ac.overridePendingTransition(R.anim.enter_righttoleft,
							R.anim.exit_righttoleft);
					ac.finish();
					break;
				case ThreadCommStateCode.COMMON_FAILED:
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.loadingoverlay.setVisibility(View.INVISIBLE);

					ac.findpwd_pwd_ET.setText(GlobleConstants.EMPTY_STR);
					ac.findpwd_pwd1_ET.setText(GlobleConstants.EMPTY_STR);
					break;

				default:
					break;
				}
			}
		}
	}

}
