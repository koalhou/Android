/**
 * @(#)CoreMsgReqParam.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.coremsg;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-9 上午11:36:04
 */
public class CoreMsgReqParam {
	private CoreMsgReqTreeOject treeObject;

	/**
	 * @return Returns the treeObject.
	 */
	public CoreMsgReqTreeOject getTreeObject() {
		return treeObject;
	}

	/**
	 * @param treeObject
	 *            The treeObject to set.
	 */
	@XmlElement(name = "tree-object")
	public void setTreeObject(CoreMsgReqTreeOject treeObject) {
		this.treeObject = treeObject;
	}
}
