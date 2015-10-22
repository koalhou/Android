package com.yutong.axxc.parents.view.home;

import java.util.Calendar;
import java.util.List;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Toast;

import com.yutong.axxc.parents.common.Tools;

/**
 * 
 * 
 * @author zhangyongn
 * 
 */
public class TimelinePageChangeListener implements OnPageChangeListener {
	private TimeLineListPagerView prevView = null;
	private static final int MAX_CACHE_SIZE = 10;

	private static final String NO_PREV = "没有上一日数据";

	private static final String NO_NEXT = "未来日期无数据";

	private static final int MAX_TIME_ZERO = 4;

	private MainActivity activity;

	private int count;

	private boolean haveShowNext = false;

	private boolean haveShowPrev = false;

	private boolean returntodaytip = false;

	private int prevPage = -1000;

	public TimelinePageChangeListener(MainActivity activity) {
		this.activity = activity;

	}

	@Override
	public void onPageScrollStateChanged(int state) {

		if (0 == state) {
			activity.setInit(true);
		} else {
			activity.setInit(false);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// activity.getViewList().size()-1;

		// Logger.d(getClass(), "onPageScrolled position is " + arg0 + "," +
		// arg1 + "," + arg2);
		if (positionOffset == 0 && positionOffsetPixels == 0) {
			count++;
			if (count > MAX_TIME_ZERO) {
				if (position == 0 && !haveShowPrev) {
					haveShowPrev = true;
					Toast.makeText(activity, NO_PREV, Toast.LENGTH_SHORT)
							.show();
				} else if (!haveShowNext
						&& position == activity.getViewList().size() - 1) {
					haveShowNext = true;
					Toast.makeText(activity, NO_NEXT, Toast.LENGTH_SHORT)
							.show();
				}

				count = 0;
			}
		} else {
			count = 0;
		}
	}

	@Override
	public void onPageSelected(int currentPosition) {// p:currentPosition
		// Logger.d(getClass(), "onPageSelected");
		List<TimeLineListPagerView> viewList = activity.getViewList();
		if (activity != null) {

			if (!Tools.isToday(viewList.get(currentPosition).getCurrentCal())) {
				if (!returntodaytip) {
					Toast.makeText(activity, "点击头像，回到今天。", Toast.LENGTH_SHORT)
							.show();
					returntodaytip = true;
				}
			} else {
				returntodaytip = false;
			}
		}
		// 停止掉上一个view
		if (viewList != null) {
			if (prevView != null) {
				prevView.stop();
			}
			prevView = viewList.get(currentPosition);
		}

		if (currentPosition == viewList.size() - 1) {
			TimeLineListPagerView lastView = viewList.get(currentPosition);
			Calendar c = Calendar.getInstance();
			c.setTime(lastView.getCurrentCal().getTime());
			c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
			if (Tools.DayBetween(c, activity.getMinCal())) {

				TimeLineListPagerView view = new TimeLineListPagerView(
						activity, c, activity.getCurrentChild());

				TimeLineViewPagerAdapter adapter = activity.getPagerAdatper();
				adapter.addView(activity.getViewPager(), view, viewList.size());

				if (viewList.size() > MAX_CACHE_SIZE) {
					adapter.removeView(activity.getViewPager(), 0);
				}
				activity.getViewPager().setCurrentItem(viewList.size() - 2);

			}
		} else if (currentPosition == 0) {
			TimeLineListPagerView oldView = viewList.get(0);
			Calendar c = Calendar.getInstance();
			c.setTime(oldView.getCurrentCal().getTime());
			c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
			if (Tools.DayBetween(c, activity.getMinCal())) {
				TimeLineListPagerView view = new TimeLineListPagerView(
						activity, c, activity.getCurrentChild());
				TimeLineViewPagerAdapter adapter = activity.getPagerAdatper();
				adapter.addView(activity.getViewPager(), view, 0);
				if (viewList.size() > MAX_CACHE_SIZE) {

					adapter.removeView(activity.getViewPager(),
							viewList.size() - 1);
				}
				activity.getViewPager().setCurrentItem(1);
			}
		}
		int ci = activity.getViewPager().getCurrentItem();
		activity.setMonthView(viewList.get(ci).getCurrentCal());

		// 启动当前view
		if (viewList != null) {
			viewList.get(currentPosition).start();
		}

		if (prevPage == -1000) {
			prevPage = currentPosition;
		} else {
			if (prevPage != currentPosition) {
				haveShowNext = false;
				haveShowPrev = false;
			}
		}

	}

}
