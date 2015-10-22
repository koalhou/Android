package com.yutong.axxc.parents.view.home;

import java.io.Serializable;

import com.yutong.axxc.parents.common.beans.MsgRecordBean;
import com.yutong.axxc.parents.common.beans.StudentInfoBean;

public class PushMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2724554748252124080L;
	public static final int NORMALMSG = 1;
//	public static final int ALARMMSG = 2;
	public static final int CURRENTMSG = 3;
	public static final int FIRSTMSG = 4;
//	public static final int LASTMSG = 5;

	
	/**
	 * 内部消息实体，保存推送消息，这个用于历史消息
	 */
	private MsgRecordBean innerMsg;
//
//	/**
//	 * 学生乘车信息，这个用于当前消息的乘车信息显示
//	 */
//	private RidingRecordBean ridingRecord;

//	/**
//	 * 历史消息配置信息
//	 */
//	private msgconfig historymsgconfig;
	
	private int type;
	private String time;
	private String body;
	
	private String teacherphone;
	private String teachername;
	private String speed;
	
	private String gps_lon;
	private String gps_lat;
	private String stationname;
	private String stationid;
	
	private String bus_vin;
	private String busplate;
	private String driver;

	private StudentInfoBean student;

	private boolean showDetail = false;
	private boolean showGps = false;
	private boolean showCall = false;

	public PushMessage() {
		this("");
	}

	public PushMessage(String _body) {
		body = _body;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the showDetail
	 */
	public boolean isShowDetail() {
		return showDetail;
	}

	/**
	 * @param showDetail
	 *            the showDetail to set
	 */
	public void setShowDetail(boolean showDetail) {
		this.showDetail = showDetail;
	}

//	/**
//	 * @return the innerMsg
//	 */
//	public MsgRecordBean getInnerMsg() {
//		return innerMsg;
//	}
//
//	/**
//	 * @param innerMsg
//	 *            the innerMsg to set
//	 */
//	public void setInnerMsg(MsgRecordBean innerMsg) {
//		this.innerMsg = innerMsg;
//	}
//
//	/**
//	 * @return the ridingRecord
//	 */
//	public RidingRecordBean getRidingRecord() {
//		return ridingRecord;
//	}
//
//	/**
//	 * @param ridingRecord
//	 *            the ridingRecord to set
//	 */
//	public void setRidingRecord(RidingRecordBean ridingRecord) {
//		this.ridingRecord = ridingRecord;
//	}
//
//	public msgconfig getHistorymsgconfig() {
//		return historymsgconfig;
//	}
//
//	public void setHistorymsgconfig(msgconfig historymsgconfig) {
//		this.historymsgconfig = historymsgconfig;
//	}

	public String getTeacherphone() {
		return teacherphone;
	}

	public void setTeacherphone(String teacherphone) {
		this.teacherphone = teacherphone;
	}

	public String getGps_lon() {
		return gps_lon;
	}

	public void setGps_lon(String gps_lon) {
		this.gps_lon = gps_lon;
	}

	public String getGps_lat() {
		return gps_lat;
	}

	public void setGps_lat(String gps_lat) {
		this.gps_lat = gps_lat;
	}

	public String getBusplate() {
		return busplate;
	}

	public void setBusplate(String busplate) {
		this.busplate = busplate;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	/**
	 * @return the teachername
	 */
	public String getTeachername() {
		return teachername;
	}

	/**
	 * @param teachername the teachername to set
	 */
	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	/**
	 * @return the bus_vin
	 */
	public String getBus_vin() {
		return bus_vin;
	}

	/**
	 * @param bus_vin the bus_vin to set
	 */
	public void setBus_vin(String bus_vin) {
		this.bus_vin = bus_vin;
	}

	/**
	 * @return the stationname
	 */
	public String getStationname() {
		return stationname;
	}

	/**
	 * @param stationname the stationname to set
	 */
	public void setStationname(String stationname) {
		this.stationname = stationname;
	}

	/**
	 * @return the stationid
	 */
	public String getStationid() {
		return stationid;
	}

	/**
	 * @param stationid the stationid to set
	 */
	public void setStationid(String stationid) {
		this.stationid = stationid;
	}

	/**
	 * @return the student
	 */
	public StudentInfoBean getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(StudentInfoBean student) {
		this.student = student;
	}

	/**
	 * @return the showGps
	 */
	public boolean isShowGps() {
		return showGps;
	}

	/**
	 * @param showGps the showGps to set
	 */
	public void setShowGps(boolean showGps) {
		this.showGps = showGps;
	}

	/**
	 * @return the showCall
	 */
	public boolean isShowCall() {
		return showCall;
	}

	/**
	 * @param showCall the showCall to set
	 */
	public void setShowCall(boolean showCall) {
		this.showCall = showCall;
	}

	/**
	 * @return the innerMsg
	 */
	public MsgRecordBean getInnerMsg() {
		return innerMsg;
	}

	/**
	 * @param innerMsg the innerMsg to set
	 */
	public void setInnerMsg(MsgRecordBean innerMsg) {
		this.innerMsg = innerMsg;
	}
}
