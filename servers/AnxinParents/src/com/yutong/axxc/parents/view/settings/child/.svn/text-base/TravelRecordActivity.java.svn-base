package com.yutong.axxc.parents.view.settings.child;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.cache.AppCacheData;
import com.yutong.axxc.parents.business.student.GetStudentPunchCardDateBiz;
import com.yutong.axxc.parents.business.view.UploadLogBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ReadMethodEnum;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.CustomerDatePickerDialog;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar;
import com.yutong.axxc.parents.view.common.SingleSelectChildrenBar.OnBarItemClickListener;
import com.yutong.axxc.parents.view.common.UserGridAdapter;
import com.yutong.axxc.parents.view.common.UserGridAdapter.UserGridInfo;
import com.yutong.axxc.parents.view.common.UserBehaviorConstants;
import com.yutong.axxc.parents.view.common.UserGridSelectItemListener;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.common.YtHandler;
import com.yutong.axxc.parents.view.settings.account.SettingAccountHomeActivity;

/**
 * 乘车记录日历页面。
 * 
 * @author zhangyongn
 */
public class TravelRecordActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;

	private TextView tv_top_title;

	private Button btn_title_right;

	/* zhouzechen add */
	UserGridAdapter uadapter;

	YtAsyncTask biz = null;

	List<UserGridInfo> userdata;

	/**
	 * 当前选择月所有打卡日期
	 */
	List<String> punchCardDates = null;

	SingleSelectChildrenBar mhv_users;

	GridView gv;

	List<GridInfo> gdata;

	MCalendarGridAdapter adapter;

	ImageView btn_change;

	TextView tv_time;

	int current_year = 2013;

	int current_month = 8;

	StudentInfoBean studentInfo;

	List<StudentInfoBean> studentInfos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_record);
		initViews();
		initListener();
		loadStudentinfo();
		prepareuserData();
		startGetPunchCardInfo();
		
		 //用户行为日志上报com.yutong.axxc.parents.view.common.UserBehaviorConstants
        UploadLogBiz.addUserBehavior(UserBehaviorConstants.MODULE_M_0018);
        

	}

	@Override
	protected void onStart() {

		super.onStart();
	}

	private void loadStudentinfo() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		studentInfo = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		if (studentInfo == null)
			return;
		// TODO 加载学生信息, 通过学生id 获取当月 打卡数据
		tv_top_title.setText(studentInfo.getCld_name() + "的乘车记录");
		// 获取当前时间
		Calendar cal = Calendar.getInstance(Locale.CHINA);
		current_year = cal.get(Calendar.YEAR); // 获取年
		current_month = cal.get(Calendar.MONTH); // 获取月份，0表示1月份
		tv_time.setText(current_year + "/" + (current_month + 1));

	}

	private void initListener() {
		// btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);

		final CustomerDatePickerDialog datePickerDialog = new CustomerDatePickerDialog(
				TravelRecordActivity.this, new OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						if (current_year == year && current_month == month)
							return;
						// Log.d("ok", "new =>year:" + year + ",month:" +
						// month);
						current_year = year;
						current_month = month;
						tv_time.setText(current_year + "/"
								+ (current_month + 1));
						startGetPunchCardInfo();
					}
				}, current_year, current_month, 1);

		btn_change.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*
				 * Log.d("ok", "onClick =>year:" + current_year + ",month:" +
				 * current_month);
				 */
				datePickerDialog.setDate(current_year, current_month);
				datePickerDialog.show();
			}
		});

		mhv_users.setOnBarItemClickListener(new OnBarItemClickListener() {

			@Override
			public void onClick(int index) {
				if (uadapter.getSelectedIndexs() != null
						&& uadapter.getSelectedIndexs().size() > 0) {
					if (index == uadapter.getSelectedIndexs().get(0))// 点击了当前选择项
						startGetPunchCardInfo();
				}
			}
		});

	}

	private StudentInfoBean getStudentInfo(String cld_id) {
		for (StudentInfoBean item : studentInfos) {
			if (cld_id.equals(item.getCld_id())) {
				return item;
			}
		}

		return null;
	}

	private void initViews() {

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		btn_title_right.setVisibility(View.INVISIBLE);

		tv_top_title.setText("乘车记录");
		/* zhouzechen add */

		btn_change = (ImageView) findViewById(R.id.btn_change);

		/* 头像列表相关 */
		mhv_users = (SingleSelectChildrenBar) findViewById(R.id.mHScrollView1);

		userdata = new ArrayList<UserGridInfo>();

		uadapter = new UserGridAdapter(this, userdata, mhv_users);
		uadapter.setOnUserGridSelectItemChangedListener(new UserGridSelectItemListener() {

			@Override
			public void onChanged(List<Integer> oldselections,
					List<Integer> newselections) {
				if (newselections == null)
					return;
				// TODO 更换学生，重新刷新打开日期
				String cld_id = userdata.get(newselections.get(0)).ChildId;

				studentInfo = getStudentInfo(cld_id);
				if (studentInfo != null) {
					tv_top_title.setText(studentInfo.getCld_name() + "的乘车记录");
				}
				startGetPunchCardInfo();

			}

		});

		mhv_users.setGridViewAdapter(uadapter);

		/* 日历相关 */
		gv = (GridView) findViewById(R.id.gridView_calendar);

		tv_time = (TextView) findViewById(R.id.tv_timeshow);

		gdata = new ArrayList<GridInfo>();
		adapter = new MCalendarGridAdapter(this, gdata);

		gv.setAdapter(adapter);

		gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				adapter.changeSelection(arg1);
				GridInfo info = gdata.get(arg2);

				Intent intent = new Intent(TravelRecordActivity.this,
						TravelRecordTimeLineActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(ActivityCommConstant.TIME_INFO,
						info.date);
				bundle.putSerializable(ActivityCommConstant.STUDENT_INFO,
						studentInfo);
				intent.putExtras(bundle);
				startActivity(intent);
				// // 跳转到时间轴界面
				// ToastMessage("click select "
				// + info.getDate().getTime().toLocaleString());
			}
		});

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
		default:
			break;
		}

	}

	/**
	 * 设置头像信息
	 */
	private void prepareuserData() {

		// 获取用户授权下的所有孩子信息

		studentInfos = AppCacheData.getStudentInfos(YtApplication.getInstance()
				.getUid());

		// studentInfos = CacheUtils.getStudentInfo();

		if (studentInfos != null && studentInfos.size() > 0) {
			Log.d("", "prepare Children Data:" + studentInfos.size());
			for (StudentInfoBean stuInfo : studentInfos) {

				if (studentInfo.getCld_id().equals(stuInfo.getCld_id())) {
					// 检测是否是当前小孩，是的话设置到第一个
					userdata.add(
							0,
							new UserGridInfo(stuInfo.getCld_id(), stuInfo
									.getCld_name(), getResources().getDrawable(
									R.drawable.default_boy), "",
									stuInfo.getCld_photo()));
				} else {
					userdata.add(new UserGridInfo(stuInfo.getCld_id(), stuInfo
							.getCld_name(), getResources().getDrawable(
							R.drawable.default_boy), "", stuInfo
							.getCld_photo()));
				}

			}
		}
	}

	/* 下面是日历相关 */
	private void reloadData(int year, int month, int week) {
		gdata.clear();
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.set(year, month, 1);
		c.set(Calendar.WEEK_OF_MONTH, week);

		int before = c.get(Calendar.DAY_OF_WEEK)-1;
		if (before >= 7)// 一周都是上月的情况
			before = 0;
		c.add(Calendar.DAY_OF_YEAR, -before);//

		for (int i = 0; i < 35; i++) {
			Calendar newc = Calendar.getInstance(Locale.CHINA);
			newc.setTime(c.getTime());
			GridInfo info = new GridInfo();
			info.setDate(newc);

			info.setContent("");
			String day = Tools.getFormatTime(newc);
			if (punchCardDates != null) {

				if (punchCardDates.contains(day)) {
					info.setIsmarked(true);
					info.setCanbeselect(true);
				}
			}

			gdata.add(info);
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		adapter.ClearSelection();
		adapter.notifyDataSetChanged();
	}

	/**
	 * 刷新日历
	 * 
	 * @param year
	 * @param month
	 */
	private void reloadData(int year, int month) {
		// 默认从第一周开始
		reloadData(year, month, 1);
	}

	public class GridInfo {

		private Calendar date;

		private String content;

		private boolean ismarked;

		private boolean canbeselect;

		public boolean isCanbeselect() {
			return canbeselect;
		}

		public void setCanbeselect(boolean canbeselect) {
			this.canbeselect = canbeselect;
		}

		public boolean isMarked() {
			return ismarked;
		}

		public void setIsmarked(boolean ismarked) {
			this.ismarked = ismarked;
		}

		public Calendar getDate() {
			return date;
		}

		public void setDate(Calendar date) {
			this.date = date;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}
	}

	public class MCalendarGridAdapter extends BaseAdapter {

		List<GridInfo> mData;

		Context mContext;

		List<Integer> selecteditems;

		public void setSelectedItem(int index) {
			selecteditems.clear();
			selecteditems.add(index);
			notifyDataSetChanged();

		}

		public void ClearSelection() {
			selecteditems.clear();
			notifyDataSetChanged();
		}

		public MCalendarGridAdapter(Context context, List<GridInfo> data) {
			selecteditems = new ArrayList<Integer>();
			// selecteditems.add(0);
			mContext = context;
			mData = data;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mData.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		private boolean isSelectedItem(int index) {
			if (selecteditems.contains(index))
				return true;
			return false;
		}

		private View lastSelectedItem = null;

		public void changeSelection(View newv) {

			if (newv == null)
				return;

			if (lastSelectedItem != null && lastSelectedItem.equals(newv))
				return;

			if (lastSelectedItem != null) {
				ViewHolder oldvholder = (ViewHolder) lastSelectedItem.getTag();
				int oldselection = (Integer) lastSelectedItem
						.getTag(R.layout.component_calendar_item);

				// 间隔色
				if (oldselection % 2 == 0)
					oldvholder.BackGround
							.setBackgroundResource(R.drawable.griditem1);
				else
					oldvholder.BackGround
							.setBackgroundResource(R.drawable.griditem0);
			}

			ViewHolder newvholder = (ViewHolder) newv.getTag();
			newvholder.BackGround.setBackgroundColor(getResources().getColor(
					R.color.maincolor));
			int newindex = (Integer) newv
					.getTag(R.layout.component_calendar_item);
			Log.d("", "changeSelection to:" + newindex);
			selecteditems.clear();
			selecteditems.add(newindex);

			lastSelectedItem = newv;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			ViewHolder holder = null;
			if (arg1 == null) {
				holder = new ViewHolder();
				arg1 = View.inflate(TravelRecordActivity.this,
						R.layout.component_calendar_item, null);
				holder.BackGround = (LinearLayout) arg1
						.findViewById(R.id.ll_bg);
				holder.Flag = (LinearLayout) arg1.findViewById(R.id.ll_im);
				holder.Content = (TextView) arg1.findViewById(R.id.tv_content);

				arg1.setTag(holder);

			} else {
				holder = (ViewHolder) arg1.getTag();
			}

			arg1.setTag(R.layout.component_calendar_item, arg0);
			// 获取信息
			GridInfo info = mData.get(arg0);
			if (!info.isCanbeselect()) {
				// 这段一加, gridview的onitemclicklistener就没有效果了
				arg1.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
					}
				});
			}

			String ctext = "";
			if (info.getDate().get(Calendar.MONTH) == (current_month)) {
				ctext = info.getDate().get(Calendar.DAY_OF_MONTH) + "";
			}
			// 设置显示
			holder.Content.setText(ctext);

			// 画圈圈
			if (info.isMarked())
				holder.Flag.setBackgroundResource(R.drawable.mark);
			else
				holder.Flag.setBackgroundDrawable(null);

			// 间隔色
			if (arg0 % 2 == 0)
				holder.BackGround.setBackgroundResource(R.drawable.griditem1);
			else
				holder.BackGround.setBackgroundResource(R.drawable.griditem0);

			// 选中色
			if (isSelectedItem(arg0)) {
				Log.d("", "find selection :" + arg0);
				holder.BackGround.setBackgroundColor(getResources().getColor(
						R.color.maincolor));
				lastSelectedItem = arg1;
			}

			if (info.isCanbeselect()) {
				holder.Content.setTextColor(getResources().getColor(
						R.color.fontcolor_weekday));
			} else {
				holder.Content.setTextColor(getResources().getColor(
						R.color.fontcolor_weekend));
			}

			return arg1;
		}

		class ViewHolder {
			public LinearLayout BackGround;

			public LinearLayout Flag;

			public TextView Content;
		}
	}

	private static final int LOADING_CODE_1 = 0X1001;

	private void startGetPunchCardInfo() {

		if (studentInfo == null) {
			return;
		}
		showLoading("加载学生数据中...", LOADING_CODE_1);
		GetStudentPunchCardDateBiz biz = new GetStudentPunchCardDateBiz(
				this.getApplicationContext(),
				new ProcessPunchCardHandler(this), studentInfo.getCld_id(),
				current_year
						+ ""
						+ ((current_month > 8) ? (current_month + 1)
								: ("0" + (current_month + 1))));
		biz.setReadMethod(ReadMethodEnum.OPTYPE_LOCAL_AND_REMOTE);
		biz.execute();
	}

	private class ProcessPunchCardHandler extends YtHandler {
		private final WeakReference<TravelRecordActivity> weakActivity;

		public ProcessPunchCardHandler(TravelRecordActivity activity) {
			this.weakActivity = new WeakReference<TravelRecordActivity>(
					activity);
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			Logger.i(SettingAccountHomeActivity.class, "[handler]:msg.what:",
					msg.what);
			TravelRecordActivity activity = weakActivity.get();
			if (activity != null) {
				super.handleMessage(msg, activity);
				dismissLoading();
				switch (msg.what) {
				case ThreadCommStateCode.REMOTE_DATA_CHANGED:
					Logger.i(getClass(),
							"ProcessPunchCardHandler->REMOTE_DATA_CHANGED ");
				case ThreadCommStateCode.REMOTE_DATA_NO_CHANGED:
					Logger.i(getClass(),
							"ProcessPunchCardHandler->REMOTE_DATA_NO_CHANGED ");
				case ThreadCommStateCode.COMMON_SUCCESS:
					Logger.i(getClass(),
							"ProcessPunchCardHandler->COMMON_SUCCESS ");
					// activity.loadingoverlay.setVisibility(View.INVISIBLE);
					activity.punchCardDates = (List<String>) msg.obj;
					Log.d("", "new =>activity.current_year:"
							+ activity.current_year
							+ ",activity.current_month:"
							+ activity.current_month);
					activity.reloadData(activity.current_year,
							activity.current_month);
					break;
				case ThreadCommStateCode.TOKEN_INVALID:
					// activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							"Token失效，请重新登录！", Toast.LENGTH_SHORT).show();
					break;

				case ThreadCommStateCode.COMMON_FAILED:

					// activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(), "失败！请重试！",
							Toast.LENGTH_SHORT).show();
					break;

				default:
					// activity.loadingoverlay.setVisibility(View.INVISIBLE);
					Toast.makeText(activity.getApplicationContext(),
							(String) msg.obj, Toast.LENGTH_SHORT).show();
					break;
				}
			}
		}
	}

}
