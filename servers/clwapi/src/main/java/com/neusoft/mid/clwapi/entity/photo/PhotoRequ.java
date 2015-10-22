/**
 * @(#)PhotoRequ.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.photo;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 下午3:04:35
 */
public class PhotoRequ {

	/**
	 * 获取照片类型： 1 – 下发命令的照片 2 – 全部照片
	 */
	private String type;

	/**
	 * 每页元素的数量
	 */
	private String num;

	/**
	 * 开始时间，格式yyyyMMddHH24mmss
	 */
	@XmlElement(name = "start_time")
	private String startTime;

	/**
	 * 结束时间，格式yyyyMMddHH24mmss
	 */
	@XmlElement(name = "end_time")
	private String endTime;

	/**
	 * 企业ID
	 */
	@JsonIgnore
	private String enId;

	/**
	 * 用户ID
	 */
	@JsonIgnore
	private String usrId;
	/**
	 * 获取第几页
	 */
	private String page;
	/**
	 * 开始页
	 */
	@JsonIgnore
	private String startRow;
	/**
	 * 结束页
	 */
	@JsonIgnore
	private String endRow;
	/**
	 * 车辆vin
	 */
	private String vin;
	/**
	 * 车队ID
	 */
	@XmlElement(name = "orga_id")
	private String orgaId;
	/**
	 * 组织ID，限制查看权限
	 */
	private String organizationId;

	/**
	 * @return Returns the organizationId.
	 */
	public String getOrganizationId() {
		return organizationId;
	}

	/**
	 * @param organizationId
	 *            The organizationId to set.
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
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
	 * @return Returns the startRow.
	 */
	public String getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow
	 *            The startRow to set.
	 */
	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return Returns the endRow.
	 */
	public String getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow
	 *            The endRow to set.
	 */
	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return Returns the page.
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page
	 *            The page to set.
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return Returns the enId.
	 */
	public String getEnId() {
		return enId;
	}

	/**
	 * @param enId
	 *            The enId to set.
	 */
	public void setEnId(String enId) {
		this.enId = enId;
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
	 * @return Returns the num.
	 */
	public String getNum() {
		return num;
	}

	/**
	 * @param num
	 *            The num to set.
	 */
	public void setNum(String num) {
		this.num = num;
	}

	/**
	 * @return Returns the startTime.
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            The startTime to set.
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return Returns the endTime.
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            The endTime to set.
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return Returns the usrId.
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * @param usrId
	 *            The usrId to set.
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PhotoRequ [type=" + type + ", num=" + num + ", startTime="
				+ startTime + ", endTime=" + endTime + ", enId=" + enId
				+ ", usrId=" + usrId + ", page=" + page + ", startRow="
				+ startRow + ", endRow=" + endRow + ", vin=" + vin
				+ ", orgaId=" + orgaId + "]";
	}

}
