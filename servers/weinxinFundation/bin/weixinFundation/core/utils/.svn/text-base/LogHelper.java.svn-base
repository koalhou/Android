package weixinFundation.core.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogHelper {
	private static Logger logging = Logger.getLogger(LogHelper.class.getName());
	private static Logger logger;

	public static Logger getLogger() {
		if (null == logger) {
			String rootPath = BaseLocalPathUtil.getBaseLocalPath();
			String path = rootPath + "\\WEB-INF\\classes\\logger.properties";
			InputStream is = null;
			try {
				is = new FileInputStream(path);
				LogManager.getLogManager().readConfiguration(is);
			} catch (Exception e) {
				logging.warning("input properties file is error.\n"
						+ e.toString());
			} finally {
				try {
					if (is != null)
						is.close();
				} catch (IOException e) {
					logging.warning("close FileInputStream a case.\n"
							+ e.toString());
				}
			}
			logger = Logger.getLogger("PDWY");
		}

		return logger;
		// return null;
	}

	public static void i(String log) {
		getLogger().log(Level.INFO, log);
	}

	public static void e(String log) {
		getLogger().log(Level.WARNING, log);

	}

	public static void e(String log, Exception e) {
		getLogger().log(Level.WARNING, log + "ERR:" + e.getMessage());
	}
}
