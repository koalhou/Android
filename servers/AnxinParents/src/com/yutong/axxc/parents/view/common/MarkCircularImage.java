package com.yutong.axxc.parents.view.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.yutong.axxc.parents.R;
import com.yutong.axxc.parents.view.util.DensityUtil;

public class MarkCircularImage extends CircularImage {

	public MarkCircularImage(Context paramContext) {
		super(paramContext);
		// TODO Auto-generated constructor stub
	}

	public MarkCircularImage(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		// TODO Auto-generated constructor stub
	}

	public MarkCircularImage(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas paramCanvas) {
		super.onDraw(paramCanvas);
		if (needMark)
			drawMark(paramCanvas);
		if (!message.equals("")) {
			drawMessage(paramCanvas, message);
		}

	}

	private String message = "3";

	private boolean needMark = false;

	public boolean isNeedMark() {
		return needMark;
	}

	public void setNeedMark(boolean needMark) {
		this.needMark = needMark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private void drawMark(Canvas paramCanvas) {
		paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
				Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		Paint markPaint = new Paint();
		markPaint.setAntiAlias(true);

		Bitmap bmp = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.selectedmark)).getBitmap();

		int bwidth = bmp.getWidth();
		int bheight = bmp.getHeight();

		int pwidth = this.getWidth();
		int pheight = this.getHeight();

		Rect brec = new Rect(0, 0, bwidth, bheight);

		int dheight = pheight / 3;
		int dwidth = dheight * pwidth / pheight;

		int left = pwidth - dwidth;
		int top = pheight - dheight;
		int right = pwidth;
		int bottom = pheight;

		RectF dstRec = new RectF(left, top, right, bottom);
		paramCanvas.drawBitmap(bmp, brec, dstRec, markPaint);
	}

	private void drawMessage(Canvas paramCanvas, String msg) {
		paramCanvas.setDrawFilter(new PaintFlagsDrawFilter(0,
				Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
		// Log.d("", "drawMessage :" + msg);
		float p_w = getWidth();
		float p_h = getHeight();

		float draw_padding = DensityUtil.dip2px(getContext(), 0);//
		float border_w = DensityUtil.dip2px(getContext(), 2);//
		float txt_padding = DensityUtil.dip2px(getContext(), 3);//

		Paint txtPaint = new Paint();
		txtPaint.setARGB(255, 151, 98, 44);
		txtPaint.setTextSize(DensityUtil.sp2px(getContext(), 14));
		txtPaint.setTextAlign(Paint.Align.LEFT);
		txtPaint.setAntiAlias(true);
		Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		txtPaint.setTypeface(font);
		Rect txtbounds = new Rect();

		txtPaint.getTextBounds("13", 0, 2, txtbounds);
		float t_w = txtPaint.measureText(msg);// txtbounds.width();//
		float t_h = txtbounds.height();

		Paint bgPaint = new Paint();
		bgPaint.setARGB(255, 255, 199, 61);
		txtPaint.setAntiAlias(true);

		Paint borderPaint = new Paint();
		borderPaint.setARGB(255, 255, 255, 255);
		txtPaint.setAntiAlias(true);

		float left = p_w - t_w - draw_padding - 2 * border_w - 2 * txt_padding;
		float top = draw_padding;
		float right = p_w - draw_padding;
		float bottom = draw_padding + 2 * txt_padding + t_h + 2 * border_w;

		float rwidth = right - left;
		float rheight = bottom - top;
		if (rheight > rwidth) {
			left = left - (rheight - rwidth);
		}

		RectF outrect = new RectF(left, top, right, bottom);

		paramCanvas.drawRoundRect(outrect, outrect.height(), outrect.height(),
				borderPaint);

		RectF inrect = new RectF(left + border_w, top + border_w, right
				- border_w, bottom - border_w);

		paramCanvas.drawRoundRect(inrect, inrect.height(), inrect.height(),
				bgPaint);

		float t_x = (right + left) / 2 - t_w / 2;
		float t_y = (bottom + top) / 2 + t_h / 2;

		// Log.d("", "drawMessage :" + msg + "[" + t_x + "," + t_y + "]t_h " +
		// t_h);
		paramCanvas.drawText(msg, t_x, t_y, txtPaint);

	}
}
