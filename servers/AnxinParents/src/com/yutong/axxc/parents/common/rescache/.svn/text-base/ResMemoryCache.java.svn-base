package com.yutong.axxc.parents.common.rescache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 资源内存缓存类
 * @author zhangzhia 2013-8-22 上午9:52:20
 *
 */
public class ResMemoryCache
{
    /**
     * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
     */
    private static final int SOFT_CACHE_SIZE = 8; // 软引用缓存容量

    private static LruCache<String, String> mLruCache; // 硬引用缓存

    private static LinkedHashMap<String, SoftReference<String>> mSoftCache; // 软引用缓存
    
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
    
    public ResMemoryCache(Context context)
    {
        int memClass = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 4; // 硬引用缓存容量，为系统可用内存的1/4
        mLruCache = new LruCache<String, String>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, String value)
            {
                if (value != null)
                    return value.length();
                else
                    return 0;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key,
                    String oldValue, String newValue)
            {
                if (oldValue != null)
                    // 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的资源转入此软引用缓存
                    mSoftCache.put(key, new SoftReference<String>(oldValue));
            }
        };
        mSoftCache = new LinkedHashMap<String, SoftReference<String>>(
                SOFT_CACHE_SIZE, 0.75f, true)
        {
            private static final long serialVersionUID = 6040103833179403725L;

            @Override
            protected boolean removeEldestEntry(
                    Entry<String, SoftReference<String>> eldest)
            {
                if (size() > SOFT_CACHE_SIZE)
                {
                    return true;
                }
                return false;
            }
        };
    }

    /**
     * 从缓存中获取资源
     */
    public String getResFromCache(String key)
    {
        if(key == null || key.length() > 0)
        {
            return null;
        }
        
        String value;
        // 先从硬引用缓存中获取
        synchronized (mLruCache)
        {
            value = mLruCache.get(key);
            if (value != null)
            {
                // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(key);
                mLruCache.put(key, value);
                return value;
            }
        }
        // 如果硬引用缓存中找不到，到软引用缓存中找
        synchronized (mSoftCache)
        {
            SoftReference<String> reference = mSoftCache.get(key);
            if (reference != null)
            {
                value = reference.get();
                if (value != null)
                {
                    // 将资源移回硬缓存
                    mLruCache.put(key, value);
                    mSoftCache.remove(key);
                    return value;
                }
                else
                {
                    mSoftCache.remove(key);
                }
            }
        }
        return null;
    }

    /**
     * 添加资源到缓存
     */
    public void addResToCache(String key, String value)
    {
        if (value != null)
        {
            synchronized (mLruCache)
            {
                mLruCache.put(key, value);
            }
        }
    }

    public void clearCache()
    {
        mSoftCache.clear();
    }
}