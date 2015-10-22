/**
 * @公司名称：YUTONG
 * @作者：zhangyongn
 * @版本号：1.0
 * @生成日期：2013-10-28 下午1:55:16
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author zhangyongn 2013-10-28 下午1:55:16
 *
 */
public class LoginLinearLayout extends LinearLayout {
    
    public LoginLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
 
    private OnResizeListener mListener;
 
    public void setOnResizeListener(OnResizeListener l) {
        mListener = l;
    }
    
    public interface OnResizeListener {
        void OnResize(int w, int h, int oldw, int oldh);
    }
 
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        
        if (mListener != null) {
            mListener.OnResize(w, h, oldw, oldh);
        }
    }
 

}

