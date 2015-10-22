/**
 * @(#)UsrTokenService.java 2013-4-1
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.oauth.MobileUsrAllInfo;
import com.neusoft.mid.clwapi.mapper.OauthMapper;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-1 上午8:43:03
 */
@Service("usrOauthService")
public class UsrOauthService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);
	@Autowired
	private OauthMapper oauthMapper;

	/**
	 * 根据终端用户token信息判断用户是否授权使用服务, 如token信息有效则返回终端用户管理信息, 如未授权则返回HTTP状态码401.
	 * 
	 * @param token
	 *            用户令牌信息.
	 * @return 终端用户信息.
	 */
	public MobileUsrAllInfo getMblUsrInfoByToken(String token) {
		MobileUsrAllInfo mblUsrInfo = oauthMapper.getMblUsrInfoByToken(token);

		// 检验token信息.
		boolean flg = checkUsrToken(mblUsrInfo, token);
		if (flg) {
			return mblUsrInfo;
		} else {
			return null;
		}
	}

	/**
	 * 校验终端用户token信息.
	 * 
	 * @param usrInfo
	 *            数据库中终端用户管理信息.
	 * @param nowToken
	 *            用户当前使用的token信息.
	 * @return 授权是否合法.
	 */
	private boolean checkUsrToken(MobileUsrAllInfo usrInfo, String nowToken) {
		// 是否存在有效的终端用户管理信息.
		if (null == usrInfo) {
			// 未获取到符合条件的终端用户管理信息.
			logger.info("不存在与access_token:" + nowToken + "相符的有效终端用户授权信息");
			return false;
		} else {
			logger.info("存在与access_token:" + nowToken + "相符的有效终端用户授权信息");
			return true;
		}
	}
}
