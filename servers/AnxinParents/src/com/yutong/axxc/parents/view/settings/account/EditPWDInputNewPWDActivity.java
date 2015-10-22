package com.yutong.axxc.parents.view.settings.account;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.settings.PasswordUpdateBiz;
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
 * 
 */
public class EditPWDInputNewPWDActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private ExEditText evNewPassword_EX;

	private ExEditText evReNewPassword_EX;

	private YtAsyncTask biz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account_editpwd_inputnewpwd);
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

		btn_title_right.setText(R.string.submittext);
		tv_top_title.setText(R.string.update_password);

		evNewPassword_EX = (ExEditText) findViewById(R.id.evNewPassword_EX);
		evReNewPassword_EX = (ExEditText) findViewById(R.id.evReNewPassword_EX);

		evNewPassword_EX.getEditText().setInputType(
				InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
		evReNewPassword_EX.getEditText().setInputType(
				InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			String evNewPwd = StringUtil.parseStr(evNewPassword_EX.getText());
			String evReNewPwd = StringUtil.parseStr(evReNewPassword_EX
					.getText());

			if (evNewPwd == null || evNewPwd.length() == 0) {
				Toast.makeText(this.getApplicationContext(), "请您输入新密码!",
						Toast.LENGTH_SHORT).show();
				break;
			}

			if (evReNewPwd == null || evReNewPwd.length() == 0) {
				Toast.makeText(this.getApplicationContext(), "请您确认新密码!",
						Toast.LENGTH_SHORT).show();
				break;
			}

			if (!evReNewPwd.equals(evNewPwd)) {
				Toast.makeText(this.getApplicationContext(), "两次密码不一致!",
						Toast.LENGTH_SHORT).show();
				break;
			}

			// 获取数据
			Intent intent = getIntent();
			Bundle b = intent.getExtras();

			startTask(b.getString("md5OldPwd"), Tools.getMD5Str(evReNewPwd));

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	private void startTask(String md5OldPwd, String md5NewPwd) {
		if (biz != null)
			biz.cancel();
		biz = new PasswordUpdateBiz(getApplicationContext(),
				new ProcessHandler(EditPWDInputNewPWDActivity.this), md5OldPwd,
				md5NewPwd);

		biz.execute();

	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<EditPWDInputNewPWDActivity> weakActivity;

		public ProcessHandler(EditPWDInputNewPWDActivity activity) {
			this.weakActivity = new WeakReference<EditPWDInputNewPWDActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(EditPWDInputNewPWDActivity.class,
					"[更新密码-handler]:msg.what:", msg.what);
			EditPWDInputNewPWDActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.PWD_UPDATE_SUCCESS:

					// 成功后跳转到修改该成功页面。
					activity.loadingoverlay.setVisibility(View.INVISIBLE);

					Intent intent = new Intent(activity.getApplication(),
							SettingAccountHomeActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
							| Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ToastMessage("更新密码成功");
					activity.startActivity(intent);
					activity.finish();
					break;
				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"更新密码失败！请重试！", Toast.LENGTH_SHORT).show();
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
