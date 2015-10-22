package com.yutong.axxc.parents.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.baidu.mapapi.GeoPoint;
import com.baidu.mapapi.MapView;
import com.yutong.axxc.parents.common.Logger;

/**
 * 自定义MapView
 * 
 * @author lizyi
 * 
 */
public class EnhancedMapView extends MapView
{
	// 地图缩放监听
	public interface OnZoomChangeListener
	{
		public void onZoomChange(MapView view, int newZoom, int oldZoom);
	}

	// 平移监听
	public interface OnPanChangeListener
	{
		public void onPanChange(MapView view, GeoPoint newCenter,
				GeoPoint oldCenter);
	}

	private EnhancedMapView _this;

	private boolean is_touched = false;

	private GeoPoint last_center_pos;

	private int last_zoom;
	private Context ctx;
	private EnhancedMapView.OnZoomChangeListener zoom_change_listener;

	private EnhancedMapView.OnPanChangeListener pan_change_listener;

	public EnhancedMapView(Context context)
	{
		super(context);
		_this = this;
		_this.ctx = context;
		last_center_pos = this.getMapCenter();
		last_zoom = this.getZoomLevel();
	}

	public EnhancedMapView(Context context, String apiKey)
	{
		super(context);
		_this = this;
		_this.ctx = context;
		last_center_pos = this.getMapCenter();
		last_zoom = this.getZoomLevel();

	}

	public EnhancedMapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		_this = this;
		_this.ctx = context;
	}

	public EnhancedMapView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		_this = this;
		_this.ctx = context;
	}

	public void setOnZoomChangeListener(EnhancedMapView.OnZoomChangeListener l)
	{
		zoom_change_listener = l;
	}

	public void setOnPanChangeListener(EnhancedMapView.OnPanChangeListener l)
	{
		pan_change_listener = l;
	}

	private float baseValue = 0;

	private boolean isShow = false;

	// @Override
	// public boolean onTouchEvent(MotionEvent event)
	// {
	// if (event.getAction() == MotionEvent.ACTION_UP)
	// {
	// is_touched = false;
	// }
	// else
	// {
	// is_touched = true;
	// }
	//
	// if (event.getAction() == MotionEvent.ACTION_DOWN)
	// {
	// baseValue = 0;
	// isShow = false;
	// }
	// if (event.getAction() == MotionEvent.ACTION_MOVE)
	// {
	// if (event.getPointerCount() == 2)
	// {
	// float x = event.getX(0) - event.getX(1);
	// float y = event.getY(0) - event.getY(1);
	// float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
	// if (baseValue == 0)
	// {
	// baseValue = value;
	// }
	// else
	// {
	// // 当前两点间的距离大于手指落下时的距离,且当前是地图的最大级别
	// // 0720版本增加了对最小地图的限制
	// if (value > baseValue
	// && getZoomLevel() == getMaxZoomLevel())
	// {
	// if (!isShow)
	// {
	// isShow = true;
	// Toast.makeText(ctx, "当前已是最大级别", 0).show();
	// }
	// return false;
	// }
	// else if (value < baseValue
	// && getZoomLevel() == getMinZoomLevel())
	// {
	// if (!isShow)
	// {
	// isShow = true;
	// Toast.makeText(ctx, "当前已是最小级别", 0).show();
	// }
	// }
	// else
	// {
	// return super.onTouchEvent(event);
	// }
	// }
	// }
	// }
	// if (isShow)
	// {
	// return false;
	// }
	// else
	// {
	// return super.onTouchEvent(event);
	// }
	// }

	// @Override
	// public void computeScroll()
	// {
	// super.computeScroll();
	// if (zoom_change_listener != null && getZoomLevel() != last_zoom)
	// {
	// zoom_change_listener.onZoomChange(_this, getZoomLevel(), last_zoom);
	// last_zoom = getZoomLevel();
	// Logger.d(EnhancedMapView.class, "当前地图缩放级别" + getZoomLevel());
	// last_center_pos = getMapCenter();
	// }
	// if (pan_change_listener != null
	// && !getMapCenter().equals(last_center_pos) || !is_touched)
	// {
	// if (pan_change_listener != null)
	// {
	// pan_change_listener.onPanChange(_this, getMapCenter(),
	// last_center_pos);
	//
	// }
	// }
	// if (last_zoom == getMaxZoomLevel())
	// {
	// Logger.d(EnhancedMapView.class, "当前地图最大缩放级别" + getMaxZoomLevel());
	// getController().setZoom(last_zoom);
	// }
	// }
}
