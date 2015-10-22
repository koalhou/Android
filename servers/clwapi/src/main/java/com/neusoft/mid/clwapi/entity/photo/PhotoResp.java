/**
 * @(#)PhotoResp.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.entity.photo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 下午3:10:36
 */
public class PhotoResp {
	/**
	 * 照片列表
	 */
	List<PhotoInfo> imgs = new ArrayList<PhotoInfo>();

	/**
	 * @return Returns the imgs.
	 */
	public List<PhotoInfo> getImgs() {
		return imgs;
	}

	/**
	 * @param imgs
	 *            The imgs to set.
	 */
	public void setImgs(List<PhotoInfo> imgs) {
		this.imgs = imgs;
	}
}
