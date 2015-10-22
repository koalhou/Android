/**
 * @(#)PhotoInfo.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.photo;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 下午3:11:24
 */
public class PhotoInfo {

	/**
	 * 图片编号
	 */
	@XmlElement(name = "img_id")
	private String imgId;
	/**
	 * 车辆VIN号码
	 */
	private String vin;
	/**
	 * 车牌号码
	 */
	@XmlElement(name = "car_ln")
	private String carLn;
	/**
	 * 拍摄时间，格式yyyymmddhh24miss
	 */
	private String time;
	/**
	 * 0- 未超载1- 超载
	 */
	private String flag;
	/**
	 * 照片备注信息
	 */
	private String info;
	/**
	 * 标记最后修改的时间,可能为空
	 */
	@XmlElement(name = "flag_time")
	private String flagTime;
	/**
	 * 车队
	 */
	@XmlElement(name = "orga_id")
	private String orgaId;
	/**
	 * 通道号
	 */
	private String type;
	/**
	 * 照片的入库时间
	 */
	@XmlElement(name = "operate_time")
	private String operateTime;

	/**
	 * @return Returns the operateTime.
	 */
	public String getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            The operateTime to set.
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return Returns the orgaId.
	 */
	public String getOrgaId() {
		return orgaId;
	}

	/**
	 * @param orgaId
	 *            The orgaId to set.
	 */
	public void setOrgaId(String orgaId) {
		this.orgaId = orgaId;
	}

	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the flagTime.
	 */
	public String getFlagTime() {
		return flagTime;
	}

	/**
	 * @param flagTime
	 *            The flagTime to set.
	 */
	public void setFlagTime(String flagTime) {
		this.flagTime = flagTime;
	}

	/**
	 * @return Returns the imgId.
	 */
	public String getImgId() {
		return imgId;
	}

	/**
	 * @param imgId
	 *            The imgId to set.
	 */
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	/**
	 * @return Returns the vin.
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin
	 *            The vin to set.
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return Returns the carLn.
	 */
	public String getCarLn() {
		return carLn;
	}

	/**
	 * @param carLn
	 *            The carLn to set.
	 */
	public void setCarLn(String carLn) {
		this.carLn = carLn;
	}

	/**
	 * @return Returns the time.
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @param time
	 *            The time to set.
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return Returns the flag.
	 */
	public String getFlag() {
		return flag;
	}

	/**
	 * @param flag
	 *            The flag to set.
	 */
	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * @return Returns the info.
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            The info to set.
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PhotoInfo [imgId=" + imgId + ", vin=" + vin + ", carLn="
				+ carLn + ", time=" + time + ", flag=" + flag + ", info="
				+ info + ", flagTime=" + flagTime + ", type=" + type + "]";
	}

}
