/**
 * @(#)SiteInfo.java 2013-3-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.site;

import javax.xml.bind.annotation.XmlElement;



/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-24 下午4:27:55
 */
public class SiteInfo {

	/**
	 * 站点ID.
	 */
	@XmlElement(name = "site_id")
	private String siteId;

	/**
	 * 站点名称.
	 */
	@XmlElement(name = "site_name")
	private String siteName;

	/**
	 * 站点经纬度信息.
	 */
	@XmlElement(name = "loc_info")
	private String locInfo;
	/**
	 * 纬度信息.
	 */
	@XmlElement(name = "latitude")
	private String latitude;
	/**
	 * 经度信息.
	 */
	@XmlElement(name = "longitude")
	private String longitude;

	/**
	 * @return Returns the siteId.
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * @param siteId
	 *            The siteId to set.
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	/**
	 * @return Returns the siteName.
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName
	 *            The siteName to set.
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * @return Returns the locInfo.
	 */
	public String getLocInfo() {
		return locInfo;
	}

	/**
	 * @param locInfo
	 *            The locInfo to set.
	 */
	public void setLocInfo(String locInfo) {
		this.locInfo = locInfo;
	}

	/**
	 * @return Returns the latitude.
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            The latitude to set.
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return Returns the longitude.
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            The longitude to set.
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}
