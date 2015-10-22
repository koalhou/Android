/**
 * @(#)TackListResp.java 2013-3-25
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.tacks;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-25 下午7:57:52
 */
public class TackListResp {

	/**
	 * 行车记录列表信息.
	 */
	@XmlElement(name = "result")
	private List<TackListInfo> resultList;

	/**
	 * @return Returns the resultList.
	 */
	public List<TackListInfo> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList The resultList to set.
	 */
	public void setResultList(List<TackListInfo> resultList) {
		this.resultList = resultList;
	}
}
