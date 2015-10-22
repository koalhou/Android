/**
 * @(#)EnterpriseSiteInfosResp.java 2013-3-24
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.site;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;


/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-24 下午4:45:33
 */
public class EnterpriseSiteInfosResp {

	/**
	 * 站点ID.
	 */
	@XmlElement(name = "site_infos")
	private List<SiteInfo> siteInfoList;

	/**
	 * @return Returns the siteInfoList.
	 */
	public List<SiteInfo> getSiteInfoList() {
		return siteInfoList;
	}

	/**
	 * @param siteInfoList
	 *            The siteInfoList to set.
	 */
	public void setSiteInfoList(List<SiteInfo> siteInfoList) {
		this.siteInfoList = siteInfoList;
	}
}
