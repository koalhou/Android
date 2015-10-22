/**
 * @(#)TacksDetailResp.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.tacks;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午8:03:13
 */
public class TackDetailResp {

	/**
	 * 行车记录详细信息.
	 */
	@XmlElement(name = "result")
	private List<TackDetailInfo> resultList;

	/**
	 * @return Returns the resultList.
	 */
	public List<TackDetailInfo> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList The resultList to set.
	 */
	public void setResultList(List<TackDetailInfo> resultList) {
		this.resultList = resultList;
	}
}
