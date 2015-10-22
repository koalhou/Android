/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 下午3:51:41
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.linestation;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.StatusRange;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.common.utils.StringUtil;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.RemindStationInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.StationAreaTable;
import com.yutong.clw.ygbclient.dao.common.tables.StationInfoTable;

/**
 * 站点信息数据库操作类
 * 
 * @author zhangzhia 2013-11-22 上午8:51:41
 */
public class StationInfoDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    private String logName = "[站点信息数据库操作类]:";

    /**
     * <默认构造函数>
     */
    public StationInfoDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 组装数据库记录为站点实体
     * 
     * @author zhangzhia 2013-11-22 上午8:47:21
     * @param cursor
     * @return
     */
    private StationInfo buildStationInfo(Cursor cursor)
    {
        StationInfo temp = new StationInfo();
        if (notBlank(cursor, StationInfoTable.Area_Type))
        {
            temp.setArea_type(AreaType.myValueOf(cursor.getInt(cursor.getColumnIndex(StationInfoTable.Area_Type))));
        }
        if (notBlank(cursor, StationInfoTable.Status_Range))
        {
            temp.setStatus_range(StatusRange.myValueOf(cursor.getInt(cursor.getColumnIndex(StationInfoTable.Status_Range))));
        }

        temp.setId(cursor.getString(cursor.getColumnIndex(StationInfoTable.Id)));
        temp.setBelong_area_id(cursor.getString(cursor.getColumnIndex(StationInfoTable.Belong_Area_Id)));
        temp.setName(cursor.getString(cursor.getColumnIndex(StationInfoTable.Name)));
        temp.setAlias(cursor.getString(cursor.getColumnIndex(StationInfoTable.Alias)));
        temp.setGps_lon(cursor.getDouble(cursor.getColumnIndex(StationInfoTable.gps_lon)));
        temp.setGps_lat(cursor.getDouble(cursor.getColumnIndex(StationInfoTable.gps_lat)));

        temp.setStatus(DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(StationInfoTable.Status))));

        temp.setFavorites(DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(StationInfoTable.Favorites))));

        return temp;
    }

    private boolean notBlank(Cursor cursor, String field)
    {
        return StringUtil.isNotBlank(cursor.getString(cursor.getColumnIndex(field)));
    }

    /**
     * 批量更新站点信息
     * 
     * @author zhangzhia 2013-10-25 下午4:44:49
     * @param stations 收藏的站点信息
     * @param user_code 工号
     * @return
     */
    public void batchUpdate(List<StationInfo> stations, String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Cursor cursor = null;
            ContentValues values = null;
            StationInfo temp = null;
            int count = 0;
            db.beginTransaction();

            Logger.i(this.getClass(), logName + "先删除此用户所有站点信息");
            String delselection = StationInfoTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(StationInfoTable.Table_Name, delselection, delselectionArgs);

            if (stations != null)
            {
                for (int i = 0; i < stations.size(); i++)
                {
                    temp = stations.get(i);
                    values = buildContentValue(temp, user_code);

                    db.insert(StationInfoTable.Table_Name, null, values);

                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                    count++;
                }

                Logger.i(this.getClass(), logName + "批量添加站点信息：" + count);
            }
            db.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "添加站点数据信息异常：", e);
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

    private ContentValues buildContentValue(StationInfo info, String user_code)
    {
        ContentValues values = new ContentValues();
        values.put(StationInfoTable.Id, info.getId());
        values.put(StationInfoTable.Belong_Area_Id, info.getBelong_area_id());
        values.put(StationInfoTable.Name, info.getName());
        values.put(StationInfoTable.Alias, info.getAlias());
        values.put(StationInfoTable.gps_lon, info.getGps_lon());
        values.put(StationInfoTable.gps_lat, info.getGps_lat());
        values.put(StationInfoTable.Status, DataTypeConvertUtils.boolean2Int(info.status));
        values.put(StationInfoTable.Favorites, DataTypeConvertUtils.boolean2Int(info.isFavorites()));
        if (info.area_type != null)
        {
            values.put(StationInfoTable.Area_Type, info.getArea_type().value());
        }
        if (info.status_range != null)
        {
            values.put(StationInfoTable.Status_Range, info.status_range.value());
        }
        values.put(StationInfoTable.User_Code, user_code);
        return values;
    }

    /**
     * 获取用户站点信息
     * 
     * @author zhangzhia 2013-11-22 上午8:55:12
     * @param user_code
     * @return
     */
    public List<StationInfo> getStations(String user_code)
    {
        Logger.i(this.getClass(), logName + "获取站点信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            int count = 0;
            List<StationInfo> infos = new ArrayList<StationInfo>();
            StationInfo bean = null;
            String selection = StationInfoTable.User_Code + "=?";
            String[] selectionArgs = { user_code };
            cursor = db.query(StationInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                bean = buildStationInfo(cursor);
                infos.add(bean);
                count++;
            }
            Logger.i(this.getClass(), logName + "获取站点信息数据条数：" + count);
            return infos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "获取站点信息数据异常：", e);
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
     * 获取某一区域用户站点信息
     * 
     * @author zhangzhia 2013-11-22 上午10:10:11
     * @param belong_Area_Id
     * @param user_code
     * @return
     */
    public List<StationInfo> getStationsByBelongArea(String belong_Area_Id, String user_code)
    {
        Logger.i(this.getClass(), logName + "获取区域【" + belong_Area_Id + "】站点信息数据");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            int count = 0;
            List<StationInfo> infos = new ArrayList<StationInfo>();
            StationInfo bean = null;
            String selection = StationInfoTable.Belong_Area_Id + "=? AND " + StationInfoTable.User_Code + "=?";
            String[] selectionArgs = { belong_Area_Id, user_code };
            cursor = db.query(StationInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                bean = buildStationInfo(cursor);
                infos.add(bean);
                count++;
            }
            Logger.i(this.getClass(), logName + "获取区域【" + belong_Area_Id + "】站点信息数据条数：" + count);
            return infos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "获取区域【" + belong_Area_Id + "】战带你信息数据异常：", e);
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
     * 获取单个站点信息
     * 
     * @author zhangzhia 2013-11-22 上午9:10:30
     * @param station_id
     * @param user_code
     * @return
     */
    public StationInfo getStation(String station_id, String user_code)
    {
        //Logger.i(this.getClass(), logName + "获取单个站点信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();
            int count = 0;
            StationInfo station = null;
            StationInfo bean = null;
            String selection = StationInfoTable.Id + "=? AND " + StationInfoTable.User_Code + "=?";
            String[] selectionArgs = { station_id, user_code };
            cursor = db.query(StationInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext())
            {
                station = buildStationInfo(cursor);
                break;
            }
            //Logger.i(this.getClass(), logName + "获取单个站点信息数据信息条数：" + count);
            return station;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "获取单个站点信息数据信息异常：", e);
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
     * 更新单个站点信息
     * 
     * @author zhangzhia 2013-11-22 上午9:10:30
     * @param station_id
     * @param user_code
     * @return
     */
    public boolean updateStation(StationInfo station, String user_code)
    {
        Logger.i(this.getClass(), logName + "更新站点信息：" + station.id + "[" + station.name + "]");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            ContentValues values = null;
            StationInfo temp = null;
            String selection = StationInfoTable.Id + "=? AND " + StationInfoTable.User_Code + "=?";
            String[] selectionArgs = { station.id, user_code };

            values = buildContentValue(temp, user_code);

            db.update(StationInfoTable.Table_Name, values, selection, selectionArgs);
            Logger.i(this.getClass(), logName + "更新站点信息数据信息");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "更新站点信息数据信息异常：", e);
            return false;
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
     * 设置站点收藏状态
     * 
     * @author zhangzhia 2013-11-22 上午9:44:57
     * @param station_id
     * @param favorites
     * @param user_code
     * @return
     */
    public boolean setStationfavorites(String station_id, boolean favorites, String user_code)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            String selection = StationInfoTable.Id + "=? AND " + StationInfoTable.User_Code + "=?";
            String[] selectionArgs = { station_id, user_code };

            ContentValues values = new ContentValues();
            values.put(StationInfoTable.Favorites, DataTypeConvertUtils.boolean2Int(favorites));

            db.update(StationInfoTable.Table_Name, values, selection, selectionArgs);
            Logger.i(this.getClass(), logName + "设置站点["+ station_id + "]收藏状态:" + favorites);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "设置站点["+ station_id + "]收藏状态异常:" + favorites, e);
            return false;
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
     * 设置站点提醒状态
     * 
     * @author zhangzhia 2013-11-22 上午9:44:51
     * @param station_id
     * @param status
     * @param user_code
     * @return
     */
    public boolean setStationStatus(String station_id, boolean status, String user_code)
    {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getReadableDatabase();

            String selection = StationInfoTable.Id + "=? AND " + StationInfoTable.User_Code + "=?";
            String[] selectionArgs = { station_id, user_code };

            ContentValues values = new ContentValues();
            values.put(StationInfoTable.Status, DataTypeConvertUtils.boolean2Int(status));

            db.update(StationInfoTable.Table_Name, values, selection, selectionArgs);
            Logger.i(this.getClass(), logName + "设置站点["+ station_id + "]提醒状态:" + status);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "设置站点提醒状态异常：" , e);
            return false;
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
     * 清除当前用户所有站点信息
     * 
     * @author zhangzhia 2013-11-26 上午9:35:04
     * @param user_code
     */
    public void removeAll(String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            Logger.i(this.getClass(), logName + "清除当前用户所有站点信息");
            String delselection = StationInfoTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(StationInfoTable.Table_Name, delselection, delselectionArgs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), logName + "清除当前用户所有站点信息：", e);
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
