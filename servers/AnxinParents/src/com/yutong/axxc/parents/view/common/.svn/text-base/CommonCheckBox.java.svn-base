package com.yutong.axxc.parents.view.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.util.DensityUtil;

public class CommonCheckBox extends CheckBox implements OnGestureListener,
		OnTouchListener {

	GestureDetector gdetecter = null;

	public CommonCheckBox(Context context) {
		super(context);
		commonInitial();
	}

	public CommonCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		commonInitial();
	}

	public CommonCheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		commonInitial();
	}

	private void commonInitial() {
		gdetecter = new GestureDetector(getContext(), this);
		this.setOnTouchListener(this);
		dfilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG);
	}

	boolean onceDraw = false;
	private float xdelta = 0;
	private float xdown = 0;
	private float xorigin = 0;
	private float xtnow = 0;
	private boolean needXdelta = false;
	private Object dlock = new Object();

	float blockpadding = DensityUtil.dip2px(getContext(), 2);
	float blockwidth;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		return super.onTouchEvent(event);
	}

	PaintFlagsDrawFilter dfilter;

	@Override
	protected void onDraw(Canvas canvas) {

		if (isInEditMode()) {
			super.onDraw(canvas);
			return;
		}

		synchronized (dlock) {
			if (onceDraw) {
				return;
			}
			onceDraw = true;
			canvas.setDrawFilter(dfilter);
			if (drawCheck) {
				drawChecked(canvas);
			} else {
				drawUnChecked(canvas);
			}
			onceDraw = false;

		}
	}

	private void drawBg(Canvas canvas, int color) {
		canvas.drawARGB(0, 0, 0, 0);
		int pwidth = getWidth();
		int pheight = getHeight();
		Paint bgPaint = new Paint();
		bgPaint.setDither(true);
		bgPaint.setStyle(Style.FILL_AND_STROKE);
		// bgPaint.setStrokeWidth(2);
		bgPaint.setStrokeJoin(Join.ROUND);
		bgPaint.setStrokeCap(Cap.ROUND);
		bgPaint.setStrokeMiter(30);
		bgPaint.setColor(color);
		int bgradius = pwidth / 2;

		Path bgpath = getPath(pwidth, pheight, bgradius);
		canvas.drawPath(bgpath, bgPaint);
	}

	private Path getPath(int pwidth, int pheight, int bgradius) {
		boolean draw180 = true;
		int offsetpx = DensityUtil.dip2px(getContext(), 5);
		Path bgpath = new Path();
		bgpath.moveTo(bgradius, 0);
		bgpath.lineTo(pwidth - bgradius + offsetpx, 0);
		if (draw180) {
			RectF recright = new RectF(pwidth - bgradius + offsetpx, 0, pwidth,
					pheight);
			bgpath.arcTo(recright, 270, 180);

		} else {
			RectF recrighttop = new RectF(pwidth - bgradius, 0, pwidth,
					bgradius);
			bgpath.arcTo(recrighttop, 270, 90);
			bgpath.lineTo(pwidth, pheight - bgradius);
			RectF recrightbottom = new RectF(pwidth - bgradius, pheight
					- bgradius, pwidth, pheight);
			bgpath.arcTo(recrightbottom, 0, 90);
		}
		bgpath.lineTo(bgradius - offsetpx, pheight);
		if (draw180) {
			RectF recleft = new RectF(0, 0, bgradius - offsetpx, pheight);
			bgpath.arcTo(recleft, 90, 180);

		} else {
			RectF recleftbottom = new RectF(0, pheight - bgradius, bgradius,
					pheight);
			bgpath.arcTo(recleftbottom, 90, 90);
			bgpath.lineTo(0, bgradius);
			RectF reclefttop = new RectF(0, 0, bgradius, bgradius);
			bgpath.arcTo(reclefttop, 180, 90);
		}
		bgpath.close();
		return bgpath;
	}

	private void drawUnChecked(Canvas canvas) {
		// Log.d(getClass().toString(), "drawUnChecked");
		int pwidth = getWidth();
		int pheight = getHeight();
		drawBg(canvas, Color.argb(255, 143, 143, 143));

		String text = "关";

		Paint txtPaint = new Paint();
		txtPaint.setARGB(255, 185, 185, 185);
		txtPaint.setTextSize(this.getTextSize());
		txtPaint.setTextAlign(Paint.Align.LEFT);
		txtPaint.setAntiAlias(true);
		// Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		// txtPaint.setTypeface(font);
		txtPaint.setTypeface(this.getTypeface());

		Rect txtbounds = new Rect();
		txtPaint.getTextBounds(text, 0, text.length(), txtbounds);

		float t_w = txtbounds.width();//
		float t_h = txtbounds.height();

		// 左下角
		float t_x = (pwidth + blockwidth) / 2 - t_w / 2;
		float t_y = pheight / 2 + t_h / 2 - DensityUtil.dip2px(getContext(), 2);

		canvas.drawText(text, t_x, t_y, txtPaint);

		Paint bgPaint = new Paint();
		Bitmap blockbmp = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.switch_close_button)).getBitmap();

		/*
		 * float blockwidth = (float) (pwidth / 3); blockwidth = blockwidth >
		 * (pheight - 2 * blockpadding) ? (pheight - 2 * blockpadding) :
		 * blockwidth;
		 */
		blockwidth = (pheight - 2 * blockpadding);
		float blockheight = blockwidth;

		float blockleft = blockpadding;
		if (needXdelta) {
			float maxblockx = pwidth - blockpadding - blockwidth;
			float minblockx = blockpadding;
			blockleft = xtnow;
			if (blockleft < minblockx)
				blockleft = minblockx;
			if (blockleft > maxblockx)
				blockleft = maxblockx;
		}

		float blocktop = (pheight - blockheight) / 2;
		float blockright = blockwidth + blockleft;
		float blockbottom = (pheight - blockheight) / 2 + blockheight;

		RectF blockrec = new RectF(blockleft, blocktop, blockright, blockbottom);
		canvas.drawBitmap(blockbmp, new Rect(0, 0, blockbmp.getWidth(),
				blockbmp.getHeight()), blockrec, bgPaint);

	}

	private void drawChecked(Canvas canvas) {
		// Log.d(getClass().toString(), "drawChecked");
		int pwidth = getWidth();
		int pheight = getHeight();
		drawBg(canvas, Color.argb(255, 28, 151, 152));

		String text = "开";

		Paint txtPaint = new Paint();
		txtPaint.setARGB(255, 251, 255, 255);
		txtPaint.setTextSize(this.getTextSize());
		txtPaint.setTextAlign(Paint.Align.LEFT);
		txtPaint.setAntiAlias(true);
		txtPaint.setTypeface(this.getTypeface());
		// Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		// txtPaint.setTypeface(font);
		Rect txtbounds = new Rect();
		txtPaint.getTextBounds(text, 0, text.length(), txtbounds);

		float t_w = txtbounds.width();//
		float t_h = txtbounds.height();

		float t_x = (pwidth - blockwidth - t_w) / 2;
		float t_y = pheight / 2 + t_h / 2 - DensityUtil.dip2px(getContext(), 2);

		canvas.drawText(text, t_x, t_y, txtPaint);

		Paint bgPaint = new Paint();
		bgPaint.setAntiAlias(true);

		Bitmap blockbmp = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.switch_open_button)).getBitmap();

		blockwidth = (pheight - 2 * blockpadding);

		float blockheight = blockwidth;

		float blockleft = pwidth - blockpadding - blockwidth;
		if (needXdelta) {
			float maxblockx = pwidth - blockpadding - blockwidth;
			float minblockx = blockpadding;
			blockleft = xtnow;
			if (blockleft < minblockx)
				blockleft = minblockx;
			if (blockleft > maxblockx)
				blockleft = maxblockx;
		}
		float blocktop = (pheight - blockheight) / 2;
		float blockright = blockleft + blockwidth;
		float blockbottom = (pheight - blockheight) / 2 + blockheight;

		RectF blockrec = new RectF(blockleft, blocktop, blockright, blockbottom);
		canvas.drawBitmap(blockbmp, new Rect(0, 0, blockbmp.getWidth(),
				blockbmp.getHeight()), blockrec, bgPaint);

	}

	@Override
	public boolean onDown(MotionEvent e) {

		return false;// 设置为true，屏蔽按下事件,onFling事件才能被触发
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > 40 && Math.abs(velocityX) > 0) {
			if (this.isChecked()) {
				this.setChecked(false);
			}// 向左滑动
		} else if (e2.getX() - e1.getX() > 40 && Math.abs(velocityX) > 0) {
			if (!this.isChecked()) {
				this.setChecked(true);
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		this.setChecked(!this.isChecked());
		return false;
	}

	boolean drawCheck = false;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Log.d(getClass().toString(), event.getAction() + "");
		switch (event.getAction()) {

		case MotionEvent.ACTION_DOWN:
			// Log.d(getClass().toString(), "MotionEvent.ACTION_DOWN");
			// 初始位置
			xorigin = isChecked() ? getWidth() - blockpadding - blockwidth
					: blockpadding;
			xdown = event.getX();
			xdelta = 0;
			xtnow = xorigin;
			drawCheck = isChecked();
			needXdelta = true;
			break;
		case MotionEvent.ACTION_MOVE:
			// Log.d(getClass().toString(), "MotionEvent.ACTION_MOVE");
			xdelta = event.getX() - xdown;
			xtnow = xorigin + xdelta;
			// 滑动超过一半切换显示状态
			if (xtnow + blockwidth / 2 > (this.getWidth() / 2)) {
				drawCheck = true;
			} else {
				drawCheck = false;
			}
			needXdelta = true;
			break;
		case MotionEvent.ACTION_UP:
			// Log.d(getClass().toString(), "MotionEvent.ACTION_UP");
			xdelta = event.getX() - xdown;
			if (xdelta == 0) {// 点击后反置状态
				needXdelta = false;
				drawCheck = !isChecked();
				setChecked(drawCheck);
				break;
			}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			needXdelta = false;
			setChecked(drawCheck);
			break;
		default:
			break;
		}
		invalidate();
		return true;
		// return gdetecter.onTouchEvent(event);
	}

	@Override
	public void setChecked(boolean checked) {
		// Log.d(getClass().toString(), "setChecked :" + checked);
		drawCheck = checked;
		super.setChecked(checked);
	}

}
