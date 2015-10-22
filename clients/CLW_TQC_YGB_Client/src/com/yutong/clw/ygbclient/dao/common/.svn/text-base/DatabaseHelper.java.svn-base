package com.yutong.clw.ygbclient.dao.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.yutong.clw.ygbclient.common.Logger;
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

public class DatabaseHelper extends SQLiteOpenHelper {
	/** 数据库名 */
	private static final String DB_NAME = "tqcygb.db";

	/** 数据库版本 */
	private static final int DB_VERSION = 20;

	/** Context对象 */
	private Context context;

	// private static DatabaseHelper singleton = null;
	//
	// public static synchronized DatabaseHelper getInstance(Context context)
	// {
	// if (singleton == null)
	// {
	// singleton = new DatabaseHelper(context);
	// }
	// return singleton;
	// }

	/**
	 * 第一个参数：Context类型，上下文对象。 
		第二个参数：String类型，数据库的名称 
		第三个参数：CursorFactory类型 
		第四个参数：int类型，数据库版本 
	 */
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * <默认构造函数>
	 */
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);

		if (context == null) {
			Logger.i(getClass(), "[DatabaseHelper管理类]ntext为null");
		}

		this.context = context;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		rebuildDB(db);
	}

	public void rebuildDB(SQLiteDatabase db) {
		context.deleteDatabase(DB_NAME);
		Logger.i(getClass(), "重建数据库");

		createTable(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createTable(db);

	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(LoginInfoTable.getTableSQL());
		db.execSQL(PushMsgRuleTable.getTableSQL());
		db.execSQL(LineInfoTable.getTableSQL());
		db.execSQL(NewsInfoTable.getTableSQL());
		db.execSQL(RemindStationInfoTable.getTableSQL());
		db.execSQL(ResRouterTable.getTableSQL());
		db.execSQL(FavoritesTable.getTableSQL());
		db.execSQL(CahceItemConfigTable.getTableSQL());
		db.execSQL(StationInfoTable.getTableSQL());
		db.execSQL(StationAreaTable.getTableSQL());
		db.execSQL(RecommendStationTable.getTableSQL());
		db.execSQL(DriverInfoTable.getTableSQL());
		
		db.execSQL(PushAnnouncementMsgTable.getTableSQL());

	}

}
