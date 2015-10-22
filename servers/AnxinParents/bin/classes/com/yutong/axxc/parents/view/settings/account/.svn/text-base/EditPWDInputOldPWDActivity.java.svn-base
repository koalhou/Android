package com.yutong.axxc.parents.view.settings.account;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.settings.OldPasswordCheckBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “更多”设置主页面
 * 
 * @author zhangyongn
 */
public class EditPWDInputOldPWDActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private YtAsyncTask biz;

	private ExEditText evOldPassword_EX;

	private Bundle bundleObj = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account_editpwd_inputoldpwd);
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

						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

	}

	private void initViews() {
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		evOldPassword_EX = (ExEditText) findViewById(R.id.evOldPassword_EX);

		EditText pwdtxt = evOldPassword_EX.getEditText();
		pwdtxt.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);

		btn_title_right.setText(R.string.homeCanceYes);
		tv_top_title.setText(R.string.update_password);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			String oldPwd = StringUtil.parseStr(evOldPassword_EX.getText());
			if (oldPwd == null || oldPwd.length() == 0) {
				Toast.makeText(this.getApplicationContext(), "请您输入旧密码!",
						Toast.LENGTH_SHORT).show();
				break;
			}

			String md5OldPwd = Tools.getMD5Str(oldPwd);
			bundleObj.putString("md5OldPwd", md5OldPwd);

			startTask(md5OldPwd);

			break;
		case R.id.btn_title_left:

			finish();
			break;
		default:
			break;
		}

	}

	private void startTask(String md5Pwd) {
		if (biz != null)
			biz.cancel();
		biz = new OldPasswordCheckBiz(getApplicationContext(),
				new ProcessHandler(EditPWDInputOldPWDActivity.this), md5Pwd);

		biz.execute();

	}

	private static class ProcessHandler extends YtHandler {
		private final WeakReference<EditPWDInputOldPWDActivity> weakActivity;

		public ProcessHandler(EditPWDInputOldPWDActivity activity) {
			this.weakActivity = new WeakReference<EditPWDInputOldPWDActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(EditPWDInputOldPWDActivity.class,
					"[验证旧密码-handler]:msg.what:", msg.what);
			EditPWDInputOldPWDActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.OLD_PWD_CHECK_SUCCESS:

					// 成功后跳转到输入新密码页面。
					activity.loadingoverlay.setVisibility(View.INVISIBLE);

					Intent intent = new Intent(activity.getApplication(),
							EditPWDInputNewPWDActivity.class);

					intent.putExtras(activity.bundleObj);

					activity.startActivityForResult(intent, 1);

					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"验证旧密码失败！请重试！", Toast.LENGTH_SHORT).show();
					break;

				default:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}
}
