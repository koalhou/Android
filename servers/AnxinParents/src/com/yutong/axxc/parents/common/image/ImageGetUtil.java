
package com.yutong.axxc.parents.common.image;

import android.content.Context;
import android.graphics.Bitmap;


/**
 * 图片获取工具类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public final class ImageGetUtil
{
    private ImageMemoryCache memoryCache;

    private ImageFileCache fileCache;

    private ImageGetFromHttp ImageGetFromHttp;

    private static ImageGetUtil shareInstance = null;

    private static ImageGetUtil getShareInstance(Context context)
    {
        if (shareInstance == null)
        {
            shareInstance = new ImageGetUtil(context);
        }

        return shareInstance;
    }

    private ImageGetUtil(Context context)
    {
        memoryCache = new ImageMemoryCache(context);
        fileCache = new ImageFileCache();
        ImageGetFromHttp = new ImageGetFromHttp();
    }

    public Bitmap getBitmap(String url)
    {

        Bitmap result = memoryCache.getBitmapFromCache(url);
        if (result == null)
        {

            result = fileCache.getImage(url);
            if (result == null)
            {

                result = ImageGetFromHttp.downloadBitmap(url);
                if (result != null)
                {
                    fileCache.saveBitmap(result, url);
                    memoryCache.addBitmapToCache(url, result);
                }
            }
            else
            {

                memoryCache.addBitmapToCache(url, result);
            }
        }
        return result;
    }
}
