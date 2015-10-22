package com.yutong.clw.ygbclient.view.common.map;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
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
public class StationOverlay {

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

	Drawable icon_station_remind;// 提醒站点图标
	Drawable icon_station_normal;// 正常站点图标
	Drawable icon_station_edit_normal;// 正常站点编辑图标
	Drawable icon_station_edit_remid;// 提醒站点编辑图标
	Drawable icon_station_start;// 开始站点图标
	Drawable icon_station_end;// 结束站点图标
	Drawable icon_showmark_normal;// 弹出框正常图标
	Drawable icon_showmark_remind;// 弹出框提醒图标

	OnItemTapListener onItemTapListener = null;
	OnItemTapListener onItemRemindTapListener = null;
	View.OnClickListener onClickListener = null;

	private boolean canTapItem = true;

	private ExecutorService mService = null;

	private boolean needEdit = true;

	private boolean needStationName = true;

	/**
	 * 站点层构造函数
	 * 
	 * @param context
	 *            上下文
	 * @param view
	 *            地图控件
	 * @param remindicon
	 *            提醒站点图标
	 * @param normalicon
	 *            正常站点图标
	 * @param editicon
	 *            正常站点编辑图标
	 * @param editicon_remid
	 *            提醒站点编辑图标
	 * @param starticon
	 *            开始站点图标
	 * @param endicon
	 *            结束站点图标
	 * @param shownormal
	 *            弹出框正常图标
	 * @param showremind
	 *            弹出框提醒图标
	 */
	public StationOverlay(Context context, MapView view, Drawable remindicon,
			Drawable normalicon, Drawable editicon, Drawable editicon_remid,
			Drawable starticon, Drawable endicon, Drawable shownormal,
			Drawable showremind) {

		mService = Executors.newFixedThreadPool(2);
		mContext = context;
		mv = view;

		this.icon_station_remind = (remindicon != null) ? remindicon : mContext
				.getResources().getDrawable(R.drawable.ic_station_clock_normal);
		this.icon_station_normal = (normalicon != null) ? normalicon : mContext
				.getResources().getDrawable(R.drawable.ic_station_normal);
		this.icon_station_edit_normal = (editicon != null) ? editicon
				: mContext.getResources().getDrawable(
						R.drawable.ic_station_active);
		this.icon_station_edit_remid = (editicon_remid != null) ? editicon_remid
				: mContext.getResources().getDrawable(
						R.drawable.ic_station_clock_active);
		this.icon_station_start = (starticon != null) ? starticon : mContext
				.getResources().getDrawable(R.drawable.ic_begin);
		this.icon_station_end = (endicon != null) ? endicon : mContext
				.getResources().getDrawable(R.drawable.ic_finish);
		this.icon_showmark_normal = (shownormal != null) ? shownormal
				: mContext.getResources()
						.getDrawable(R.drawable.ic_clock_white);
		this.icon_showmark_remind = (showremind != null) ? showremind
				: mContext.getResources().getDrawable(
						R.drawable.ic_clocked_white);

		mData = new HashMap<String, StationOverlay.StationItem>();
		stationNameViews = new HashMap<String, View>();

		normalol = new ItemizedOverlay<OverlayItem>(icon_station_normal, view) {
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
					showPopView(item);
				}
				return false;
			}
		};
		remindol = new ItemizedOverlay<OverlayItem>(icon_station_remind, view) {
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
					showPopView(item);
				}
				return false;
			}
		};
		editol = new ItemizedOverlay<OverlayItem>(icon_station_edit_normal,
				view) {
			@Override
			protected boolean onTap(int arg0) {
				clearAllFromEdit();
				return false;
			}
		};
		editol_remind = new ItemizedOverlay<OverlayItem>(
				icon_station_edit_remid, view) {
			@Override
			protected boolean onTap(int arg0) {
				clearAllFromEdit();
				return true;
			}
		};
		startol = new ItemizedOverlay<OverlayItem>(icon_station_start, view) {
			@Override
			protected boolean onTap(int arg0) {
				return true;
			}
		};
		endol = new ItemizedOverlay<OverlayItem>(icon_station_end, view) {
			@Override
			protected boolean onTap(int arg0) {
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

	/*
	 * 构造函数：remindicon ：闹钟正常图标 remindicon_checked：闹钟激活图标
	 */
	public StationOverlay(Context context, MapView view, Drawable remindicon,
			Drawable remindicon_checked) {
		this(context, view, remindicon, remindicon_checked, null, null, null,
				null, null, null);
	}

	public StationOverlay(Context context, MapView view) {
		this(context, view, null, null);
	}

	public boolean isNeedStationName() {
		return needStationName;
	}

	public void setNeedStationName(boolean needStationName) {
		this.needStationName = needStationName;
	}

	private void initPopRemindView() {
		popEidtView = View.inflate(mContext, R.layout.control_mappop_remind,
				null);
		popEidtView.setVisibility(View.GONE);
		mv.addView(popEidtView);
	}

	String currentPopId = null;

	private void showPopView(final StationItem item) {
		if (item == null)
			return;
		currentPopId = item.getId();
		LinearLayout nameLL = (LinearLayout) popEidtView
				.findViewById(R.id.ll_cmr_name);
		nameLL.setTag(item);

		LinearLayout clockLL = (LinearLayout) popEidtView
				.findViewById(R.id.ll_cmr_clock);
		clockLL.setTag(item);

		TextView name = ((TextView) popEidtView.findViewById(R.id.tv_cmr_name));
		name.setText(item.getName());

		ImageView clock = (ImageView) popEidtView
				.findViewById(R.id.iv_cmr_clock);

		if (item.isNeedEdit()) {
			popEidtView.findViewById(R.id.ll_cmr_name).setBackgroundResource(
					R.drawable.bg_mappop_remind_left);
			popEidtView.findViewById(R.id.ll_cmr_clock).setVisibility(
					View.VISIBLE);
			clock.setImageDrawable(item.isRemind() ? this.icon_showmark_remind
					: this.icon_showmark_normal);

			/*
			 * clock.setOnClickListener(new OnClickListener() {
			 * 
			 * 
			 * @Override public void onClick(View v) {
			 * if(onItemRemindTapListener != null)
			 * onItemRemindTapListener.OnItemTap(item); } });
			 */

			/* 点击图片框后状态 */
			clockLL.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (onItemRemindTapListener != null)
						onItemRemindTapListener.OnItemTap(item);
				}
			});

			nameLL.setOnClickListener(this.onClickListener);

		} else {
			popEidtView.findViewById(R.id.ll_cmr_name).setBackgroundResource(
					R.drawable.bg_mappop_remind_left1);
			popEidtView.findViewById(R.id.ll_cmr_clock)
					.setVisibility(View.GONE);
		}

		MapView.LayoutParams lp = new MapView.LayoutParams(
				MapView.LayoutParams.WRAP_CONTENT,
				MapView.LayoutParams.WRAP_CONTENT, 
				item.getPoint(),
				MapView.LayoutParams.BOTTOM_CENTER);

		lp.y = -DensityUtil.dip2px(mContext, 10);

		popEidtView.setLayoutParams(lp);
		popEidtView.setVisibility(View.VISIBLE);
		popEidtView.bringToFront();
		mv.refresh();

		adjustPop(popEidtView);
	}

	private void adjustPop(View popEidtView2) {
		//计算弹出框位置校准
		MapView.LayoutParams lp = ((MapView.LayoutParams) popEidtView2
				.getLayoutParams());
		Point sp = new Point();
		mv.getProjection().toPixels(lp.point, sp);//计算弹出框的锚点在地图控件里的相对位置，锚点就是站点所在位置

		int width = popEidtView2.getWidth();//弹出框宽度
		int height = popEidtView2.getHeight();//弹出框高度
		int left = sp.x - width / 2 + lp.x;//计算弹出框的左上角的x
		int top = sp.y - height / 2 + lp.y;//计算弹出框的左上角的y
		int scrollx = 0;
		int scrolly = 0;
		int padding=DensityUtil.dip2px(mContext, 2);//设置弹出框自适应后的离四周的最小边距
		
		//计算弹出框是不是被盖住
		/*if (left < 0) {
			
			//左边情况计算
			scrollx = left+padding;
		} else if (left + width > mv.getWidth()) {
			
			//右边情况计算
			scrollx = -mv.getWidth()+ left + width-padding;
		}*/
		
		if (left < 0) {
			
			//左边情况计算
			scrollx = left-padding*5;
		} else if (left + width > mv.getWidth()) {
			
			//右边情况计算
			scrollx = -mv.getWidth()+ left + width + padding*3;
		}


		/*if (top < 0) {
			scrolly = top+padding;
		} else if (top + height > mv.getHeight()) {
			scrolly = -mv.getHeight() + top+ height-padding;
		}*/
		
		if (top < 0) {
			scrolly = top-padding*10;
			
		} else if (top + height > mv.getHeight()) {
			scrolly = -mv.getHeight() + top+ height+padding;
		}

		//计算出来的scrollx和scrolly就是需要偏移的值，弹出框偏移其实就是地图偏移，也就是中心点偏移
		Point cp=new Point(mv.getCenterPixel());//
		cp.offset(scrollx, scrolly);
		mv.getController().animateTo(mv.getProjection().fromPixels(cp.x, cp.y));
//		mv.getController().scrollBy(scrollx,
//				 scrolly);
//		Logger.i(getClass(), "adjustPop  width:" + width + ",height:" + height
//				+ ",left:" + left + ",top:" + top + ",scrollx:" + scrollx
//				+ ",scrolly:" + scrolly);

	}

	/**
	 * 导入站点数据
	 * 
	 * @param data
	 */
	public void loadData(List<StationItem> data) {
		
		removeAll();
		
		if (mldata == null) {
			mldata = new LinkedList<StationOverlay.StationItem>();
		} else {
			mldata.clear();
		}
		
		mldata.addAll(data);
		
		if (mData == null)
			return;
		for (StationItem item : mldata) {
			addItem(item);
		}
		
		mv.refresh();
	}

	public void removeAll() {
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
//			Logger.d(getClass(), "ID为[" + id + "]的站点设置与原来一样");
			return;
		}
		Logger.d(getClass(), "设置[" + id + "]的站点提醒为：" + remind);
		mData.get(id).setRemind(remind);

		/*
		 * if (remind) { removeFromNormal(id); addToRemind(id); } else {
		 * removeFromRemind(id); addToNormal(id); }
		 */

	}

	public void setItemRemindStatus(String id, boolean remind) {
		if (!mData.containsKey(id)) {
			Logger.d(getClass(), "ID为[" + id + "]的站点不存在");
			return;
		}

		if (mData.get(id).isRemind() == remind) {
//			Logger.d(getClass(), "ID为[" + id + "]的站点设置与原来一样");
			return;
		}

		Logger.d(getClass(), "设置[" + id + "]的站点提醒为：" + remind);
		mData.get(id).setRemind(remind);

		if (remind) {
			removeFromNormal(id);
			addToRemind(id);
		} else {
			removeFromRemind(id);
			addToNormal(id);
		}

		if (currentPopId != null && currentPopId.equals(id)
				&& popEidtView.getVisibility() == View.VISIBLE) {
			showPopView(mData.get(id));
		}

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

		/* 正常站点编辑图标 */
		for (OverlayItem oi : editol.getAllItem()) {
			StationItem si = mData.get(oi.getSnippet());
			if (si.isRemind()) {
				addToRemind(si.getId());
			} else {
				addToNormal(si.getId());
			}
		}

		/* 提醒站点编辑图标 */
		for (OverlayItem oi : editol_remind.getAllItem()) {
			StationItem si = mData.get(oi.getSnippet());
			if (si.isRemind()) {
				addToRemind(si.getId());
			} else {
				addToNormal(si.getId());
			}
		}

		/* 取消编辑状态 */
		editol.removeAll();
		editol_remind.removeAll();

		popEidtView.setVisibility(View.GONE);
		mv.refresh();
	}

	public void addToEdit(String id) {
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

	public void changeToEdit(StationItem item) {
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

	public void addItem(StationItem item) {
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

	/* 将原来的站点擦除在描点 */
	public void updateItem(StationItem item) {

		if (item == null) {
			return;
		}

		if (mldata.contains(item)) {
			int itemIndex = mldata.indexOf(item);
			mldata.set(itemIndex, item);
		}

		Logger.i(getClass(), "正在将[ StationID:" + item.getId() + " StationName:"
				+ item.getName() + "]状态更新");

		/* 清空编辑状态的站点 */
		clearAllFromEdit();
		addItem(item);
		addToEdit(item.getId());
		
		if (item.isRemind()) {
			removeFromNormal(item.getId());
			addToRemind(item.getId());
		} else {
			removeFromRemind(item.getId());
			addToNormal(item.getId());
		}
		
		showPopView(item);
		mv.refresh();

		Logger.i(getClass(), "将[ StationID:" + item.getId() + " StationName:"
				+ item.getName() + "]状态更新成功");

	}

	private void addToRemind(StationItem item) {
		Logger.i(getClass(), "将[" + item.getId()
				+ "]添加到提醒状态addToRemind-StationItem");
		remindol.addItem(getOverItemFromStationItem(item));
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

	public void forceToListener(final StationItem station) {
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
		OverlayItem item = new OverlayItem(station.point, station.getName(),
				station.getId());
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
			OverlayItem nitem = new OverlayItem(item.getPoint(), "",
					item.getId());
			nitem.setAnchor(OverlayItem.ALIGN_BOTTON);
			startol.addItem(nitem);
			break;
		case END:
			OverlayItem enditem = new OverlayItem(item.getPoint(), "",
					item.getId());
			enditem.setAnchor(OverlayItem.ALIGN_BOTTON);
			endol.addItem(enditem);
			break;
		default:
			break;
		}

	}

	public void drawStationName(final StationItem item) {
		if (!needStationName) {
			return;
		}
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

	public void setOnClickListener(View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

}
