/**
 * @公司名称：YUTONG
 * @作者：zhouzc
 * @版本号：1.0
 * @生成日期：2013-12-2 上午9:21:09
 * @功能描述：
 */
package com.yutong.clw.ygbclient.view.bean.command.commands;

import com.yutong.clw.ygbclient.AppDataKeyConstant;
import com.yutong.clw.ygbclient.YtApplication;
import com.yutong.clw.ygbclient.common.beans.UserInfo;
import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;

/**
 * @author zhouzc 2013-12-2 上午9:21:09
 * 
 */
public class UserCommand extends Command {

	public final static String COMMAND_ALL = "all";

	public UserCommand(String commstr) throws CommandParseException {
		super(commstr);
	}

	@Override
	public CommandExecuteResult execute() {
		CommandExecuteResult result = new CommandExecuteResult(this);
		if (functionCommand == null || functionCommand.equals(COMMAND_ALL)) {
			Object uobj = YtApplication.getInstance().getDatas(
					AppDataKeyConstant.KEY_USER);
			if (uobj != null && uobj instanceof UserInfo) {
				UserInfo cuser = (UserInfo) YtApplication.getInstance()
						.getDatas(AppDataKeyConstant.KEY_USER);
				StringBuilder builder = new StringBuilder();
				builder.append("当前用户：" + cuser.Name);
				builder.append("\r\n当前用户Id：" + cuser.Id);
				builder.append("\r\n当前用户AccessToken：" + cuser.AccessToken);
				builder.append("\r\n当前用户RefreshToken：" + cuser.RefreshToken);
				builder.append("\r\n当前用户Code：" + cuser.Code);
				builder.append("\r\n当前用户Department：" + cuser.Department);
				builder.append("\r\n当前用户EipPhone：" + cuser.EipPhone);
				builder.append("\r\n当前用户Phone：" + cuser.Phone);
				builder.append("\r\n当前用户ExpiresIn：" + cuser.ExpiresIn);
				builder.append("\r\n当前用户NameShort：" + cuser.NameShort);
				builder.append("\r\n当前用户BelongArea：" + cuser.BelongArea);
				builder.append("\r\n当前用户BindPhone：" + cuser.BindPhone);
				builder.append("\r\n当前用户DefaultPassword："
						+ cuser.DefaultPassword);
				builder.append("\r\n当前用户Sex：" + cuser.Sex);
				builder.append("\r\n当前用户Photo：" + cuser.Photo);
				result.setContent(builder.toString());
			} else {
				result.setContent("没有获取到当前用户信息");
			}
		} else {
			result.setContent("没有找到[" + functionCommand + "]对应的命令方法");
		}
		return result;
	}

	@Override
	protected void parseFromString(String commstr) throws CommandParseException {
		super.parseFromString(commstr);
		if (functionCommand == null || functionCommand.equals(COMMAND_ALL)) {

		} else {
			throw new CommandParseException(functionName + " 没有找到["
					+ functionCommand + "]对应的命令方法");
		}
	}

	@Override
	protected void preInit() {
		name = "获取用户信息";
		functionName = CommandType.UserCommand.getCommandString();
		description = "获取用户信息\r\n命令\r\n	"
				+ CommandType.UserCommand.getCommandString()
				+ "\r\n参数\r\n	/all:获取所有信息";

	}

}
