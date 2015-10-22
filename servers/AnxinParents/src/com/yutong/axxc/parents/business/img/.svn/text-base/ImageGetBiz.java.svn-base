
package com.yutong.axxc.parents.business.img;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.NetworkCheckUitl;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.YtAsyncTask;
import com.yutong.axxc.parents.common.image.ImageFileCache;
import com.yutong.axxc.parents.common.image.ImageMemoryCache;

/**
 * 图标获取业务逻辑类
 * @author zhangzhia 2013-8-22 上午11:16:33
 *
 */
public class ImageGetBiz extends YtAsyncTask {

    /** Handler对象 */
    private Handler handler;

    /** 图片URL */
    private String imgUrl;
    
    private static ImageMemoryCache memoryCache;

    private static ImageFileCache fileCache;
    
    /**
     * 构造函数
     * @param handler Handler对象
     * @param imgUrl 图片URL
     */
    public ImageGetBiz(Context context, Handler handler, String imgUrl) {
        this.handler = handler;
        this.imgUrl = imgUrl;
        logTypeName = "[图片获取工具类]：";

        if (memoryCache == null)
        {
            memoryCache = new ImageMemoryCache(context);
        }
        if (fileCache == null)
        {
            fileCache = new ImageFileCache();
        }
    }
    
    /**
     * 启动获取天气图标异步任务
     * @param context Context用户数据持久操作
     * @param imgUrl 图片URL
     */
    public static void startGetIconTask(Context context, Handler handler, String imgUrl) {
        Logger.i(ImageGetBiz.class, "[图标获取业务逻辑类]: 启动图标获取异步任务");
        ImageGetBiz imageGetBiz = new ImageGetBiz(context, handler, imgUrl);
        imageGetBiz.execute();
    }

    /**
     * （非 Javadoc）
     * @see com.yutong.axxc.parents.common.YtAsyncTask#doInBackground()
     */
    @Override
    protected void doInBackground() {
        if (imgUrl != null && !"".equals(imgUrl)) {
            Bitmap bitmap = getSmallImage(imgUrl);
            if (bitmap != null) {
                
                // 更新缓存
                fileCache.saveBitmap(bitmap, imgUrl);
                memoryCache.addBitmapToCache(imgUrl, bitmap);
                
                ThreadCommUtil.sendMsgToUI(handler, ThreadCommStateCode.IMAGE_GET, bitmap);
            }
        }
    }

    /**
     * 获取小图片，在一次HTTP交互中全部获取完毕
     * @param imageUrl
     * @return
     */
    private Bitmap getSmallImage(String imageUrl) {
        if (NetworkCheckUitl.isOnline()) {
            URL url = null;
            InputStream is = null;
            try {
                url = new URL(imageUrl);
                URLConnection conn = url.openConnection();
                conn.connect();
                is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (Exception e) {
                Logger.w(this.getClass(), logTypeName + " 网络连接异常，获取小图片失败，imageUrl=", imageUrl);
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                    }
                }
            }
        } else {
            return null;
        }
    }
}
