/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2014-2-14 上午11:13:55
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bean.command.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.os.Environment;

import com.yutong.clw.ygbclient.common.Logger;
import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;

/**
 * @author zhouzc 2014-2-14 上午11:13:55
 * 
 */
public class LogCommand extends Command {

	public final static String COMMAND_SAVE = "save";
	public final static String COMMAND_DELETE = "delete";

	public LogCommand(String commstr) throws CommandParseException {
		super(commstr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void preInit() {
		name = "日志信息";
		functionName = CommandType.LogCommand.getCommandString();
		description = "日志信息\r\n命令\r\n	"
				+ CommandType.LogCommand.getCommandString()+"\r\n log /save [filepath] \r\n log /delete";

	}

	@Override
	public CommandExecuteResult execute() {
		CommandExecuteResult result = new CommandExecuteResult(this);
		if (functionCommand.equals(COMMAND_SAVE)) {

			String filepath = getFunctionParams()[0];

			try {
				String fpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + filepath;
				copyFile(Logger.getLogFilePath(), fpath);
				result.setContent("日志已保存到目录：" + filepath);
			} catch (Exception err) {
				result.setContent("日志保存到目录：" + filepath + ".出现异常："
						+ err.getMessage());
			}
		} else if (functionCommand.equals(COMMAND_DELETE)) {

			try {
				File f = new File(Logger.getLogFilePath());
				f.delete();
				result.setContent("日志删除成功：" + Logger.getLogFilePath());
			} catch (Exception err) {
				result.setContent("日志删除出现异常：" + err.getMessage());
			}
		} else {
			result.setContent("没有找到[" + functionCommand + "]对应的命令方法");
		}
		return result;
	}

	@Override
	protected void parseFromString(String commstr) throws CommandParseException {
		super.parseFromString(commstr);
		if (functionCommand.equals(COMMAND_SAVE)) {
			if (functionParams == null || functionParams.length == 0) {
				throw new CommandParseException(functionName + "["
						+ functionCommand + "]  缺少文件路径参数");
			}
		} else if (functionCommand.equals(COMMAND_DELETE)) {
			
		} else {
			throw new CommandParseException(functionName + " 没有找到["
					+ functionCommand + "]对应的命令方法");
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public boolean copyFile(String oldPath, String newPath) throws Exception {

		int bytesum = 0;
		int byteread = 0;
		File oldfile = new File(oldPath);
		if (!oldfile.exists()) {
			return false;
		}
		if (!oldfile.isFile()) {
			return false;
		}
		if (!oldfile.canRead()) {
			return false;
		}
		File newfile = new File(newPath);
		if (!newfile.exists()) {
			if (!newfile.getParentFile().exists()) {
				newfile.getParentFile().mkdirs();
			}
		}
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = new FileInputStream(oldPath); // 读入原文件
			FileOutputStream fs = new FileOutputStream(newPath);
			byte[] buffer = new byte[1024];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread; // 字节数 文件大小
				// System.out.println(bytesum);
				fs.write(buffer, 0, byteread);
			}
			inStream.close();
			fs.close();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}
}
