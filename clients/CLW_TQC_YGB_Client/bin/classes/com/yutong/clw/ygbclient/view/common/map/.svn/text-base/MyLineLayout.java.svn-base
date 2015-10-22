package com.yutong.clw.ygbclient.view.common.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.MapView;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * 使用控件方式画路线，锯齿问题得到改善，但是会有控件大小变化问题和飘动问题，暂时没有用到
 * 
 * @author zhouzc
 * 
 */
public class MyLineLayout extends LinearLayout {

	public MyLineLayout(Context context) {
		super(context);
	}

	public MyLineLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void initialView(MapView mapView, GeoPoint[] gpoints,
			GeoPoint centerPoint) {
		mMapView = mapView;
		mgpoints = gpoints;
		this.centergPoint = centerPoint;
		points = new Point[mgpoints.length];
		cpoint = new Point();
		lefttop = new Point();

		linePaint = new Paint();

		int color = Color.argb(255, 0, 0, 255);
		linePaint.setAntiAlias(true);
		linePaint.setColor(color);
		linePaint.setDither(true);
		linePaint.setStyle(Paint.Style.STROKE);
		linePaint.setStrokeJoin(Paint.Join.ROUND);
		linePaint.setStrokeCap(Paint.Cap.ROUND);
		linePaint.setStrokeWidth(4);

		linePaint.setPathEffect(new PathEffect());

		linePath = new Path();
		linePath.setFillType(FillType.WINDING);
		// 必须设置背景，否则不会调用onDraw方法重绘
		this.setBackgroundColor(Color.argb(255, 255, 0, 0));

	}

	GeoPoint[] mgpoints;
	GeoPoint centergPoint;
	MapView mMapView;

	Point[] points;
	Point cpoint;

	Point lefttop;

	Paint linePaint;

	Path linePath;

	PaintFlagsDrawFilter dfilter = new PaintFlagsDrawFilter(0,
			Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.setDrawFilter(dfilter);
		// super.onDraw(canvas);
		canvas.drawColor(Color.argb(0, 255, 0, 0), Mode.CLEAR);
		if (points == null || points.length <= 2)
			return;
		int mwidth = getWidth();
		int mheight = getHeight();

		reTranslateLocations();
		lefttop.set(cpoint.x - mwidth / 2, cpoint.y - mheight / 2);

		boolean hasfirst = false;
		for (int pointPos = 0; pointPos < points.length; pointPos++) {
			if (points[pointPos] == null) {
				// Log.d("", "skip -" + pointPos);
				continue;
			}
			int xn = points[pointPos].x - lefttop.x;
			int yn = points[pointPos].y - lefttop.y;
			if (!hasfirst) {

				linePath.moveTo(xn, yn);
				// Log.d("", "moveTo -[" + xn + "," + yn + "]" + pointPos);
				hasfirst = true;
			} else {
				linePath.lineTo(xn, yn);
				// Log.d("", "lineTo -[" + xn + "," + yn + "]" + pointPos);
			}

		}

		// 画出路径
		canvas.drawPath(linePath, linePaint);

	}

	private void reTranslateLocations() {

		mMapView.getProjection().toPixels(centergPoint, cpoint);
		for (int i = 0; i < mgpoints.length; i++) {
			// Log.d("", "translate -" + mgpoints[i]);
			points[i] = mMapView.getProjection().toPixels(mgpoints[i],
					points[i]);

		}

	}

}
