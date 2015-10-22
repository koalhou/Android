/**
 * @(#)LogbackConfigListener.java 2013-3-16
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.tools;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

import com.neusoft.mid.clwapi.common.ModCommonConstant;

/**
 * 从web.xml中加载指定文件名的日志配置文件
 * 
 * @author <a href="mailto:majch@neusoft.com">majch </a>
 * @version $Revision 1.0 $ 2013-3-16 下午1:52:11
 */
public class LogbackConfigListener implements ServletContextListener {
	private static final Logger logger = LoggerFactory
			.getLogger(LogbackConfigListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {

			// assume SLF4J is bound to logback in the current environment
			LoggerContext context = (LoggerContext) LoggerFactory
					.getILoggerFactory();
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			// Call context.reset() to clear any previous configuration, e.g.
			// default
			// configuration. For multi-step configuration, omit calling
			// context.reset().
			context.reset();
			logger.info("logbck_conf_file_path:"
					+ ModCommonConstant.LOG4J_CONFIG_FILE);
			configurator.doConfigure(ModCommonConstant.LOG4J_CONFIG_FILE);

		} catch (Exception e) {
			logger.error("加载日志配置文件" + ModCommonConstant.LOG4J_CONFIG_FILE
					+ "出错", e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
