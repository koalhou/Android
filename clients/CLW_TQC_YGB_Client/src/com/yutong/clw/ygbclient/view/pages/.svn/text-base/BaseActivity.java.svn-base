package com.yutong.clw.ygbclient.view.pages;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.ConnectionListener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.xmpp.client.provider.PersistentConnectionListener;
import com.yutong.axxc.xmpp.client.provider.XmppManager;
import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.OnNetworkChangeListener;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.view.bizAccess.BizThrownableRouter.OnRecieveCommonExceptionListener;
import com.yutong.clw.ygbclient.view.common.ActivityCoverManager.CoverInitialListener;
import com.yutong.clw.ygbclient.view.common.ActivityManager;
import com.yutong.clw.ygbclient.view.common.PushMessageRouter.MessageRouteDirection;
import com.yutong.clw.ygbclient.view.pages.debugtool.DebugToolActivity;
import com.yutong.clw.ygbclient.view.pages.login.LoginActivity;
import com.yutong.clw.ygbclient.view.pages.main.MainActivity;
import com.yutong.clw.ygbclient.view.widget.CustomDialog;
import com.yutong.clw.ygbclient.view.widget.CustomDialog.Builder;
import com.yutong.clw.ygbclient.view.widget.LoadingOverlay;
import com.yutong.clw.ygbclient.view.widget.LoadingOverlay.OnCancelListener;

/**
 * 基础活动父类
 * 
 * @author zhouzc
 */
public abstract class BaseActivity extends Activity {
	/*------------提示信息相关---------------*/
	private RelativeLayout rl_it_tipcontainer;

	private ImageView iv_it_tipicon;

	private TextView tv_it_tiptext;

	private TextView tv_it_tipop;

	private boolean allowNetworkCheck = false;

	/**
	 * 子类继承，用于网络状态改变各自业务处理
	 */
	private OnNetworkChangeListener networkListener;

	/**
	 * 内部侦听
	 */
	private OnNetworkChangeListener innerNetworkListener;

	private InputMethodManager manager = null;

	private LoadingOverlay mloadingoverlay;

	private boolean isloading = false;

	private int currentloadingkey = 0;

	private CustomDialog relogindia = null;

	private SparseArray<OnCancelListener> onCancelListeners;

	private OnRecieveCommonExceptionListener clistener = null;

	private boolean hasShowCoverThisPage = false;

	// 标志位，指示被强制需要销毁，例如在用户被踢出的时候，用于在OnResume的时候判断是否需要执行一些数据加载操作
	private boolean forceToDestory = false;

	/**
	 * 标志位，指示被强制需要销毁，例如在用户被踢出的时候，用于在OnResume的时候判断是否需要执行一些数据加载操作
	 * 
	 * @author zhouzc 2013-11-28 上午9:28:22
	 * @return
	 */
	public boolean isForceToDestory() {
		Logger.i(getClass(), "isForceToDestory():"+forceToDestory);
		return forceToDestory;
	}

	/**
	 * 是否自动加载引导页
	 */
	protected boolean autoShowCover = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		allowNetworkCheck = false;
		super.onCreate(savedInstanceState);
		manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		// 把当前Activity加入到集合，方便后续统一销毁
		WeakReference<BaseActivity> reference = new WeakReference<BaseActivity>(
				this);
		ActivityManager.addActivity(reference);

		// 网络状态
		innerNetworkListener = new OnNetworkChangeListener() {

			@Override
			public void NetworkChanged(boolean isConnected) {
				if (!allowNetworkCheck)
					return;
				updateNetworkTip();
				if (networkListener != null) {
					networkListener.NetworkChanged(isConnected);
				}
			}
		};
		YtApplication.getInstance().addOnNetworkChangeListener(
				innerNetworkListener);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// 必须在start之后加载
		initLoadingView();
	}

	@Override
	protected void onResume() {
		isactive = true;
		YtApplication.getInstance().getPushMessageRouter()
				.setAlarmRouteDriection(MessageRouteDirection.Page);
		super.onResume();
		registerCommExceptionListener();
		if (autoShowCover && !hasShowCoverThisPage) {
			showAutoCover();
		}

	}

	private void showAutoCover() {
		hasShowCoverThisPage = true;
		if (YtApplication.getInstance().getActivityCoverManager()
				.hasCover(this)) {
			if (YtApplication.getInstance().getActivityCoverManager()
					.hasShowCover(this))
				return;
			YtApplication.getInstance().getActivityCoverManager()
					.showCover(this, new CoverInitialListener() {
						@Override
						public void OnInitial(View cover) {
							OnShowCover(cover);
						}
					});

		}
	}

	protected void showCoverByKey(String key) {
		if (YtApplication.getInstance().getActivityCoverManager().hasCover(key)) {
			YtApplication.getInstance().getActivityCoverManager()
					.showCover(this, key, new CoverInitialListener() {
						@Override
						public void OnInitial(View cover) {
							OnShowCover(cover);
						}
					});

		}
	}

	@Override
	protected void onPause() {
		isactive = false;
		if (!ActivityManager.isActive()) {
			YtApplication.getInstance().getPushMessageRouter()
					.setAlarmRouteDriection(MessageRouteDirection.Notification);
		}
		super.onPause();
		unregisterCommExceptionListener();

	}

	protected boolean isExpire = false;

	private void registerCommExceptionListener() {
		Logger.d(getClass(), "添加异常侦听");
		if (clistener == null) {
			clistener = new OnRecieveCommonExceptionListener() {
				@Override
				public void OnRecieve(CommonException exception) {
					Logger.i(getClass(), "页面接收到CommonException status:"
							+ exception.status + ",code:"
							+ exception.error_code + ",des:"
							+ exception.error_des);
					if (exception.status == CommonNetStatus.Token_InValid) {
						isExpire = true;
						
						// 注销信息
						YtApplication.getInstance().onLogout2();
						
						unregisterCommExceptionListener();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								destoryRunningWork();
								if (relogindia == null) {
									relogindia = createAlertDialog();
									relogindia.show();
								} else {
									if (!relogindia.isShowing()) {
										relogindia.show();
									}
								}
								forceToDestory = true;
								Logger.i(getClass(), "页面接收到CommonException: forceToDestory="+forceToDestory);
							}
						});
					}
				}
			};
			clistener.setTag("From " + getClass().toString());
		}
		YtApplication.getInstance().getBizThrownableRouter()
				.addOnRecieveCommonExceptionListener(clistener);
	}

	private void unregisterCommExceptionListener() {
		Logger.d(getClass(), "移除异常侦听");
		if (clistener != null)
			YtApplication.getInstance().getBizThrownableRouter()
					.removeOnRecieveCommonExceptionListener(clistener);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		YtApplication.getInstance().removeOnNetworkChangeListener(
				innerNetworkListener);
	}

	// 销毁正在运行的作业，账户在其他页面登录的时候会调用
	protected void destoryRunningWork() {

	}

	private void updateNetworkTip() {
		// 初始化网络提示
		boolean isconn = NetworkCheckUitl.isOnline();
		if (isconn) {
			hideTip();
			hideOp();
		} else {
			setTipOp("网络设置", new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));// 进入无线网络配置界面
				}

			});

			showTip(getResources().getString(R.string.networktip));
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (isloading) {
				cancelLoading();
				return true;
			}
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		if (hasTitle()) {
			loadTitle();
		}
		loadTip();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 引导页显示的时候会被调用
	 * 
	 * @param coverView
	 *            引导页的View
	 */
	protected void OnShowCover(View coverView) {

	}

	/**
	 * 配置本页是否有标题栏，具体配置请重写 BaseActivity 的{@link #loadTitleByPageSetting
	 * loadTitleByPageSetting(ImageView, ImageView, RelativeLayout, ImageView,
	 * TextView, TextView)} 方法
	 * 
	 * @return
	 */
	protected boolean hasTitle() {
		return false;
	}

	/**
	 * 配置加载标题栏，若要实现此配置请重写 BaseActivity 的 {@link #hasTitle() hasTitle()}
	 * 方法，并返回true
	 * 
	 * @param iv_left
	 *            左边按钮
	 * @param iv_right
	 *            右边按钮
	 * @param rl_center
	 *            中间文本区域
	 * @param iv_tri
	 *            右下角小三角
	 * @param tv_large
	 *            上面的大文本
	 * @param tv_small
	 *            下面的小文本
	 */
	protected void loadTitleByPageSetting(Button btn_left, Button btn_right,
			RelativeLayout rl_center, ImageView iv_tri, TextView tv_large,
			TextView tv_small) {

	}

	/**
	 * 隐藏键盘
	 * 
	 * @author zhangyongn 2013-10-30 下午4:23:17
	 */
	protected void HideSoftKeyboard() {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(this.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 显示键盘
	 * 
	 * @author zhangyongn 2013-10-30 下午4:23:29
	 * @param con
	 *            要显示对应的输入控件
	 */
	protected void ShowSoftKeyboard(View con) {
		((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.showSoftInput(con, 0);
	}

	/**
	 * Toast提示，保证在UI线程执行提示
	 * 
	 * @param msg
	 */
	protected void ToastMessage(String message) {

		final String fmessage = message;
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, fmessage, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	/**
	 * 显示加载中
	 * 
	 * @param msg
	 *            提示信息
	 * @param keycode
	 *            唯一CODE，会在{@link #onLoadingCanceled }中用到
	 */
	protected void showLoading(String msg, int keycode) {
		showLoading(msg, keycode, true);
	}

	protected void showLoading(final String msg, int keycode,
			final boolean cancelable) {
		isloading = true;
		currentloadingkey = keycode;
		final int key = keycode;

		if (onCancelListeners == null)
			onCancelListeners = new SparseArray<OnCancelListener>();
		if (onCancelListeners.indexOfKey(key) <= 0) {
			onCancelListeners.put(key, new OnCancelListener() {
				@Override
				public void onCancel() {
					onLoadingCanceled(key);
				}
			});
		}
		if (mloadingoverlay != null) {
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mloadingoverlay.setVisibility(View.VISIBLE);
					mloadingoverlay.setLoadingTip(msg);
					mloadingoverlay.setCancelable(cancelable);
					mloadingoverlay.bringToFront();
				}
			});
		}
	}

	/**
	 * 隐藏加载中
	 */
	protected void dismissLoading(int key) {
		if (currentloadingkey != key)
			return;
		isloading = false;
		if (mloadingoverlay != null)
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					mloadingoverlay.setVisibility(View.GONE);
				}
			});

	}

	/**
	 * 在加载中进度条取消的时候会调用的函数
	 * 
	 * @param key
	 *            唯一CODE，在{@link #showLoading(String, int)}中的第二个参数设置
	 */
	protected void onLoadingCanceled(int key) {

	}

	/* 下面是私有方法 */
	private void loadTip() {
		try {
			iv_it_tipicon = (ImageView) findViewById(R.id.iv_it_tipicon);
			rl_it_tipcontainer = (RelativeLayout) findViewById(R.id.rl_it_tipcontainer);
			tv_it_tiptext = (TextView) findViewById(R.id.tv_it_tiptext);
			tv_it_tipop = (TextView) findViewById(R.id.tv_it_tipop);
			hideTip();
		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	private void loadTitle() {
		try {
			Button iv_left = (Button) findViewById(R.id.btn_it_left);
			final Button iv_right = (Button) findViewById(R.id.btn_it_right);
			RelativeLayout rl_center = (RelativeLayout) findViewById(R.id.rl_it_center);
			ImageView iv_tri = (ImageView) findViewById(R.id.iv_it_triangle);
			TextView tv_large = (TextView) findViewById(R.id.tv_it_centerup);
			TextView tv_small = (TextView) findViewById(R.id.tv_it_centerdown);
			loadTitleByPageSetting(iv_left, iv_right, rl_center, iv_tri,
					tv_large, tv_small);
			rl_center.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View arg0) {
					Object userobj = YtApplication.getInstance().getDatas(
							AppDataKeyConstant.KEY_USER);
					if (userobj != null && userobj instanceof UserInfo) {
						UserInfo user = (UserInfo) userobj;
						if (user.Code != null && iv_right.isPressed())
							gotoDebugTool();
					}
					return false;
				}
			});
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	private void gotoDebugTool() {
		Intent i = new Intent(this, DebugToolActivity.class);
		// i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		startActivity(i);
	}

	private CustomDialog createAlertDialog() {
		CustomDialog.Builder builder = new Builder(this);
		builder.setTitle("登录提示").setMessage("您的账户已在其他地方登陆")
				.setPositiveButton("重新登录", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(BaseActivity.this,
								LoginActivity.class);
						startActivity(intent);
						ActivityManager.closeOtherActivityExpectLogin();
						isExpire = false;
					}
				}).setNegativeButton("退出", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						YtApplication.getInstance().exit();
					}
				});
		CustomDialog dia = builder.create();
		dia.setCancelable(false);
		return dia;
	}

	private void initLoadingView() {
		if (mloadingoverlay == null) {
			mloadingoverlay = new LoadingOverlay(this);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			this.addContentView(mloadingoverlay, lp);
			if (isloading) {
				mloadingoverlay.setVisibility(View.VISIBLE);
				mloadingoverlay.bringToFront();
			} else {
				mloadingoverlay.setVisibility(View.GONE);
			}

			mloadingoverlay.addOnCancelListener(new OnCancelListener() {
				@Override
				public void onCancel() {
					isloading = false;
					dismissLoading(currentloadingkey);
					if (onCancelListeners != null
							&& onCancelListeners.size() == 0) {
						for (int index = 0; index < onCancelListeners.size(); index++) {
							onCancelListeners.get(
									onCancelListeners.keyAt(index)).onCancel();
						}
					}
				}
			});
		}
	}

	private void cancelLoading() {
		if (mloadingoverlay != null) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mloadingoverlay.setVisibility(View.GONE);
					mloadingoverlay.cancel();
				}
			});

		} else
			isloading = false;
	}

	protected void delayRun(final Runnable run, long delay) {
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(run);
			}
		}, delay);
	}

	private boolean isactive = false;

	public boolean isActive() {
		return isactive;
	}

	/**
	 * 设置提示信息的操作文本字符串
	 * 
	 * @author zhangyongn 2013-11-18 下午3:57:13
	 * @param text
	 */
	public void setTipOp(final String text, final View.OnClickListener listener) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					tv_it_tipop.setText(text);
					tv_it_tipop.setOnClickListener(listener);
					tv_it_tipop.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					Logger.e(getClass(), "设置提示信息的操作文本异常" + e.getMessage());
					e.printStackTrace();
				}

			}
		});

	}

	/**
	 * 隐藏信息提示操作文本
	 * 
	 * @author zhangyongn 2013-11-19 下午3:38:19
	 */
	public void hideOp() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					tv_it_tipop.setVisibility(View.GONE);

				} catch (Exception e) {
					Logger.e(getClass(), " 隐藏信息提示操作文本异常" + e.getMessage());
					Logger.e(getClass(), e.getMessage());
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 显示提示信息
	 * 
	 * @author zhangyongn 2013-11-18 下午1:35:24
	 * @param tiptext
	 *            提示文本
	 */
	public void showTip(String tiptext) {
		showTip(tiptext, R.drawable.ic_speeker);
	}

	/**
	 * 显示提示信息
	 * 
	 * @author zhangyongn 2013-11-18 下午1:35:02
	 * @param tiptext
	 * @param iconResId
	 */
	public void showTip(final String tiptext, final int iconResId) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					rl_it_tipcontainer.setVisibility(View.VISIBLE);
					iv_it_tipicon.setImageResource(iconResId);
					tv_it_tiptext.setText(tiptext);
				} catch (Exception err) {
					Logger.e(getClass(), " 显示提示信息异常" + err.getMessage());
					Logger.e(getClass(), err.getMessage());
					err.printStackTrace();
				}

			}
		});

	}

	/**
	 * 隐藏提示信息
	 * 
	 * @author zhangyongn 2013-11-18 下午1:36:14
	 */
	public void hideTip() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				try {
					if (rl_it_tipcontainer != null)
						rl_it_tipcontainer.setVisibility(View.GONE);
				} catch (Exception err) {
					Logger.e(getClass(), " 隐藏提示信息异常" + err.getMessage());
					err.printStackTrace();
				}

			}
		});

	}

	/**
	 * @return networkListener
	 */
	public OnNetworkChangeListener getNetworkListener() {
		return networkListener;
	}

	/**
	 * @param networkListener
	 *            要设置的 networkListener
	 */
	public void setNetworkListener(OnNetworkChangeListener networkListener) {
		this.networkListener = networkListener;
	}

	/**
	 * @return allowNetworkCheck
	 */
	public boolean isAllowNetworkCheck() {
		return allowNetworkCheck;
	}

	/**
	 * @param allowNetworkCheck
	 *            要设置的 allowNetworkCheck
	 */
	public void setAllowNetworkCheck(boolean allowNetworkCheck) {
		this.allowNetworkCheck = allowNetworkCheck;
		if (allowNetworkCheck) {
			updateNetworkTip();
		} else {
			hideTip();
		}
	}

	/**
	 * 处理业务逻辑错误
	 * 
	 * @author zhangyongn 2013-11-28 上午10:59:08
	 * @param errorinfotype
	 */
	public void HandleLogicErrorInfo(Exception ex) {
		try {
			if (ex instanceof CommonException) {
				CommonException cex = (CommonException) ex;
				switch (cex.status) {
				case Error_Info:
					ToastMessage(cex.errorInfoType.getName());
					break;
				case NetWork_Exception:
					ToastMessage("网络连接异常，请检查您的网络");
					break;
				case NetWork_Not_Stable:
					ToastMessage("网络不稳定，请稍后重试");
					break;
				case LOGIN_FAIL:
					ToastMessage("登录失败，请检查用户名或密码");
					break;
				case Token_InValid:
					ToastMessage("登陆失效，请重新登陆");
					break;
					
				default:
					ToastMessage("操作失败");
					break;
				}

			} else {
				Logger.e(getClass(), "处理业务逻辑错误异常：" + ex.getMessage());
				ToastMessage("操作失败！");
			}

		} catch (Exception e) {
			Logger.e(getClass(), "处理业务逻辑错误异常时发生异常：" + e.getMessage());
			ToastMessage("发生错误!");
		}
	}
}
