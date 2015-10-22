package com.yutong.clw.ygbclient.view.bean.command.commands;

import com.yutong.clw.ygbclient.utils.SystemInfoUtil;
import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;


public class SysCommand extends Command {

	public final static String COMMAND_ALL = "all";

	public SysCommand(String commstr) throws CommandParseException {
		super(commstr);
	}

	@Override
	protected void preInit() {
		name = "获取系统信息";
		functionName = CommandType.SysCommand.getCommandString();
		description = "获取系统信息\r\n命令\r\n	"+ CommandType.SysCommand.getCommandString()+"\r\n参数\r\n	/all:获取所有信息";
	}
	
	@Override
	protected void parseFromString(String commstr) throws CommandParseException {
		super.parseFromString(commstr);
		if (functionCommand == null ||functionCommand.equals(COMMAND_ALL)) {

		} else {
			throw new CommandParseException(functionName+" 没有找到[" + functionCommand
					+ "]对应的命令方法");
		}
	}

	@Override
	public CommandExecuteResult execute() {
		CommandExecuteResult result = new CommandExecuteResult(this);
		if (functionCommand == null || functionCommand.equals(COMMAND_ALL)) {
			result.setContent(SystemInfoUtil.getVersionName());
		} else {
			result.setContent("没有找到[" + functionCommand + "]对应的命令方法");
		}
		return result;
	}

}
