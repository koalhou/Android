package com.yutong.clw.ygbclient.view.bean.command;

import java.util.Locale;

/**
 * 命令基类
 * 
 * 自定义Command的步骤： 1.继承本类，并在{@link #preInit()}方法中设置name，description，functionName
 * 2.在{@link CommandType}中添加对应枚举 3.在{@link CommandCreator}中添加转换
 * 
 * @author zhouzc 2013-12-2 上午9:22:19
 * 
 */
public abstract class Command {

	/**
	 * 
	 */
	protected String name;
	/**
	 * 
	 */
	protected String description;
	/**
	 * 
	 */
	protected String functionName;

	protected String commandString;
	protected String functionCommand;
	protected String[] functionParams;

	public Command(String commstr) throws CommandParseException {
		preInit();
		checkInit();
		parseFromString(commstr);
	}

	private void checkInit() {
		if (null == name)
			throw new IllegalArgumentException("name=null,请在preInit()方法中设置name");
		if (null == description)
			throw new IllegalArgumentException(
					"description=null,请在preInit()方法中设置description");
		if (null == functionName)
			throw new IllegalArgumentException(
					"functionName=null,请在preInit()方法中设置functionName");
	}

	public String getName() {
		return name;
	}

	public String getCommandString() {
		return commandString;
	}

	public String getDescription() {
		return description;
	}

	public String getFunctionName() {
		return functionName;
	}
	

	public String[] getFunctionParams() {
		return functionParams;
	}

	public static String getFunctionCommand(String commstr)
			throws CommandParseException {
		if (commstr == null || commstr.equals(""))
			throw new CommandParseException("命令不能为空");
		String userFunction = commstr.split(" ")[0].trim().toLowerCase(
				Locale.CHINA);
		return userFunction;
	}

	protected void parseFromString(String commstr) throws CommandParseException {
		if (commstr == null || commstr.equals(""))
			throw new CommandParseException("命令不能为空");
		String userFunction = commstr.split(" ")[0].trim().toLowerCase(
				Locale.CHINA);
		if (!userFunction.equals(getFunctionName().toLowerCase(Locale.CHINA)))
			throw new CommandParseException("命令方法名[" + userFunction + "]不对");
		// 解析命令开始
		String[] commsplit = commstr.split(" ");
		if (commsplit.length < 2) {
			return;
		}
		// 解析函数命令
		String funccomm = commsplit[1].trim();
		if (!funccomm.startsWith("/")) {
			throw new CommandParseException("命令的函数命令必须以斜杠 \"/\" 开头");
		}

		commandString = commstr;

		functionCommand = funccomm.substring(1).toLowerCase(Locale.CHINA);

		// 解析参数
		if (commsplit.length > 2) {
			functionParams = new String[commsplit.length - 2];
			for (int i = 0; i + 2 < commsplit.length; i++) {
				functionParams[i] = commsplit[i + 2].trim().toLowerCase(
						Locale.CHINA);
			}
		}

	}

	/**
	 * 在这个方法里面重新设置 name description functionname
	 * 
	 * @author zhouzc 2013-12-2 上午9:21:46
	 * 
	 */
	protected abstract void preInit();

	public abstract CommandExecuteResult execute();
}
