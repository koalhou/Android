package com.yutong.clw.ygbclient.dao.other;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.common.beans.VehicleDriver;
import com.yutong.clw.ygbclient.common.enums.AreaType;
import com.yutong.clw.ygbclient.common.enums.SexType;
import com.yutong.clw.ygbclient.common.utils.DataTypeConvertUtils;
import com.yutong.clw.ygbclient.dao.common.DatabaseHelper;
import com.yutong.clw.ygbclient.dao.common.tables.DriverInfoTable;
import com.yutong.clw.ygbclient.dao.common.tables.LoginInfoTable;

/**
 * 登录信息数据操作类, 目前只存一条，后续根据需要更新功能
 */
public class DriverInfoDao {
	private DatabaseHelper databaseHelper;

	/** Context对象用于查询 */
	private Context mContext;

	/**
	 * <默认构造函数>
	 */
	public DriverInfoDao(Context mContext) {
		this.mContext = mContext;
		databaseHelper = new DatabaseHelper(this.mContext);
	}

	/**
	 * 添加登录信息
	 * 
	 * @param loginInfo
	 * @return
	 */
	public boolean addDriverInfo(VehicleDriver vehicleDriver) {
		Logger.i(DriverInfoDao.class, "[登录信息数据操作类]：添加用户登录信息");
		SQLiteDatabase db = null;
		try {
			db = databaseHelper.getWritableDatabase();
			if (vehicleDriver != null) {
				db.beginTransaction();

				String execSQL_0 = "select count(*) from "
						+ DriverInfoTable.Table_Name + " where "
						+ DriverInfoTable.vehicle_vin + "= ?";
				String[] args_0 = { vehicleDriver.vehicle_vin };
				Cursor cursor = db.rawQuery(execSQL_0, args_0);
				int existNum = 0;
				if (cursor.moveToFirst()) {
					existNum = cursor.getInt(cursor.getColumnIndex("count(*)"));
				}

				if (existNum > 0) {

					String execSQL = "update " + DriverInfoTable.Table_Name
							+ " set " + DriverInfoTable.driver_name + "= ?,"
							+ DriverInfoTable.driver_tel + "=?,"
							+ DriverInfoTable.user_code + "=?  where "
							+ DriverInfoTable.vehicle_vin + "= ?";
					String[] args = { vehicleDriver.driver_name,
							vehicleDriver.driver_tel, vehicleDriver.emp_code,
							vehicleDriver.vehicle_vin };
					db.execSQL(execSQL, args);

				} else {

					String execSQL = "insert into "
							+ DriverInfoTable.Table_Name + " values(?,?,?,?,?)";
					String[] args = { vehicleDriver.vehicle_vin,
							vehicleDriver.pic_url, vehicleDriver.driver_tel,
							vehicleDriver.emp_code, vehicleDriver.driver_name };

					db.execSQL(execSQL, args);
				}

				db.setTransactionSuccessful();
			}
			return true;
		} catch (Exception e) {
			Logger.e(DriverInfoDao.class, "[登录信息数据操作类]：添加用户信息异常：", e);
			return false;
		} finally {
			if (db != null) {
				if (db.inTransaction()) {
					db.endTransaction();
				}
				db.close();
			}
		}
	}

	public List<VehicleDriver> queryDriverInfo(List<String> vehicle_vins) {
		Logger.i(DriverInfoDao.class, "[登录信息数据操作类]：添加用户登录信息");
		SQLiteDatabase db = null;
		
		List<VehicleDriver> vehicleDriverList= new ArrayList<VehicleDriver>();
		VehicleDriver vehicleDriver;
		try {
			db = databaseHelper.getReadableDatabase();

			if (vehicle_vins != null && vehicle_vins.size() > 0) {

				String vin = vehicle_vins.get(0);
				db.beginTransaction();

				String execSQL_0 = "select * from "
						+ DriverInfoTable.Table_Name + " where "
						+ DriverInfoTable.vehicle_vin + "= ?";
				String[] args_0 = { vehicle_vins.get(0) };
				Cursor cursor = db.rawQuery(execSQL_0, args_0);
				while (cursor.moveToFirst()) {
					vehicleDriver = new VehicleDriver();
					vehicleDriver.vehicle_vin = cursor.getString(cursor.getColumnIndex(DriverInfoTable.vehicle_vin));
					vehicleDriver.pic_url = cursor.getString(cursor.getColumnIndex(DriverInfoTable.pic_url));
					vehicleDriver.driver_name = cursor.getString(cursor.getColumnIndex(DriverInfoTable.driver_name));
					vehicleDriver.driver_tel = cursor.getString(cursor.getColumnIndex(DriverInfoTable.driver_tel));
					vehicleDriver.emp_code = cursor.getString(cursor.getColumnIndex(DriverInfoTable.user_code));
					vehicleDriverList.add(vehicleDriver);
				}
				db.setTransactionSuccessful();
			}
			return vehicleDriverList;
		} catch (Exception e) {
			Logger.e(DriverInfoDao.class, "[登录信息数据操作类]：添加用户信息异常：", e);
			return vehicleDriverList;
		} finally {
			if (db != null) {
				if (db.inTransaction()) {
					db.endTransaction();
				}
				db.close();
			}
		}
	}

}
