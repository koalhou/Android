package com.yutong.clw.ygbclient.view.common.map;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.yutong.clw.ygbclient.R;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.util.DensityUtil;

/**
 * 站点图层
 * 
 * @author zhouzc
 * 
 */
public class OutFactoryStationOverlay {

	ItemizedOverlay<OverlayItem> normalol;
	ItemizedOverlay<OverlayItem> remindol;
	ItemizedOverlay<OverlayItem> editol;
	ItemizedOverlay<OverlayItem> editol_remind;
	ItemizedOverlay<OverlayItem> startol;
	ItemizedOverlay<OverlayItem> endol;

	View popEidtView;

	Context mContext;
	MapView mv;
	List<StationItem> mldata;
	HashMap<String, StationItem> mData;
	HashMap<String, View> stationNameViews;

	Drawable remindicon;//闹钟提醒图标
	Drawable normalicon;/*站点图标*/
	Drawable editicon;
	Drawable editicon_remid;
	Drawable starticon;
	Drawable endicon;
	
	OnItemTapListener onItemTapListener = null;
	OnItemTapListener onItemRemindTapListener = null;

	private boolean canTapItem = true;

	private ExecutorService mService = null;

	private boolean needEdit = true;

	public OutFactoryStationOverlay(
			Context context, 
			MapView view, 
			Drawable editicon,
			Drawable editicon_checked) {
		
		if (editicon != null)
			this.editicon = editicon;
		if (editicon_checked != null)
			this.editicon_remid = editicon_checked;
		
		mService = Executors.newFixedThreadPool(2);
		mContext = context;
		mv = view;
		
		/*收藏过的站点图标*/
		/*remindicon = mContext.getResources().getDrawable(
				R.drawable.ic_station_clock_normal);
		editicon_remid = mContext.getResources().getDrawable(
				R.drawable.ic_station_clock_active);*/
		remindicon = mContext.getResources().getDrawable(
				R.drawable.bg_station_favourite_normal);
		editicon_remid = mContext.getResources().getDrawable(
				R.drawable.bg_station_favourite_pressed);
		
		
		normalicon = mContext.getResources().getDrawable(
				R.drawable.ic_station_normal);
		editicon = mContext.getResources().getDrawable(
				R.drawable.ic_station_active);
		
		starticon = mContext.getResources().getDrawable(R.drawable.ic_begin);
		endicon = mContext.getResources().getDrawable(R.drawable.ic_finish);

		mData = new HashMap<String, OutFactoryStationOverlay.StationItem>();
		stationNameViews = new HashMap<String, View>();
		normalol = new ItemizedOverlay<OverlayItem>(normalicon, view) {
			@Override
			protected boolean onTap(int arg0) {
				if (canTapItem) {
					if (!needEdit)
						return false;
					StationItem item = mData.get(normalol.getItem(arg0)
							.getSnippet());
					forceToListener(item);
					clearAllFromEdit();
					changeToEdit(item);
					showRemindView(item);
				}
				return false;
			}
		};
		remindol = new ItemizedOverlay<OverlayItem>(remindicon, view) {
			@Override
			protected boolean onTap(int arg0) {

				if (canTapItem) {
					if (!needEdit)
						return false;
					StationItem item = mData.get(remindol.getItem(arg0)
							.getSnippet());
					forceToListener(item);
					clearAllFromEdit();
					changeToEdit(item);
					showRemindView(item);
				}
				return false;
			}
		};
		editol = new ItemizedOverlay<OverlayItem>(editicon, view) {
			@Override
			protected boolean onTap(int arg0) {
				clearAllFromEdit();
				return false;
			}
		};

		startol = new ItemizedOverlay<OverlayItem>(starticon, view) {
			@Override
			protected boolean onTap(int arg0) {
				return true;
			}
		};
		endol = new ItemizedOverlay<OverlayItem>(endicon, view) {
			@Override
			protected boolean onTap(int arg0) {
				return true;
			}
		};
		editol_remind = new ItemizedOverlay<OverlayItem>(editicon_remid, view) {
			@Override
			protected boolean onTap(int arg0) {
				clearAllFromEdit();
				return true;
			}
		};
		view.getOverlays().add(normalol);
		view.getOverlays().add(remindol);

		view.getOverlays().add(startol);
		view.getOverlays().add(endol);

		view.getOverlays().add(editol);
		view.getOverlays().add(editol_remind);

		initPopRemindView();

	}

	public OutFactoryStationOverlay(Context context, MapView view) {
		this(context, view, null, null);
	}

	private void initPopRemindView() {
		popEidtView = View.inflate(mContext, R.layout.control_mappop_stations,
				null);
		popEidtView.setVisibility(View.GONE);
		mv.addView(popEidtView);
	}

	private void showRemindView(final StationItem item) {

		((TextView) popEidtView.findViewById(R.id.tv_cmr_name)).setText(item
				.getName());
		
		ImageView favouriteStar = (ImageView) popEidtView
				.findViewById(R.id.iv_cmr_star);
		
		if (item.isNeedEdit()) {
			
			popEidtView.findViewById(R.id.ll_cmr_name).setBackgroundResource(
					R.drawable.bg_mappop_remind_left);
			popEidtView.findViewById(R.id.iv_cmr_star).setVisibility(
					View.VISIBLE);
			
			/*设置是否是收藏过的站点*/
			favouriteStar.setImageResource(item.isRemind() ? R.drawable.bg_star_white
					: R.drawable.bg_star_null);
			
			favouriteStar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onItemRemindTapListener != null)
						onItemRemindTapListener.OnItemTap(item);
				}
			});
		} else {
			popEidtView.findViewById(R.id.ll_cmr_name).setBackgroundResource(
					R.drawable.bg_mappop_remind_left1);
			popEidtView.findViewById(R.id.ll_cmr_clock)
					.setVisibility(View.GONE);
		}
		MapView.LayoutParams lp = new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT, item.getPoint(),
				MapView.LayoutParams.BOTTOM_CENTER);

		lp.y = -DensityUtil.dip2px(mContext, 10);

		popEidtView.setLayoutParams(lp);
		popEidtView.setVisibility(View.VISIBLE);
		popEidtView.bringToFront();
		mv.refresh();
	}

	/**
	 * 导入站点数据
	 * 
	 * @param data
	 */
	public void loadData(List<StationItem> data) {

		normalol.removeAll();
		remindol.removeAll();
		startol.removeAll();
		endol.removeAll();
		editol.removeAll();
		editol_remind.removeAll();
		if (popEidtView != null)
			popEidtView.setVisibility(View.GONE);
		mData.clear();
		for (View v : stationNameViews.values()) {
			mv.removeView(v);
		}
		stationNameViews.clear();
		mldata = data;
		if (mData == null)
			return;
		for (StationItem item : data) {
			// Logger.d(getClass(), "开始加载站点：" + item.getName());
			addItem(item);
		}

	}

	public void removeAll(){
		
		normalol.removeAll();
		remindol.removeAll();
		startol.removeAll();
		endol.removeAll();
		editol.removeAll();
		editol_remind.removeAll();
		if (popEidtView != null)
			popEidtView.setVisibility(View.GONE);
		mData.clear();
		for (View v : stationNameViews.values()) {
			mv.removeView(v);
		}
		stationNameViews.clear();
	}
	
	
	/**
	 * 导入站点数据
	 * 
	 * @param data
	 */
	public void appendData(List<StationItem> data) {

		if (mldata == null || mldata.size() == 0) {
			loadData(data);
			return;
		} else
			mldata.addAll(data);
		for (StationItem item : data) {
			// Logger.d(getClass(), "开始加载站点：" + item.getName());
			addItem(item);
		}

	}

	public void animateTo(String itemid) {
		if (mData.containsKey(itemid)) {
			StationItem item = mData.get(itemid);
			mv.getController().animateTo(item.getPoint());
		}

	}

	public void moveTo(String itemid) {
	}

	/**
	 * 设置站点点击事件侦听
	 * 
	 * @param onItemTapListener
	 */
	public void setOnItemTapListener(OnItemTapListener onItemTapListener) {
		this.onItemTapListener = onItemTapListener;
	}

	/**
	 * 设置站点提醒弹出框点击事件侦听
	 * 
	 * @param onItemTapListener
	 */
	public void setOnItemRemindTapListener(
			OnItemTapListener onItemRemindTapListener) {
		this.onItemRemindTapListener = onItemRemindTapListener;
	}

	/**
	 * 修改站点提醒状态
	 * 
	 * @param id
	 *            站点ID
	 * @param remind
	 *            是否提醒
	 */
	public void setItemRemind(String id, boolean remind) {
		if (!mData.containsKey(id)) {
			Logger.d(getClass(), "ID为[" + id + "]的站点不存在");
			return;
		}

		if (mData.get(id).isRemind() == remind) {
			Logger.d(getClass(), "ID为[" + id + "]的站点设置与原来一样");
			return;
		}
		Logger.d(getClass(), "设置[" + id + "]的站点提醒为：" + remind);
		mData.get(id).setRemind(remind);
		/*
		 * if (remind) { removeFromNormal(id); addToRemind(id); } else {
		 * removeFromRemind(id); addToNormal(id); }
		 */

	}

	/**
	 * 重新刷新数据
	 */
	public void notifyDataSetChanged() {
		loadData(mldata);
		mv.refresh();
	}

	/**
	 * 获取是否可以点击站点
	 * 
	 * @author zhouzc 2013-10-30 下午4:34:50
	 * 
	 * @return
	 */
	public boolean isCanTapItem() {
		return canTapItem;
	}

	/**
	 * 设置是否可以点击站点
	 * 
	 * @author zhouzc 2013-10-30 下午4:34:33
	 * 
	 * @param canTapItem
	 */
	public void setCanTapItem(boolean canTapItem) {
		this.canTapItem = canTapItem;
	}

	/**
	 * 清空编辑状态的站点
	 */
	public void clearAllFromEdit() {
		Logger.i(getClass(), "清空所有编辑状态");
		for (OverlayItem oi : editol.getAllItem()) {
			StationItem si = mData.get(oi.getSnippet());
			if (si.isRemind()) {
				addToRemind(si.getId());
			} else {
				addToNormal(si.getId());
			}
		}
		for (OverlayItem oi : editol_remind.getAllItem()) {
			StationItem si = mData.get(oi.getSnippet());
			if (si.isRemind()) {
				addToRemind(si.getId());
			} else {
				addToNormal(si.getId());
			}
		}
		editol.removeAll();
		editol_remind.removeAll();
		popEidtView.setVisibility(View.GONE);
		mv.refresh();
	}

	private void addToEdit(String id) {
		Logger.i(getClass(), "将[" + id + "]添加到编辑状态addToEdit");
		StationItem item = mData.get(id);
		if (item.isRemind())
			editol_remind.addItem(getOverItemFromStationItem(item));
		else
			editol.addItem(getOverItemFromStationItem(item));
	}

	/**
	 * 设置站点为编辑状态
	 * 
	 * @param id
	 *            站点ID
	 */
	public void changeToEdit(String id) {
		Logger.i(getClass(), "将[" + id + "]设置为编辑状态changeToEdit-String");
		if (mData.containsKey(id)) {
			StationItem item = mData.get(id);
			changeToEdit(item);
		}
		mv.refresh();
	}

	public boolean isNeedEdit() {
		return needEdit;
	}

	/**
	 * 设置是否有提醒弹出框
	 * 
	 * @author zhouzc 2013-11-1 下午2:44:42
	 * 
	 * @param needEdit
	 */
	public void setNeedEdit(boolean needEdit) {
		this.needEdit = needEdit;
	}

	public void cancelEdit(String id) {
		Logger.i(getClass(), "将[" + id + "]取消编辑状态cancelEdit-String");
		if (!mData.containsKey(id))
			return;
		for (OverlayItem item : editol_remind.getAllItem()) {
			if (item.getSnippet().equals(id)) {
				editol_remind.removeItem(item);
				StationItem si = mData.get(id);
				if (si.isRemind()) {
					addToRemind(id);
				} else {
					addToNormal(id);
				}
			}
		}
		for (OverlayItem item : editol.getAllItem()) {
			if (item.getSnippet().equals(id)) {
				editol.removeItem(item);
				StationItem si = mData.get(id);
				if (si.isRemind()) {
					addToRemind(id);
				} else {
					addToNormal(id);
				}
			}
		}
		popEidtView.setVisibility(View.GONE);
		mv.refresh();
	}

	private void changeToEdit(StationItem item) {
		Logger.i(getClass(), "将[" + item.getId() + "]设置为编辑状态changeToEdit-Item");
		if (item.isRemind()) {
			removeFromRemind(item.getId());
			addToEdit(item.getId());

		} else {
			removeFromNormal(item.getId());
			addToEdit(item.getId());
		}

	}

	private void addToNormal(StationItem item) {
		Logger.i(getClass(), "将[" + item.getId()
				+ "]添加到正常状态addToNormal-StationItem");
		synchronized (normalol) {
			normalol.addItem(getOverItemFromStationItem(item));
		}
	}

	private void addToNormal(String id) {
		Logger.i(getClass(), "将[" + id + "]添加到正常状态addToNormal-String");
		addToNormal(mData.get(id));
	}

	private void removeFromNormal(String id) {
		Logger.i(getClass(), "将[" + id + "]从正常状态移除removeFromNormal-String");
		synchronized (normalol) {
			List<OverlayItem> items = normalol.getAllItem();
			for (int i = items.size() - 1; i >= 0; i--) {
				OverlayItem item = items.get(i);
				if (item.getSnippet().equals(id)) {
					normalol.removeItem(item);
					Logger.i(getClass(), "将[" + id + "]从正常状态移除成功");
				}
			}
		}

	}

	private void addItem(StationItem item) {
		Logger.i(getClass(), "添加新站点[" + item.getId() + "]addItem");
		mData.put(item.getId(), item);
		if (item.isRemind()) {
			addToRemind(item);
		} else {
			addToNormal(item);
		}

		drawStationName(item);

		drawStationMarker(item);
	}

	private void addToRemind(StationItem item) {
		Logger.i(getClass(), "将[" + item.getId()
				+ "]添加到提醒状态addToRemind-StationItem");
		synchronized (remindol) {
			remindol.addItem(getOverItemFromStationItem(item));
		}

	}

	private void addToRemind(String id) {
		Logger.i(getClass(), "将[" + id + "]添加到提醒状态addToRemind-String");
		addToRemind(mData.get(id));
	}

	private void removeFromRemind(String id) {
		Logger.i(getClass(), "将[" + id + "]从提醒状态移除removeFromRemind-String");
		synchronized (remindol) {
			List<OverlayItem> items = remindol.getAllItem();
			for (int i = items.size() - 1; i >= 0; i--) {
				OverlayItem item = items.get(i);
				if (item.getSnippet().equals(id)) {
					remindol.removeItem(item);
					Logger.i(getClass(), "将[" + id + "]从提醒状态移除成功");
				}
			}
		}
	}

	private void forceToListener(final StationItem station) {
		Logger.i(getClass(), "触发站点[" + station.getId() + "]点击事件");
		mService.submit(new Runnable() {
			@Override
			public void run() {
				if (onItemTapListener != null) {
					onItemTapListener.OnItemTap(station);
				}
			}
		});
	}

	private OverlayItem getOverItemFromStationItem(StationItem station) {
		OverlayItem item = new OverlayItem(station.point, station.getName(),station.getId());
		item.setAnchor(OverlayItem.ALING_CENTER);
		return item;
	}

	private void drawStationMarker(StationItem item) {
		if (item == null)
			return;

		switch (item.positionType) {
		case CENTER:
			break;
		case START:
			OverlayItem nitem = new OverlayItem(item.getPoint(), "", "");
			nitem.setAnchor(OverlayItem.ALIGN_BOTTON);
			startol.addItem(nitem);
			break;
		case END:
			OverlayItem enditem = new OverlayItem(item.getPoint(), "", "");
			enditem.setAnchor(OverlayItem.ALIGN_BOTTON);
			endol.addItem(enditem);
			break;
		default:
			break;
		}

	}

	private void drawStationName(final StationItem item) {
		if (item == null)
			return;

		if (StringUtil.isEmpty(item.getShowname())) {
			return;
		}

		if (mContext instanceof Activity) {
			((Activity) mContext).runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// 添加文本
					LinearLayout ll = new LinearLayout(mContext);
					TextView tv_name = new TextView(mContext);
					LayoutParams tvlp = new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					tvlp.gravity = Gravity.CENTER;
					ll.addView(tv_name, tvlp);
					ll.setBackgroundResource(R.drawable.bg_map_stationname);
					tv_name.setText(item.getShowname());
					MapView.LayoutParams lp = new MapView.LayoutParams(
							MapView.LayoutParams.WRAP_CONTENT,
							MapView.LayoutParams.WRAP_CONTENT, item.getPoint(),
							MapView.LayoutParams.LEFT);
					lp.x = 24;
					lp.y = -48;
					mv.addView(ll, 1, lp);
					stationNameViews.put(item.getId(), ll);
				}
			});
		}

		/*
		 * TextItem titem = new TextItem(); titem.align = TextItem.ALIGN_BOTTOM;
		 * Symbol textSymbol = new Symbol();
		 * 
		 * Symbol.Color textColor = textSymbol.new Color(); textColor.alpha =
		 * 255; textColor.red = 0; textColor.blue = 0; textColor.green = 0;
		 * 
		 * Symbol.Color textColor1 = textSymbol.new Color(); textColor1.alpha =
		 * 0; textColor1.red = 213; textColor1.blue = 213; textColor1.green =
		 * 213;
		 * 
		 * titem.fontColor = textColor; titem.bgColor = textColor1;
		 * titem.fontSize = DensityUtil.sp2px(mContext, 14.0f); titem.typeface =
		 * Typeface.SANS_SERIF; titem.text = item.getShowname();
		 * 
		 * int offsety = getyoffsetByLevel(mv.getZoomLevel());
		 * 
		 * titem.pt = new GeoPoint(item.getPoint().getLatitudeE6() + 100, item
		 * .getPoint().getLongitudeE6() + offsety); txtol.addText(titem);
		 */
	}

	/**
	 * 站点点击侦听器
	 * 
	 * @author zhouzc
	 * 
	 */
	public interface OnItemTapListener {

		/**
		 * 站点被点击
		 * 
		 * @param value
		 *            站点值
		 */
		public void OnItemTap(StationItem value);
	}

	/**
	 * 
	 * @author zhouzc
	 * 
	 */
	public enum StationItemPositionType {
		/**
		 * 起始点
		 */
		START,
		/**
		 * 结束点
		 */
		END,
		/**
		 * 中间点
		 */
		CENTER
	}

	/**
	 * 站点数据对象
	 * 
	 * @author zhouzc
	 * 
	 */
	public static class StationItem {

		private String id;
		private String name;
		private String showname;
		private int direction;
		private GeoPoint point;
		private boolean remind;
		private StationItemPositionType positionType;
		private boolean needEdit = true;

		public StationItem(String id, GeoPoint point, String name,
				String showname, int direction, boolean remind,
				StationItemPositionType ptype) {

			this.id = id;
			this.point = point;
			this.name = name;
			this.showname = showname;
			this.direction = direction;
			this.remind = remind;
			this.positionType = ptype;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getShowname() {
			return showname;
		}

		public void setShowname(String showname) {
			this.showname = showname;
		}

		public int getDirection() {
			return direction;
		}

		public void setDirection(int direction) {
			this.direction = direction;
		}

		public GeoPoint getPoint() {
			return point;
		}

		public void setPoint(GeoPoint point) {
			this.point = point;
		}

		public boolean isRemind() {
			return remind;
		}

		public void setRemind(boolean remind) {
			this.remind = remind;
		}

		public StationItemPositionType getPositionType() {
			return positionType;
		}

		public void setPositionType(StationItemPositionType positionType) {
			this.positionType = positionType;
		}

		public boolean isNeedEdit() {
			return needEdit;
		}

		public void setNeedEdit(boolean needEdit) {
			this.needEdit = needEdit;
		}

	}

}
