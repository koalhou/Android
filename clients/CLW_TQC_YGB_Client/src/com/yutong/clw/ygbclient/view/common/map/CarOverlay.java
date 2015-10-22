package com.yutong.clw.ygbclient.view.common.map;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapView.LayoutParams;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.view.util.DensityUtil;
import com.yutong.clw.ygbclient.view.widget.RotateButton;

/**
 * 车辆层 --因为是用控件方式做的，所以会有飘动现象
 * 
 * @author zhouzc
 * 
 */
public class CarOverlay {

	Activity mactivity;
	// List<CarMapItem> mItems;
	HashMap<String, CarMapItem> mItems;
	MapView mv;
	HashMap<String, View> popViews;

	int offsetx = 0;
	int offsety = 0;

	float offsetangle = 0;

	/**
	 * 构造函数
	 * 
	 * @param activity
	 *            MapView所在活动
	 * @param arg1
	 *            MapView控件实例
	 */
	public CarOverlay(Activity activity, MapView arg1) {
		mactivity = activity;
		mv = arg1;
		mItems = new HashMap<String, CarOverlay.CarMapItem>();
		popViews = new HashMap<String, View>();

		offsety = DensityUtil.dip2px(mactivity, 15);
	}

	private void whenRemoveItem(final CarMapItem item) {
		mItems.remove(item.getId());
		mactivity.runOnUiThread(new Runnable() {
			public void run() {
				if (popViews.containsKey(item.getId())) {
					mv.removeView(popViews.get(item.getId()));
				}
			}
		});

	}

	private final static Object vlocObj = new Object();

	private void whenRemoveAllItems() {

		mactivity.runOnUiThread(new Runnable() {
			public void run() {
				synchronized (vlocObj) {
					for (String key : popViews.keySet()) {
						mv.removeView(popViews.get(key));
					}
					popViews.clear();
				}
			}
		});
		mItems.clear();
	}

	private void whenAddItem(final CarMapItem item) {
		mItems.put(item.getId(), item);
		mactivity.runOnUiThread(new Runnable() {
			public void run() {
				View v = View.inflate(mactivity.getApplicationContext(),
						R.layout.control_mappop_car, null);
				((TextView) v.findViewById(R.id.tv_cmc_content)).setText(item
						.getTitle());

				MapView.LayoutParams lp = new LayoutParams(
						MapView.LayoutParams.WRAP_CONTENT,
						MapView.LayoutParams.WRAP_CONTENT, item.getPoint(),
						MapView.LayoutParams.BOTTOM_CENTER);
				lp.y = offsety;
				RotateButton arraw = ((RotateButton) v
						.findViewById(R.id.btn_cmc_arraw));

				float degree = getRealRotate(item.getDirection());

				arraw.setRotatedegree(degree);
				arraw.setEnabled(item.isOnline());
				mv.addView(v, lp);
				popViews.put(item.getId(), v);
			}
		});

	}

	private void whenAddItems(List<CarMapItem> items) {
		for (CarMapItem item : items) {
			whenAddItem(item);
		}
	}

	private void whenUpdateItem(final CarMapItem item) {

		mactivity.runOnUiThread(new Runnable() {
			public void run() {
				View v = popViews.get(item.getId());
				((TextView) v.findViewById(R.id.tv_cmc_content)).setText(item
						.getTitle());
				RotateButton arraw = ((RotateButton) v
						.findViewById(R.id.btn_cmc_arraw));

				float degree = getRealRotate(item.getDirection());

				arraw.setRotatedegree(degree);
				Logger.i(getClass(), "更新车辆[" + item.getId() + "][Direction="
						+ item.getDirection() + "][offsetangle=" + offsetangle
						+ "]偏移：" + degree);
				arraw.setEnabled(item.isOnline());
				MapView.LayoutParams lp = new LayoutParams(
						MapView.LayoutParams.WRAP_CONTENT,
						MapView.LayoutParams.WRAP_CONTENT, item.getPoint(),
						MapView.LayoutParams.BOTTOM_CENTER);
				lp.y = offsety;
				/*v.setLayoutParams(lp);*/
				
				mv.updateViewLayout(v, lp);
				splashCar(arraw, item.isOnline);
			}
		});
	}

	private float getRealRotate(float degree) {
		degree = degree + offsetangle;
		if (degree < 0) {
			degree = 360 + degree;
		}
		degree = degree % 360;
		return degree;
	}

	public void addItem(CarMapItem arg0) {

		whenAddItem(arg0);

	}

	public void addItem(List<CarMapItem> arg0) {
		whenAddItems(arg0);

	}

	public void animateTo(final String itemid) {
		mactivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Logger.d(getClass(), "animateTo" + itemid);
				if (mItems.containsKey(itemid)) {
					mv.getController().animateTo(mItems.get(itemid).getPoint());
				}
			}
		});
	}

	public boolean removeAll() {
		whenRemoveAllItems();
		return true;

	}

	public boolean removeItem(String itemid) {
		if (mItems.containsKey(itemid)) {
			whenRemoveItem(mItems.get(itemid));
			return true;
		}
		return false;
	}

	public boolean removeItem(CarMapItem arg0) {
		whenRemoveItem(arg0);
		return true;
	}

	public boolean updateItem(CarMapItem arg0) {
		whenUpdateItem(arg0);
		return true;
	}

	public boolean addOrUpdate(CarMapItem item) {
		if (!popViews.containsKey(item.getId())) {
			addItem(item);
		} else {
			updateItem(item);
		}
		return true;
	}

	public boolean getOnLineStatus(String itemid) {
		if (mItems.containsKey(itemid)) {
			CarMapItem item = mItems.get(itemid);
			return item.isOnline;
		}
		return false;
	}

	public void setOnLineStatus(String itemid, boolean isonline) {
		if (mItems.containsKey(itemid)) {
			CarMapItem item = mItems.get(itemid);
			item.setOnline(isonline);
			updateItem(item);
			mv.refresh();
		}
	}

	public void rotate(String itemid, float degree) {
		if (mItems.containsKey(itemid)) {
			CarMapItem item = mItems.get(itemid);
			item.setDirection(item.getDirection() + degree);
			updateItem(item);
			mv.refresh();
		}
	}

	public float getOffsetangle() {
		return offsetangle;
	}

	public void adapteToMapRotate() {
		setOffsetangle(-mv.getMapRotation());
	}

	public void setOffsetangle(float offset) {
		// Logger.i(getClass(), "设置图层偏移：" + offset);
		if (offset == this.offsetangle)
			return;
		this.offsetangle = offset;
		refreshAll();
	}

	public void refreshAll() {
		if (mItems == null)
			return;
		Logger.i(getClass(), "刷新所有车辆图层");
		for (String key : mItems.keySet()) {
			updateItem(mItems.get(key));
		}
	}

	public static class CarMapItem {

		public CarMapItem(String id, GeoPoint point, String title,
				float degree, boolean online) {
			this.Id = id;
			this.point = point;
			this.title = title;
			Logger.i(getClass(), "初始[" + id + "]旋转度为:" + degree);
			this.direction = degree;
			this.isOnline = online;
		}

		private String Id;
		private GeoPoint point;
		private String title;
		private float direction;
		private boolean isOnline;

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public boolean isOnline() {
			return isOnline;
		}

		public void setOnline(boolean isOnline) {
			this.isOnline = isOnline;
		}

		public GeoPoint getPoint() {
			return point;
		}

		public void setPoint(GeoPoint geoPoint) {
			this.point = geoPoint;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public float getDirection() {
			return direction;
		}

		public void setDirection(float direction) {
			// Logger.i(getClass(), "设置[" + this.Id + "]旋转度为:" + direction);
			this.direction = direction;
		}

	}

	/**
	 * 闪烁
	 * 
	 * @author zhouzc 2013-11-6 上午9:34:23
	 * 
	 * @param itemid
	 */
	public void splashItemTitle(String itemid) {
		if (popViews.containsKey(itemid)) {
			View v = popViews.get(itemid);
			TextView txt = (TextView) v.findViewById(R.id.tv_cmc_content);
			if (txt.getTag() == null || txt.getTag().toString().equals("0")) {
				int originColor = txt.getTextColors().getDefaultColor();
				int spColor = Color.BLUE;
				goSplash(txt, originColor, spColor);
			}
		}

	}

	/**
	 * 闪烁
	 * 
	 * @author zhouzc 2013-11-6 上午9:34:23
	 * 
	 * @param itemid
	 */
	public void splashItemCar(String itemid) {
		if (popViews.containsKey(itemid)) {
			RotateButton arraw = ((RotateButton) popViews.get(itemid)
					.findViewById(R.id.btn_cmc_arraw));
			splashCar(arraw, mItems.get(itemid).isOnline);
		}
	}

	private void splashCar(RotateButton arraw, boolean online) {
		if (online) {
			goSplash(arraw, R.drawable.ic_map_car_normal,
					R.drawable.ic_map_car_splash, 3);
		} else {
			// goSplash(arraw, R.drawable.bg_arraw_online,
			// R.drawable.ic_map_car_splash, 3);
		}
	}

	private void goSplash(final RotateButton v, final int bg1, final int bg2,
			final int splashcount) {
		// TODO
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				mactivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							int count = 0;
							if (v.getTag() != null)
								count = Integer.valueOf(v.getTag().toString());
							if (count % 2 == 0)
								v.setBackgroundResource(bg1);
							else
								v.setBackgroundResource(bg2);
							
							if (count >= splashcount) {
								v.setBackgroundResource(R.drawable.bg_arraw);
								v.setTag(0);
								t.cancel();
							} else {
								v.setTag(count + 1);
							}
						} catch (Exception er) {
							er.printStackTrace();
							v.setBackgroundResource(R.drawable.bg_arraw);
							v.setTag(0);
							t.cancel();
						}
					}
				});

			}
		}, 0, 200);

	}

	private void goSplash(final TextView v, final int color1, final int color2) {
		// TODO
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				mactivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							int count = 0;
							if (v.getTag() != null)
								count = Integer.valueOf(v.getTag().toString());
							if (count % 2 == 0)
								v.setTextColor(color2);
							else
								v.setTextColor(color1);
							if (count >= 4) {
								v.setTextColor(color1);
								v.setTag(0);
								t.cancel();
							} else {
								v.setTag(count + 1);
							}
						} catch (Exception er) {
							er.printStackTrace();
							v.setTextColor(color1);
							v.setTag(0);
							t.cancel();
						}
					}
				});

			}
		}, 0, 200);

	}

}