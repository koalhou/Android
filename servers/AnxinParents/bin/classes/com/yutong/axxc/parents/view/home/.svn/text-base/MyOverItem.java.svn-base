/*******************************************************************************
 * @(#)MyOverItem.java 2013-2-27
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.SumPathEffect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.business.beans.BizOverlayItemBean;
import com.yutong.axxc.parents.common.GlobleConstants;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.view.common.YtApplication;
import com.yutong.axxc.parents.view.util.DensityUtil;

/**
 * 地图经纬度点显示封装
 * 
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-2-27 下午04:20:19
 */
public class MyOverItem extends ItemizedOverlay<OverlayItem> {
	private static List<BizOverlayItemBean> overlayItems = new ArrayList<BizOverlayItemBean>();

	private Activity activity;

	private float phase;

	private LayoutInflater inflater;

	private View view;

	private TextView textView;

	private ImageView busStateIma;

	private String busState = GlobleConstants.EMPTY_STR;

	private Drawable marker;

	public MyOverItem(Drawable arg0) {
		super(arg0);
		this.activity = activity;
		this.overlayItems = overlayItems;
		inflater = activity.getLayoutInflater();
		populate();
	}

	public MyOverItem(Activity activity, List<BizOverlayItemBean> overlayItems) {
		super(activity.getResources().getDrawable(
				(Integer) (overlayItems.get(0).getMarker())));
		this.activity = activity;
		this.overlayItems = overlayItems;
		inflater = activity.getLayoutInflater();
		populate();

	}

	public static void clearCach() {
		List<LinearLayout> linearLayouts = YtApplication.getInstance()
				.getLinearLayouts();
		if (linearLayouts.size() != 0) {
			for (LinearLayout layout : linearLayouts) {
				// layout.findViewById(R.id.busNameTx).setBackgroundDrawable(null);
				// ((ImageView)
				// layout.findViewById(R.id.busStateIma)).setImageDrawable(null);
				// layout.setBackgroundDrawable(null);
				// layout.destroyDrawingCache();
			}
		}
		linearLayouts.clear();
		Logger.i(MyOverItem.class, "清除图片缓存");
	}

	public void updateOverlay() {
		populate();
	}

	@Override
	protected OverlayItem createItem(int position) {
		OverlayItem item = getOverlayItem(position);
		Drawable marker;
		if (overlayItems.get(position).getVehicleDetail() == null) {
			marker = getMarkerDrawable(position);
		} else {
			marker = getMarkerFromCach(position);

		}
		// 控制图片显示位置
		marker.setBounds(-marker.getIntrinsicWidth()
				/ YtApplication.getInstance().getOFFSET(),
				-marker.getIntrinsicHeight()
						/ YtApplication.getInstance().getOFFSET(),
				marker.getIntrinsicWidth()
						/ YtApplication.getInstance().getOFFSET(),
				marker.getIntrinsicHeight()
						/ YtApplication.getInstance().getOFFSET()); // 为maker定义位置和边界
		item.setMarker(marker);
		return item;
	}

	@Override
	public int size() {
		return overlayItems.size();
	}

	public OverlayItem getOverlayItem(int position) {
		return overlayItems.get(position).getOverlayItem();
	}

	public Drawable getMarkerDrawable(int position) {
		Drawable drawable = activity.getResources().getDrawable(
				overlayItems.get(position).getMarker());
		return drawable;
	}

	/**
	 * 将图片放到View中，然后将View转换为bitMap,最后输出drawable
	 * 
	 * @param resId
	 * @return
	 */
	public Drawable getMarkerFromCach(int position) {
		Drawable drawable = convertHandMarker(position);
		return drawable;
	}

	public Drawable convertHandMarker(int position) {
		// view = inflater.inflate(R.layout.bus_map_marker, null);
		// textView = (TextView) view.findViewById(R.id.busNameTx);
		// busStateIma = (ImageView) view.findViewById(R.id.busStateIma);
		// // 如果当前是车辆车牌号，则显示相应车牌号，背景变为相应的车辆状态（告警、离线、正常在线）,否则显示站点名称
		// if (overlayItems.get(position).getVehicleDetail() != null) {
		// busState =
		// overlayItems.get(position).getVehicleDetail().getvMapStatus();
		// if ((VConstants.V_NORMAL).equals(busState)) {
		// textView.setBackgroundResource(R.drawable.bus_on);
		// textView.setTextColor(Color.WHITE);
		// } else if (VConstants.V_WARNING.equals(busState)) {
		// // add by wenxw 20130805如果车辆离线，显示为灰色否则，显示为红色
		// if
		// (VConstants.V_OFFLINE.equals(overlayItems.get(position).getVehicleDetail().getvStatus()))
		// {
		// textView.setBackgroundResource(R.drawable.bus_off);
		// textView.setTextColor(Color.GRAY);
		// } else {
		// textView.setBackgroundResource(R.drawable.bus_warn);
		// textView.setTextColor(Color.WHITE);
		// }
		// } else if ((VConstants.V_OFFLINE).equals(busState)) {
		// textView.setBackgroundResource(R.drawable.bus_off);
		// textView.setTextColor(Color.GRAY);
		// }
		// textView.setText(overlayItems.get(position).getVehicleDetail().getName());
		// }
		// busStateIma.setImageResource(overlayItems.get(position).getMarker());
		// LinearLayout busbaseLL = (LinearLayout)
		// view.findViewById(R.id.busbaseLL);
		// busbaseLL.setDrawingCacheEnabled(true);
		// busbaseLL.measure(MeasureSpec.makeMeasureSpec(10000,
		// MeasureSpec.UNSPECIFIED),
		// MeasureSpec.makeMeasureSpec(10000, MeasureSpec.UNSPECIFIED));
		// busbaseLL.layout(0, 0, busbaseLL.getMeasuredWidth(),
		// busbaseLL.getMeasuredHeight());
		// busbaseLL.buildDrawingCache();
		// Drawable drawable = new BitmapDrawable(busbaseLL.getDrawingCache());
		// YtApplication.getInstance().getLinearLayouts().add(busbaseLL);
		// return drawable;
		return null;
	}

	public Drawable getCachDrawable(int position) {
		return new BitmapDrawable(YtApplication.getInstance()
				.getLinearLayouts().get(position).getDrawingCache());
	}

	public static GeoPoint getGeoPoint(double latitude, double longitude) {
		GeoPoint point = new GeoPoint((int) (latitude * 1E6),
				(int) (longitude * 1E6));

		return point;
	}

	private static OverlayItem getOverlayItem(GeoPoint geoPoint, String title,
			String snippet) {
		return new OverlayItem(geoPoint, title, snippet);
	}

	/**
	 * 生成可显示点
	 * 
	 * @param latitude
	 * @param longitude
	 * @param marker
	 * @return
	 */
	public static BizOverlayItemBean getOverlayItems(double latitude,
			double longitude, int marker, String title, String snippet) {
		BizOverlayItemBean overlayItem = new BizOverlayItemBean(
				R.drawable.bus_ico);
		GeoPoint point = getGeoPoint(latitude, longitude);
		OverlayItem item = MyOverItem.getOverlayItem(point, title, snippet);
		overlayItem.setOverlayItem(item);
		overlayItem.setMarker(marker);
		return overlayItem;
	}

	@Override
	public void draw(Canvas arg0, MapView arg1, boolean arg2) {
		try {
			super.draw(arg0, arg1, arg2);
		} catch (Exception e) {
			Logger.i(getClass(), "加载数据不正确");
		}
	}

	PaintFlagsDrawFilter dfilter = new PaintFlagsDrawFilter(0,
			Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

	/**
	 * 默认的画线实现
	 * 
	 * @param canvas
	 * @param mapview
	 * @param shadow
	 */
	protected void defaultDraw(Canvas canvas, MapView mapview, boolean shadow,
			Paint paint) {
		// add by zhouzc 美化绘图
		canvas.setDrawFilter(dfilter);
		canvas.drawARGB(0, 0, 0, 0);
		if (overlayItems == null || overlayItems.size() <= 0)
			return;
		Projection projection = mapview.getProjection();
		ArrayList<Point> points = new ArrayList<Point>();

		for (int position = 0; position < overlayItems.size(); position++) {
			Point p = new Point();
			projection.toPixels(getOverlayItem(position).getPoint(), p);
			points.add(p);
		}
		Path path = new Path();
		path.setFillType(FillType.WINDING);
		path.moveTo(points.get(0).x, points.get(0).y);
		for (int pointPos = 1; pointPos < points.size(); pointPos++) {
			path.lineTo(points.get(pointPos).x, points.get(pointPos).y);
		}
		populate();
		// 画出路径
		canvas.drawPath(path, paint);
		// 这里注释掉，性能大增。
		// super.draw(canvas, mapview, shadow);
		// 调整一个drawable边界，使得（0，0）是这个drawable底部最后一行中心的一个像素
		for (int drawablePos = 0; drawablePos < overlayItems.size(); drawablePos++) {
			boundCenter(getMarkerDrawable(drawablePos));
		}
	}

	/**
	 * 绘制文本
	 * 
	 * @param canvas
	 * @param mapview
	 * @param shadow
	 */
	protected void mTextDraw(Canvas canvas, MapView mapview, boolean shadow,
			Paint paint) {
		// Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换
		Projection projection = mapview.getProjection();
		// 遍历所有的OverlayItem
		for (int index = this.size() - 1; index >= 0; index--) {
			// 得到给定索引的item
			OverlayItem overLayItem = getItem(index);

			// 把经纬度变换到相对于MapView左上角的屏幕像素坐标
			Point point = projection.toPixels(overLayItem.getPoint(), null);

			Paint paintText = new Paint();
			// 绘制文本
			canvas.drawText(overLayItem.getTitle(),
					point.x + DensityUtil.dip2px(this.activity, 10), point.y
							- DensityUtil.dip2px(this.activity, 15), paintText);
		}
		super.draw(canvas, mapview, shadow);
		boundCenterBottom(marker);
	}

	/**
	 * 转换经纬度点为像素点
	 * 
	 * @param mapView
	 * @param point
	 * @return
	 */
	public static Point getPointPixels(MapView mapView, GeoPoint point) {
		Projection projection = mapView.getProjection();
		Point p = new Point();
		projection.toPixels(point, p);
		return p;

	}

	/**
	 * 删除所有位置信息
	 */
	public void removeAllView() {
		this.overlayItems.clear();
	}

	/**
	 * 定义路径动画
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unused")
	private PathEffect[] initPathEffect(Path path) {
		PathEffect[] effects = new PathEffect[7];
		effects[0] = null;
		// 使用CornerPathEffect路径效果
		effects[1] = new CornerPathEffect(10);
		// 初始化DiscretePathEffect
		effects[2] = new DiscretePathEffect(3.0f, 5.0f);
		// 初始化DashPathEffect
		effects[3] = new DashPathEffect(new float[] { 20, 10, 5, 10 }, phase);
		// 初始化PathDashPathEffect
		// 连接 所有点
		path.addRect(0, 0, 8, 8, Path.Direction.CCW);
		effects[4] = new PathDashPathEffect(path, 12, phase,
				PathDashPathEffect.Style.ROTATE);
		// 初始化PathDashPathEffect
		effects[5] = new ComposePathEffect(effects[2], effects[1]);
		effects[6] = new SumPathEffect(effects[4], effects[3]);
		return effects;
	}
}
