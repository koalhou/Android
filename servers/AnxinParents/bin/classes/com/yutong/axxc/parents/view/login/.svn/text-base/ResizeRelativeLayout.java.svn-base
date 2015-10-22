/*******************************************************************************
 * @(#)ResizeLinearLayout.java 2013-3-28
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.yutong.axxc.parents.view.login;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 首页线性布局
 * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
 * @version $Revision 1.1 $ 2013-3-28 下午8:20:38
 */
public class ResizeRelativeLayout extends RelativeLayout {

    /**
     * 页面重置监听
     */
    private OnResizeListener mListener;

    /**
     * 页面重置监听接口
     * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
     * @version $Revision 1.1 $ 2013-4-8 上午11:43:45
     */
    public interface OnResizeListener {
        void OnResize(int w, int h, int oldw, int oldh);
    }

    public void setOnResizeListener(OnResizeListener resizeListener) {
        mListener = resizeListener;
    }

    /**
     * @param context
     * @param attrs
     */
    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mListener != null) {
            mListener.OnResize(w, h, oldw, oldh);
        }
    }
}
