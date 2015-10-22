/**
 * @公司名称：YUTONG
 * @作者：zhangzhia
 * @版本号：1.0
 * @生成日期：2013-10-25 上午10:53:32
 * @功能描述：
 */
package com.yutong.clw.ygbclient.dao.linestation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yutong.clw.ygbclient.business.ProxyManager;
import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.line.LineInfo;
import com.yutong.clw.ygbclient.common.enums.other.FilterEnum;
import com.yutong.clw.ygbclient.common.utils.BusinessUtils;
import com.yutong.clw.ygbclient.common.utils.DateUtils;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.FavoritesTable;
import com.yutong.clw.ygbclient.dao.common.tables.LineInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.StationInfoTable;
import com.yutong.clw.ygbclient.dao.login.LoginInfoDao;

/**
 * 线路信息数据操作类
 * 
 * @author zhangzhia 2013-10-25 上午10:53:32
 */
public class LineInfoDao // extends AbstractDaoHelper
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public LineInfoDao(Context context)
    {
        // super(context);
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    /**
     * 插入线路信息集合，已存在则更新 2013-11-05 新增user_code
     * 
     * @author zhangzhia 2013-10-25 上午11:10:37
     * @param lineInfos
     */
    public void insertLineInfo(List<LineInfo> lineInfos, List<String> filters, String user_code)
    {
        // 未处理filter条件
        Logger.i(LineInfoDao.class, "[线路信息数据操作类]：添加线路信息");

        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {
            String key = "";
            if (filters != null && filters.size() > 0)
            {
                FilterEnum convertToFilter = BusinessUtils.convertToFilter(filters);
                key = convertToFilter.name();
            }
            db = databaseHelper.getWritableDatabase();

            if (db.isDbLockedByOtherThreads())
            {
                Logger.i(LineInfoDao.class, "[线路信息数据操作类]：其他线程正在操作数据库！");
            }

            if (db.isDbLockedByCurrentThread())
            {
                Logger.i(LineInfoDao.class, "[线路信息数据操作类]：当前线程正在操作数据库！");
            }

            LineInfo bean = null;
            int count = 0;
            ContentValues values = null;
            // 开启事务
            db.beginTransaction();
            if (lineInfos != null && lineInfos.size() > 0)
            {
                for (int i = 0; i < lineInfos.size(); i++)
                {
                    bean = lineInfos.get(i);
                    String selection = LineInfoTable.Line_Id + "=? AND " + LineInfoTable.User_Code + "=?";
                    String[] selectionArgs = { bean.getLine_id(), user_code };
                    cursor = db.query(LineInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
                    if (cursor != null && cursor.moveToNext())
                    {
                        while (cursor != null && cursor.moveToNext())
                        {
                            // 缓存key
                            String line_id = cursor.getString(cursor.getColumnIndex(LineInfoTable.Line_Id));
                            String cache_key = cursor.getString(cursor.getColumnIndex(LineInfoTable.Cache_key));
                            values = buildLineInfoValue(bean);

                            if (line_id.equals(bean.line_id))
                            {
                                // 判断是否区域条件线路信息
                                if (StringUtils.isNotBlank(cache_key) && cache_key.equals(key))
                                {
                                    String whereClause = LineInfoTable.Cache_key + "=? AND " + LineInfoTable.Line_Id + "=? AND " + LineInfoTable.User_Code + "=?";
                                    String[] whereArgs = { cache_key, bean.getLine_id(), user_code };
                                    
                                    values.put(LineInfoTable.Cache_key, key);
                                    db.update(LineInfoTable.Table_Name, values, whereClause, whereArgs);
                                    continue;
                                }

                                String whereClause = LineInfoTable.Line_Id + "=? AND " + LineInfoTable.User_Code + "=?";
                                String[] whereArgs = { bean.getLine_id(), user_code };
                                // 更新线路信息
                                values = buildLineInfoValue(bean);
                                db.update(LineInfoTable.Table_Name, values, whereClause, whereArgs);

                            }

                        }
                    }
                    else
                    {
                        /* 插入数据库操作 */
                        values = buildLineInfoValue(bean);

                        // 判断是否区域条件线路信息
                        if (StringUtils.isNotBlank(key))
                        {
                            values.put(LineInfoTable.Cache_key, key);
                            db.insert(LineInfoTable.Table_Name, null, values);
                        }
                        // 插入线路信息
                        values = buildLineInfoValue(bean);
                        db.insert(LineInfoTable.Table_Name, null, values);
                    }
                    if (cursor != null && !cursor.isClosed())
                    {
                        cursor.close();
                    }
                    count++;
                }
            }
            // 关闭事务
            db.setTransactionSuccessful();
            Logger.i(LineInfoDao.class, "[线路信息数据操作类]：更新线路信息条数：" + count);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[线路信息数据操作类]：添加线路信息异常：", e);
        }
        finally
        {

            if (cursor != null)
            {
                cursor.close();
            }
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

    /**
     * 组装线路信息为数据库记录
     * 
     * @param loginInfoBean
     * @return
     */
    private ContentValues buildLineInfoValue(LineInfo bean)
    {
        ContentValues values = new ContentValues();

        values.put(LineInfoTable.Create_Time, DateUtils.getFormatTime(new Date(), DateUtils.LONG_TIME_FORMAT));
        values.put(LineInfoTable.Line_Id, bean.getLine_id());
        values.put(LineInfoTable.Line_Name, bean.getLine_name());
        if (bean.getArea_type() != null)
        {
            values.put(LineInfoTable.Area_Type, bean.getArea_type().value());
        }
        if (bean.getLine_range() != null)
        {
            values.put(LineInfoTable.Line_Range, bean.getLine_range().value());
        }
        if (bean.getStatus_range() != null)
        {
            values.put(LineInfoTable.Status_Range, bean.getStatus_range().value());
        }
        values.put(LineInfoTable.Json_Data, buildJson(bean));
        values.put(LineInfoTable.User_Code, ProxyManager.getInstance(context).getUserCode());
        return values;
    }

    /**
     * 对象转json字符串
     * 
     * @param bean
     * @return
     */
    private <T> String buildJson(T bean)
    {
        Gson gson = new Gson();
        Type type = new TypeToken<T>()
        {
        }.getType(); // 指定集合对象属性
        return gson.toJson(bean, type);
    }

    /**
     * json字符串转对象
     * 
     * @param json
     * @param c
     * @return
     */
    private <T> T parseJson(String json, Class<T> c)
    {
        Gson gson = new Gson();
        return gson.fromJson(json, c);
    }

    /**
     * 根据filter组合条件获取路线值
     * 
     * @author zhangzhia 2013-10-25 上午10:56:42
     * @param filter 意义参见协议文档
     * @return
     */
    public List<LineInfo> getInfoFromFilter(List<String> filter, String user_code)
    {
        SQLiteDatabase db = null;
        try
        {
            if (filter == null || filter.size() == 0)
            {
                Logger.e(LineInfoDao.class, "[线路信息数据操作类]：根据filter组合条件获取路线值,过滤条件为空");
                return null;
            }
            // 判断查询条件(未区分厂内，厂外，厂间***)
            FilterEnum convertToFilter = BusinessUtils.convertToFilter(filter);

            Logger.i(LineInfoDao.class, "[线路信息数据操作类]：获取线路信息：" + convertToFilter.name());
            db = databaseHelper.getReadableDatabase();
            List<LineInfo> lineInfos = new ArrayList<LineInfo>();

            int count = 0;
            Cursor cursor = null;

            String key = convertToFilter.name();
            // 查询是否存在已有记录
            String selection = LineInfoTable.Cache_key + "=? AND " + LineInfoTable.User_Code + "=?";
            String[] selectionArgs = { key, user_code };
            cursor = db.query(LineInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            String json = null;
            while (cursor != null && cursor.moveToNext())
            {
                json = cursor.getString(cursor.getColumnIndex(LineInfoTable.Json_Data));
                LineInfo temp = parseJson(json, LineInfo.class);
                temp.creatTime = DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(LineInfoTable.Create_Time)),
                        DateUtils.LONG_TIME_FORMAT);
                lineInfos.add(temp);
                count++;
            }
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            Logger.i(LineInfoDao.class, "[线路信息数据操作类]：根据filter组合条件获取路线条数：" + count);
            return lineInfos;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[线路信息数据操作类]：根据filter组合条件获取路线值信息异常：", e);
            return null;
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
     * 根据路线ids获取路线值
     * 
     * @author zhangzhia 2013-10-25 上午10:56:42
     * @param ids 路线id集合
     * @return
     */
    public List<LineInfo> getInfoFromIds(List<String> ids, String user_code)
    {

        // 未处理filter条件
        Logger.i(LineInfoDao.class, "[线路信息数据操作类]：根据路线ids查询线路信息" + ids.toString());
        SQLiteDatabase db = null;
        try
        {
            if (ids == null || ids.size() == 0)
            {
                Logger.e(LineInfoDao.class, "[线路信息数据操作类]：根据路线ids获取路线值,线路ids为空");
                return null;
            }
            db = databaseHelper.getWritableDatabase();
            List<LineInfo> lineInfos = new ArrayList<LineInfo>();
            LineInfo bean = null;
            int count = 0;
            Cursor cursor = null;
            ContentValues values = null;
            // 判断查询条件(未区分厂内，厂外，厂间***)
            // TO DO
            for (int i = 0; i < ids.size(); i++)
            {
                // 查询是否存在已有记录
                String selection = LineInfoTable.Cache_key + " is null AND " + LineInfoTable.Line_Id + "=? AND " + LineInfoTable.User_Code
                        + "=?";
                String[] selectionArgs = { ids.get(i), user_code };
                cursor = db.query(LineInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
                String json = null;
                if (cursor != null && cursor.moveToNext())
                {
                    json = cursor.getString(cursor.getColumnIndex(LineInfoTable.Json_Data));
                    // 更新记录
                    LineInfo temp = parseJson(json, LineInfo.class);
                    temp.creatTime = DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(LineInfoTable.Create_Time)),
                            DateUtils.LONG_TIME_FORMAT);
                    lineInfos.add(temp);
                    Logger.i(LineInfoDao.class, "[线路信息数据操作类]：找到路线id：" + temp.line_id);
                    count++;
                }
                if (cursor != null)
                {
                    cursor.close();
                    cursor = null;
                }
            }
            Logger.i(LineInfoDao.class, "[线路信息数据操作类]：根据路线ids获取路线条数：" + count);
            return lineInfos;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[线路信息数据操作类]：根据路线ids获取路线值信息异常：", e);
            return null;
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
     * 根据路线id获取路线值
     * 
     * @author zhangzhia 2013-10-25 上午10:56:42
     * @param id 路线id
     * @return
     */
    public LineInfo getInfoFromId(String id, String user_code)
    {

        // 未处理filter条件
        Logger.i(LineInfoDao.class, "[线路信息数据操作类]：根据路线id[" + id + "]查询线路信息");
        SQLiteDatabase db = null;
        LineInfo lineInfo = null;
        Cursor cursor = null;
        try
        {
            db = databaseHelper.getWritableDatabase();

            // 查询是否存在已有记录
            String selection = LineInfoTable.Cache_key + " is null AND " + LineInfoTable.Line_Id + "=? AND " + LineInfoTable.User_Code
                    + "=?";
            String[] selectionArgs = { id, user_code };
            cursor = db.query(LineInfoTable.Table_Name, null, selection, selectionArgs, null, null, null);
            String json = null;

            while (cursor != null && cursor.moveToNext())
            {
                json = cursor.getString(cursor.getColumnIndex(LineInfoTable.Json_Data));
                // 更新记录
                lineInfo = parseJson(json, LineInfo.class);
                lineInfo.creatTime = DateUtils.strToDate(cursor.getString(cursor.getColumnIndex(LineInfoTable.Create_Time)),
                        DateUtils.LONG_TIME_FORMAT);

                break;
            }
            return lineInfo;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(LineInfoDao.class, "[线路信息数据操作类]：根据路线id[" + id + "]获取路线值信息异常：", e);
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
     * 清空表
     * 
     * @author zhangzhia 2013-10-25 上午10:59:18
     * @return
     */
    public boolean delete()
    {
        Logger.i(LineInfoDao.class, "[线路信息数据操作类]：删除线路信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            return db.delete(LineInfoTable.Table_Name, null, null) > 0;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[线路信息数据操作类]：删除线路信息异常：", e);
            return false;
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
     * 清除当前用户所有线路站点信息
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

            Logger.i(this.getClass(), "[线路信息数据操作类]：清除当前用户所有线路站点信息");
            String delselection = LineInfoTable.User_Code + "=?";
            String[] delselectionArgs = { user_code };
            db.delete(LineInfoTable.Table_Name, delselection, delselectionArgs);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Logger.e(this.getClass(), "[线路信息数据操作类]：清除当前用户所有线路站点信息：", e);
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
