package com.yutong.axxc.parents.view.common;

import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.view.home.MainActivity;

/**
 * 引导覆盖层管理类
 * 
 * @author zhouzc
 * 
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
	 * 显示覆盖引导层
	 * 
	 * @param mactivity
	 */
	public void showCover(Activity mactivity) {
		showCover(mactivity, null);
	}

	/**
	 * 显示覆盖引导层
	 * 
	 * @param mactivity
	 * @param listener
	 */
	public void showCover(Activity mactivity, CoverInitialListener listener) {
		if (mactivity == null)
			return;
		if (!coverConfigs.containsKey(mactivity.getClass().toString())) {
			Log.e(TAG, "本页[" + mactivity.getClass().toString() + "]没有配置覆盖层");
			return;
		}
		CoverConfig config = coverConfigs.get(mactivity.getClass().toString());
		// 这里是使用Dialog覆盖的模式
		Dialog dia = null;
		View mv = null;

		if (config.CoverDialog == null) {
			dia = new Dialog(mactivity, R.style.mTransparent);
			dia.setCancelable(false);
			mv = View.inflate(mactivity, config.CoverLayout, null);
			dia.setContentView(mv);
			dia.setOwnerActivity(mactivity);
			config.CoverDialog = dia;

			WindowManager.LayoutParams wparams = dia.getWindow()
					.getAttributes();
			wparams.dimAmount = 0f;// dialog背景不变暗
			dia.getWindow().setAttributes(wparams);

		} else {
			dia = config.CoverDialog;
			mv = dia.findViewById(android.R.id.content);
		}
		if (config.InitialListener != null)
			config.InitialListener.OnInitial(mv);
		if (listener != null)
			listener.OnInitial(mv);

		if (mactivity != null && !mactivity.isFinishing())
			dia.show();
		dia.getWindow().setFormat(PixelFormat.RGBA_8888);

		// 下面是使用添加到View内部的模式
		/*
		 * View root = getRootView(mactivity); int sid = 0x111121; if (root
		 * instanceof RelativeLayout) { if
		 * (!coverConfigs.containsKey(mactivity.getClass().toString())) {
		 * Log.d(TAG, "本页[" + mactivity + "]没有配置覆盖层"); // .show(); return; }
		 * config.HasShown = true;
		 * 
		 * if (root.findViewById(sid) != null) {
		 * root.findViewById(sid).setVisibility(View.VISIBLE); return; }
		 * root.setPadding(0, 0, 0, 0); LayoutParams rlp = new
		 * LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		 * final View mv = View.inflate(mactivity, config.CoverLayout, null);
		 * mv.setId(sid); if (config.InitialListener != null)
		 * config.InitialListener.OnInitial(mv); if (listener != null) {
		 * listener.OnInitial(mv); }
		 * 
		 * ((RelativeLayout) root).addView(mv, rlp); } else { Log.d(TAG,
		 * "本页结构不支持覆盖层"); }
		 */

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
			coverConfigs.get(mactivityName).CoverDialog=null;
		}
	}

	// private View getRootView(Activity context) {
	// // return context.findViewById(android.R.id.content);
	// return ((ViewGroup) context.findViewById(android.R.id.content))
	// .getChildAt(0);
	// }

	private void initialConfigs() {
		coverConfigs = new HashMap<String, ActivityCoverManager.CoverConfig>();

		CoverConfig mainconfig = new CoverConfig(MainActivity.class.toString(),
				R.layout.cover_main, new CoverInitialListener() {
					@Override
					public void OnInitial(View cover) {
						//TODO 演示暂时屏蔽，现在每次退出后再进来还有引导
						/*SharedPreferencesUtil.set(
								SharedPreferencesUtil.PREFS_NAME_FLAG,
								"flag_main_cover_hasshown", "true");*/
						final ImageView iv1 = (ImageView) cover
								.findViewById(R.id.imageView1);
						final ImageView iv2 = (ImageView) cover
								.findViewById(R.id.imageView2);
						final ImageView iv3 = (ImageView) cover
								.findViewById(R.id.imageView3);
						final ImageView iv4 = (ImageView) cover
								.findViewById(R.id.imageView4);
						iv1.setVisibility(View.VISIBLE);
						iv2.setVisibility(View.INVISIBLE);
						iv3.setVisibility(View.INVISIBLE);
						iv4.setVisibility(View.INVISIBLE);
						cover.setOnClickListener(new OnClickListener() {
							int count = 3;

							@Override
							public void onClick(View arg0) {
								if (count <= 0)
									dismissCover(MainActivity.class.toString());
								switch (count) {
								case 1:
									iv4.setVisibility(View.VISIBLE);
									break;
								case 2:
									iv3.setVisibility(View.VISIBLE);
									break;
								case 3:
									iv2.setVisibility(View.VISIBLE);
									break;
								default:
									break;
								}
								count--;
							}
						});

					}
				});

		mainconfig.HasShown = SharedPreferencesUtil.get(
				SharedPreferencesUtil.PREFS_NAME_FLAG,
				"flag_main_cover_hasshown", "false").equals("true");

		coverConfigs.put(MainActivity.class.toString(), mainconfig);

		// TODO 配置
		// coverConfigs.clear();
	}

	private HashMap<String, CoverConfig> coverConfigs;

	/**
	 * 
	 * @author zhouzc
	 * 
	 */
	public static interface CoverInitialListener {
		public void OnInitial(View cover);
	}

	/**
	 * 引导层配置
	 * 
	 * @author zhouzc
	 * 
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
