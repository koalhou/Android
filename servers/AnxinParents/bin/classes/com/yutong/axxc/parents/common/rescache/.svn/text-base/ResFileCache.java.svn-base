package com.yutong.axxc.parents.common.rescache;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Comparator;

import android.os.Environment;
import android.os.StatFs;

import com.yutong.axxc.parents.common.Logger;
import com.yutong.axxc.parents.common.SysConfigUtil;

/**
 * 资源文件缓存类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ResFileCache
{
    private static final String WHOLESALE_CONV = ".res";

    private static final int MB = 1024 * 1024;

    private static final int CACHE_SIZE = 10;

    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10;

    private String logTypeName = "[资源文件缓存类]:";

//    private static ResFileCache mInstance = null;
//
//    public static ResFileCache getInstance()
//    {
//        if(mInstance == null)
//        {
//            mInstance = new ResFileCache();
//        }
//        return mInstance;
//    }

    
    public void delFileCache()
    {
        removeCache(SysConfigUtil.getResCachePath());
    }

    public String getRes(final String key)
    {
        final String filePath = SysConfigUtil.getResCachePath() + key + WHOLESALE_CONV;

        File file = new File(filePath);
        if (!file.exists())
        {
            Logger.w(this.getClass(), logTypeName + "文件未找到");
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        try
        {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            Reader in = new BufferedReader(isr);
            int i;
            while ((i = in.read()) > -1)
            {
                buffer.append((char) i);
            }
            in.close();
            return buffer.toString();
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

    public void saveRes(String key, String value)
    {
        if (value == null)
        {
            return;
        }

        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd())
        {
            return;
        }

        String dir = SysConfigUtil.getResCachePath();

        File dirFile = new File(dir);
        if (!dirFile.exists())
            dirFile.mkdirs();

        File file = new File(dir + key + WHOLESALE_CONV);
        try
        {
            file.createNewFile();

            FileOutputStream outStream = new FileOutputStream(file);

            byte[] bytes = value.getBytes();

            outStream.write(bytes);
            outStream.close();

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

    private boolean removeCache(String dirPath)
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

    public void updateFileTime(String path)
    {
        File file = new File(path);
        long newModifiedTime = System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
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