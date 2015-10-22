package com.yutong.clw.ygbclient.view.pages.splash;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.beans.VersionInfo;
import com.yutong.clw.ygbclient.common.utils.SysInfoGetUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizDataTypeEnum;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;
import com.yutong.clw.ygbclient.view.bizAccess.splash.BizSplash;
import com.yutong.clw.ygbclient.view.common.ActivityCommConstant;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionCheckStatus;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionUpdateListener;
import com.yutong.clw.ygbclient.view.common.VersionUpdateManager.VersionUpdateStatus;
import com.yutong.clw.ygbclient.view.pages.BaseActivity;
import com.yutong.clw.ygbclient.view.pages.login.LoginActivity;
import com.yutong.clw.ygbclient.view.pages.login.PhoneBindActivity;
import com.yutong.clw.ygbclient.view.pages.main.MainActivity;
import com.yutong.clw.ygbclient.view.pages.setting.userguide.UserGuideActivity;
import com.yutong.clw.ygbclient.view.util.ActivityUtils;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;

/**
 * 闪屏界面
 * 
 * @author zhouzc
 */
public class SplashActivity extends BaseActivity {
	private VersionUpdateListener vlistener;

	/** 版本更新布局 */
	private RelativeLayout versionUpgradeRL;

	private TextView tvDownloadrate;

	private TextView tv_version;

	private BizSplash biz = new BizSplash(this);

	private long createTime;

	private long minDelayTime = 5500;
	
	private long delay=0;
	
	TimerTask task = new TimerTask(){  
		public void run(){  
			playRemindMedia();
		}  
	};  
	Timer timer = new Timer();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		createTime = Calendar.getInstance(Locale.getDefault())
				.getTimeInMillis();
		initViews();
		initialListeners();
	}
	
	@Override
	protected void onResume() {
		if (vlistener != null) {
			YtApplication.getInstance().getVersionUpdateManager()
					.addVersionUpdateListener(vlistener);
		}
		
		if(!YtApplication.getInstance().getVersionUpdateManager().isVersionUpdating())
			checkVersion();
		
		playRemindMedia();
		
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
		// TODO Auto-generated method stub
		super.onBackPressed();
		YtApplication.getInstance().exit();
	}
	
	private MediaPlayer mediaPlayer;
	
	protected void playRemindMedia() {
		
		Logger.i(getClass(), "开始播放闪屏提醒音乐");
		
		AudioManager mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		
		final int current = mAudioManager
		.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前音量
		
		if (mediaPlayer == null) {
			mediaPlayer = MediaPlayer.create(this, R.raw.splash);
			mediaPlayer.setLooping(false);
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					if (mediaPlayer != null) {
						mediaPlayer.release();
						mediaPlayer = null;
					}
					((AudioManager) getSystemService(Context.AUDIO_SERVICE))
							.setStreamVolume(AudioManager.STREAM_MUSIC,
									current, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
				}
			});
		}
		
		int volume = 0;
		
		int vibrate_type_notification  = mAudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_NOTIFICATION);
		int vibrate_type_ringer  = mAudioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER);
		
		boolean vibrateMode = vibrate_type_notification == AudioManager.VIBRATE_SETTING_ON;
		
		boolean ringerMode_Normal = mAudioManager.getRingerMode()== AudioManager.RINGER_MODE_NORMAL ? true:false;
		
		/*if( vibrateMode||ringerMode){*/
		if(ringerMode_Normal){
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 5,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}else {
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume,
					AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		}
		
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}
	
	private void initialListeners() {
		vlistener = new VersionUpdateListener() {
			@Override
			public void onUpdateStatusChanged(final VersionUpdateStatus status,
					final int percent) {
				
				runOnUiThread(new Runnable() {
					
					public void run() {
						switch (status) {
						case Canceled:
							ToastMessage("停止版本更新");// 更新取消，按成功处理。
						case Success:
							versionUpgradeRL.setVisibility(View.GONE);
							tvDownloadrate.setText(percent + "%");
							autoLogin();
							break;
						case Failed:
							ToastMessage("版本更新失败");
							{
								CustomDialog dia = new CustomDialog.Builder(
										SplashActivity.this)
										.setTitle("错误")
										.setNegativeButton(
												"取消",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														dialog.dismiss();
														finish();
													}
												})
										.setPositiveButton(
												"重试",
												new DialogInterface.OnClickListener() {
													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														dialog.dismiss();
														YtApplication
																.getInstance()
																.getVersionUpdateManager()
																.checkVersion();
													}
												})
										.setMessage("网络未正确连接，是否重新尝试？").create();
								dia.setOnCancelListener(new OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {
										dialog.dismiss();
										finish();

									}
								});

								dia.show();
							}

							break;
						case Updating:
							versionUpgradeRL.setVisibility(View.VISIBLE);
							tvDownloadrate.setText(percent + "%");
							break;

						default:
							break;
						}
					}
				});

			}

			@Override
			public void getCheckStatusChanged(final VersionCheckStatus status,
					final VersionInfo versioninfo) {
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						switch (status) {
						case Failed:
							ToastMessage("获取版本信息失败");
							autoLogin();
							break;
						case Success_ForceUpdate: {
							CustomDialog dia = new CustomDialog.Builder(
									SplashActivity.this)
									.setTitle(
											"检测到新版本"
													+ versioninfo.version_name
													+ "")
									.setNegativeButton(
											"以后再说",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();
													finish();
												}
											})
									.setPositiveButton(
											"立即更新",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();
													// 确认立即更新，开始下载程序文件进行更新
													YtApplication
															.getInstance()
															.getVersionUpdateManager()
															.UpdateNewVersion(
																	versioninfo);
													// // 确认立即更新，开始下载程序文件进行更新
													versionUpgradeRL
															.setVisibility(View.VISIBLE);
												}
											}).setMessage("是否要更新？").create();
							dia.setOnCancelListener(new OnCancelListener() {

								@Override
								public void onCancel(DialogInterface dialog) {
									dialog.dismiss();
									finish();

								}
							});

							dia.show();
						}
							break;
						case Success_NeedUpdate:

							CustomDialog dia = new CustomDialog.Builder(
									SplashActivity.this)
									.setTitle(
											"检测到新版本"
													+ versioninfo.version_name
													+ "")
									.setNegativeButton(
											"跳过更新",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();
													autoLogin();
												}
											})
									.setPositiveButton(
											"立即更新",
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(
														DialogInterface dialog,
														int which) {
													dialog.dismiss();
													// 确认立即更新，开始下载程序文件进行更新
													YtApplication
															.getInstance()
															.getVersionUpdateManager()
															.UpdateNewVersion(
																	versioninfo);
													// // 确认立即更新，开始下载程序文件进行更新
													versionUpgradeRL
															.setVisibility(View.VISIBLE);
												}
											}).setMessage("是否要更新？").create();
							dia.setOnCancelListener(new OnCancelListener() {

								@Override
								public void onCancel(DialogInterface dialog) {
									dialog.dismiss();
									autoLogin();

								}
							});

							dia.show();

							break;
						case Success_NoNeedUpdate:
							autoLogin();
							break;
						default:
							break;
						}

					}
				});

			}

		};

	}

	private void autoLogin() {
		boolean isAutologin = biz.isAutoLogin();
		Logger.i(getClass(), "[能否进行远程登录：]"+isAutologin);
		if (isAutologin) {
			doAutoLogin();
		} else {
			// 判断是否为第一次使用系统
			if (PrefDataUtil.getIsFirstLogin(this)) {

				Bundle bundle = new Bundle();
				bundle.putString(ActivityCommConstant.GUID_RETURN_TYPE,
						ActivityCommConstant.GUID_GOTO_LOGIN);
				ActivityUtils.changeActivity(SplashActivity.this,
						UserGuideActivity.class, bundle);

				PrefDataUtil.setIsFirstLogin(this, false);

				finish();
			} else {
				long delay = Calendar.getInstance(Locale.getDefault())
						.getTimeInMillis() - createTime-minDelayTime;
				if (delay < 0)
					delay = 0;
				delayRun(new Runnable() {
					@Override
					public void run() {
						gotoLoginActivity();
					}
				}, 0);
			}

		}
	}

	private void doAutoLogin() {
		biz.AutoLogin(new BizResultProcess<UserInfo>() {

			@Override
			public void onBizExecuteError(Exception exception, Error error) {
				final Exception ex = exception;
				runOnUiThread(new Runnable() {
					public void run() {
						Logger.e(getClass(), ex);
						gotoLoginActivity();

					}
				});

			}

			@Override
			public void onBizExecuteEnd(BizDataTypeEnum datatype, UserInfo t) {
				final UserInfo user = t;
				runOnUiThread(new Runnable() {
					public void run() {

						if (user == null) {

							gotoLoginActivity();
							return;
						}

						biz.splashInit(new BizResultProcess<String>() {
							@Override
							public void onBizExecuteError(Exception exception,
									Error error) {

								runOnUiThread(new Runnable() {
									public void run() {
										afterlogin();
									}
								});

							}

							@Override
							public void onBizExecuteEnd(
									BizDataTypeEnum datatype, String t) {
								runOnUiThread(new Runnable() {
									public void run() {
										afterlogin();
									}
								});
							}
						});

					}

					private void afterlogin() {
						YtApplication.getInstance().addDatas(
								AppDataKeyConstant.KEY_USER, user);
						YtApplication.getInstance().onLoginSuccess();
						long delay = Calendar.getInstance(Locale.getDefault())
								.getTimeInMillis() - createTime-minDelayTime;
						if (delay < 0)
							delay = 0;
						
						delayRun(new Runnable() {
							@Override
							public void run() {
								if (user.BindPhone) {
									ActivityUtils.changeActivity(
											SplashActivity.this,
											MainActivity.class);
								} else {
									ActivityUtils.changeActivity(
											SplashActivity.this,
											PhoneBindActivity.class);
								}
								SplashActivity.this.finish();
							}
						}, 0);

					}
				});

			}
		});

	}

	protected void gotoLoginActivity() {
		ActivityUtils.changeActivity(SplashActivity.this, LoginActivity.class);
		this.finish();
	}

	public void initViews() {
		tv_version = (TextView) this.findViewById(R.id.tv_version);
		versionUpgradeRL = (RelativeLayout) findViewById(R.id.versionUpgradeRL);
		tvDownloadrate = (TextView) findViewById(R.id.downloadrate);
		String version = SysInfoGetUtil.getPackageInfo(this);
		tv_version.setText(version);
	}

	private void checkVersion() {
		YtApplication.getInstance().getVersionUpdateManager().checkVersion();

	}

}
