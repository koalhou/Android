/**
 * @(#)SoftwareServiceImpl.java 2013-4-23
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.service.software;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.neusoft.mid.clwapi.common.ErrorConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.entity.oauth.CheckLoginReq;
import com.neusoft.mid.clwapi.entity.oauth.SoftwareVersion;
import com.neusoft.mid.clwapi.exception.common.ApplicationException;
import com.neusoft.mid.clwapi.mapper.OauthMapper;
import com.neusoft.mid.clwapi.tools.JacksonUtils;
import com.neusoft.mid.clwapi.tools.PropertiesTools;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-4-23 下午5:48:56
 */
public class SoftwareServiceImpl implements SoftwareService {

	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ModCommonConstant.LOGGER_NAME);

	@Context
	private MessageContext context;
	
	@Autowired
	private OauthMapper oauthMapper;
	// 配置文件中存放access_token超时时间配置的key值.
	private static String NEW_VERSION_SOFTWARE_ADD = "new.version.software.host";

	
	@Override
	public Response checkSoftwareVersion(String version) {
			
		// 检查客户端版本
				CheckLoginReq checkLoginReq = JacksonUtils.fromJsonRuntimeException(
						version, CheckLoginReq.class);
				//保持兼容不检查os_name
				if (null == checkLoginReq || !StringUtils.hasText(checkLoginReq.getVersion())) {
					logger.error("请求参数非法");
					throw new ApplicationException(ErrorConstant.ERROR10001,
							Response.Status.BAD_REQUEST);
				} else {
					logger.info("用户当前使用的软件版本为:" + checkLoginReq.getVersion());
					String softwareUpdateInfo=null;
					if("ios".equalsIgnoreCase(checkLoginReq.getOsName())){
						softwareUpdateInfo = getSoftwareVersionIos(checkLoginReq.getVersion(), null);
					}else{
						softwareUpdateInfo = getSoftwareVersion(checkLoginReq.getVersion(), null);
					}
					// 如存在新版本信息则直接返回新版本软件信息.
					if (null != softwareUpdateInfo) {
						return Response.ok(softwareUpdateInfo).build();
					} else {
						return Response.status(Status.NO_CONTENT).build();
					}

				}
	}


	/**
	 * 检查客户度软件版本信息是否合法及版本是否升级.
	 * 
	 * @param version
	 *            用户当前使用的软件版本号.
	 * @return 新版本信息.
	 * @author majch.
	 */
	public String getSoftwareVersion(String version, String token) {
		logger.info("检查是否存在高于客户端当前所使用软件版本信息");
		// 从数据库中获取新版本信息.
		List<SoftwareVersion> softversionList = oauthMapper
				.getMaxVersion(version);
		if (null == softversionList || softversionList.size() == 0) {
			logger.info("未检索到高版本客户端软件信息");
			return null;
		} else {
			SoftwareVersion newVersion = softversionList.get(0);
			try {
				String host = PropertiesTools.readValue(
						ModCommonConstant.SYS_CONF_FILE_PATH,
						NEW_VERSION_SOFTWARE_ADD);
				logger.info("存在高版本软件,高软件版本为:" + newVersion.getTargetVersion());
				// 添加token信息.
				newVersion.setToken(token);
				// 拼装完成的软件URL信息.
				newVersion.setUri(host + newVersion.getUri());
				return JacksonUtils.toJsonRuntimeException(newVersion);
			} catch (IOException e) {
				logger.error("从配置文件" + ModCommonConstant.SYS_CONF_FILE_PATH
						+ "获取新版软件所在主机信息时出错", e);
				throw new ApplicationException(ErrorConstant.ERROR90000,
						Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
	}

	/**
	 * added by houjh 2014-02-25 IOS客户端强制升级检测
	 * 
	 * 检查客户度软件版本信息是否合法及版本是否升级.
	 * 
	 * @param version
	 *            用户当前使用的软件版本号.
	 * @return 新版本信息.
	 * @author majch.
	 */
	public String getSoftwareVersionIos(String version, String token) {
		logger.info("检查是否存在高于客户端当前所使用软件版本信息");
		// 从数据库中获取新版本信息.
		List<SoftwareVersion> softversionList = oauthMapper.getMaxVersionIos(version);
		if (null == softversionList || softversionList.size() == 0) {
			logger.info("未检索到高版本客户端软件信息");
			return null;
		} else {
			SoftwareVersion newVersion = softversionList.get(0);
			try {
				String host = PropertiesTools.readValue(ModCommonConstant.SYS_CONF_FILE_PATH,NEW_VERSION_SOFTWARE_ADD);
				logger.info("存在高版本软件,高软件版本为:" + newVersion.getTargetVersion());
				// 添加token信息.
				newVersion.setToken(token);
				// 拼装完成的软件URL信息.
//				newVersion.setUri(host + newVersion.getUri());
				return JacksonUtils.toJsonRuntimeException(newVersion);
			} catch (IOException e) {
				logger.error("从配置文件" + ModCommonConstant.SYS_CONF_FILE_PATH + "获取新版软件所在主机信息时出错", e);
				throw new ApplicationException(ErrorConstant.ERROR90000, Response.Status.INTERNAL_SERVER_ERROR);
			}

		}
	}	
}
