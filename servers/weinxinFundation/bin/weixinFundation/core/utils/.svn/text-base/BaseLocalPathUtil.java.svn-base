package weixinFundation.core.utils;

import java.io.File;
import java.net.URISyntaxException;

import weixinFundation.core.menu.MenuUtil;

/**
 * 系统 主路径
 * @author yunfei
 *
 */
public class BaseLocalPathUtil {
	/*
	 * 获得 apk文件的存放路径
	 */
	public static String getBaseLocalPath() {
		String url1;
		try {
			url1 = MenuUtil.class.getResource("/").toURI().getPath();
			File webinfoDir = new File(url1).getParentFile();
			return webinfoDir.getParent();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return "";
	}
}
