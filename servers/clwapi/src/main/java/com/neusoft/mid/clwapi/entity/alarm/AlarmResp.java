/**
 * @(#)AlarmResp.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.alarm;

import java.util.List;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 上午10:14:02
 */
public class AlarmResp {
	/**
	 * 返回内容
	 */
	private List<AlarmInfo> content = null;

	/**
	 * @return Returns the content.
	 */
	public List<AlarmInfo> getContent() {
		return content;
	}

	/**
	 * @param content
	 *            The content to set.
	 */
	public void setContent(List<AlarmInfo> content) {
		this.content = content;
	}

}
