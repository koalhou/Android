package com.yutong.axxc.parents.view.settings.child;

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

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.student.SetStudentCustomInfoBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.StudentCustomInfoBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

public class ChildAliasChangeActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private ExEditText et_name;
	private StudentInfoBean studentInfo;

	private SetStudentCustomInfoBiz studentCustomBiz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_nameset);
		initViews();
		initListener();

		loadStudentinfo();
	}

	private void loadStudentinfo() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		if (studentInfo == null)
			return;
		// TODO 显示名称，看是否是这个字段
		et_name.setText(studentInfo.getCld_alias());
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);

		et_name = (ExEditText) findViewById(R.id.et_scn_name);
		et_name.getEditText().setInputType(InputType.TYPE_CLASS_TEXT);
		et_name.getEditText().setFilters(
				new InputFilter[] { new InputFilter.LengthFilter(10) });

	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setText(getResources().getString(R.string.submittext));
		// tv_top_title.setText(R.string.registerTitle);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	private void submitNameChange() {
		// TODO 修改小孩昵称
		studentInfo.setCld_alias(et_name.getText().toString());
		startTask(studentInfo.getCustonInfo());

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			submitNameChange();
			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	private void startTask(StudentCustomInfoBean studentCustomInfoBean) {
		if (studentCustomBiz != null)
			studentCustomBiz.cancel();
		studentCustomBiz = new SetStudentCustomInfoBiz(getApplicationContext(),
				new ProcessHandler(ChildAliasChangeActivity.this),
				studentInfo.getCld_id(), studentCustomInfoBean);

		studentCustomBiz.execute();

	}

	private class ProcessHandler extends YtHandler {
		private final WeakReference<ChildAliasChangeActivity> weakActivity;

		public ProcessHandler(ChildAliasChangeActivity activity) {
			this.weakActivity = new WeakReference<ChildAliasChangeActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(ChildAliasChangeActivity.class, "[修改-handler]:msg.what:",
					msg.what);
			ChildAliasChangeActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:
					dismissLoading();
					// ToastMessage("修改成功！");

					AppCacheData.putStudentInfo(YtApplication.getInstance()
							.getUid(), activity.studentInfo);

					finish();

					// activity.loadColorSetting();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					dismissLoading();
					ToastMessage("Token失效，请重新登录！");
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					dismissLoading();
					ToastMessage((String) msg.obj);

					break;

				default:
					dismissLoading();
					ToastMessage((String) msg.obj);
					break;
				}
			}
		}
	}

}
