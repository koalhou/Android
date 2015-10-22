package com.yutong.axxc.parents.common.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.axxc.parents.common.SharedPreferencesUtil;
import com.yutong.axxc.parents.common.SysConfigUtil;

/**
 * 图像文件缓存类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class ImageFileCache { 
    //private static final String CACHDIR1 = "ImgCach"; 
    private static final String WHOLESALE_CONV = ".img"; 
                                                             
    private static final int MB = 1024*1024; 
    private static final int CACHE_SIZE = 10; 
    private static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 10; 
                          
    private static Map<String, String> mImgRouter = new HashMap<String, String>(); // 图片路由
    private Gson gson = new Gson();
    private Type type = new TypeToken<Map<String, String>>(){}.getType();
    public ImageFileCache() { 

        removeCache(getDirectory()); 
        String json = SharedPreferencesUtil.get(SharedPreferencesUtil.PREFS_NAME_CACHE, "imgRouter");
        if(StringUtils.isNotBlank(json)){
            mImgRouter = gson.fromJson(json, type);
            if(mImgRouter == null)
            {
                mImgRouter = new HashMap<String, String>(); // 图片路由
            }
        }
    } 
                                                                 
    public Bitmap getImage(final String url) {

        final String path = getDirectory() + convertUrlToFileName(url); 
        
        if(path != null)
        {
            File file = new File(path); 
            if (file.exists()) { 
                Bitmap bmp = BitmapFactory.decodeFile(path); 
                if (bmp == null) { 
                    file.delete(); 
                } else { 
                    updateFileTime(path); 
                    return bmp; 
                } 
            } 
        }
        return null; 
    } 
                                                                 

    public void saveBitmap(Bitmap bm, String url) { 
        if (bm == null) { 
            return; 
        } 

        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) { 

            return; 
        } 
        

        String filename = String.valueOf(System.currentTimeMillis()); 
        
        String dir = getDirectory(); 
        File dirFile = new File(dir); 
        if (!dirFile.exists()) 
            dirFile.mkdirs(); 
        File file = new File(dir +"/" + filename + WHOLESALE_CONV); 
        try { 
            file.createNewFile(); 
            OutputStream outStream = new FileOutputStream(file); 
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream); 
            outStream.flush(); 
            outStream.close(); 
        } catch (FileNotFoundException e) { 
            Log.w("ImageFileCache", "FileNotFoundException"); 
        } catch (IOException e) { 
            Log.w("ImageFileCache", "IOException"); 
        } 
        
        mImgRouter.put(url, filename); 
        SharedPreferencesUtil.set(SharedPreferencesUtil.PREFS_NAME_CACHE, "imgRouter", gson.toJson(mImgRouter, type));
        
    }  
                                                                 

    private boolean removeCache(String dirPath) { 
        File dir = new File(dirPath); 
        File[] files = dir.listFiles(); 
        if (files == null) { 
            return true; 
        } 
        if (!android.os.Environment.getExternalStorageState().equals( 
                android.os.Environment.MEDIA_MOUNTED)) { 
            return false; 
        } 
                                                             
        int dirSize = 0; 
        for (int i = 0; i < files.length; i++) { 
            if (files[i].getName().contains(WHOLESALE_CONV)) { 
                dirSize += files[i].length(); 
            } 
        } 
                                                             
        if (dirSize > CACHE_SIZE * MB || FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) { 
            int removeFactor = (int) ((0.4 * files.length) + 1); 
            Arrays.sort(files, new FileLastModifSort()); 
            for (int i = 0; i < removeFactor; i++) { 
                if (files[i].getName().contains(WHOLESALE_CONV)) { 
                    files[i].delete(); 
                } 
            } 
        } 
                                                             
        if (freeSpaceOnSd() <= CACHE_SIZE) { 
            return false; 
        } 
                                                                     
        return true; 
    } 
                                                                 
    public void updateFileTime(String path) { 
        File file = new File(path); 
        long newModifiedTime = System.currentTimeMillis(); 
        file.setLastModified(newModifiedTime); 
    } 
                                                                 

    private int freeSpaceOnSd() { 
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath()); 
        double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB; 
        return (int) sdFreeMB; 
    }  
                                                                 

    private String convertUrlToFileName(String url) { 
        
        if(mImgRouter.containsKey(url))
        {
            return mImgRouter.get(url) + WHOLESALE_CONV; 
        }
        else
        {
            return null;
        }
    } 

    
//  private String convertUrlToFileName(String url) { 
//        

//            String[] strs = url.split("/"); 
//            return strs[strs.length - 1] + WHOLESALE_CONV; 
//    } 
//        
    
    private String getDirectory() { 
        String dir = SysConfigUtil.getPhotoCachePath(); 
        return dir; 
    } 
                                                                 

    private String getSDPath() { 
        File sdDir = null; 
        boolean sdCardExist = Environment.getExternalStorageState().equals( 
                android.os.Environment.MEDIA_MOUNTED);  
        if (sdCardExist) { 
            sdDir = Environment.getExternalStorageDirectory();
        } 
        if (sdDir != null) { 
            return sdDir.toString(); 
        } else { 
            return ""; 
        } 
    }  

    private class FileLastModifSort implements Comparator<File> { 
        public int compare(File arg0, File arg1) { 
            if (arg0.lastModified() > arg1.lastModified()) { 
                return 1; 
            } else if (arg0.lastModified() == arg1.lastModified()) { 
                return 0; 
            } else { 
                return -1; 
            } 
        } 
    } 
                                                             
}