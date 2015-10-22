/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-11 下午2:49:38
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * @author zhouzc 2013-12-11 下午2:49:38
 * 
 */
public class WidthFitListView extends ListView {

	public WidthFitListView(Context context) {
		this(context, null);
	}

	public WidthFitListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (attrs != null) {
			int[] attids = new int[] { android.R.attr.layout_width };
			TypedArray array = context.obtainStyledAttributes(attrs, attids);
			initpw = array.getInt(0, 0);
			array.recycle();
		}
	}

	int initpw = 0;
	int pwidth = 0;

	@Override
	public void invalidate() {
		super.invalidate();
		if (initpw == LayoutParams.WRAP_CONTENT) {
			int tempw = pwidth;
			for (int i = 0; i < this.getChildCount(); i++) {
				View child = this.getChildAt(i);
				child.measure(0, 0);
				if (tempw < child.getMeasuredWidth()) {
					tempw = child.getMeasuredWidth();
				}
			}
			pwidth = tempw;
			ViewGroup.LayoutParams lp = this.getLayoutParams();
			if (lp != null) {
				lp.width = pwidth;
				this.setLayoutParams(lp);
			}
		}
	}

}
