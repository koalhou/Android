package com.yutong.axxc.parents.view.common;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.yutong.axxc.parents.common.BitmapUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;

/**
 * 图标handler处理类
 */
public class ImageHandler extends YtHandler {
	/** 图片 */
	private final WeakReference<ImageView> imageViewReference;

	public ImageHandler(ImageView imageView) {
		imageViewReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	public void handleMessage(Message msg) {
		if (imageViewReference != null) {
			final ImageView imageView = imageViewReference.get();
			 Bitmap img;
			if (imageView != null) {
				switch (msg.what) {

				case ThreadCommStateCode.COMMON_SUCCESS:

				    try{
				    
					ResourceInfoBean res = (ResourceInfoBean) msg.obj;
					int len = res.getResource().length();
					Logger.i(getClass(), "len:" + len);
					img = BitmapUtils.Bytes2Bimap(res.getResourceBytes());
					
					if(img == null)
					{
					    return;
					}
					WeakReference<Bitmap> wk = new WeakReference<Bitmap>(img);

					if (!wk.get().isRecycled()) {
						imageView.setImageBitmap(wk.get());
						img.isRecycled();
					} else {
						Logger.i(getClass(), "图片已经被回收掉了");
					}
				    }
				    catch(Exception e)
				    {
				        Logger.i(getClass(), "解析资源图片出错！");
				    }
					break;
				case ThreadCommStateCode.IMAGE_GET:
				    img =  (Bitmap)msg.obj;
				    
                    WeakReference<Bitmap> wk1 = new WeakReference<Bitmap>(img);

                    if (!wk1.get().isRecycled()) {
                        imageView.setImageBitmap(wk1.get());
                        img.isRecycled();
                    } else {
                        Logger.i(getClass(), "图片已经被回收掉了");
                    }

                    break;
				case ThreadCommStateCode.TOKEN_INVALID:

					break;

				case ThreadCommStateCode.COMMON_FAILED:

					break;

				default:

					break;
				}

			}
		}
	}
}
