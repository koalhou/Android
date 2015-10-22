package com.yutong.axxc.parents.view.common;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yutong.axxc.parents.business.img.ImageGetBiz;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.image.ImageFileCache;
import com.yutong.axxc.parents.common.image.ImageMemoryCache;

public class ImageUtils
{
    private static ImageMemoryCache memoryCache;

    private static ImageFileCache fileCache;

    /**
     * 异步读取资源并填充到ImageView
     * 
     * @author zhangzhia 2013-9-16 上午8:07:39
     * @param context 上下文
     * @param imageView 需填充图片的ImageView
     * @param res_id 资源id
     */
    public static void startGetImg(Context context, ImageView imageView, String url)
    {
        if(StringUtils.isBlank(url))
        {
            return;
        }
        
        if (memoryCache == null)
        {
            memoryCache = new ImageMemoryCache(context);
        }
        if (fileCache == null)
        {
            fileCache = new ImageFileCache();
        }

        Bitmap bitmap = memoryCache.getBitmapFromCache(url);
        if (bitmap == null)
        {
            bitmap = fileCache.getImage(url);
            if (bitmap == null)
            {
                // 从服务器获取资源
                new ImageGetBiz(context, new ImageHandler(imageView), url).execute();
            }
            else
            {
                // 加入内存缓存
                memoryCache.addBitmapToCache(url, bitmap);

                WeakReference<Bitmap> wk = new WeakReference<Bitmap>(bitmap);

                if (!wk.get().isRecycled())
                {
                    imageView.setImageBitmap(wk.get());
                }
                else
                {
                    Logger.i(ImageUtils.class, "图片已经被回收掉了");
                }
            }
        }
        else
        {
            WeakReference<Bitmap> wk = new WeakReference<Bitmap>(bitmap);

            if (!wk.get().isRecycled())
            {
                imageView.setImageBitmap(wk.get());
            }
            else
            {
                Logger.i(ImageUtils.class, "图片已经被回收掉了");
            }
        }

    }

    /**
     * 组成上传的资源包
     * 
     * @author zhangzhia 2013-9-17 下午3:46:59
     * @param suffix
     * @param bytes
     * @return
     */
    public static ResourceInfoBean pagketResourceInfo(String suffix, byte[] bytes)
    {
        ResourceInfoBean resInfo = new ResourceInfoBean();
        resInfo.setPlatform(SysConfigUtil.getPlatfrom());
        resInfo.setSuffix(suffix);
        resInfo.setSize(String.valueOf(bytes.length));
        resInfo.setResource(Tools.bytesToBase64(bytes));
        // byte[] bytes = BitmapUtils.Bitmap2Bytes(img);
        return resInfo;
    }
}
