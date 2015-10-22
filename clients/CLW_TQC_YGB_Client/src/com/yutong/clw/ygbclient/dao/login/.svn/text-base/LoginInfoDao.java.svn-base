package com.yutong.clw.ygbclient.dao.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.SexType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.LoginInfoTable;

/**
 * 登录信息数据操作类, 目前只存一条，后续根据需要更新功能
 * 
 * @author zhangzhia 2013-9-2 下午3:04:15
 */
public class LoginInfoDao
{
    private DatabaseHelper databaseHelper;

    /** Context对象用于查询 */
    private Context context;

    /**
     * <默认构造函数>
     */
    public LoginInfoDao(Context context)
    {
        this.context = context;
        databaseHelper = new DatabaseHelper(this.context);
    }

    /**
     * 添加登录信息
     * 
     * @param loginInfo
     * @return
     */
    public boolean addLoginInfo(UserInfo loginInfo)
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：添加用户登录信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            if (loginInfo != null)
            {
                // 开启事务
                db.beginTransaction();
                // 为保证数据只有一条，添加之前先进行删除
                db.delete(LoginInfoTable.Table_Name, null, null);
                ContentValues values = new ContentValues();

                values.put(LoginInfoTable.AccessToken, loginInfo.AccessToken);
                values.put(LoginInfoTable.ExpiresIn, loginInfo.ExpiresIn);
                values.put(LoginInfoTable.RefreshToken, loginInfo.RefreshToken);

                values.put(LoginInfoTable.Id, loginInfo.Id);
                values.put(LoginInfoTable.Code, loginInfo.Code);
                values.put(LoginInfoTable.Name, loginInfo.Name);

                values.put(LoginInfoTable.NameShort, loginInfo.NameShort);
                values.put(LoginInfoTable.BelongArea, loginInfo.BelongArea.value());
                values.put(LoginInfoTable.Sex, loginInfo.Sex.value());

                values.put(LoginInfoTable.Phone, loginInfo.Phone);
                values.put(LoginInfoTable.Photo, loginInfo.Photo);
                values.put(LoginInfoTable.Department, loginInfo.Department);

                values.put(LoginInfoTable.EipPhone, loginInfo.EipPhone);
                values.put(LoginInfoTable.BindPhone, loginInfo.BindPhone);
                values.put(LoginInfoTable.DefaultPassword, loginInfo.DefaultPassword);

                /* 插入数据库操作 */
                db.insert(LoginInfoTable.Table_Name, null, values);
                // 关闭事务
                db.setTransactionSuccessful();
            }
            return true;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：添加用户信息异常：", e);
            return false;
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

    /**
     * 删除登录信息
     * 
     * @return true:成功，false:失败
     * @see
     */
    public boolean delLoginInfo()
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：删除登录信息");
        SQLiteDatabase db = null;
        try
        {
            db = databaseHelper.getWritableDatabase();
            db.delete(LoginInfoTable.Table_Name, null, null);
            return true;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：删除登录信息异常：", e);
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
     * 获取登录信息
     * 
     * @return
     */
    public UserInfo getLoginInfo()
    {
        Logger.i(LoginInfoDao.class, "[登录信息数据操作类]：查询用户信息");
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try
        {

            db = databaseHelper.getReadableDatabase();
            String[] columns = { LoginInfoTable.AccessToken, 
            					LoginInfoTable.ExpiresIn, 
            					LoginInfoTable.RefreshToken, 
            					
            					LoginInfoTable.Id,
            					LoginInfoTable.Code, 
            					LoginInfoTable.Name, 
            					
            					LoginInfoTable.NameShort, 
            					LoginInfoTable.BelongArea, 
            					LoginInfoTable.Sex,
            					
            					LoginInfoTable.Phone, 
            					LoginInfoTable.Photo, 
            					LoginInfoTable.Department, 
            					
            					LoginInfoTable.EipPhone,
            					LoginInfoTable.BindPhone, 
            					LoginInfoTable.DefaultPassword };

            cursor = db.query(LoginInfoTable.Table_Name, columns, null, null, null, null, null);

            if (cursor != null && cursor.moveToNext())
            {
                UserInfo loginUser = new UserInfo();

                loginUser.AccessToken = cursor.getString(cursor.getColumnIndex(LoginInfoTable.AccessToken));
                loginUser.ExpiresIn = cursor.getLong(cursor.getColumnIndex(LoginInfoTable.ExpiresIn));
                loginUser.RefreshToken = cursor.getString(cursor.getColumnIndex(LoginInfoTable.RefreshToken));

                loginUser.Id = cursor.getString(cursor.getColumnIndex(LoginInfoTable.Id));
                loginUser.Code = cursor.getString(cursor.getColumnIndex(LoginInfoTable.Code));
                loginUser.Name = cursor.getString(cursor.getColumnIndex(LoginInfoTable.Name));
                loginUser.NameShort = cursor.getString(cursor.getColumnIndex(LoginInfoTable.NameShort));

                loginUser.BelongArea = AreaType.myValueOf(cursor.getInt(cursor.getColumnIndex(LoginInfoTable.BelongArea)));
                loginUser.Sex = SexType.myValueOf(cursor.getInt(cursor.getColumnIndex(LoginInfoTable.Sex)));
                loginUser.Phone = cursor.getString(cursor.getColumnIndex(LoginInfoTable.Phone));
                loginUser.Photo = cursor.getString(cursor.getColumnIndex(LoginInfoTable.Photo));
                loginUser.Department = cursor.getString(cursor.getColumnIndex(LoginInfoTable.Department));
                loginUser.EipPhone = cursor.getString(cursor.getColumnIndex(LoginInfoTable.EipPhone));
                loginUser.BindPhone = DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(LoginInfoTable.BindPhone)));
                loginUser.DefaultPassword = DataTypeConvertUtils.int2Boolean(cursor.getInt(cursor.getColumnIndex(LoginInfoTable.DefaultPassword)));

                return loginUser;
            }
            return null;
        }
        catch (Exception e)
        {
            Logger.e(LoginInfoDao.class, "[登录信息数据操作类]：根据用户信息异常：", e);
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

}
