package com.yutong.clw.ygbclient.view.common.command;

import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;
import com.yutong.clw.ygbclient.view.bean.command.commands.CacheStatusCommand;
import com.yutong.clw.ygbclient.view.bean.command.commands.CommandType;
import com.yutong.clw.ygbclient.view.bean.command.commands.HelpCommand;
import com.yutong.clw.ygbclient.view.bean.command.commands.LogCommand;
import com.yutong.clw.ygbclient.view.bean.command.commands.SettingCommand;
import com.yutong.clw.ygbclient.view.bean.command.commands.SysCommand;
import com.yutong.clw.ygbclient.view.bean.command.commands.UserCommand;

public class CommandCreator {

	public static Command createFromString(String commstr)
			throws CommandParseException {
		String funcComm = Command.getFunctionCommand(commstr);
		CommandType type = CommandType.getCommandTypeFromString(funcComm);
		switch (type) {
		case SysCommand:
			return new SysCommand(commstr);
		case HelpCommand:
			return new HelpCommand(commstr);
		case UserCommand:
			return new UserCommand(commstr);
		case SettingCommand:
			return new SettingCommand(commstr);
		case CacheStatusCommand:
            return new CacheStatusCommand(commstr);
		case LogCommand:
            return new LogCommand(commstr);
			//TODO 当枚举变化的时候需要这面添加对应的构造函数
		default:
			throw new CommandParseException("没有找到[" + funcComm
					+ "]对应的命令 ");
		}
	}

	public static String getAllCommands() {
		StringBuilder allcomm = new StringBuilder();
		boolean first = true;
		for (CommandType t : CommandType.values()) {
			if (!t.equals(CommandType.UnKnown)) {
				if (!first)
					allcomm.append("\r\n");
				allcomm.append(t.getDescription());
				allcomm.append(":");
				allcomm.append(t.getCommandString());
				first = false;
			}
		}
		return allcomm.toString();
	}
}
