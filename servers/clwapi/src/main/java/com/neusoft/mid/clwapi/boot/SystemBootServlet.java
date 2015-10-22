/**
 * @(#)SystemBootServlet.java 2013-03-16
 *
 * Copyright 2011 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.boot;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.neusoft.mid.clwapi.common.HttpConstant;
import com.neusoft.mid.clwapi.common.ModCommonConstant;
import com.neusoft.mid.clwapi.tools.PropertiesTools;

/**
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-03-16 上午10:21:38
 */
public class SystemBootServlet extends HttpServlet {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志记录器.
	 */
	private static Logger logger = LoggerFactory.getLogger(ModCommonConstant.LOGGER_NAME);

	/**
	 * CLWAPI初始化.
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {

		// 添加处理类型信息至日志
		MDC.put("module", "[MAIN]");
		MDC.put("processType", "[SYSTEM-INIT]");

		// 加载系统中所需要的相关资源
		try {
			loadPropertie();
			logger.info("-------------------------------");
			logger.info("---安芯移动服务API模块启动成功---");
			logger.info("-------------------------------");
		} catch (final Exception e) {
			logger.error("加载资源信息时出错" + e, e);
			logger.error("-------------------------------");
			logger.error("---安芯移动服务API模块启动失败---");
			logger.error("-------------------------------");

		}

		// 从日志中移除处理类型信息
		MDC.remove("processType");
		MDC.remove("module");
	}

	/**
	 * 获取配置信息
	 * 
	 * @throws IOException
	 * 
	 */
	private void loadPropertie() throws IOException {
		HttpConstant.WEATHER_IMG_PATH = PropertiesTools.readValue(ModCommonConstant.SYS_CONF_FILE_PATH, "weather.web.path");
		HttpConstant.NEWS_IMG_PATH = PropertiesTools.readValue(ModCommonConstant.SYS_CONF_FILE_PATH, "info.web.path");
		HttpConstant.WEATHER_VALID_TIME = PropertiesTools.readValue(ModCommonConstant.SYS_CONF_FILE_PATH, "weather.valid.time");
	}

	/**
	 * CLWAPI系统销毁.
	 */
	@Override
	public void destroy() {

		// 添加处理类型信息至日志
		MDC.put("module", "[MAIN]");
		MDC.put("processType", "[SYSTEM-DESTROY]");

		logger.info("-------------------------------");
		logger.info("-----安芯移动服务API模块退出-----");
		logger.info("-------------------------------");

		// 从日志中移除处理类型信息
		MDC.remove("processType");
		MDC.remove("module");
	}

}
