/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-9 上午9:47:02
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.widget;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

/**
 * @author zhouzc 2013-12-9 上午9:47:02
 * 
 */
public class ShakeImageView extends RotateButton {

	public ShakeImageView(Context context) {
		this(context, null);
	}

	public ShakeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		startshake();
	}

	Timer timer = null;

	float shakedegree = 8;

	Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			if (ShakeImageView.this.getRotatedegree() != shakedegree) {
				ShakeImageView.this.setRotatedegree(shakedegree);
			} else {
				ShakeImageView.this.setRotatedegree(-shakedegree);
			}
			return true;
		}

	});

	private void startshake() {
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.obtainMessage().sendToTarget();
			}
		}, 0, 50);
	}

	public void stopShake() {
		if (timer != null)
			timer.cancel();
	}
}
