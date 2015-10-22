/**
 * @(#)AdviseMapper.java 2013-4-14
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-14 上午11:59:20
 */
public interface AdviseMapper {
	void insertUserAdviseInfo(@Param(value = "userId") String userId,
			@Param(value = "enterpriseId") String enterpriseId,
			@Param(value = "adviseCont") String adviseCont)
			throws DataAccessException;
}
