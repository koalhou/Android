package weixinFundation.core.menu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weixinFundation.core.utils.BaseLocalPathUtil;

public class CustomMenuFileReader {

	/**
	 * 读取 路径 “\\WEB-INF\\classes\\menu.txt”
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readMenuDescriptionFile() {
		String rootPath = BaseLocalPathUtil.getBaseLocalPath();
		String path = rootPath + "\\WEB-INF\\classes\\menu.txt";
		File f = new File(path);
		if (!f.exists()) {
			return null;
		}
		return readFile(path);
	}

	public static String readFile(String fileName) {
		File file = new File(fileName);
		FileInputStream stream = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		String res = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			stream = new FileInputStream(file);
			isr = new InputStreamReader(stream, "utf-8");
			reader = new BufferedReader(isr);
			String tempString = null;
			StringBuilder sb = new StringBuilder();
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				sb.append(tempString);
				line++;
			}
			reader.close();
			res = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (isr != null)
					isr.close();
				if (stream != null)
					stream = null;
			} catch (IOException e1) {
			}
		}
		return res;
	}
}
