/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-22 上午10:18:51
 * @功能描述：
 */
package com.yutong.clw.ygbclient.business.common;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.Display;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.ThreadCommUtil;
import com.yutong.clw.ygbclient.common.YtAsyncTask;
import com.yutong.clw.ygbclient.common.Router.ResRouterItem;
import com.yutong.clw.ygbclient.common.constant.ThreadCommStateCode;
import com.yutong.clw.ygbclient.common.enums.CommonNetStatus;
import com.yutong.clw.ygbclient.common.enums.ResourceType;
import com.yutong.clw.ygbclient.common.exception.CommonException;
import com.yutong.clw.ygbclient.common.rescache.ResFileCacheHelper;
import com.yutong.clw.ygbclient.common.rescache.ResMemoryCacheHepler;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;
import com.yutong.clw.ygbclient.common.utils.NetworkCheckUitl;
import com.yutong.clw.ygbclient.common.utils.StringUtil;

/**
 * 获取网络资源工具类
 * 
 * @author zhangzhia 2013-8-22 上午11:16:33
 */
public class GetNetResourceBiz extends AbstractDataControl
{
    private ResMemoryCacheHepler memoryCache;

    private ResFileCacheHelper fileCache;

    private Display disPlay;

    // private static GetNetResourceBiz singleton = null;
    //
    // public static synchronized GetNetResourceBiz getInstance(Context context)
    // {
    // if (singleton == null)
    // {
    // singleton = new GetNetResourceBiz(context);
    // }
    // return singleton;
    // }

    /**
     * 构造函数
     * 
     * @param context
     */
    public GetNetResourceBiz(Context context)
    {
        logTypeName = "[获取网络资源工具类]：";

        if (memoryCache == null)
        {
            memoryCache = ResMemoryCacheHepler.getInstance(context);
        }
        if (fileCache == null)
        {
            fileCache = ResFileCacheHelper.getInstance(context);
        }
        this.context = context;
        disPlay = ((Activity) context).getWindowManager().getDefaultDisplay();
    }

    
    /**
     * 显示图片
     * 
     * @author lizyi 2013-11-2 下午1:52:30
     * @param imgUrl
     * @param view
     */
    public void startGetImage(final String imgUrl, final ImageView imageView)
    {
        new AsyncTask<Object, Void, Bitmap>()
        {
            @Override
            protected Bitmap doInBackground(Object... params)
            {
                //String imgUrl = (String) params[0];
                //ImageView imageView = (ImageView) params[1];
                return getImage(imgUrl);     
            }

            @Override
            protected void onPostExecute(Bitmap bitmap)
            {
                if (bitmap != null && imageView != null)
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
                else
                {
                    // 加载默认图片
                    Logger.w(this.getClass(), logTypeName + "获取图片失败,加载默认图片");
                }

                super.onPostExecute(bitmap);
            }
          }.execute();
        //}.execute(imgUrl, imageView);

    }
    
    /**
     * 根据图片url返回bitmap
     * 
     * @author lizyi 2013-11-2 下午1:52:01
     * @param imgUrl 图片url
     * @return
     */
    public Bitmap getImage(String imgUrl)
    {
        if (StringUtil.isNotBlank(imgUrl))
        {
            // 判断是服务器资源还是网络资源区分资源类型
            ResRouterItem router = memoryCache.getResFromCache(imgUrl);
            if (router != null && router.getBytes() != null && router.getBytes().length > 0)
            {
                return ImageUtils.byteToBitmap(router.getBytes());
            }
            else
            {
                router = fileCache.getResFromCache(imgUrl);
                if (router != null && router.getBytes() != null && router.getBytes().length > 0)
                {
                    // 加入内存缓存
                    memoryCache.addResToCache(imgUrl, router);

                    return ImageUtils.byteToBitmap(router.getBytes());
                }
                else
                {
                    return getNetImages(imgUrl);
                }
            }

        }
        else
        {
            return null;
        }
    }

//    public Bitmap startGetImage(String imgUrl, ImageView imageView)
//    {
//        Bitmap bitmap = getImage(imgUrl);
//
//        if (bitmap != null && imageView != null)
//        {
//            WeakReference<Bitmap> wk = new WeakReference<Bitmap>(bitmap);
//
//            if (!wk.get().isRecycled())
//            {
//                imageView.setImageBitmap(wk.get());
//            }
//            else
//            {
//                Logger.i(ImageUtils.class, "图片已经被回收掉了");
//            }
//        }
//        return bitmap;
//    }

    public Bitmap getNetImages(String imgUrl)
    {
        Bitmap bitmap = getNetImageFromServer(imgUrl);
        if (bitmap != null)
        {
            ResRouterItem router = new ResRouterItem(imgUrl, ResourceType.NetURL, StringUtil.getUrlSuffix(imgUrl));
            byte[] data = ImageUtils.bitmapToByte(bitmap);
            router.setBytes(data);
            router.setSize(data.length / 1024 + "");
            // 更新缓存
            memoryCache.addResToCache(imgUrl, router);
            fileCache.addResToCache(imgUrl, router);
            Logger.i(GetNetResourceBiz.class, logTypeName + "从网络下载资源并更新到本地:" + imgUrl);
        }

        return bitmap;
    }

    /**
     * 获取小图片，在一次HTTP交互中全部获取完毕
     * 
     * @param imageUrl
     * @return
     * @throws CommonException
     */
    private Bitmap getNetImageFromServer(String imageUrl)
    {
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
                e.printStackTrace();
                Logger.e(this.getClass(), logTypeName + " 网络连接异常，获取小图片失败，imageUrl=", imageUrl);
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
        
//        if (NetworkCheckUitl.isOnline())
//        {
//            URL url = null;
//            InputStream is = null;
//            try
//            {
//                // url = new URL(imageUrl);
//                // URLConnection conn = url.openConnection();
//                // conn.connect();
//                // is = conn.getInputStream();
//                HttpGet httpGet = new HttpGet(imageUrl);// 编者按：与HttpPost区别所在，这里是将参数在地址中传递
//                HttpResponse response = new DefaultHttpClient().execute(httpGet);
//                byte[] imageData = null;
//                if (response.getStatusLine().getStatusCode() == 200)
//                {
//                    HttpEntity entity = response.getEntity();
//                    imageData = EntityUtils.toByteArray(entity);
//                }
//                if (imageData != null && imageData.length > 0)
//                {
//                    BitmapFactory.Options opts = new BitmapFactory.Options();
//                    opts.inJustDecodeBounds = true;
//                    BitmapFactory.decodeByteArray(imageData, 0, imageData.length, opts);
//                    // Calculate inSampleSize
//                    opts.inSampleSize = ImageUtils.calculateInSampleSize(opts, disPlay.getWidth(), disPlay.getHeight());
//                    // Decode bitmap with inSampleSize set
//                    opts.inJustDecodeBounds = false;
//                    return BitmapFactory.decodeByteArray(imageData, 0, imageData.length, opts);
//                }
//                else
//                {
//                    return null;
//                }
//
//            }
//            catch (Exception e)
//            {
//                e.printStackTrace();
//                Logger.e(this.getClass(), logTypeName + " 网络连接异常，获取图片失败，imageUrl=", imageUrl);
//                return null;
//            }
//            finally
//            {
//                if (is != null)
//                {
//                    try
//                    {
//                        is.close();
//                    }
//                    catch (IOException e)
//                    {
//                    }
//                }
//            }
//        }
//        else
//        {
//            Logger.e(this.getClass(), logTypeName + " 网络连接异常");
//            return null;
//        }
    }
}
