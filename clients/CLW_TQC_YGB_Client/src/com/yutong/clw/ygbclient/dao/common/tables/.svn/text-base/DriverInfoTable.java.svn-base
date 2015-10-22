package com.yutong.clw.ygbclient.dao.common.tables;

public class DriverInfoTable {

	public static final String Table_Name = "DriverInfo";
	public static final String vehicle_vin = "vehicle_vin";//主键
	public static final String pic_url= "pic_url";
	public static final String driver_tel = "driver_tel";
	public static final String user_code = "user_code";
	public static final String driver_name = "driver_name";
	

	public static String getTableSQL() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE IF NOT EXISTS ");
		builder.append(DriverInfoTable.Table_Name).append("(");
		builder.append(DriverInfoTable.vehicle_vin).append(" TEXT,");
		builder.append(DriverInfoTable.pic_url).append(" TEXT,");
		builder.append(DriverInfoTable.driver_tel).append(" TEXT,");
		builder.append(DriverInfoTable.user_code).append(" TEXT,");
		builder.append(DriverInfoTable.driver_name).append(" TEXT");
		
		builder.append(");");
		return builder.toString();
	}
}
