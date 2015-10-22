package com.yutong.clw.ygbclient.view.common.command;

import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;

public class CommandExecutor {

	public static Command parse(String commstr) throws CommandParseException {
		return CommandCreator.createFromString(commstr);
	}

	public static CommandExecuteResult execute(String commstr) throws CommandParseException {
		return parse(commstr).execute();
	}

}
