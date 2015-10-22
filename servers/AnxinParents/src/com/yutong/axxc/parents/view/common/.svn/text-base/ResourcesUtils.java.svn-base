package com.yutong.axxc.parents.view.common;

import java.lang.ref.WeakReference;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yutong.axxc.parents.business.other.ResDownloadBiz;
import com.yutong.axxc.parents.business.other.ResUploadBiz;
import com.yutong.axxc.parents.common.BitmapUtils;
import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SysConfigUtil;
import com.yutong.axxc.parents.common.ThreadCommStateCode;
import com.yutong.axxc.parents.common.ThreadCommUtil;
import com.yutong.axxc.parents.common.Tools;
import com.yutong.axxc.parents.common.beans.ResourceInfoBean;
import com.yutong.axxc.parents.common.rescache.ResFileCache;
import com.yutong.axxc.parents.common.rescache.ResMemoryCache;
import com.yutong.axxc.parents.connect.http.packet.ResourceDownloadRes;

public class ResourcesUtils
{
    private static ResMemoryCache memoryCache;

    private static ResFileCache fileCache;

    /**
     * 异步读取资源并填充到ImageView
     * 
     * @author zhangzhia 2013-9-16 上午8:07:39
     * @param context 上下文
     * @param imageView 需填充图片的ImageView
     * @param res_id 资源id
     */
    public static void startGetImg(Context context, ImageView imageView, String res_id)
    {
        Bitmap img;
        if(StringUtils.isBlank(res_id))
        {
            return;
        }
        
        if (memoryCache == null)
        {
            memoryCache = new ResMemoryCache(context);
        }
        if (fileCache == null)
        {
            fileCache = new ResFileCache();
        }

        String jsonString = memoryCache.getResFromCache(res_id);
        if (jsonString == null)
        {
            jsonString = fileCache.getRes(res_id);
            if (jsonString == null)
            {
                // 从服务器获取资源
                new ResDownloadBiz(context, new ImageHandler(imageView), res_id).execute();
            }
            else
            {
                // 加入内存缓存
                memoryCache.addResToCache(res_id, jsonString);
                ResourceDownloadRes res = new ResourceDownloadRes();
                res.parse(jsonString);
                img = BitmapUtils.Bytes2Bimap(res.getResourceInfoBean().getResourceBytes());

                WeakReference<Bitmap> wk = new WeakReference<Bitmap>(img);

                if (!wk.get().isRecycled())
                {
                    imageView.setImageBitmap(wk.get());
                    img.isRecycled();
                }
                else
                {
                    Logger.i(ResourcesUtils.class, "图片已经被回收掉了");
                }
            }
        }
        else
        {
            ResourceDownloadRes res = new ResourceDownloadRes();
            res.parse(jsonString);
            img = BitmapUtils.Bytes2Bimap(res.getResourceInfoBean().getResourceBytes());

            WeakReference<Bitmap> wk = new WeakReference<Bitmap>(img);

            if (!wk.get().isRecycled())
            {
                imageView.setImageBitmap(wk.get());
                img.isRecycled();
            }
            else
            {
                Logger.i(ResourcesUtils.class, "图片已经被回收掉了");
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

    /**
     * 读取资源并返到Bitmap
     *@author zhangzhia 2013-9-24 下午2:46:56
     *
     * @param context 上下文
     * @param res_id  资源id
     * @return
     */
    public static Bitmap getImg(Context context, String res_id)
    {
        if (memoryCache == null)
        {
            memoryCache = new ResMemoryCache(context);
        }
        if (fileCache == null)
        {
            fileCache = new ResFileCache();
        }

        String jsonString = memoryCache.getResFromCache(res_id);
        if (jsonString == null)
        {
            jsonString = fileCache.getRes(res_id);
            if (jsonString == null)
            {
                return null;
            }
            else
            {
                // 加入内存缓存
                memoryCache.addResToCache(res_id, jsonString);
                ResourceDownloadRes res = new ResourceDownloadRes();
                res.parse(jsonString);
                Bitmap img = BitmapUtils.Bytes2Bimap(res.getResourceInfoBean().getResourceBytes());

                WeakReference<Bitmap> wk = new WeakReference<Bitmap>(img);

                if (!wk.get().isRecycled())
                {
                    return wk.get();
                }
                else
                {
                    return null;
                }
            }
        }
        else
        {
            ResourceDownloadRes res = new ResourceDownloadRes();
            res.parse(jsonString);
            Bitmap img = BitmapUtils.Bytes2Bimap(res.getResourceInfoBean().getResourceBytes());

            WeakReference<Bitmap> wk = new WeakReference<Bitmap>(img);

            if (!wk.get().isRecycled())
            {
                return wk.get();
            }
            else
            {
                return null;
            }
        }

        
    }
}
