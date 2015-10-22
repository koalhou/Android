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
import com.yutong.clw.ygbclient.view.bean.command.Command;
import com.yutong.clw.ygbclient.view.bean.command.CommandExecuteResult;
import com.yutong.clw.ygbclient.view.bean.command.CommandParseException;
import com.yutong.clw.ygbclient.view.common.PrefDataUtil;

/**
 * @author zhouzc 2013-12-2 上午11:28:18
 * 
 */
public class SettingCommand extends Command {

	public final static String COMMAND_ALL = "all";

	public SettingCommand(String commstr) throws CommandParseException {
		super(commstr);
	}

	@Override
	protected void preInit() {
		name = "设置命令";
		functionName = CommandType.SettingCommand.getCommandString();
		description = CommandType.SettingCommand.getDescription()
				+ "\r\n命令\r\n	" + CommandType.SettingCommand.getCommandString()
				+ "\r\n参数\r\n	/all:获取所有信息";

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
	public CommandExecuteResult execute() {
		CommandExecuteResult result = new CommandExecuteResult(this);
		StringBuilder builder = new StringBuilder();
		if (functionCommand == null || functionCommand.equals(COMMAND_ALL)) {
			builder.append("地图是否刷新："
					+ PrefDataUtil.getAutoMapRefresh(YtApplication
							.getInstance()));
			builder.append("\r\n地图刷新频率(秒)："
					+ PrefDataUtil.getMapRefreshInterval(YtApplication
							.getInstance()));
			builder.append("\r\n是否第一次登录："
					+ PrefDataUtil.getIsFirstLogin(YtApplication.getInstance()));
			builder.append("\r\n提醒铃声名称："
					+ PrefDataUtil.getRemindRingName(YtApplication
							.getInstance()));
			builder.append("\r\n提醒铃声路径："
					+ (null == PrefDataUtil.getRemindRingUri(YtApplication
							.getInstance()) ? "null" : PrefDataUtil
							.getRemindRingUri(YtApplication.getInstance())
							.getPath()));
			builder.append("\r\n提醒铃声音量："
					+ PrefDataUtil.getRemindRingVolume(YtApplication
							.getInstance()));
			builder.append("\r\n提醒是否震动："
					+ PrefDataUtil.getRemindVibrate(YtApplication.getInstance()));
			builder.append("\r\n厂区设置："
					+ PrefDataUtil.getFactory(YtApplication.getInstance()));

			builder.append("\r\n搜索历史：" + getSearchHistoryContent());

		}
		result.setContent(builder.toString());
		return result;
	}

	private String getSearchHistoryContent() {
		StringBuilder builder = new StringBuilder();
		List<StationInfo> hiss = PrefDataUtil
				.getStationSearchHistory(YtApplication.getInstance());
		if (hiss == null || hiss.size() == 0) {
			builder.append("无");
		} else {
			List<String> sdata = new ListHelper<StationInfo, String>() {
				@Override
				protected String getItem(StationInfo data) {
					try {
						if (data == null)
							return "null";
						Gson g = new Gson();
						return g.toJson(data);
					} catch (Exception err) {
						err.printStackTrace();
						return "id:" + data.id;
					}
				}
			}.getNewList(hiss);

			boolean first = true;
			for (String s : sdata) {
				if (!first)
					builder.append("|");
				builder.append(s);
			}
		}
		return builder.toString();
	}
}
