/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-2 上午11:28:18
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bean.command.commands;

import java.util.List;

import com.google.gson.Gson;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.ListHelper;
import com.yutong.clw.ygbclient.common.beans.StationInfo;
import com.yutong.clw.ygbclient.common.utils.SysConfigUtil;
import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;

/**
 * @author zhouzc 2013-12-2 上午11:28:18
 */
public class CacheStatusCommand extends Command {

	public final static String COMMAND_SET = "set";

	public final static String COMMAND_GET = "get";

	public CacheStatusCommand(String commstr) throws CommandParseException {
		super(commstr);
	}

	@Override
	protected void preInit() {
		name = "设置命令";
		functionName = CommandType.CacheStatusCommand.getCommandString();
		description = CommandType.CacheStatusCommand.getDescription()
				+ "\r\n命令\r\n	"
				+ CommandType.CacheStatusCommand.getCommandString()
				+ "\r\n参数\r\n	/all:获取所有信息";

	}

	@Override
	protected void parseFromString(String commstr) throws CommandParseException {
		super.parseFromString(commstr);

		if (functionCommand != null&& (functionCommand.equals(COMMAND_SET) || functionCommand
						.equals(COMMAND_GET))) {

		} else {
			throw new CommandParseException(functionName + " 没有找到["
					+ functionCommand + "]对应的命令方法");
		}
	}

	@Override
	public CommandExecuteResult execute() {
		CommandExecuteResult result = new CommandExecuteResult(this);
		StringBuilder builder = new StringBuilder();
		if (functionCommand != null) {

			if (functionCommand.equals(COMMAND_SET)) {
				if (functionParams != null && functionParams.length == 1) {
					if ("true".equals(functionParams[0])) {
						SysConfigUtil.setCacheOpen(Boolean
								.parseBoolean(functionParams[0]));
						builder.append("设置缓存状态：" + functionParams[0]);
					} else if ("false".equals(functionParams[0])) {
						SysConfigUtil.setCacheOpen(Boolean
								.parseBoolean(functionParams[0]));
						builder.append("设置缓存状态：" + functionParams[0]);
					} else {
						builder.append("输入命令错误！");
					}
				} else {
					builder.append("输入命令错误！");
				}
			}
			if (functionCommand.equals(COMMAND_GET)) {
				builder.append("当前缓存状态：" + SysConfigUtil.getCacheOpen());
			}

		}
		result.setContent(builder.toString());
		return result;
	}

}
