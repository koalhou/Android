package com.yutong.axxc.parents.view.settings.child;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.view.home.TimeLineListPagerView;

/**
 * Viewpager Adapter
 * 
 * @author zhangyongn
 * 
 */
public class RecordViewPagerAdapter extends PagerAdapter {

	List<RecordPageView> superviseViews = new ArrayList<RecordPageView>();

	

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
			RecordPageView v = superviseViews.get(position);
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
	public void setViews(List<RecordPageView> superviseViews) {
		this.superviseViews = superviseViews;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (position < superviseViews.size()) {
			container.removeView(superviseViews.get(position).getView());
		}
	}

	public int addView(ViewPager pager, RecordPageView v, int position) {
		pager.setAdapter(null);
		superviseViews.add(position, v);
		pager.setAdapter(this);
		return position;
	}

}
