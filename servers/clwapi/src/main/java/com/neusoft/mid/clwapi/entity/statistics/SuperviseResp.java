/**
 * @(#)SuperviseResp.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.statistics;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午9:43:23
 */
public class SuperviseResp {

	/**
	 * 监管统计信息.
	 */
	@XmlElement(name = "result")
	private List<SuperviseInfo> resultList;

	/**
	 * @return Returns the resultList.
	 */
	public List<SuperviseInfo> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList The resultList to set.
	 */
	public void setResultList(List<SuperviseInfo> resultList) {
		this.resultList = resultList;
	}
}
