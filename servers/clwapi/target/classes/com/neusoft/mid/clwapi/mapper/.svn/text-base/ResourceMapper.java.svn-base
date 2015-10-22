/**
 * @(#)ResourceMapper.java 2013-4-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.neusoft.mid.clwapi.entity.resource.ResourceInfo;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-12 上午9:21:28
 */
public interface ResourceMapper {

	ResourceInfo getResource(@Param("type") String type,
			@Param("resourceId") String resourceId) throws DataAccessException;

}
