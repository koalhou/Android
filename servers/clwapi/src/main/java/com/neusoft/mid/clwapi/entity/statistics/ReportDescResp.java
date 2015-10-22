/**
 * @(#)ReportDescResp.java 2013-4-18
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-18 下午2:44:36
 */
public class ReportDescResp {
	/**
	 * 平台最新标记信息
	 */
	@XmlElement(name = "ETag")
	private String etag;
	/**
	 * 评语模板，参见评语模板信息
	 */
	@XmlElement(name = "comment_all")
	private List<ReportDesc> list;

	/**
	 * @return Returns the etag.
	 */
	public String getEtag() {
		return etag;
	}

	/**
	 * @param etag
	 *            The etag to set.
	 */
	public void setEtag(String etag) {
		this.etag = etag;
	}

	/**
	 * @return Returns the list.
	 */
	public List<ReportDesc> getList() {
		return list;
	}

	/**
	 * @param list
	 *            The list to set.
	 */
	public void setList(List<ReportDesc> list) {
		this.list = list;
	}

}
