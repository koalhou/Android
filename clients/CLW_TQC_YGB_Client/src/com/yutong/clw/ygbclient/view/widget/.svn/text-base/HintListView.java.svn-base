/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2014-1-23 上午11:56:52
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import com.yutong.clw.ygbclient.view.util.DensityUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author zhouzc 2014-1-23 上午11:56:52
 * 
 */
public class HintListView extends ListView {

	public HintListView(Context context) {
		super(context);
	}

	public HintListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		try{
		if (attrs != null) {
			int[] attrids = new int[] { android.R.attr.hint,// 0
					android.R.attr.textSize,// 1
					android.R.attr.textColor // 2
			};
			TypedArray array = context.obtainStyledAttributes(attrs, attrids);
			String h = array.getString(0);
			if(null!=h){
				hint=h;
			}
			hintTextSize = array.getDimension(1, hintTextSize);
			hintTextColor = array.getColor(2, hintTextColor);
			array.recycle();
		}
		}catch(Exception ex){
			
		}
	}

	String hint = "无数据";
	float hintTextSize = 16f;
	int hintTextColor = Color.BLACK;

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (this.getChildCount() == 0) {
			drawHintText(canvas);
		}

	}

	private void drawHintText(Canvas canvas) {

		String text = hint;
		Paint txtPaint = new Paint();
		txtPaint.setColor(hintTextColor);
		txtPaint.setTextSize(DensityUtil.sp2px(getContext(), hintTextSize));
		txtPaint.setTextAlign(Paint.Align.LEFT);
		txtPaint.setAntiAlias(true);
		// Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
		// txtPaint.setTypeface(font);

		Rect txtbounds = new Rect();
		txtPaint.getTextBounds(text, 0, text.length(), txtbounds);

		float t_w = txtbounds.width();//
		float t_h = txtbounds.height();

		int pwidth = this.getWidth();
//		int pheight = this.getHeight();
		int padding = DensityUtil.dip2px(getContext(), 25);
		// 左下角
		float t_x = pwidth / 2 - t_w / 2;
		float t_y = padding + t_h;
		;
		canvas.drawText(text, t_x, t_y, txtPaint);
	}

}
