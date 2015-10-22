/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-15 下午2:23:40
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bizAccess.common;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yutong.clw.ygbclient.business.other.DownloadResourcesBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.ResourceInfo;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.view.bizAccess.BizBase;
import com.yutong.clw.ygbclient.view.bizAccess.BizResultProcess;

/**
 * 资源相关
 * 
 * @author zhangzhia 2013-11-15 下午2:23:40
 */
public class BizResource extends BizBase
{
    public BizResource(Context context)
    {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public BizResource(Context context, int threadcount)
    {
        super(context, threadcount);
        // TODO Auto-generated constructor stub
    }

    /**
     * 获取服务器资源图片
     * 
     * @author zhangzhia 2013-11-15 下午2:40:49
     * @param res_id 资源id
     * @param imageView imageView对象
     * @param process
     */
    public void startGetImage(final String res_id, final ImageView imageView, BizResultProcess<Boolean> process)
    {
        if (StringUtil.isBlank(res_id))
        {
            Logger.i(this.getClass(), "[资源相关]res_id为空");
            return;
        }
        final DownloadResourcesBiz biz = new DownloadResourcesBiz(mContext, res_id);
        
        Callable<Boolean> networkCallable = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws Exception
            {
                ResourceInfo resInfo = biz.getResourceInfoFromLocal();
                Bitmap bitmap = ImageUtils.byteToBitmap(resInfo.getBytes());

                if (bitmap != null && imageView != null)
                {
                    WeakReference<Bitmap> wk = new WeakReference<Bitmap>(bitmap);

                    if (!wk.get().isRecycled())
                    {
                        imageView.setImageBitmap(wk.get());
                    }
                    else
                    {
                        Logger.i(this.getClass(), "[资源相关]图片已经被回收掉了");
                    }
                }
                else
                {
                    // 加载默认图片
                    Logger.w(this.getClass(),  "[资源相关]获取图片失败,加载默认图片");
                }

                return true;
            }
        };

        BizNetWork(networkCallable, process);
    }

    /**
     * 获取服务器资源
     * 
     * @author zhangzhia 2013-11-15 下午2:40:49
     * @param res_id 资源id
     * @param process
     */
    public void getServerResource(final String res_id, BizResultProcess<ResourceInfo> process)
    {
        if (StringUtil.isBlank(res_id))
        {
            Logger.i(this.getClass(), "[资源相关]res_id为空");
            return;
        }
        final DownloadResourcesBiz biz = new DownloadResourcesBiz(mContext, res_id);

        Callable<ResourceInfo> networkCallable = new Callable<ResourceInfo>()
        {
            @Override
            public ResourceInfo call() throws Exception
            {
                return biz.getResourceInfoFromLocal();
            }
        };

        BizNetWork(networkCallable, process);
    }
}
