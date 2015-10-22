package com.yutong.axxc.parents.view.home;

import java.util.ArrayList;
import java.util.List;

import com.yutong.axxc.parents.common.Logger;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * Viewpager Adapter
 * 
 * @author zhangyongn
 * 
 */
public class TimeLineViewPagerAdapter extends PagerAdapter {

	List<TimeLineListPagerView> superviseViews = new ArrayList<TimeLineListPagerView>();

	public void start(int position) {
		try {

			if (superviseViews != null) {
				if (position < 0 || position >= superviseViews.size())
					return;
				superviseViews.get(position).start();
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	public void start() {
		try {
			if (superviseViews != null) {
				for (int i = 0; i < superviseViews.size(); i++) {
					superviseViews.get(i).start();
				}
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	public void destroy() {
		try {
			if (superviseViews != null) {
				for (int i = 0; i < superviseViews.size(); i++) {
					superviseViews.get(i).destroy();
				}
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	public void stop() {
		try {
			if (superviseViews != null) {
				for (int i = 0; i < superviseViews.size(); i++) {
					superviseViews.get(i).stop();
				}
			}
		} catch (Exception e) {
			Logger.e(getClass(), e.getMessage());
		}
	}

	/**
	     * 
	     */
	@Override
	public int getCount() {
		return superviseViews.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View v = superviseViews.get(position).getView();
		container.addView(v);
		return v;
	}

	// public int removeView(ViewPager pager, EnterpriseContentView v) {
	// return removeView(pager, superviseViews.indexOf(v));
	// }

	public int removeView(ViewPager pager, int position) {
		pager.setAdapter(null);
		try {
			TimeLineListPagerView v = superviseViews.get(position);
			if (v != null)
				v.destroy();
			superviseViews.remove(position);
			pager.setAdapter(this);
		} catch (Exception e) {
			Logger.e(this.getClass(), e.getMessage());
		}
		return position;
	}

	/**
	 * 
	 * @param reportPagerViews
	 */
	public void setViews(List<TimeLineListPagerView> superviseViews) {
		this.superviseViews = superviseViews;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (position < superviseViews.size()) {
			container.removeView(superviseViews.get(position).getView());
		}
	}

	public int addView(ViewPager pager, TimeLineListPagerView v, int position) {
		pager.setAdapter(null);
		superviseViews.add(position, v);
		pager.setAdapter(this);
		return position;
	}

}
