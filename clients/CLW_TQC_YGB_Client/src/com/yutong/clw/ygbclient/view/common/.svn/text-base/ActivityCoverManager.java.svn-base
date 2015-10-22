package com.yutong.clw.ygbclient.view.common;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.view.pages.main.inFactory.InFactoryActivity;
import com.yutong.clw.ygbclient.view.pages.main.outOfFactory.OutOfFactoryActivity;

/**
 * 引导覆盖层管理类 实例请统一从 {@link YtApplication#getActivityCoverManager()} 获取
 * 
 * @author zhouzc
 */
public class ActivityCoverManager {

	private static final String TAG = "ActivityCoverManager";

	public ActivityCoverManager() {
		initialConfigs();
	}

	/**
	 * 查看Activity是否有覆盖引导配置
	 * 
	 * @param mactivity
	 * @return
	 */
	public boolean hasCover(Activity mactivity) {
		if (mactivity == null || coverConfigs == null)
			return false;
		if (coverConfigs.containsKey(mactivity.getClass().toString())) {
			return true;
		}
		return false;
	}

	/**
	 * 查看Activity是否有覆盖引导配置
	 * 
	 * @param mactivity
	 * @return
	 */
	public boolean hasCover(String coverkey) {
		if (coverConfigs == null)
			return false;

		return coverConfigs.containsKey(coverkey);
	}

	/**
	 * 查看Activity的覆盖引导是否已经显示过了
	 * 
	 * @param mactivity
	 * @return
	 */
	public boolean hasShowCover(Activity mactivity) {
		if (!hasCover(mactivity))
			return false;
		CoverConfig config = coverConfigs.get(mactivity.getClass().toString());
		if (config == null)
			return false;
		return config.HasShown;
	}

	/**
	 * 查看覆盖引导是否已经显示过了
	 * 
	 * @param key
	 * @return
	 */
	public boolean hasShowCover(String key) {
		if (!hasCover(key))
			return false;
		CoverConfig config = coverConfigs.get(key);
		if (config == null)
			return false;
		return config.HasShown;
	}

	/**
	 * 显示覆盖引导层
	 * 
	 * @param mactivity
	 */
	public void showCover(Activity mactivity) {
		showCover(mactivity, null);
	}

	/**
	 * @author zhouzc 2013-11-28 上午9:24:15
	 * @param host
	 * @param coverkey
	 * @param listener
	 */
	public void showCover(Activity host, final String coverkey,
			CoverInitialListener listener) {
		if (host == null)
			return;
		if (!coverConfigs.containsKey(coverkey)) {
			Log.i(TAG, "本页[" + coverkey + "]没有配置覆盖层");
			return;
		}
		currentCoverKey = coverkey;
		PrefDataUtil.setCoverHasShown(host, coverkey, true);
		CoverConfig config = coverConfigs.get(coverkey);
		config.HasShown = true;
		// 这里是使用Dialog覆盖的模式
		Dialog dia = null;
		View mv = null;
		// PopupWindow pop=new PopupWindow(host);
		// pop.setContentView(contentView)
		if (config.CoverDialog == null) {
			dia = new Dialog(host, R.style.cover_transparent);
			dia.setCancelable(false);
			mv = View.inflate(host, config.CoverLayout, null);
			dia.setContentView(mv);
			// dia.setOwnerActivity(host);
			config.CoverDialog = dia;

			WindowManager.LayoutParams wparams = dia.getWindow()
					.getAttributes();
			wparams.dimAmount = 0f;// dialog背景不变暗
			dia.getWindow().setAttributes(wparams);
			dia.getWindow().setFormat(PixelFormat.RGBA_8888);
			dia.setOnDismissListener(new OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					if (coverkey.equals(currentCoverKey))
						currentCoverKey = null;
				}
			});
		} else {
			dia = config.CoverDialog;
			mv = dia.findViewById(android.R.id.content);
		}
		if (config.InitialListener != null)
			config.InitialListener.OnInitial(mv);
		if (listener != null)
			listener.OnInitial(mv);

		if (host != null) {
			dia.show();
		}

	}

	private String currentCoverKey = null;

	public boolean isShowingCover() {
		return null != currentCoverKey;
	}

	public boolean isShowingCover(String key) {
		if (key == null)
			return false;
		return key.equals(currentCoverKey);
	}

	/**
	 * 显示覆盖引导层
	 * 
	 * @param mactivity
	 * @param listener
	 */
	public void showCover(final Activity mactivity, final CoverInitialListener listener) {
		mactivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showCover(mactivity, mactivity.getClass().toString(), listener);
			}
		});
		
	}

	/**
	 * 隐藏覆盖引导层
	 * 
	 * @param mactivity
	 */
	public void dismissCover(Activity mactivity) {
		dismissCover(mactivity.getClass().toString());
	}

	/**
	 * 隐藏覆盖引导层
	 * 
	 * @param mactivityName
	 *            类名
	 */
	public void dismissCover(String mactivityName) {
		if (!coverConfigs.containsKey(mactivityName)) {
			Log.e(TAG, "本页没有配置覆盖层");
			return;
		}
		if (coverConfigs.get(mactivityName).CoverDialog != null) {
			coverConfigs.get(mactivityName).CoverDialog.dismiss();
			coverConfigs.get(mactivityName).CoverDialog = null;
		}
	}

	private void initialConfigs() {

		coverConfigs = new HashMap<String, ActivityCoverManager.CoverConfig>();

		final String Key_in = InFactoryActivity.class.toString();
		final CoverConfig mainconfig_inFactory = new CoverConfig(

		Key_in, R.layout.cover_main, new CoverInitialListener() {
			@Override
			public void OnInitial(View cover) {

				PrefDataUtil.setCoverHasShown(YtApplication.getInstance(),
						Key_in, true);

				final ImageView iv1 = (ImageView) cover
						.findViewById(R.id.imageView1);
				final ImageView iv2 = (ImageView) cover
						.findViewById(R.id.imageView2);
				iv1.post(new Runnable() {
					@Override
					public void run() {
						iv1.setVisibility(View.VISIBLE);
					}
				});
				iv2.post(new Runnable() {
					@Override
					public void run() {
						iv2.setVisibility(View.INVISIBLE);
					}
				});
				cover.setOnClickListener(new OnClickListener() {
					int count = 1;

					@Override
					public void onClick(View arg0) {
						// if(count==1){
						// dismissCover(Key_in);
						// return;
						// }
						if (count <= 0)
							dismissCover(Key_in);
						switch (count) {
						case 1:
							iv2.post(new Runnable() {
								@Override
								public void run() {
									iv2.setVisibility(View.VISIBLE);
								}
							});
							break;
						default:
							break;
						}
						count--;
					}
				});

			}
		});
		mainconfig_inFactory.HasShown = PrefDataUtil.getCoverHasShown(
				YtApplication.getInstance(), Key_in);

		final String Key_out = OutOfFactoryActivity.class.toString()
				+ "recommend";
		CoverConfig mainconfig_outFctory = new CoverConfig(Key_out,
				R.layout.cover_outfactory_guide, new CoverInitialListener() {
					@Override
					public void OnInitial(View cover) {

						PrefDataUtil.setCoverHasShown(
								YtApplication.getInstance(), Key_out, true);

						final ImageView iv1 = (ImageView) cover
								.findViewById(R.id.imageView1);
						final ImageView iv2 = (ImageView) cover
								.findViewById(R.id.imageView2);
						final ImageView iv3 = (ImageView) cover
								.findViewById(R.id.imageView3);

						iv1.setVisibility(View.VISIBLE);
						iv2.setVisibility(View.INVISIBLE);
						iv3.setVisibility(View.INVISIBLE);

						cover.setOnClickListener(new OnClickListener() {
							int count = 2;

							@Override
							public void onClick(View arg0) {
								if (count <= 0)
									dismissCover(Key_out);
								switch (count) {
								case 1:
									iv3.post(new Runnable() {
										@Override
										public void run() {
											iv3.setVisibility(View.VISIBLE);
										}
									});
									// iv3.setVisibility(View.VISIBLE);
									break;
								case 2:
									iv2.post(new Runnable() {
										@Override
										public void run() {
											iv2.setVisibility(View.VISIBLE);
										}
									});
									// iv2.setVisibility(View.VISIBLE);
									break;

								default:
									break;
								}
								count--;
							}
						});

					}
				});

		mainconfig_outFctory.HasShown = PrefDataUtil.getCoverHasShown(
				YtApplication.getInstance(), Key_out);

		coverConfigs.put(Key_in, mainconfig_inFactory);
		coverConfigs.put(Key_out, mainconfig_outFctory);
	}

	private HashMap<String, CoverConfig> coverConfigs;

	/**
	 * @author zhouzc
	 */
	public static interface CoverInitialListener {
		public void OnInitial(View cover);
	}

	/**
	 * 引导层配置
	 * 
	 * @author zhouzc
	 */
	public class CoverConfig {
		public CoverConfig(String cname, int rsid, CoverInitialListener listener) {
			ActivityName = cname;
			CoverLayout = rsid;
			HasShown = false;
			InitialListener = listener;
		}

		/**
		 * Activity类名 -Activity.class.toString()
		 */
		public String ActivityName;

		/**
		 * 引导层布局
		 */
		public int CoverLayout;

		/**
		 * 是否已经显示过
		 */
		public boolean HasShown;

		/**
		 * 引导层
		 */
		public Dialog CoverDialog;

		/**
		 * 引导层初始化侦听器
		 */
		public CoverInitialListener InitialListener;
	}
}
