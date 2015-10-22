package com.yutong.axxc.parents.view.home;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.ItemizedOverlay;
import com.baidu.mapapi.MapView;
import com.baidu.mapapi.MapView.LayoutParams;
import com.baidu.mapapi.OverlayItem;
import com.baidu.mapapi.Projection;
import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.settings.child.RemindSetActivity;
import com.yutong.axxc.parents.view.util.DensityUtil;

public class MyOverItemT extends ItemizedOverlay<OverlayItem>
{
	public static List<OverlayItem> mGeoList = new ArrayList<OverlayItem>();
	public static GeoPoint lastGepoint;
	public static int lastZoom;
	private Drawable marker;// 标记
	private Activity ma;// 上下文
	public List<GeoPoint> gpl = new ArrayList<GeoPoint>();
	Projection projection;

	public MyOverItemT(Drawable arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	// 初始化，生成图层的item。
	public MyOverItemT(Drawable marker, Activity context, List<GeoPoint> gplist)
	{
		super(boundCenterBottom(marker));
		this.marker = marker;
		this.ma = context;
		populate();// 更新
	}

	// 核心的方法 1 draw：将图层上的item绘制出来 2 onTap：触发每个item的时候响应动作
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow)
	{
		// 将List<OverlayItem> mGeoList 全部绘制出来
		// Projection接口用于屏幕像素坐标和经纬度坐标之间的变换
		projection = mapView.getProjection();
		// 得到OverlayItem，然后转换成point点
		// 然后根据这个点绘制
		for (int index = size() - 1; index >= 0; index--)
		{
			OverlayItem overLayItem = getItem(index);
			String title = overLayItem.getTitle();
			Point point = projection.toPixels(overLayItem.getPoint(), null);
			Paint paintText = new Paint();
			paintText.setColor(Color.BLACK);
			paintText.setTextSize(DensityUtil.sp2px(this.ma, 13));
//			消除锯齿
			paintText.setAntiAlias(true);
			canvas.drawText(title, point.x + DensityUtil.dip2px(this.ma, 20), point.y, paintText); // 绘制文本
		}
		super.draw(canvas, mapView, shadow);
		boundCenterBottom(marker);
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1)
	{
		return super.onTap(arg0, arg1);
	}

	@Override
	protected boolean onTap(int i)
	{
		return true;
	}

	@Override
	protected OverlayItem createItem(int arg0)
	{
		// TODO Auto-generated method stub
		return mGeoList.get(arg0);
	}

	@Override
	public int size()
	{
		// TODO Auto-generated method stub
		return mGeoList.size();
	}

}
