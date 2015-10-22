/**
 * @(#)PhotoMapper.java 2013-4-9
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.photo.PhotoInfo;
import com.neusoft.mid.clwapi.entity.photo.PhotoRequ;
import com.neusoft.mid.clwapi.entity.photo.PhotoSignInfo;
import com.neusoft.mid.clwapi.entity.photo.PhotoSignRequ;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-9 下午3:15:38
 */
public interface PhotoMapper {

	/**
	 * 获取照片列表
	 * 
	 * @param iPhotoResq
	 *            参数
	 * @return 照片列表
	 * @throws DataAccessException
	 *             数据库异常
	 */
	List<PhotoInfo> getPhotoList(PhotoRequ iPhotoRequ)
			throws DataAccessException;

	/**
	 * 获取照片的标记信息
	 * 
	 * @param iPhotoSignRequ
	 *            参数
	 * @return 标记信息
	 * @throws DataAccessException
	 *             数据库异常
	 */
	PhotoSignInfo getPhotoSign(PhotoSignRequ iPhotoSignRequ)
			throws DataAccessException;

	/**
	 * 标记照片
	 */
	void signPhoto(PhotoSignRequ iPhotoSignRequ) throws DataAccessException;

	String getUpDate(PhotoSignRequ iPhotoSignRequ) throws DataAccessException;

}
