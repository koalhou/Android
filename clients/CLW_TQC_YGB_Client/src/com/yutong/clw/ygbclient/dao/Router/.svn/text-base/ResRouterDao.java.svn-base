/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:53:32
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.Router;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.Router.ResRouterItem;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.ResourceType;
import com.yutong.clw.ygbclient.common.enums.SexType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.LineInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.RecommendStationTable;
import com.yutong.clw.ygbclient.dao.common.tables.ResRouterTable;
import com.yutong.clw.ygbclient.dao.common.tables.ResRouterTable;
import com.yutong.clw.ygbclient.dao.common.tables.ResRouterTable;
import com.yutong.clw.ygbclient.dao.linestation.LineInfoDao;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;

/**
 * 资源路由信息数据操作类
 * 
 * @author zhangzhia 2013-10-25 上午10:53:32
 */
public class ResRouterDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public ResRouterDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 插入路由数据 如果已存在，则更新
     * 
     * @author zhangzhia 2013-10-25 上午11:12:03
     * @param key
     * @param router
     */
    public boolean save(String key, ResRouterItem router)
    {
        Logger.i(ResRouterDao.class, "[资源路由信息数据操作类]：更新资源");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            ContentValues values = null;
            // 查询是否存在已有记录
            String selection = ResRouterTable.Key + "=?";
            String[] selectionArgs = { key };
            cursor = db.query(ResRouterTable.Table_Name, null, selection, selectionArgs, null, null, null);
            values = buildContentValue(router, key);
            if (cursor != null && cursor.moveToNext())
            {
                db.update(ResRouterTable.Table_Name, values, selection, selectionArgs);
            }
            else
            {
                db.insert(ResRouterTable.Table_Name, null, values);
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[资源路由信息数据操作类]：更新资源信息异常：", e);
            return false;
        }
        finally
        {
            if (cursor != null)
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
     * 组装资源实体为数据库记录
     * 
     * @author lizyi 2013-10-29 下午5:45:56
     * @param router
     * @param key
     * @return
     */
    private ContentValues buildContentValue(ResRouterItem router, String key)
    {
        ContentValues values = new ContentValues();

        values.put(ResRouterTable.Key, key);
        values.put(ResRouterTable.Name, router.getName());
        values.put(ResRouterTable.Path, router.getPath());

        values.put(ResRouterTable.ResourceType, router.getResType() == null ? 0 : router.getResType().value());
        values.put(ResRouterTable.Size, router.getSize());
        values.put(ResRouterTable.Suffix, router.getSuffix());
        return values;
    }

    /**
     * 获取路由值
     * 
     * @author zhangzhia 2013-10-25 上午10:56:42
     * @param key
     * @return
     */
    public ResRouterItem get(String key)
    {
        Logger.i(ResRouterDao.class, "[资源路由信息数据操作类]：根据资源key查询资源");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            ContentValues values = null;
            // 查询是否存在已有记录
            String selection = ResRouterTable.Key + "=?";
            String[] selectionArgs = { key };
            cursor = db.query(ResRouterTable.Table_Name, null, selection, selectionArgs, null, null, null);
            ResRouterItem item = null;
            if (cursor != null && cursor.moveToNext())
            {
                item = buildResRouterItem(cursor);
            }
            return item;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[资源路由信息数据操作类]：查询资源信息异常：", e);
            return null;
        }
        finally
        {
            if (cursor != null)
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
     * 组装资源实体
     * 
     * @author lizyi 2013-10-29 下午5:37:58
     * @param cursor
     * @return
     */
    private ResRouterItem buildResRouterItem(Cursor cursor)
    {
        ResRouterItem daoLoginInfoBean = new ResRouterItem("", null, "");

        daoLoginInfoBean.setResKey(cursor.getString(cursor.getColumnIndex(ResRouterTable.Key)));
        daoLoginInfoBean.setResType(ResourceType.myValueOf(cursor.getInt(cursor.getColumnIndex(ResRouterTable.ResourceType))));
        daoLoginInfoBean.setName(cursor.getString(cursor.getColumnIndex(ResRouterTable.Name)));

        daoLoginInfoBean.setSize(cursor.getString(cursor.getColumnIndex(ResRouterTable.Size)));
        daoLoginInfoBean.setPath(cursor.getString(cursor.getColumnIndex(ResRouterTable.Path)));
        daoLoginInfoBean.setSuffix(cursor.getString(cursor.getColumnIndex(ResRouterTable.Suffix)));
        return daoLoginInfoBean;
    }

    /**
     * 此key是否存在路由资源
     * 
     * @author zhangzhia 2013-10-14 上午11:53:16
     * @param key
     * @return
     */
    public boolean containsKey(String key)
    {
        Logger.i(ResRouterDao.class, "[资源路由信息数据操作类]：根据资源key查询资源");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            ContentValues values = null;
            // 开启事务
            // db.beginTransaction();
            // 查询是否存在已有记录
            String selection = ResRouterTable.Key + "=?";
            String[] selectionArgs = { key };
            cursor = db.query(ResRouterTable.Table_Name, null, selection, selectionArgs, null, null, null);
            if (cursor != null && cursor.moveToNext())
            {
                return true;
            }
            // 关闭事务
            // db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[资源路由信息数据操作类]：查询资源信息异常：", e);
            return false;
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
        return false;
    }

    /**
     * 清空表
     * 
     * @author zhangzhia 2013-10-25 上午10:59:18
     * @return
     */
    public void delete()
    {
        Logger.i(ResRouterDao.class, "[资源路由信息数据操作类]：清空信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            db.delete(ResRouterTable.Table_Name, null, null);
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[资源路由信息数据操作类]：清空信息异常：", e);
            e.printStackTrace();
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
     * 清除所有资源路由信息
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     */
    public void removeAll()
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), "[资源路由信息数据操作类]：清除所有资源路由信息");

            db.delete(ResRouterTable.Table_Name, null, null);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), "[资源路由信息数据操作类]：清除所有资源路由信息：", e);
        }
        finally
        {

            if (db != null)
            {
                db.close();
            }
        }
    }
}
