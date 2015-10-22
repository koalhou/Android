package com.yutong.axxc.parents.view.settings.child;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;
import com.yutong.axxc.parents.view.common.ActivityCommConstant;
import com.yutong.axxc.parents.view.common.LoadingOverlay;
import com.yutong.axxc.parents.view.common.YtAbstractActivity;
import com.yutong.axxc.parents.view.home.TimeLineListPagerView;
import com.yutong.axxc.parents.view.home.TimeLineViewPagerAdapter;
import com.yutong.axxc.parents.view.home.TimelinePageChangeListener;

/**
 * 乘车记录细节页面。时间轴
 * 
 * @author zhangyongn
 * 
 */
public class TravelRecordTimeLineActivity extends YtAbstractActivity implements
		OnClickListener {
	private Button btn_title_left;
	private TextView tv_top_title;
	private Button btn_title_right;
	// 加载弹出提示相关视图
	private LoadingOverlay loadingoverlay;

	private TextView dateTV;
	private Button returntodayBtn;
	/* 消息相关 */
	private ViewPager viewPager = null;
	private RecordViewPagerAdapter pagerAdapter;
	private List<RecordPageView> viewList = new ArrayList<RecordPageView>();

	private StudentInfoBean currentChild;
	private Calendar date;

	public List<RecordPageView> getViewList() {
		return viewList;
	}

	private Calendar minCal = null;

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

	public RecordViewPagerAdapter getPagerAdatper() {
		return pagerAdapter;
	}

	public ViewPager getViewPager() {
		return viewPager;
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

	public void setMonthView(Calendar calendar2) {
		this.date = calendar2;
		onDateChanged();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_child_recordtimeline);
		initMinCal();
		initData();

		initViews();
		initListener();
		if (viewPager == null) {
			pagerAdapter = new RecordViewPagerAdapter();
			viewPager = (ViewPager) findViewById(R.id.timeline_viewPager);
			viewPager.setAdapter(pagerAdapter);
			viewPager
					.setOnPageChangeListener(new RecordPageChangeListener(this));

		}
		viewList.clear();
		pagerAdapter.setViews(viewList);
		initPagerView(this.date, this.currentChild);
	}

	public void initPagerView(Calendar calendar, StudentInfoBean chld) {

		viewList.clear();
		pagerAdapter.setViews(viewList);
		// 昨天
		Calendar cLast = Calendar.getInstance();
		cLast.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		cLast.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		cLast.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		int size = 0;
		if (Tools.DayBetween(cLast, getMinCal())) {
			RecordPageView lastview = new RecordPageView(this, cLast, chld);

			pagerAdapter.addView(viewPager, lastview, size);
			size++;
		}
		// 今天
		RecordPageView curview = new RecordPageView(this, calendar, chld);
		pagerAdapter.addView(viewPager, curview, size);
		size++;

		// 明天
		Calendar cNext = Calendar.getInstance();
		cNext.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		cNext.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		cNext.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		if (Tools.DayBetween(cNext, getMinCal())) {
			RecordPageView nextView = new RecordPageView(this, cNext, chld);
			pagerAdapter.addView(viewPager, nextView, size);
		}
		viewPager.setCurrentItem(size - 1);
		pagerAdapter.notifyDataSetChanged();

	}

	private void initData() {
		Intent intent = getIntent();
		if (intent == null)
			return;
		Bundle bundle = intent.getExtras();
		if (bundle == null)
			return;
		currentChild = (StudentInfoBean) bundle
				.getSerializable(ActivityCommConstant.STUDENT_INFO);
		date = (Calendar) bundle
				.getSerializable(ActivityCommConstant.TIME_INFO);

	}

	private void initListener() {
		btn_title_right.setOnClickListener(this);
		btn_title_left.setOnClickListener(this);
		returntodayBtn.setOnClickListener(this);

	}

	private void initViews() {
		// loadingoverlay = (LoadingOverlay) findViewById(R.id.loadingoverlay);
		dateTV = (TextView) findViewById(R.id.dateTV);
		returntodayBtn = (Button) findViewById(R.id.returntodayBtn);

		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		tv_top_title = (TextView) findViewById(R.id.tv_top_title);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		// btn_title_right.setText(R.string.next);
		btn_title_right.setVisibility(View.INVISIBLE);
		onStudentChanged();
		onDateChanged();

	}

	private void onDateChanged() {
		if (date != null) {
			String dtstr = Tools.getFormatTime(date, "yyyy-MM-dd");
			dateTV.setText(dtstr);
		}
		returntodayBtn.setVisibility(Tools.isToday(date) ? View.INVISIBLE
				: View.VISIBLE);

	}

	private void onStudentChanged() {
		if (currentChild != null)
			tv_top_title.setText(currentChild.getFullName() + "的乘车记录");

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_title_left:
			finish();
			break;
		case R.id.returntodayBtn:
			goToToday();

			break;
		default:
			break;
		}

	}

	private void goToToday() {
		if (Tools.isToday(date))
			return;
		try {
			for (int i = 0; i < viewList.size(); i++) {
				RecordPageView v = viewList.get(i);
				if (Tools.isToday(v.getCurrentCal())) {
					viewPager.setCurrentItem(i);
					return;
				}
			}
			date = Calendar.getInstance();
			initPagerView(date, this.getCurrentChild());
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
			Toast.makeText(this, "操作失败！", Toast.LENGTH_SHORT).show();
		}

	}
}
