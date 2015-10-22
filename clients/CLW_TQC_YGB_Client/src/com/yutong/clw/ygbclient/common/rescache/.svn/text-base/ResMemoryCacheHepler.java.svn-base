/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午11:22:26
 * @功能描述：
 */
package com.yutong.clw.ygbclient.common.rescache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.v4.util.LruCache;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.Router.ResRouterItem;

/**
 * 资源内存缓存类
 * 
 * @author zhangzhia 2013-8-22 上午9:52:20
 */
public class ResMemoryCacheHepler
{
    /**
     * 从内存读取数据速度是最快的，为了更大限度使用内存，这里使用了两层缓存。 硬引用缓存不会轻易被回收，用来保存常用数据，不常用的转入软引用缓存。
     */
    private static final int SOFT_CACHE_SIZE = 8; // 软引用缓存容量

    private static LruCache<String, ResRouterItem> mLruCache; // 硬引用缓存

    private static LinkedHashMap<String, SoftReference<ResRouterItem>> mSoftCache; // 软引用缓存

    private static ResMemoryCacheHepler singleton = null;

    private static String logTypeName = "[获取网络资源工具类]：";

    public static synchronized ResMemoryCacheHepler getInstance(Context context)
    {
        if (singleton == null)
        {
            singleton = new ResMemoryCacheHepler(context);
        }
        return singleton;
    }

    private ResMemoryCacheHepler(Context context)
    {
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
        int cacheSize = 1024 * 1024 * memClass / 4; // 硬引用缓存容量，为系统可用内存的1/4
        mLruCache = new LruCache<String, ResRouterItem>(cacheSize)
        {

            @Override
            protected void entryRemoved(boolean evicted, String key, ResRouterItem oldValue, ResRouterItem newValue)
            {
                System.out.println("当前缓存个数：" + size());
                if (size() < maxSize())
                {
                    return;
                }
                if (oldValue != null)
                    // 硬引用缓存容量满的时候，会根据LRU算法把最近没有被使用的资源转入此软引用缓存
                    mSoftCache.put(key, new SoftReference<ResRouterItem>(oldValue));
                Logger.i(ResMemoryCacheHepler.class, logTypeName + "硬引用内存满，删除最近没有使用的资源，转移资源到软引用缓存" + key);
            }
        };
        // 几个参数都是什么意思？
        mSoftCache = new LinkedHashMap<String, SoftReference<ResRouterItem>>(SOFT_CACHE_SIZE, 0.75f, true)
        {
            private static final long serialVersionUID = 6040103833179403725L;

            @Override
            protected boolean removeEldestEntry(Entry<String, SoftReference<ResRouterItem>> eldest)
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
    public ResRouterItem getResFromCache(String key)
    {
        if (!StringUtils.isNotBlank(key))
        {
            return null;
        }

        ResRouterItem value;
        // 先从硬引用缓存中获取
        synchronized (mLruCache)
        {
            value = mLruCache.get(key);
            if (value != null)
            {
                // 如果找到的话，把元素移到LinkedHashMap的最前面，从而保证在LRU算法中是最后被删除
                mLruCache.remove(key);
                mLruCache.put(key, value);

                Logger.i(ResMemoryCacheHepler.class, logTypeName + "从硬引用内存中获取资源并更新资源的位置:" + key);
                return value;
            }
        }
        // 如果硬引用缓存中找不到，到软引用缓存中找
        synchronized (mSoftCache)
        {
            SoftReference<ResRouterItem> reference = mSoftCache.get(key);
            if (reference != null)
            {
                value = reference.get();
                if (value != null)
                {
                    // 将资源移回硬缓存
                    mLruCache.put(key, value);
                    mSoftCache.remove(key);
                    Logger.i(ResMemoryCacheHepler.class, logTypeName + "从软引用内存中获取资源并转移到软引用:" + key);
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
    public void addResToCache(String key, ResRouterItem value)
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