package com.yutong.axxc.parents.view.settings;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.common.SysInfoGetUtil;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NetworkCheckUitl;
import com.yutong.axxc.parents.common.beans.VersionInfoBean;
import com.yutong.axxc.parents.view.common.ActivityUtils;
import com.yutong.axxc.parents.view.common.VersionUpdateManager.VersionCheckStatus;
import com.yutong.axxc.parents.view.common.VersionUpdateManager.VersionUpdateListener;
import com.yutong.axxc.parents.view.common.VersionUpdateManager.VersionUpdateStatus;
import com.yutong.axxc.parents.view.common.CustomAlertDialog;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.settings.child.ParentActivity;

/**
 * 关于
 * 
 * @author zhangyongn
 */
public class SettingAboutActivity extends YtAbstractActivity implements
		OnClickListener {

	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	private VersionInfoBean versionInfoBean;

	private VersionUpdateListener vlistener;

	RelativeLayout rl_version, rl_serverlist;
	ImageView iv_new;
	TextView tv_currentversion, tv_newversion;

	boolean hasNewVersion = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_about);
		initViews();
		initListener();

		// 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0024);
	}

	private void initListener() {
		vlistener = new VersionUpdateListener() {
			@Override
			public void onUpdateStatusChanged(VersionUpdateStatus status,
					int percent) {
				switch (status) {
				case Success:
					break;
				case Canceled:
					startVersionChkTask();
					Toast.makeText(SettingAboutActivity.this, "已停止下载", Toast.LENGTH_SHORT).show();
					break;
			case Failed:
					tv_newversion.setText("下载失败...");
					break;
				case Updating:
					tv_newversion.setText("正在下载中(" + percent + ")%");
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
					tv_newversion.setText("获取最新版本信息失败...");
					hasNewVersion = false;
					break;
				case Success_ForceUpdate:
				case Success_NeedUpdate:
					tv_newversion.setText(versioninfo.getTargetVersion());
					iv_new.setVisibility(View.VISIBLE);
					hasNewVersion = true;
					break;
				case Success_NoNeedUpdate:
					tv_newversion.setText("已经是最新版本");
					iv_new.setVisibility(View.INVISIBLE);
					break;
				default:
					break;
				}
			}
		};

		btn_title_left.setOnClickListener(this);

		rl_serverlist.setOnClickListener(this);

		rl_version.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		if (YtApplication.getInstance().getVersionUpdateManager()
				.getLatestVersioninfo() != null) {
			iv_new.setVisibility(View.VISIBLE);
			tv_newversion.setText(YtApplication.getInstance()
					.getVersionUpdateManager().getLatestVersioninfo()
					.getTargetVersion());
			hasNewVersion = true;
		}
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

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setVisibility(View.INVISIBLE);
		tv_top_title.setText(R.string.aboutme);

		rl_version = (RelativeLayout) findViewById(R.id.rl_sa_version);
		rl_serverlist = (RelativeLayout) findViewById(R.id.rl_sa_serverlist);

		tv_currentversion = (TextView) findViewById(R.id.tv_sa_currentversion);
		tv_newversion = (TextView) findViewById(R.id.tv_sa_newversion);

		iv_new = (ImageView) findViewById(R.id.iv_sa_isnew);
		iv_new.setVisibility(View.INVISIBLE);

		tv_newversion.setText("已经是最新版本");

		tv_currentversion.setText(SysInfoGetUtil.getClientInfoBean(this)
				.getApp_version());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:

			break;
		case R.id.btn_title_left:
			finish();
			break;
		case R.id.rl_sa_version:
			if (hasNewVersion) {
				showConfirmDialog();
			} else {
				startVersionChkTask();
			}
			break;
		case R.id.rl_sa_serverlist:
			ActivityUtils.changeActivity(SettingAboutActivity.this,
					ServiceTermsActivity.class, null);
			break;

		default:
			break;
		}

	}

	private void showConfirmDialog() {
		if (YtApplication.getInstance().getVersionUpdateManager()
				.isVersionUpdating()) {
			CustomAlertDialog deletedia = new CustomAlertDialog(
					SettingAboutActivity.this) {
				@Override
				public void onUserOK() {
					Logger.i(this.getClass(), "停止下载新版本...");
					stopVersionUpdate();
				}

				@Override
				public void onUserCancel() {
				}
			};
			deletedia.setTitle("是否停止下载");
			deletedia.setOkStr("停止下载");
			deletedia.setCancelStr("取消");

			deletedia.show();
		} else {
			boolean _3gonline = NetworkCheckUitl.is3GOnline();
			String title = "请确认是否下载？";
			if (_3gonline) {
				title = "当前不是wifi环境，继续下载将会耗费流量，请确认是否下载？";
			}

			CustomAlertDialog deletedia = new CustomAlertDialog(
					SettingAboutActivity.this) {
				@Override
				public void onUserOK() {
					Logger.i(this.getClass(), "开始下载新版本...");
					startVersionUpdateTask();
				}

				@Override
				public void onUserCancel() {
				}
			};
			deletedia.setTitle(title);
			deletedia.setOkStr("开始下载");
			deletedia.setCancelStr("取消");

			deletedia.show();
		}

	}

	protected void stopVersionUpdate() {
		YtApplication.getInstance().getVersionUpdateManager().CancelUpdating();
	}

	private void startVersionChkTask() {

		if (!YtApplication.getInstance().getVersionUpdateManager()
				.isVersionChecking()) {
			YtApplication.getInstance().getVersionUpdateManager()
					.CheckVersion();
		}
		tv_newversion.setText("正在检测新版本...");

	}

	private void startVersionUpdateTask() {
		if (YtApplication.getInstance().getVersionUpdateManager()
				.isVersionUpdating())
			return;
		YtApplication
				.getInstance()
				.getVersionUpdateManager()
				.UpdateNewVersion(
						YtApplication.getInstance().getVersionUpdateManager()
								.getLatestVersioninfo());
		tv_newversion.setText("正在下载中(0%)");
	}

}
