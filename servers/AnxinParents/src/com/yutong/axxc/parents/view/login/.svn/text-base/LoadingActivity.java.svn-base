package com.yutong.axxc.parents.view.login;

import java.lang.ref.WeakReference;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.behaviour.MobileEnvUploadBiz;
import com.yutong.axxc.parents.business.login.AutoLoginBiz;
import com.yutong.axxc.parents.business.login.AutoLoginChkBiz;
import com.yutong.axxc.parents.business.login.UserInfoClearBiz;
import com.yutong.axxc.parents.business.login.VersionUpgradeBiz;
import com.yutong.axxc.parents.business.view.FlashUILoadingBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.UserInfoBean;
import com.yutong.axxc.parents.common.beans.VersionInfoBean;
import com.yutong.axxc.parents.common.context.ContextUtil;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.FlowDialog;
import com.yutong.axxc.parents.view.common.VersionUpdateManager.VersionCheckStatus;
import com.yutong.axxc.parents.view.common.VersionUpdateManager.VersionUpdateListener;
import com.yutong.axxc.parents.view.common.VersionUpdateManager.VersionUpdateStatus;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.home.MainActivity;
import com.yutong.axxc.parents.view.home.MainNotBindActivity;

public class LoadingActivity extends YtAbstractActivity {
	boolean isFirstIn = false;

	/** 版本升级业务逻辑类 */
	private VersionUpgradeBiz versionUpgradeBiz;

	/** 版本更新布局 */
	private RelativeLayout versionUpgradeRL;
	private TextView tvDownloadrate;

	private VersionUpdateListener vlistener;
//	FlowDialog needUpdatedia = null;
//	FlowDialog forceUpdatedia = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_loading);
		versionUpgradeRL = (RelativeLayout) findViewById(R.id.versionUpgradeRL);
		tvDownloadrate = (TextView) findViewById(R.id.downloadrate);
		initialListeners();
		startLoadBaseDataTask();

	}

	private void initialListeners() {
		vlistener = new VersionUpdateListener() {
			@Override
			public void onUpdateStatusChanged(VersionUpdateStatus status,
					int percent) {
				switch (status) {
				case Canceled:
					ToastMessage("停止版本更新");//更新取消，按成功处理。
				case Success:
					versionUpgradeRL.setVisibility(View.GONE);
					tvDownloadrate.setText(percent + "%");
					startAutoLoginTask();
					break;
				case Failed:
					ToastMessage("版本更新失败");
					final Dialog dialog = new Dialog(LoadingActivity.this) {

						@Override
						public void onBackPressed() {
							super.onBackPressed();
							LoadingActivity.this.finish();
						}

					};
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.dialog_temp);
					TextView titleTx = (TextView) dialog
							.findViewById(R.id.titleTx);
					TextView messageTx = (TextView) dialog
							.findViewById(R.id.messageTx);
					Button firstBt = (Button) dialog.findViewById(R.id.firstBt);
					Button secondBt = (Button) dialog
							.findViewById(R.id.secondBt);
					titleTx.setText("错误");
					messageTx.setText("网络未正确连接，是否重新尝试？");
					firstBt.setText("取消");
					secondBt.setText("重试");
					firstBt.setOnClickListener(new Button.OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.onBackPressed();
						}
					});
					secondBt.setOnClickListener(new Button.OnClickListener() {

						@Override
						public void onClick(View v) {
							dialog.dismiss();
							YtApplication.getInstance()
									.getVersionUpdateManager().CheckVersion();
						}
					});

					if (!isFinishing()) {
						dialog.show();
					}
					startAutoLoginChkTask();
					break;
				case Updating:
					versionUpgradeRL.setVisibility(View.VISIBLE);
					tvDownloadrate.setText(percent + "%");
					break;

				default:
					break;
				}
			}

			@Override
			public void getCheckStatusChanged(VersionCheckStatus status,
					final VersionInfoBean versioninfo) {
				switch (status) {
				case Failed:
					ToastMessage("获取版本信息失败");
					startAutoLoginChkTask();
					break;
				case Success_ForceUpdate:

					final FlowDialog forceUpdatedia = new FlowDialog(LoadingActivity.this) {
						@Override
						public void onOK() {
							super.onOK();
							YtApplication.getInstance()
									.getVersionUpdateManager()
									.UpdateNewVersion(versioninfo);
							// 确认立即更新，开始下载程序文件进行更新
							versionUpgradeRL.setVisibility(View.VISIBLE);
						}

						@Override
						public void onCancel() {
							super.onCancel();
							// 取消更新，直接退出
							finish();
						}
					};
					forceUpdatedia.setTitle("安芯家长版已有新的更新");
					forceUpdatedia.setOkstr("立即更新");
					forceUpdatedia.setCancelstr("以后再说");
					forceUpdatedia.show();
					break;
				case Success_NeedUpdate:
					final FlowDialog needUpdatedia = new FlowDialog(LoadingActivity.this) {
						@Override
						public void onOK() {
							super.onOK();
							// 确认立即更新，开始下载程序文件进行更新
							YtApplication.getInstance()
									.getVersionUpdateManager()
									.UpdateNewVersion(versioninfo);
							// 确认立即更新，开始下载程序文件进行更新
							versionUpgradeRL.setVisibility(View.VISIBLE);
						}

						@Override
						public void onCancel() {
							super.onCancel();
							// 取消更新，直接退出
							startAutoLoginChkTask();
						}
					};
					needUpdatedia.setTitle("安芯家长版已有新的更新");
					needUpdatedia.setOkstr("立即更新");
					needUpdatedia.setCancelstr("跳过更新");
					needUpdatedia.show();
					break;
				case Success_NoNeedUpdate:
					startAutoLoginChkTask();
					break;
				default:
					break;
				}
			}
		};

	}

	private void startLoadBaseDataTask() {

		FlashUILoadingBiz biz = new FlashUILoadingBiz(getApplicationContext(),
				new LoadDataHandler(LoadingActivity.this));

		biz.execute();

	}

	/**
	 * 检查自动更新
	 * 
	 * @author zhangzhia 2013-9-15 下午3:31:52
	 * 
	 */
	private void startVersionChkTask() {

		YtApplication.getInstance().getVersionUpdateManager().CheckVersion();
	}

	/**
	 * 检查自动登录
	 */
	private void startAutoLoginChkTask() {
		AutoLoginChkBiz autologin = new AutoLoginChkBiz(
				this.getApplicationContext(), new AutoLoginChkHandler(this));
		autologin.execute();
	}

	private void startAutoLoginTask() {
		AutoLoginBiz autologin = new AutoLoginBiz(this.getApplicationContext(),
				new AutoLoginHandler(this));
		autologin.execute();
	}

	@Override
	protected void onResume() {
		if (vlistener != null)
			YtApplication.getInstance().getVersionUpdateManager()
					.addVersionUpdateListener(vlistener);
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (vlistener != null)
			YtApplication.getInstance().getVersionUpdateManager()
					.removeVersionUpdateListener(vlistener);
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	private static class LoadDataHandler extends YtHandler {
		private final WeakReference<LoadingActivity> activity;

		LoadDataHandler(LoadingActivity activity) {
			this.activity = new WeakReference<LoadingActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(LoadingActivity.class, "[加载页面-LoadDataHandler]:msg.what:",
					msg.what);
			LoadingActivity ac = activity.get();
			if (ac != null) {

				ac.startVersionChkTask();
			}
		}
	}

	private static class AutoLoginChkHandler extends YtHandler {
		private static final String IS_FIRST_USE_SYS = "is_first_use_sys";

		private final WeakReference<LoadingActivity> activity;

		AutoLoginChkHandler(LoadingActivity activity) {
			this.activity = new WeakReference<LoadingActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			Logger.i(LoadingActivity.class, "[加载页面-LoadingHandler]:msg.what:",
					msg.what);
			LoadingActivity ac = activity.get();
			if (ac != null) {

				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.LOGIN_NO_USER_INFO:
					// TODO 显示引导页
					// 判断是否为第一次使用系统
					if (ContextUtil.getPreference(ac,
							ActivityCommConstant.PREFS_NAME, MODE_PRIVATE,
							IS_FIRST_USE_SYS, true)) {
						Intent intent = new Intent(ac.getApplication(),
								GuideActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtra(ActivityCommConstant.GUID_RETURN_TYPE,
								ActivityCommConstant.GUID_GOTO_LOGIN);
						ac.getApplication().startActivity(intent);
						ContextUtil.setPreferences(ac,
								ActivityCommConstant.PREFS_NAME, MODE_PRIVATE,
								IS_FIRST_USE_SYS, false);
						ac.finish();
					} else {
						Intent intent = new Intent(ac.getApplication(),
								LoginActivity.class);
						ac.startActivity(intent);
						ac.finish();
					}
					break;
				case ThreadCommStateCode.LOGIN_EXIST_USER_INFO:
					ac.startAutoLoginTask();
					break;
				default:
					break;
				}
			}
		}
	}

	private static class AutoLoginHandler extends YtHandler {
		private final WeakReference<LoadingActivity> activity;

		AutoLoginHandler(LoadingActivity activity) {
			this.activity = new WeakReference<LoadingActivity>(activity);
		}

		private UserInfoBean userInfoBean = null;

		@Override
		public void handleMessage(Message msg) {
			Logger.i(LoadingActivity.class,
					"[加载页面-AutoLoginHandler]:msg.what:", msg.what);
			final LoadingActivity ac = activity.get();
			if (ac != null) {
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.AUTO_LOGIN_SUCCESS:
					userInfoBean = (UserInfoBean) msg.obj;

					// 上传手机环境信息
					(new MobileEnvUploadBiz(ac, null)).execute();

					Intent intent1 = null;
					if (userInfoBean == null) {
						ac.startActivity(new Intent(ac.getApplication(),
								LoginActivity.class));
						Toast.makeText(ac.getApplicationContext(),
								(String) msg.obj, Toast.LENGTH_SHORT).show();

						ac.finish();
						break;
					}
					// zyong test
					// userInfoBean.setPhone_bind(UserInfoBean.PHONEBIND);
					// userInfoBean.setPhone_bind(UserInfoBean.PHONENOTBIND);

					if (userInfoBean.getPhone_bind() == null
							|| userInfoBean.getPhone_bind().equals(
									UserInfoBean.PHONENOTBIND)) {
						intent1 = new Intent(ac.getApplication(),
								MainNotBindActivity.class);
					} else {
						intent1 = new Intent(ac.getApplication(),
								MainActivity.class);
					}

					// 注册推送服务
					YtApplication.getInstance().registerPushReceiver();

					YtApplication.getInstance().registerNetworkReceiver();

					YtApplication.getInstance().setUserInfoCache(userInfoBean);
					intent1.putExtra(ActivityCommConstant.USER_INFO,
							userInfoBean);
					ac.startActivity(intent1);
					ac.finish();
					break;
				case ThreadCommStateCode.COMMON_FAILED:

					new UserInfoClearBiz(ac.getApplication()).execute();// 清除登录用户信息
					ac.startActivity(new Intent(ac.getApplication(),
							LoginActivity.class));
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.finish();
					break;

				case ThreadCommStateCode.NETWORK_ERROR:

					ac.startActivity(new Intent(ac.getApplication(),
							LoginActivity.class));
					Toast.makeText(ac.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					ac.finish();

					break;
				default:
					break;

				}
			}
		}
	}
}
