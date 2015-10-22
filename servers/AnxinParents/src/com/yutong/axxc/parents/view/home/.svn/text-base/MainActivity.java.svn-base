package com.yutong.axxc.parents.view.home;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.common.CommonPushMsgUtil;
import com.yutong.axxc.parents.business.messagepush.PushNotificationUtil;
import com.yutong.axxc.parents.business.other.WeatherInfoSearchBiz;
import com.yutong.axxc.parents.business.student.GetStudentInfoBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NetworkCheckUitl;
import com.yutong.axxc.parents.common.OnNetworkChangeListener;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.common.beans.WeatherInfoBean;
import com.yutong.axxc.parents.common.context.StringUtil;
import com.yutong.axxc.parents.connect.push.CommonPushMsg;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost;
import com.yutong.axxc.parents.connect.push.PushMessageListenHost.OnMessageReceiveListener;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.ActivityManager;
import com.yutong.axxc.parents.view.common.ChildCustomConfigs;
import com.yutong.axxc.parents.view.common.CustomAlertDialog;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar.OnBarItemClickListener;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar.OnBarItemLongClickListener;
import com.yutong.axxc.parents.view.common.UserGridAdapter;
import com.yutong.axxc.parents.view.common.UserGridAdapter.UserGridInfo;
import com.yutong.axxc.parents.view.common.UserGridExchangeListener;
import com.yutong.axxc.parents.view.common.WeatherIconUtil;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.settings.SettingAboutActivity;
import com.yutong.axxc.parents.view.settings.SettingHomeActivity;
import com.yutong.axxc.parents.view.settings.child.RecordPageView;
import com.yutong.axxc.parents.view.settings.child.SettingChildHomeActivity;

public class MainActivity extends YtAbstractActivity implements OnClickListener {
	private FrameLayout title_leftFL;
	private TextView title_left_popTV;
	private OnMessageReceiveListener msgListener;

	private OnNetworkChangeListener networkListener;
	private RelativeLayout networkRL;

	/* 天气预报 */
	private YtAsyncTask weatherbiz;
	private TextView weatherTV;
	private ImageView weatherIV;

	/* 当前时间 */
	private Calendar calendar = Calendar.getInstance();
	private Calendar minCal = null;

	/* 小孩相关 */
	private YtAsyncTask childbiz;
	private List<StudentInfoBean> students;
	private TextView headerstudentTV;

	SingleSelectChildrenBar mhv_users;
	UserGridAdapter uadapter;
	List<UserGridInfo> userdata;
	private StudentInfoBean currentChild = null;

	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	/* 消息相关 */
	private ViewPager viewPager = null;
	private TimeLineViewPagerAdapter pagerAdapter;
	private List<TimeLineListPagerView> viewList = new ArrayList<TimeLineListPagerView>();
	private boolean init = true;

	private Button btn_title_left;
	private TextView tv_top_title;
	private TextView tv_top_date;
	private Button btn_title_right;
	private Button btn_map;
	private RelativeLayout headerRL;
	private CustomAlertDialog menudia = null;
	private View titleLL;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initMinCal();

		initViews();
		initListener();

		initChildInfo();

		initNetworkTip();

		// 用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
		UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0007);

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
		YtApplication.getInstance().removeOnNetworkChangeListener(
				networkListener);
	}

	private void initNetworkTip() {
		// 初始化网络提示
		boolean isconn = NetworkCheckUitl.isOnline();
		networkRL.setVisibility(isconn ? View.GONE : View.VISIBLE);
	}

	@Override
	protected void onResume() {

		super.onResume();
		// startWeatherTask();
		Logger.i(this.getClass(),
				"main.....................................resume");
		if (viewPager != null)
			pagerAdapter.start();
		reloadCacheChildren();
		updateNewsCount();
		updateChildMsg();
		PushMessageListenHost host = YtApplication.getInstance().getPushHost();
		if (host != null) {

			host.addOnMessageReceiveListener(msgListener);
		}

	}

	private void reloadCacheChildren() {
		students = AppCacheData.getStudentInfos(YtApplication.getInstance()
				.getUid());
		if (students != null) {
			loadChildBar(students);
			onChildChanged(getCurrentChild());
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Logger.i(this.getClass(),
				"main.....................................pause");
		if (viewPager != null)
			pagerAdapter.stop();
		PushMessageListenHost host = YtApplication.getInstance().getPushHost();
		if (host != null) {
			host.removeOnMessageReceiveListener(msgListener);
		}
	}

	private void startWeatherTask() {

		if (weatherbiz != null)
			weatherbiz.cancel();
		if (this.getCurrentChild() == null) {
			Logger.e(getClass(), "首页获取天气信息：学生为null.");
			return;
		}
		weatherbiz = new WeatherInfoSearchBiz(getApplicationContext(),
				new WeatherHandler(MainActivity.this), this.getCurrentChild()
						.getCld_id());

		weatherbiz.execute();

	}

	private void initWeatherUI() {
		weatherTV.setText("获取天气..");

	}

	/**
	 * 设置最小时间选择边界
	 */
	private void initMinCal() {
		setMinCal(Calendar.getInstance());
		getMinCal().set(Calendar.YEAR, 1900);
		getMinCal().set(Calendar.MONTH, 0);
		getMinCal().set(Calendar.DATE, 1);

	}

	private void initChildInfo() {
		loadingoverlay.setVisibility(View.VISIBLE);
		loadingoverlay.setLoadingTip("正在加载学生信息,请稍候...");
		startLoadStudentTask();
	}

	private void startLoadStudentTask() {
		if (childbiz != null)
			childbiz.cancel();
		childbiz = new GetStudentInfoBiz(getApplicationContext(),
				new StudentHandler(MainActivity.this));

		childbiz.execute();

	}

	private void initListener() {
		gdetector = new GestureDetector(new OnGestureListener() {

			@Override
			public boolean onDown(MotionEvent e) {
				return canTouchFling;
			}

			@Override
			public void onLongPress(MotionEvent e) {
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {

			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				return false;
			}

			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				float deltax = e2.getX() - e1.getX();
				float deltay = e2.getY() - e1.getY();
				if (Math.abs(deltay) > Math.abs(deltax)) {
					if (deltay < -60) {
						hideHeadRLAnimate();
						// headerRL.setVisibility(View.GONE);
						// iv_fold.setVisibility(View.VISIBLE);
					} else if (deltay > 40) {
						showHeadRLAnimate();
						// headerRL.setVisibility(View.VISIBLE);
						// iv_fold.setVisibility(View.GONE);
					}
				}
				return true;
			}
		});
		networkRL.setOnClickListener(this);
		networkListener = new OnNetworkChangeListener() {

			@Override
			public void NetworkChanged(boolean isConnected) {
				initNetworkTip();
				if (isConnected) {
					if (viewPager != null)
						pagerAdapter.start();
					updateNewsCount();
					updateChildMsg();
				} else {
					if (viewPager != null)
						pagerAdapter.stop();

				}
				// Toast.makeText(
				// YtApplication.getInstance().getApplicationContext(),
				// "网络状态变化：" + isConnected, Toast.LENGTH_SHORT).show();
			}
		};
		YtApplication.getInstance().addOnNetworkChangeListener(networkListener);
		msgListener = new OnMessageReceiveListener() {

			@Override
			public void OnReceivedReminds(CommonPushMsg message) {
				onChildNewMsg(message.getCld_id());
			}

			@Override
			public void OnReceivedNews(CommonPushMsg message) {
				updateNewsCount();

			}

			@Override
			public void OnReceived(CommonPushMsg message) {

			}
		};

		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		btn_map.setOnClickListener(this);

		loadingoverlay
				.addOnCancelListener(new LoadingOverlay.OnCancelListener() {

					@Override
					public void onCancel() {
						if (childbiz != null)
							childbiz.cancel();
						loadingoverlay.setVisibility(View.INVISIBLE);

					}
				});

		headerRL.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gdetector.onTouchEvent(event);
			}
		});

		titleLL.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gdetector.onTouchEvent(event);
			}
		});
	}

	/**
	 * 更新学生消息
	 */
	public void updateChildMsg() {
		if (this.students == null)
			return;
		for (int i = 0; i < students.size(); i++) {
			onChildNewMsg(students.get(i).getCld_id());
		}
	}

	protected void onChildNewMsg(String childId) {
		try {
			if (childId == null)
				return;
			UserGridInfo info = getuserInChildBar(childId);
			int count = CommonPushMsgUtil
					.getChildRidingNotificationCount(childId);
			if (info != null) {
				info.Message = (count <= 0) ? "" : count + "";
				uadapter.notifyDataSetChanged();
			}
			// StudentInfoBean currentcld = this.getCurrentChild();
			// if (currentcld != null
			// && StringUtil.equals(currentcld.getCld_id(), childId)) {
			// onChildChanged(currentcld);
			// }

		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}

	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public void setMonthView(Calendar calendar2) {
		this.setCalendar(calendar2);
		this.showDate();
	}

	private void initViews() {

		networkRL = (RelativeLayout) findViewById(R.id.networkRL);
		headerRL = (RelativeLayout) findViewById(R.id.headerRL);
		title_leftFL = (FrameLayout) findViewById(R.id.title_leftFL);
		title_left_popTV = (TextView) findViewById(R.id.title_left_popTV);

		weatherTV = (TextView) findViewById(R.id.weatherTV);
		weatherIV = (ImageView) findViewById(R.id.weatherIV);
		headerstudentTV = (TextView) findViewById(R.id.headerstudentTV);

		loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		tv_top_date = (TextView) findViewById(R.id.tv_top_date);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setBackgroundResource(R.drawable.anxin_button);
		titleLL = findViewById(R.id.toptitle);
		btn_map = (Button) findViewById(R.id.btn_map);

		title_left_popTV.setVisibility(View.INVISIBLE);
		// tv_top_title.setText(R.string.registerTitle);
		/* 头像列表相关 */
		mhv_users = (SingleSelectChildrenBar) findViewById(R.id.mHScrollView1);

		if (viewPager == null) {
			pagerAdapter = new TimeLineViewPagerAdapter();
			viewPager = (ViewPager) findViewById(R.id.timeline_viewPager);
			viewPager.setAdapter(pagerAdapter);
			viewPager.setOnPageChangeListener(new TimelinePageChangeListener(
					this));

		}
		viewList.clear();
		pagerAdapter.setViews(viewList);
		// pagerAdapter.notifyDataSetChanged();

		weatherIV.setVisibility(View.INVISIBLE);
		initWeatherUI();

	}

	private void loadChildBar(List<StudentInfoBean> studentinfos) {
		prepareuserData(studentinfos);
		uadapter = new UserGridAdapter(this, userdata, mhv_users);
		mhv_users.setOnBarItemClickListener(new OnBarItemClickListener() {

			@Override
			public void onClick(int index) {
				try {
					if (!uadapter.getSelectedIndexs().contains(index))
						return;
					TimeLineListPagerView curView = viewList.get(viewPager
							.getCurrentItem());
					// 如果不是今天，则先跳转到今天
					if (!Tools.isToday(curView.getCurrentCal())) {
						// 先跳转到今天
						for (int i = 0; i < viewList.size(); i++) {
							TimeLineListPagerView v = viewList.get(i);
							if (Tools.isToday(v.getCurrentCal())) {
								viewPager.setCurrentItem(i);
								return;
							}
						}
						//zyong
						setMonthView(Calendar.getInstance());
						
						initPagerView(getCalendar(), currentChild);
					} else {
						Logger.i(getClass(), "点击小孩事件：" + index);
						UserGridInfo firstinfo = userdata.get(index);

						uadapter.notifyDataSetChanged();
						currentChild = getStudentById(firstinfo.ChildId);
						if (currentChild != null) {

							// onChildChanged(currentChild);
							onChildHeaderClick(currentChild);
						}
					}
				} catch (Exception e) {
					Logger.e(getClass(), e.getMessage());
				}

			}
		});

		uadapter.setExChangelistener(new UserGridExchangeListener() {

			@Override
			public void GotoTop(int oldindex) {
				try {
					setMonthView(Calendar.getInstance());
					int newIndex = 0;// 第0个为最新的选中项。
					Logger.i(getClass(), "点击小孩事件：" + newIndex);
					UserGridInfo firstinfo = userdata.get(newIndex);

					uadapter.notifyDataSetChanged();
					currentChild = getStudentById(firstinfo.ChildId);
					if (currentChild != null) {

						 onChildChanged(currentChild);
						//onChildHeaderClick(currentChild);//这里还是用childchanged，切换小孩，重新刷新。
					}
				} catch (Exception e) {
					Logger.e(getClass(), e.getMessage());
				}
			}

			@Override
			public void ExchangeStart(int index1, int index2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void ExchangeEnd(int oldIndex, int newIndex) {

			}
		});
		mhv_users
				.setOnBarItemLongClickListener(new OnBarItemLongClickListener() {
					@Override
					public void onClick(int index) {

						try {

							// 长按跳转到孩子设置界面

							UserGridInfo firstinfo = userdata.get(index);

							uadapter.notifyDataSetChanged();
							StudentInfoBean child = getStudentById(firstinfo.ChildId);

							// 长按跳转到小孩页面
							Intent intent = new Intent(MainActivity.this,
									SettingChildHomeActivity.class);
							Bundle bundle = new Bundle();

							bundle.putSerializable(
									ActivityCommConstant.STUDENT_INFO, child);
							intent.putExtras(bundle);
							startActivity(intent);
						} catch (Exception e) {
							Logger.e(getClass(), e.getMessage());
							Toast.makeText(MainActivity.this, "发生错误！",
									Toast.LENGTH_SHORT).show();
						}

					}
				});
		mhv_users.setGridViewAdapter(uadapter);
		List<Integer> selectedIndexs = uadapter.getSelectedIndexs();

		if (selectedIndexs != null && selectedIndexs.size() > 0)
			this.currentChild = students.get(selectedIndexs.get(0));
	}

	/**
	 * 小孩点击事件，只更新当前viewpage的信息，与onChildChanged不一样的地方是： 该方法不会闪界面。
	 * 
	 * @param currentChild2
	 */
	protected void onChildHeaderClick(StudentInfoBean child) {
		if (viewPager == null)
			return;
		if (child == null) {
			Toast.makeText(this, "无学生信息。", Toast.LENGTH_SHORT).show();
			return;
		}
		headerRL.setBackgroundResource(ChildCustomConfigs.getInstance()
				.getChildCustomThemeByKey(child.getCld_color())
				.getMainBackgroundResId());

		headerstudentTV.setText(child.getFullName());
		startWeatherTask();

		int index = viewPager.getCurrentItem();
		TimeLineListPagerView pageview = viewList.get(index);
		pageview.setChild(child);
		pageview.setCurrentCal(this.getCalendar());
		pageview.OnChildClick();
	}

	protected void onChildChanged(StudentInfoBean child) {
		if (child == null) {
			Toast.makeText(this, "无学生信息。", Toast.LENGTH_SHORT).show();
			return;
		}
		headerRL.setBackgroundResource(ChildCustomConfigs.getInstance()
				.getChildCustomThemeByKey(child.getCld_color())
				.getMainBackgroundResId());

		headerstudentTV.setText(child.getFullName());
		startWeatherTask();
		initPagerView(this.getCalendar(), child);

	}

	private void showDate() {

		String dateStr = Tools.getFormatTime(getCalendar(), "yyyy-MM-dd");
		tv_top_date.setText(dateStr);
		if (!init) {
			init = true;
		}

	}

	public List<TimeLineListPagerView> getViewList() {
		return viewList;
	}

	public TimeLineViewPagerAdapter getPagerAdatper() {
		return pagerAdapter;
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void initPagerView(Calendar calendar, StudentInfoBean chld) {

		pagerAdapter.destroy();

		viewList.clear();
		pagerAdapter.setViews(viewList);
		// 昨天
		Calendar cLast = Calendar.getInstance();
		cLast.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		cLast.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		cLast.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		int size = 0;
		if (Tools.DayBetween(cLast, getMinCal())) {
			TimeLineListPagerView lastview = new TimeLineListPagerView(this,
					cLast, chld);

			pagerAdapter.addView(viewPager, lastview, size);
			size++;
		}
		// 今天
		TimeLineListPagerView curview = new TimeLineListPagerView(this,
				calendar, chld);
		pagerAdapter.addView(viewPager, curview, size);
		size++;

		// 明天
		Calendar cNext = Calendar.getInstance();
		cNext.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		cNext.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		cNext.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		if (Tools.DayBetween(cNext, getMinCal())) {
			TimeLineListPagerView nextView = new TimeLineListPagerView(this,
					cNext, chld);
			pagerAdapter.addView(viewPager, nextView, size);
		}
		viewPager.setCurrentItem(size - 1);
		pagerAdapter.notifyDataSetChanged();

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

	/**
	 * 拦截MENU
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// Logger.i(getClass(), "系统主界面menu菜单被点击，正常应弹出退出菜单");

		return false;
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

	GestureDetector gdetector = null;

	boolean canTouchFling = true;

	private void showHeadRLAnimate() {
		canTouchFling = false;
		headerRL.setVisibility(View.VISIBLE);
		canTouchFling = true;
	}

	private void hideHeadRLAnimate() {
		canTouchFling = false;
		headerRL.setVisibility(View.GONE);
		canTouchFling = true;
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
		case R.id.btn_map:
			Intent intent3 = new Intent(getApplication(), MainMapActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
					this.getCurrentChild());
			intent3.putExtras(bundle);
			startActivity(intent3);
			break;
		case R.id.networkRL:
			startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));// 进入无线网络配置界面
			break;

		default:
			break;
		}

	}

	/**
	 * 设置头像信息
	 */
	private void prepareuserData(List<StudentInfoBean> studentinfos) {
		userdata = new ArrayList<UserGridInfo>();

		if (studentinfos == null || studentinfos.size() <= 0) {
			return;
		}
		for (int i = 0; i < studentinfos.size(); i++) {
			StudentInfoBean b = studentinfos.get(i);
			boolean isboy = b.getCld_sex().equals("0");
			userdata.add(new UserGridInfo(b.getCld_id(), b.getCld_name(),
					getResources().getDrawable(
							isboy ? R.drawable.default_boy
									: R.drawable.default_gril), "", b
							.getCld_photo()));

		}

	}

	/**
	 * @return the minCal
	 */
	public Calendar getMinCal() {
		return minCal;
	}

	/**
	 * @param minCal
	 *            the minCal to set
	 */
	public void setMinCal(Calendar minCal) {
		this.minCal = minCal;
	}

	/**
	 * @return the currentChildId
	 */
	public StudentInfoBean getCurrentChild() {
		return currentChild;
	}

	/**
	 * @param currentChildId
	 *            the currentChildId to set
	 */
	public void setCurrentChild(StudentInfoBean currentChildId) {
		this.currentChild = currentChildId;
	}

	private UserGridInfo getuserInChildBar(String childId) {
		if (childId == null)
			return null;
		if (userdata == null)
			return null;
		UserGridInfo info = null;
		for (int i = 0; i < userdata.size(); i++) {
			info = userdata.get(i);
			if (StringUtil.equals(childId, info.ChildId))
				return info;
		}
		return null;
	}

	private StudentInfoBean getStudentById(String childId) {
		if (this.students == null)
			return null;
		for (int i = 0; i < students.size(); i++) {
			if (StringUtil.equals(childId, students.get(i).getCld_id())) {
				return students.get(i);
			}
		}
		return null;
	}

	/**
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar
	 *            the calendar to set
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * 加载学生Handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class StudentHandler extends YtHandler {
		private final WeakReference<MainActivity> activity;

		public StudentHandler(MainActivity activity) {
			this.activity = new WeakReference<MainActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {

			MainActivity ac = activity.get();
			if (ac != null) {
				ac.loadingoverlay.setVisibility(View.INVISIBLE);
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:

					ac.students = (List<StudentInfoBean>) msg.obj;
					if (ac.students == null || ac.students.size() <= 0) {
						ac.ToastMessage("无小孩信息");
						break;
					}
					ac.loadChildBar(ac.students);
					ac.onChildChanged(ac.getCurrentChild());

					break;
				case ThreadCommStateCode.COMMON_FAILED:
					ac.ToastMessage((String) msg.obj);

					break;

				default:
					break;
				}
			}
		}
	}

	/**
	 * 天气预报Handler
	 * 
	 * @author zhangyongn
	 * 
	 */
	private static class WeatherHandler extends YtHandler {
		private final WeakReference<MainActivity> activity;

		public WeatherHandler(MainActivity activity) {
			this.activity = new WeakReference<MainActivity>(activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {

			MainActivity ac = activity.get();
			if (ac != null) {
				ac.loadingoverlay.setVisibility(View.INVISIBLE);
				super.handleMessage(msg, ac);
				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
				case ThreadCommStateCode.COMMON_SUCCESS:
				case ThreadCommStateCode.HOME_GET_WEATHER_SUCCESS:
					WeatherInfoBean weather = (WeatherInfoBean) msg.obj;
					if (weather != null) {
						ac.weatherIV.setVisibility(View.VISIBLE);

						ac.weatherIV.setImageResource(WeatherIconUtil
								.getWeatherIconID(weather.getTodayImg()));

						String weathertext = getweatherText(weather);
						ac.weatherTV.setText(weathertext);
					} else {
						ac.weatherIV.setImageResource(R.drawable.nothing);
						ac.weatherTV.setText("无气象信息。");
					}
					break;
				case ThreadCommStateCode.COMMON_FAILED:
				case ThreadCommStateCode.HOME_GET_WEATHER_FAIL:
					ac.weatherTV.setText("未获取到天气信息。");

					break;

				default:
					break;
				}
			}
		}

		private String getweatherText(WeatherInfoBean weather) {
			if (weather == null || weather.getCity() == null
					|| weather.getTodayDesc() == null
					|| weather.getTodayTemper() == null
					|| weather.getTodayWind() == null) {
				return "无气象信息。";
			}
			String city = weather.getCity();

			String desc = weather.getTodayDesc();
			String temp = weather.getTodayTemper();
			String pm = weather.getTodayPM();
			String wind = weather.getTodayWind();
			// return String.format("%s/%s\n%s/%s", city, desc, temp, wind);
			return String.format("%s/%s\n%s", city, desc, temp);
		}

	}
}
