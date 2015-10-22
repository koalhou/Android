/**
 * @(#)CarStatReport.java 2013-4-7
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:suyingtao@neusoft.com">suyingtao </a>
 * @version $Revision 1.0 $ 2013-4-7 上午10:31:22
 */
public class EpStatReport {	
	/**
	 * 有效标志.
	 */
	@XmlElement(name = "valid_flag")
	private String validFlag;
	
	/**
	 * 统计月份，格式：yyyy-mm.
	 */
	@XmlElement(name = "date_time")
	private String dateTime;
	
	/**
	 * 车辆总数.
	 */
	@XmlElement(name = "car_num")
	private String carNum;
	
	/**
	 * 累计里程.
	 */
	@XmlElement(name = "total_mileage")	
	private String totalMileage;
	
	/**
	 * 累计油耗.
	 */
	@XmlElement(name = "total_oil")
	private String totalOil;
	
	/**
	 * 统计维度信息，包含类型[01]- [11].
	 */
	@XmlElement(name = "detail_info")	
	private List<MoldStatInfo> detailInfo;
	
	/**
	 * 不良驾驶类型分布，包含类型[01]- [05].
	 */
	@XmlElement(name = "bad_drive_info")	
	private List<BadBehaviorPerp> badDriveInfo;

	/**
	 * @return Returns the dateTime.
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime The dateTime to set.
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return Returns the totalMileage.
	 */
	public String getTotalMileage() {
		return totalMileage;
	}

	/**
	 * @param totalMileage The totalMileage to set.
	 */
	public void setTotalMileage(String totalMileage) {
		this.totalMileage = totalMileage;
	}

	/**
	 * @return Returns the totalOil.
	 */
	public String getTotalOil() {
		return totalOil;
	}

	/**
	 * @param totalOil The totalOil to set.
	 */
	public void setTotalOil(String totalOil) {
		this.totalOil = totalOil;
	}

	/**
	 * @return Returns the detailInfo.
	 */
	public List<MoldStatInfo> getDetailInfo() {
		return detailInfo;
	}

	/**
	 * @param detailInfo The detailInfo to set.
	 */
	public void setDetailInfo(List<MoldStatInfo> detailInfo) {
		this.detailInfo = detailInfo;
	}

	/**
	 * @return Returns the carNum.
	 */
	public String getCarNum() {
		return carNum;
	}

	/**
	 * @param carNum The carNum to set.
	 */
	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	/**
	 * @return Returns the badDriveInfo.
	 */
	public List<BadBehaviorPerp> getBadDriveInfo() {
		return badDriveInfo;
	}

	/**
	 * @param badDriveInfo The badDriveInfo to set.
	 */
	public void setBadDriveInfo(List<BadBehaviorPerp> badDriveInfo) {
		this.badDriveInfo = badDriveInfo;
	}

	/**
	 * @return Returns the validFlag.
	 */
	public String getValidFlag() {
		return validFlag;
	}

	/**
	 * @param validFlag The validFlag to set.
	 */
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

}
