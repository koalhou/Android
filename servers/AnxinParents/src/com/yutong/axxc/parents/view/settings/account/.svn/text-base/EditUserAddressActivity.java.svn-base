package com.yutong.axxc.parents.view.settings.account;

import java.lang.ref.WeakReference;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.settings.UserInfoUpdateBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ExEditText;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;

/**
 * “修改家庭地址
 * 
 * @author zhangzhia
 */
public class EditUserAddressActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private YtAsyncTask biz;

	private ExEditText evUserAddress_EX;

	private UserInfoBean userInfoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_account_editaddress);
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

		evUserAddress_EX = (ExEditText) findViewById(R.id.evUserAddress_EX);

		btn_title_right.setText(R.string.submittext);
		tv_top_title.setText(R.string.update_useraddress);
		
		evUserAddress_EX.setText(YtApplication.getInstance().getUserInfoCache().getUsr_addr());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			String userAddress = StringUtil
					.parseStr(evUserAddress_EX.getText());
			if (userAddress == null || userAddress.length() == 0) {
				Toast.makeText(this.getApplicationContext(), "请您输入家庭住址!",
						Toast.LENGTH_SHORT).show();
				break;
			}

			loadingoverlay.setVisibility(View.VISIBLE);
			loadingoverlay.setLoadingTip("正在执行，请稍等...");

			userInfoBean = YtApplication.getInstance().getUserInfoCache();

			// 测试 start
			if (userInfoBean == null) {
				userInfoBean = new UserInfoBean();
			}
			// 测试 end

			userInfoBean.setUsr_addr(userAddress);
			startTask(userInfoBean);

			break;
		case R.id.btn_title_left:

			finish();
			break;
		default:
			break;
		}

	}

	private void startTask(UserInfoBean userInfoBean) {
		if (biz != null)
			biz.cancel();
		biz = new UserInfoUpdateBiz(getApplicationContext(),
				new ProcessHandler(EditUserAddressActivity.this), userInfoBean);

		biz.execute();

	}

	private static class ProcessHandler extends YtHandler {
		private final WeakReference<EditUserAddressActivity> weakActivity;

		public ProcessHandler(EditUserAddressActivity activity) {
			this.weakActivity = new WeakReference<EditUserAddressActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(EditUserAddressActivity.class,
					"[修改家庭住址-handler]:msg.what:", msg.what);
			EditUserAddressActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				switch (msg.what) {
				case ThreadCommStateCode.COMMON_SUCCESS:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(), "修改地址成功！",
							Toast.LENGTH_SHORT).show();
					if (activity.userInfoBean != null) {
						YtApplication
								.getInstance()
								.getUserInfoCache()
								.setUsr_addr(
										activity.userInfoBean.getUsr_addr());
					}
					
					activity.finish();
					break;

				case ThreadCommStateCode.TOKEN_INVALID:
					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"修改住址失败！请重试！", Toast.LENGTH_SHORT).show();
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
