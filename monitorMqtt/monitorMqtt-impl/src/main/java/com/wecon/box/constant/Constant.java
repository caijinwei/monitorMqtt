package com.wecon.box.constant;

/**
 * Created by whp on 2017/8/25.
 */
public class Constant {

	// 状态常量
	public class State {
		/**
		 * 已同步给盒子
		 */
		public static final int STATE_SYNCED_BOX = 0;

		/**
		 * 新增配置
		 */
		public static final int STATE_NEW_CONFIG = 1;

		/**
		 * 更新配置，没有修改驱动
		 */
		public static final int STATE_UPDATE_CONFIG = 2;

		/**
		 * 删除配置
		 */
		public static final int STATE_DELETE_CONFIG = 3;

		/**
		 * 更新配置，有修改驱动
		 */
		public static final int STATE_UPDATE_CONFIG_PD = 4;

		/**
		 * 盒子在线状态
		 */
		public static final int STATE_BOX_ONLINE = 1;

		/**
		 * 盒子离线状态
		 */
		public static final int STATE_BOX_OFFLINE = 0;

		/**
		 * 监控点离线状态
		 */
		public static final int STATE_MONITOR_OFFLINE = 0;

		/**
		 * 监控点在线状态
		 */
		public static final int STATE_MONITOR_ONLINE = 1;

		/**
		 * 监控点超时状态
		 */
		public static final int STATE_MONITOR_TIMEOUT = 2;

		/**
		 * 编译失败
		 */
		public static final int STATE_COMPILE_FAILED = -1;

		/**
		 * 地址转换失败
		 */
		public static final int STATE_ADDR_TRANS_FAILED = -2;

		/**
		 * 地址绑定失败
		 */
		public static final int STATE_ADDR_BIND_FAILED = -3;
	}

	public class DataType {
		/**
		 * 实时数据类型
		 */
		public static final int DATA_TYPE_REAL = 0;

		/**
		 * 历史数据类型
		 */
		public static final int DATA_TYPE_HISTORY = 1;
		/**
		 * 报警数据类型
		 */
		public static final int DATA_TYPE_ALARM = 2;
	}
}
