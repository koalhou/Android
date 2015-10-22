/**
 * @(#)URLCoderUtil.java 2013-4-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.neusoft.mid.clwapi.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author <a href="mailto:yi_liu@neusoft.com">yi_liu </a>
 * @version $Revision 1.0 $ 2013-4-12 上午9:02:50
 */
public class URLCoderUtil {

	public static String charset = "UTF-8";

	/**
	 * 批量URL转义
	 * 
	 * @param strings
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String[] decoder(String... strings) {
		String[] str = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			try {
				str[i] = URLDecoder.decode(strings[i], charset);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return str;
	}
}
