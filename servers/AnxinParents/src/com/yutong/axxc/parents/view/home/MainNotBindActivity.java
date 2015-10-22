package com.yutong.axxc.parents.view.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.CacheUtils;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.business.messagepush.PushNotificationUtil;
import com.yutong.axxc.parents.business.news.NewsSummaryInfoBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.NewsInfoBean;
import com.yutong.axxc.parents.common.constant.NewsTypeConstant;
import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost.OnMessageReceiveListener;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ActivityManager;
import com.yutong.axxc.parents.view.common.CustomAlertDialog;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.settings.SettingAboutActivity;
import com.yutong.axxc.parents.view.settings.SettingHomeActivity;
import com.yutong.axxc.parents.view.settings.news.NewsDetailActivity;
import com.yutong.axxc.parents.view.settings.news.NewsHomeActivity;

public class MainNotBindActivity extends YtAbstractActivity implements
		OnClickListener {

	private Button btn_title_left;
	private TextView tv_top_title;
	private TextView title_left_popTV;
	private TextView tv_top_date;
	private Button btn_title_right;
	private Button bindbtn;
	
	private CustomAlertDialog menudia = null;
	private OnMessageReceiveListener msgListener;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private static Boolean isExit = false;
	private static Boolean hasTask = false;
	Timer tExit = new Timer();
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			isExit = false;
			hasTask = true;
		}
	};

	/* 新闻 */
	private NewsSummaryInfoBiz biz;
	List<NewsInfoBean> newsInfoBeans;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_notbind);

		initViews();
		initListener();
		startTask();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PushMessageListenHost host = YtApplication.getInstance().getPushHost();
		if (host != null) {
			host.removeOnMessageReceiveListener(msgListener);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		updateNewsCount();
		newsInfoBeans = CacheUtils.getNews(this);
		loadNews();
		PushMessageListenHost host = YtApplication.getInstance().getPushHost();
		if (host != null) {

			host.addOnMessageReceiveListener(msgListener);
		}
	}

	private void updateNewsCount() {
		int newscount = CommonPushMsgUtil.getNewsNotificationCount();
		if (newscount > 0) {
			title_left_popTV.setText(newscount + "");
			title_left_popTV.setVisibility(View.VISIBLE);
		} else {
			title_left_popTV.setVisibility(View.INVISIBLE);
		}

	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		bindbtn.setOnClickListener(this);
		// loadingoverlay
		// .addOnCancelListener(new LoadingOverlay.OnCancelListener() {
		//
		// @Override
		// public void onCancel() {
		//
		// loadingoverlay.setVisibility(View.INVISIBLE);
		//
		// }
		// });

		
		msgListener = new OnMessageReceiveListener() {

			@Override
			public void OnReceivedReminds(CommonPushMsg message) {
				
			}

			@Override
			public void OnReceivedNews(CommonPushMsg message) {
				updateNewsCount();

			}

			@Override
			public void OnReceived(CommonPushMsg message) {

			}
		};
	}

	private Calendar calendar = Calendar.getInstance();

	public void setMonthView(Calendar calendar2) {
		this.calendar = calendar2;
		this.showDate();
	}

	private void showDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
				Locale.CHINESE);
		String dateStr = dateFormat.format(calendar.getTime());

		tv_top_date.setText(dateStr);

	}

	private void initViews() {
		// loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_date = (TextView) findViewById(R.id.tv_top_date);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);
		bindbtn = (Button) findViewById(R.id.bindbtn);
		title_left_popTV = (TextView) findViewById(R.id.title_left_popTV);
		btn_title_left.setBackgroundResource(R.drawable.setting_button);
		btn_title_right.setBackgroundResource(R.drawable.anxin_button);

		ListView list = (ListView) findViewById(R.id.lv_amn_news);
		String date = Tools.getFormatTime(Calendar.getInstance(), "yyyy-MM-dd");
		tv_top_date.setText(date);
		title_left_popTV.setVisibility(View.INVISIBLE);
		// 生成动态数组，加入数据
		listItem = new ArrayList<HashMap<String, Object>>();

		listItemAdapter = new SimpleAdapter(
				this,// this是当前Activity的对象
				listItem,// 数据源 为填充数据后的ArrayList类型的对象
				R.layout.newlistview_item,// 子项的布局.xml文件名
				new String[] { "ItemImage", "ItemTitle", "ItemText", "ItemSign" },
				// 这个String数组中的元素就是list对象中的列，list中有几这个数组中就要写几列。
				new int[] { R.id.NewItemImage, R.id.NewItemTitle,
						R.id.NewItemDesc, R.id.NewItemContent });// 值是对应XML布局文件中的一个ImageView,三个TextView的id
		// 添加并显示
		list.setAdapter(listItemAdapter);

		// 添加点击
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			// 重写单击响应
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// setTitle("点击第" + arg2 + "个项目");//直接在标题显示
				Intent intent = new Intent(getApplication(),
						NewsDetailActivity.class);

				Bundle bundle = new Bundle();

				bundle.putSerializable(ActivityCommConstant.NEWS_INFO,
						newsInfoBeans.get(arg2));

				intent.putExtras(bundle);

				startActivity(intent);
				overridePendingTransition(R.anim.enter_righttoleft,
						R.anim.exit_righttoleft);
			}
		});

	}

	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	 menu.add("menu");// 必须创建一项
	 return super.onCreateOptionsMenu(menu);
	 }

	@Override
	public void onBackPressed() {
		// 此处返回按键表示回到桌面，如果不加，则程序会退出。
		Intent MyIntent = new Intent(Intent.ACTION_MAIN);
		MyIntent.addCategory(Intent.CATEGORY_HOME);
		startActivity(MyIntent);

	}


	 @Override
	 public boolean onMenuOpened(int featureId, Menu menu) {
		return false;
	 }

	ArrayList<HashMap<String, Object>> listItem;

	SimpleAdapter listItemAdapter;

	private static final int LOADING_CODE_1 = 0X1001;

	@Override
	protected void onLoadingCanceled(int key) {
		switch (key) {
		case LOADING_CODE_1:
			if (biz != null && !biz.isCanceled())
				biz.cancel();
			break;

		default:
			break;
		}
		super.onLoadingCanceled(key);
	}

	private void startTask() {

		if (biz != null && !biz.isCanceled())
			biz.cancel();
		// loadingoverlay.setVisibility(View.VISIBLE);
		// loadingoverlay.setLoadingTip("正在执行,请稍候...");
		showLoading("加载中...", LOADING_CODE_1);
		biz = new NewsSummaryInfoBiz(getApplicationContext(),
				new ProcessHandler(), NewsTypeConstant.ALL_TYPE);
		biz.execute();

	}

	private class ProcessHandler extends YtHandler {

		@SuppressLint("HandlerLeak")
		public ProcessHandler() {
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			Logger.i(NewsHomeActivity.class, "[获取新闻概要-handler]:msg.what:",
					msg.what);
			super.handleMessage(msg);
			switch (msg.what) {
			case ThreadCommStateCode.COMMON_SUCCESS:
				dismissLoading();
				// Toast.makeText(activity.getApplicationContext(),
				// "获取新闻概要成功！", Toast.LENGTH_SHORT).show();
				newsInfoBeans = (List<NewsInfoBean>) msg.obj;

				loadNews();
				break;

			case ThreadCommStateCode.COMMON_SEARCH_RES_COUNT:
				dismissLoading();

				break;
			case ThreadCommStateCode.COMMON_SEARCH_NO_RES:
				dismissLoading();

				break;
			case ThreadCommStateCode.TOKEN_INVALID:
				dismissLoading();
				ToastMessage("Token失效，请重新登录！");
				break;

			case ThreadCommStateCode.COMMON_FAILED:
				dismissLoading();
				ToastMessage("获取新闻概要失败！请重试！");
				break;

			default:
				dismissLoading();
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	}

	private void loadNews() {

		// TODO 获取新闻数据
		if (newsInfoBeans != null) {
			listItem.clear();
			for (NewsInfoBean news : newsInfoBeans) {
				HashMap<String, Object> map = new HashMap<String, Object>();

				if (!news.getIs_read()) {
					map.put("ItemImage", R.drawable.news_new);// 图像资源图片，显示在小项右端
				}

				map.put("ItemTitle", news.getNews_title());
				map.put("ItemText", Tools.getFormatTime(news.getNews_time(),
						"yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss"));
				map.put("ItemSign", news.getNews_summary());
				listItem.add(map);// 添加到listItem中
			}
		}

		// for (int i = 0; i < 10; i++) {
		// // HashMap为键值对类型。第一个参数为键，第二个参数为值
		// HashMap<String, Object> map = new HashMap<String, Object>();
		// map.put("ItemImage", R.drawable.news_new);// 图像资源图片，显示在小项右端
		// map.put("ItemTitle", "河南信阳一辆6座校车载了28...");
		// map.put("ItemText", "2013-09-02");
		// map.put("ItemSign", "此处可显示正文或者文件路径,此处可显示正文或者文件路径,此处可显示正文或者文件路径  ");
		// listItem.add(map);// 添加到listItem中
		// }
		listItemAdapter.notifyDataSetChanged();
	}

	/**
	 * 处理键盘事件
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		case KeyEvent.KEYCODE_MENU:
			if (menudia == null) {
				menudia = new CustomAlertDialog(this) {

					@Override
					public void onUserOK() {
						Exit();
					}

					@Override
					public void onUserCancel() {
					}
				};
				menudia.setTitle("如果退出，您将无法再收到消息和提醒");
				menudia.setCancelStr("取消");
				menudia.setOkStr("退出");
				menudia.show();
			} else {
				if (menudia.isShowing()) {
					menudia.dismiss();
				} else {
					menudia.show();
				}
			}

			break;
		default:
			break;
		}

		return false;
	}

	private void Exit() {
		PushNotificationUtil.clearAllNotification();
		// 注销推送服务
		YtApplication.getInstance().unRegisterPushReceiver();
		YtApplication.getInstance().unRegisterNetworkReceiver();

		// UserBehaviorUploadBiz.startUserBehaviorUploadTask(null, null,
		// UserBehaviorConstants.MODULE_ID_BASE,
		// UserBehaviorConstants.MODULE_SON_ID_LOGOUT);
		Intent MyIntent = new Intent(Intent.ACTION_MAIN);
		MyIntent.addCategory(Intent.CATEGORY_HOME);
		startActivity(MyIntent);

		ActivityManager.exitApp();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_right:
			Intent intent = new Intent(getApplication(),
					SettingAboutActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.enter_righttoleft,
					R.anim.exit_righttoleft);
			break;
		case R.id.btn_title_left:
			Intent intent1 = new Intent(getApplication(),
					SettingHomeActivity.class);

			startActivity(intent1);
			break;
		case R.id.bindbtn:
			Intent intent2 = new Intent(getApplication(),
					BindChildActivity.class);

			startActivity(intent2);
			break;
		default:
			break;
		}

	}

}
