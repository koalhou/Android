package com.yutong.clw.ygbclient.view.bean.command.commands;

import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;
import com.yutong.clw.ygbclient.view.common.command.CommandCreator;


public class HelpCommand extends Command {

	public HelpCommand(String commstr) throws CommandParseException {
		super(commstr);
	}

	@Override
	protected void preInit() {
		name = "获取帮助信息";
		functionName = CommandType.HelpCommand.getCommandString();
		description = "获取帮助信息\r\n命令\r\n	" + CommandType.HelpCommand.getCommandString()
				+ "\r\n参数\r\n	/all:获取所有信息";
	}

	@Override
	public CommandExecuteResult execute() {
		CommandExecuteResult result = new CommandExecuteResult(this);
		result.setContent("可执行命令有:\r\n"+CommandCreator.getAllCommands());
		return result;
	}

}
