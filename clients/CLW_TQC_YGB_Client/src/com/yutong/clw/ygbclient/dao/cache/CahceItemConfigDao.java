/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-19 下午3:00:11
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.cache.CacheItem;
import com.yutong.clw.ygbclient.common.enums.ActionURI;
import com.yutong.clw.ygbclient.common.enums.TimeoutMethod;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.CahceItemConfigTable;

/**
 * 缓存配置数据数据库操作类
 * 
 * @author zhangzhia 2013-11-19 下午3:00:11
 */
public class CahceItemConfigDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[缓存配置数据数据操作类]:";

    /**
     * <默认构造函数>
     */
    public CahceItemConfigDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 保存缓存数据配置
     * 
     * @author zhangzhia 2013-11-19 下午3:04:21
     * @param cacheItem
     */
    public void putCacheItem(CacheItem cacheItem)
    {
        Logger.i(CahceItemConfigDao.class, logName + "添加缓存配置信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            if (cacheItem == null)
            {
                Logger.w(CahceItemConfigDao.class, logName + "添加缓存配置信息为空或没有用户");
                return;
            }
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=? AND " + CahceItemConfigTable.Key + "=?";
            String[] selectionArgs = { enum2Str(cacheItem.ActionURI), cacheItem.Key };
            cursor = db.query(CahceItemConfigTable.Table_Name, null, selection, selectionArgs, null, null, null);
            values = buildCacheValue(cacheItem);
            if (cursor != null && cursor.moveToNext())
            {
                // 更新记录
                db.update(CahceItemConfigTable.Table_Name, values, selection, selectionArgs);

            }
            else
            {
                /* 插入数据库操作 */
                db.insert(CahceItemConfigTable.Table_Name, null, values);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "添加添加缓存配置信息：", e);
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 获取缓存数据配置
     * 
     * @author zhangzhia 2013-11-19 下午3:06:06
     * @param uri
     * @param key
     * @return
     */
    public CacheItem getCacheItem(ActionURI uri, String key)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            if (uri == null || StringUtil.isBlank(key))
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存配置信息为空");

                return null;
            }
            db = databaseHelper.getReadableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=? AND " + CahceItemConfigTable.Key + "=?";
            String[] selectionArgs = { enum2Str(uri), key };
            cursor = db.query(CahceItemConfigTable.Table_Name, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToNext())
            {
                //Logger.i(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存配置信息");
                return buildCacheItem(cursor);
            }
            else
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存配置信息为空");
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存配置信息出错：", e);
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 获取缓存ETag值
     * 
     * @author zhangzhia 2013-11-19 下午3:06:01
     * @param uri
     * @param key
     * @return
     */
    public String getCacheETag(ActionURI uri, String key)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            if (uri == null || StringUtil.isBlank(key))
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存ETag信息为空");

                return null;
            }
            db = databaseHelper.getReadableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=? AND " + CahceItemConfigTable.Key + "=?";
            String[] selectionArgs = { enum2Str(uri), key };
            cursor = db.query(CahceItemConfigTable.Table_Name, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToNext())
            {
                Logger.i(CahceItemConfigDao.class, logName + logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存ETag信息");
                return buildCacheItem(cursor).ETag;
            }
            else
            {
                Logger.w(CahceItemConfigDao.class, logName + logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存ETag信息为空");
                return null;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + logName + "通过uri:" + uri.toString() + "模块和key:" + key + "获取缓存ETag信息出错", e);
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 通过uri模块和key标记缓存超时
     * 
     * @author zhangzhia 2013-10-24 下午2:55:54
     * @param uri
     * @return
     */
    public void setCacheExpires(ActionURI uri, String key)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            if (uri == null || StringUtil.isBlank(key))
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key标记缓存超时为空或没有用户");
                return;
            }
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=? AND " + CahceItemConfigTable.Key + "=?";
            String[] selectionArgs = { enum2Str(uri), key };
            cursor = db.query(CahceItemConfigTable.Table_Name, null, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToNext())
            {
                CacheItem cacheItem = buildCacheItem(cursor);
                cacheItem.IsCacheExpires = true;
                values = buildCacheValue(cacheItem);
                // 更新记录
                db.update(CahceItemConfigTable.Table_Name, values, selection, selectionArgs);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "标记缓存超时：", e);
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 通过uri模块标记缓存超时
     * 
     * @author zhangzhia 2013-10-24 下午2:55:54
     * @param uri
     * @return
     */
    public void setCacheExpires(ActionURI uri)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int count = 0;
        try
        {
            if (uri == null)
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块标记缓存超时uri为空或没有用户");
                return;
            }
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=?";
            String[] selectionArgs = { enum2Str(uri) };
            cursor = db.query(CahceItemConfigTable.Table_Name, null, selection, selectionArgs, null, null, null);
            CacheItem cacheItem = null;
            while (cursor != null && cursor.moveToNext())
            {
                cacheItem = buildCacheItem(cursor);
                cacheItem.IsCacheExpires = true;
                cacheItem.ETag = "0";
                values = buildCacheValue(cacheItem);
                // 更新记录
                db.update(CahceItemConfigTable.Table_Name, values, selection, selectionArgs);
                count++;
            }
            Logger.i(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块标记缓存超时：条数" + count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块标记缓存超时：", e);
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 通过uri模块和key删除缓存数据
     * 
     * @author zhangzhia 2013-11-19 下午3:06:01
     * @param uri
     * @param key
     * @return
     */
    public void removeCache(ActionURI uri, String key)
    {
        SQLiteDatabase db = null;
        int count = 0;
        try
        {
            if (uri == null || StringUtil.isBlank(key))
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "删除缓存数据为空或没有用户");
                return;
            }
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=? AND " + CahceItemConfigTable.Key + "=?";
            String[] selectionArgs = { enum2Str(uri), key };
            // 更新记录
            count = db.delete(CahceItemConfigTable.Table_Name, selection, selectionArgs);
            Logger.i(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "删除缓存数据条数：" + count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块和key:" + key + "删除缓存数据：", e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 通过uri模块删除缓存数据
     * 
     * @author zhangzhia 2013-11-19 下午3:06:01
     * @param uri
     * @return
     */
    public void removeCache(ActionURI uri)
    {
        SQLiteDatabase db = null;
        int count = 0;
        try
        {
            if (uri == null)
            {
                Logger.w(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块删除缓存数据为空或没有用户");
                return;
            }
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            String selection = CahceItemConfigTable.ActionURI + "=?";
            String[] selectionArgs = { enum2Str(uri) };
            // 更新记录
            count = db.delete(CahceItemConfigTable.Table_Name, selection, selectionArgs);
            Logger.i(CahceItemConfigDao.class, logName + "通过uri:"+ uri.toString() +"模块删除缓存数据：条数" + count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "通过uri:" + uri.toString() + "模块删除缓存数据出错：", e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    /**
     * 删除所有缓存数据
     * 
     * @author zhangzhia 2013-11-19 下午3:06:01
     * @param uri
     * @return
     */
    public void removeAllCache()
    {
        SQLiteDatabase db = null;

        int count = 0;
        try
        {
            db = databaseHelper.getWritableDatabase();
            // 删除
            count = db.delete(CahceItemConfigTable.Table_Name, null, null);

            Logger.i(CahceItemConfigDao.class, logName + "删除所有缓存数据：条数" + count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CahceItemConfigDao.class, logName + "删除所有缓存数据：", e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    /*******************************************/

    /**
     * 组装缓存配置为数据库记录
     * 
     * @author lizyi 2013-11-19 下午3:42:54
     * @param cacheItem
     * @return
     */
    private ContentValues buildCacheValue(CacheItem cacheItem)
    {
        ContentValues values = new ContentValues();
        values.put(CahceItemConfigTable.ActionURI, enum2Str(cacheItem.ActionURI));
        
        values.put(CahceItemConfigTable.Key, cacheItem.Key);
        if (cacheItem.DownloadTime != null)
        {
            values.put(CahceItemConfigTable.DownloadTime, DateUtils.dateToStr(cacheItem.DownloadTime, DateUtils.LONG_TIME_FORMAT));
        }
        
        values.put(CahceItemConfigTable.CacheTimeoutMethod, enum2Str(cacheItem.Method));
        values.put(CahceItemConfigTable.ETag, cacheItem.ETag);
        values.put(CahceItemConfigTable.CacheData, cacheItem.CacheData);
        values.put(CahceItemConfigTable.IsCacheExpires, cacheItem.IsCacheExpires);
        values.put(CahceItemConfigTable.NeedCacheMechanism, cacheItem.NeedCacheMechanism);
        values.put(CahceItemConfigTable.ValidTime_3G, cacheItem.ValidTime_3G);
        values.put(CahceItemConfigTable.ValidTime_WIFI, cacheItem.ValidTime_WIFI);
        return values;
    }

    /**
     * 组装数据库记录为缓存配置实体
     * 
     * @author lizyi 2013-10-30 上午8:59:36
     * @param cursor
     * @return
     */
    private CacheItem buildCacheItem(Cursor cursor)
    {
        CacheItem temp = new CacheItem();

        try
        {
            temp.ActionURI = (ActionURI) str2Enum(ActionURI.class, cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.ActionURI)));
            temp.CacheData = cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.CacheData));
            if (StringUtil.isNotBlank(cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.DownloadTime))))
            {
                temp.DownloadTime = DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.DownloadTime)),
                        DateUtils.LONG_TIME_FORMAT);
            }
            temp.ETag = cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.ETag));
            temp.IsCacheExpires = DataTypeConvertUtils
                    .int2Boolean(cursor.getInt(cursor.getColumnIndex(CahceItemConfigTable.IsCacheExpires)));
            temp.NeedCacheMechanism = DataTypeConvertUtils
                    .int2Boolean(cursor.getInt(cursor.getColumnIndex(CahceItemConfigTable.NeedCacheMechanism)));

            temp.Key = cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.Key));
            temp.Method = (TimeoutMethod) str2Enum(TimeoutMethod.class,
                    cursor.getString(cursor.getColumnIndex(CahceItemConfigTable.CacheTimeoutMethod)));
            temp.ValidTime_3G = cursor.getLong(cursor.getColumnIndex(CahceItemConfigTable.ValidTime_3G));
            temp.ValidTime_WIFI = cursor.getLong(cursor.getColumnIndex(CahceItemConfigTable.ValidTime_WIFI));
        }
        catch (Exception e)
        {
            Logger.e(CahceItemConfigDao.class, logName + "组装数据库记录为缓存配置实体：", e);
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 数据转换
     * 
     * @author lizyi 2013-11-19 下午3:38:31
     * @param cacheItem
     * @return
     */
    private String enum2Str(Enum enumItem)
    {
        return enumItem.toString();
    }

    private Enum str2Enum(Class<? extends Enum> enumClazz, String enum_str) throws InstantiationException, IllegalAccessException
    {
        return Enum.valueOf(enumClazz, enum_str);
    }

    /*******************************************/
}
