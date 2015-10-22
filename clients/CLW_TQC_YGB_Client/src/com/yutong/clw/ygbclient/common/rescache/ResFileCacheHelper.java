/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午11:22:26
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.rescache;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.yutong.clw.ygbclient.business.common.GetNetResourceBiz;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.Router.ResRouterHelper;
import com.yutong.clw.ygbclient.common.Router.ResRouterItem;
import com.yutong.clw.ygbclient.common.enums.ResourceType;
import com.yutong.clw.ygbclient.common.utils.ImageUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;

/**
 * 资源文件缓存类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ResFileCacheHelper
{
    /** Context对象 */
    protected Context context;

    private final String WHOLESALE_CONV = ".res";

    private final int MB = 1024 * 1024;

    private final int CACHE_SIZE = 10;

    private final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;

    private String logTypeName = "[资源文件缓存类]:";

    private static ResFileCacheHelper singleton = null;

    public static synchronized ResFileCacheHelper getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ResFileCacheHelper(context);
        }
        return singleton;
    }

    private ResFileCacheHelper(Context context)
    {
        this.context = context;
    }

    public void delFileCache()
    {
        removeCache(SysConfigUtil.getResCachePath());
    }

    @SuppressWarnings("null")
    public synchronized ResRouterItem getResFromCache(String key)
    {
        ResRouterItem resRouter = ResRouterHelper.getInstance(context).get(key);

        String filePath;

        if (resRouter != null)
        {
            // add by lizyi
            // switch (resRouter.resType)
            // {
            // case NetURL:
            // filePath = SysConfigUtil.getNetCachePath();
            // break;
            // case Resource:
            // filePath = SysConfigUtil.getResCachePath();
            // break;
            // default:
            // filePath = SysConfigUtil.getResCachePath();
            // break;
            // }
            filePath = resRouter.path;
        }
        else
        {
            return null;
        }

        File file = new File(filePath);
        if (!file.exists())
        {
            Logger.w(this.getClass(), logTypeName + "文件不存在：" + filePath);
            return null;
        }

        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size)))
            {
                bos.write(buffer, 0, len);
            }

            resRouter.setBytes(bos.toByteArray());
            Logger.w(this.getClass(), logTypeName + "从文件缓存中获取资源信息");
            return resRouter;
        }
        catch (FileNotFoundException e)
        {
            Logger.w(this.getClass(), logTypeName + "文件未找到");
            return null;
        }
        catch (IOException e)
        {
            Logger.w(this.getClass(), logTypeName + "打开文件出错");
            return null;
        }
    }

    public synchronized void addResToCache(String key, ResRouterItem resRouter)
    {
        // add by lizyi 这个时候只有路由信息，可能本地文件已经不存在了
        // if (ResRouterHelper.getInstance(context).containsKey(key))
        // {
        // return;
        // }

        if (resRouter == null || resRouter.getBytes() == null)
        {
            return;
        }
        /**
         * 剩余SD卡空间，多大才满足存放条件？
         */
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd())
        {
            return;
        }

        String dir = SysConfigUtil.getResCachePath();
        String randomId = "";
        switch (resRouter.resType)
        {
        case NetURL:
            dir = SysConfigUtil.getNetCachePath();
            // 对于网络资源，为避免重复，路由文件名为uuid
            randomId = UUID.randomUUID().toString();
            resRouter.path = dir + randomId + WHOLESALE_CONV;
            break;
        // 两种资源的路径不同有什么区别？
        case Resource:
            dir = SysConfigUtil.getResCachePath();
            resRouter.path = dir + key + WHOLESALE_CONV;
            break;
        }

        File dirFile = new File(dir);
        if (!dirFile.exists())
            dirFile.mkdirs();

        File file = new File(resRouter.path);

        try
        {
            file.createNewFile();

            FileOutputStream outStream = new FileOutputStream(file);

            byte[] bytes = resRouter.getBytes();

            outStream.write(bytes);
            outStream.close();
            resRouter.setName(randomId + WHOLESALE_CONV);
            ResRouterHelper.getInstance(context).save(key, resRouter);
        }
        catch (FileNotFoundException e)
        {
            Logger.w(this.getClass(), logTypeName + "文件未找到");
        }
        catch (IOException e)
        {
            Logger.w(this.getClass(), logTypeName + "打开文件出错");
        }
    }

    private synchronized boolean removeCache(String dirPath)
    {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null)
        {
            return true;
        }
        if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            return false;
        }

        int dirSize = 0;
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].getName().contains(WHOLESALE_CONV))
            {
                dirSize += files[i].length();
            }
        }

        if (dirSize > CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd())
        {
            int removeFactor = (int) ((0.4 * files.length) + 1);
            Arrays.sort(files, new FileLastModifSort());
            for (int i = 0; i < removeFactor; i++)
            {
                if (files[i].getName().contains(WHOLESALE_CONV))
                {
                    files[i].delete();
                }
            }
        }

        if (freeSpaceOnSd() <= CACHE_SIZE)
        {
            return false;
        }

        return true;
    }

    private int freeSpaceOnSd()
    {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
        return (int) sdFreeMB;
    }

    private class FileLastModifSort implements Comparator<File>
    {
        public int compare(File arg0, File arg1)
        {
            if (arg0.lastModified() > arg1.lastModified())
            {
                return 1;
            }
            else if (arg0.lastModified() == arg1.lastModified())
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
    }

    private String getSDPath()
    {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();
        }
        if (sdDir != null)
        {
            return sdDir.toString();
        }
        else
        {
            return "";
        }
    }

}