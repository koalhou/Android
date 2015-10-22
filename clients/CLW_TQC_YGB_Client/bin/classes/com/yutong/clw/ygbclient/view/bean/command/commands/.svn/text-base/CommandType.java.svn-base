package com.yutong.clw.ygbclient.view.bean.command.commands;

/**
 * 命令类型枚举，和{@link Command}类的子类对应
 * @author zhouzc 2013-12-2 上午11:30:04
 *
 */
public enum CommandType {
	UnKnown {
		@Override
		public String getCommandString() {
			return "unknown";
		}

		@Override
		public String getDescription() {
			return "未知命令";
		}
	},	HelpCommand {
		@Override
		public String getCommandString() {
			return "help";
		}

		@Override
		public String getDescription() {
			return "获取帮助信息命令";
		}
	},
	SysCommand {
		@Override
		public String getCommandString() {
			return "sys";
		}

		@Override
		public String getDescription() {
			return "系统相关命令";
		}
	},UserCommand {
		@Override
		public String getCommandString() {
			return "user";
		}

		@Override
		public String getDescription() {
			return "用户相关命令";
		}
	},SettingCommand {
		@Override
		public String getCommandString() {
			return "setting";
		}

		@Override
		public String getDescription() {
			return "设置相关命令";
		}
	},CacheStatusCommand {
        @Override
        public String getCommandString() {
            return "cachestatus";
        }

        @Override
        public String getDescription() {
            return "设置App缓存状态";
        }
    },LogCommand {
        @Override
        public String getCommandString() {
            return "log";
        }

        @Override
        public String getDescription() {
            return "日志设置";
        }
    };
	


	public abstract String getCommandString();
	
	public abstract String getDescription();
	

	public static CommandType getCommandTypeFromString(String str) {
		
		for(CommandType t:CommandType.values()){
			if(t.getCommandString().equals(str)){
				return t;
			}
		}
		return CommandType.UnKnown;
	}
}
