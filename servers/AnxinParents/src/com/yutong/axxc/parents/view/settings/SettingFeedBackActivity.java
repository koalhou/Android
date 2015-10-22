package com.yutong.axxc.parents.view.settings;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.settings.AdviseBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.GlobleConstants;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.MainActivity;

/**
 * 意见反馈
 * 
 * @author zhangyongn
 */
public class SettingFeedBackActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	private EditText ev_feedBack_content;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private YtAsyncTask biz;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_feedback);
		initViews();
		initListener();

		// 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0023);
	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {

						if (biz != null)
							biz.cancel();
						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

	}

	private void initViews() {
		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		ev_feedBack_content = (EditText) findViewById(R.id.inputET);

		tv_top_title.setText(R.string.feedback);
		btn_title_right.setText(R.string.submittext);

		ev_feedBack_content
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
						300) });

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			String content = StringUtil.parseStr(ev_feedBack_content.getText());
			if (content == null || content.length() == 0) {
				Toast.makeText(this.getApplicationContext(), "请您输入意见反馈内容!",
						Toast.LENGTH_SHORT).show();
				break;
			}

			loadingoverlay.setVisibility(View.VISIBLE);
			loadingoverlay.setLoadingTip("正在提交服务器,请稍候...");

			startTask(content);

			break;
		case R.id.btn_title_left:
			finish();
			break;
		default:
			break;
		}

	}

	private void startTask(String content) {
		if (biz != null)
			biz.cancel();
		biz = new AdviseBiz(getApplicationContext(), new ProcessHandler(this),
				content);

		biz.execute();

	}

	private static class ProcessHandler extends YtHandler {
		private final WeakReference<SettingFeedBackActivity> weakActivity;

		public ProcessHandler(SettingFeedBackActivity activity) {
			this.weakActivity = new WeakReference<SettingFeedBackActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(SettingFeedBackActivity.class,
					"[提交意见反馈-handler]:msg.what:", msg.what);
			SettingFeedBackActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"提交意见反馈成功！", Toast.LENGTH_SHORT).show();
					activity.finish();
					break;
				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"提交意见反馈失败！请重试！", Toast.LENGTH_SHORT).show();
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
