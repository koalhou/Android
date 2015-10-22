/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-11-19 下午3:00:11
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.cache.CacheItem;
import com.yutong.clw.ygbclient.dao.common.tables.CahceItemConfigTable;
import com.yutong.clw.ygbclient.dao.common.tables.DriverInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.FavoritesTable;
import com.yutong.clw.ygbclient.dao.common.tables.LineInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.LoginInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.NewsInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.PushAnnouncementMsgTable;
import com.yutong.clw.ygbclient.dao.common.tables.PushMsgRuleTable;
import com.yutong.clw.ygbclient.dao.common.tables.RecommendStationTable;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.ResRouterTable;
import com.yutong.clw.ygbclient.dao.common.tables.StationAreaTable;
import com.yutong.clw.ygbclient.dao.common.tables.StationInfoTable;

/**
 * 公共数据库操作类
 * 
 * @author zhangzhia 2013-11-19 下午3:00:11
 */
public class CommonDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[公共数据操作类]:";

    /**
     * <默认构造函数>
     */
    public CommonDao(Context context)
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
    public void rebuildDB()
    {
        Logger.i(CommonDao.class, logName + "重建数据库");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            databaseHelper.rebuildDB(db);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CommonDao.class, logName + "重建数据库：", e);
        }
        finally
        {
            if (db != null)
            {
                db.close();
            }
        }
    }

    public void cleanCahce()
    {
        Logger.i(CommonDao.class, logName + "重建数据库");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            db.beginTransaction();

            db.delete(PushMsgRuleTable.Table_Name, null, null);
            db.delete(LineInfoTable.Table_Name, null, null);
            db.delete(NewsInfoTable.Table_Name, null, null);
            db.delete(RemindStationInfoTable.Table_Name, null, null);
            db.delete(ResRouterTable.Table_Name, null, null);
            db.delete(FavoritesTable.Table_Name, null, null);
            db.delete(CahceItemConfigTable.Table_Name, null, null);
            db.delete(StationInfoTable.Table_Name, null, null);
            db.delete(StationAreaTable.Table_Name, null, null);
            db.delete(RecommendStationTable.Table_Name, null, null);
            
            db.delete(DriverInfoTable.Table_Name, null, null);
            /*db.delete(PushAnnouncementMsgTable.Table_Name, null, null);*/
            
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(CommonDao.class, logName + "重建数据库：", e);
        }
        finally
        {
            if (db != null)
            {
                if (db.inTransaction())
                {
                    db.endTransaction();
                }
                db.close();
            }
        }
    }
}
