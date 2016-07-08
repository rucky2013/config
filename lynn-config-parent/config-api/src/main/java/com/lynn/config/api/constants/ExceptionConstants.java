/*
 * Copyright @ 2015QIANLONG.
 * All right reserved.
 */

package com.lynn.config.api.constants;

/**
 * 
 * Description:异常信息和代码
 * 
 * @PackageName:com.lynn.tkj.cs.core.constants
 * @ClassName:ExceptionConstants
 * @author xiongweitao
 * @date 2015年11月17日 下午5:29:50
 */
public interface ExceptionConstants {
	/**
	 * 
	 * Description:系统相关的异常信息
	 * 
	 * @PackageName:com.lynn.tkj.cs.core.constants
	 * @ClassName:System
	 * @author xiongweitao
	 * @date 2015年11月17日 下午5:30:05
	 */
	public interface System {
		
			public static final String SERVER_SUCCESS = "00000000";

			public static final String SERVER_ERROR = "56000001";
			public static final String SERVER_ERROR_MSG = "系统异常，请稍后再试！";

			public static final String SERVER_CONNECTION_TIMED_OUT = "56000002";
			public static final String SERVER_CONNECTION_TIMED_OUT_MSG = "服务器连接超时";

			public static final String DATABASE_CONNECTION_TIMED_OUT = "56000003";
			public static final String DATABASE_CONNECTION_TIMED_OUT_MSG = "数据库连接超时";

			public static final String CACHE_CONNECTION_TIMED_OUT = "56000004";
			public static final String CACHE_CONNECTION_TIMED_OUT_MSG = "缓存连接超时";

			public static final String MQ_CONNECTION_TIMED_OUT = "56000005";
			public static final String MQ_CONNECTION_TIMED_OUT_MSG = "消息队列连接超时";

			public static final String DATABASE_CONNECTIONS_EXHAUSTED = "56000006";
			public static final String DATABASE_CONNECTIONS_EXHAUSTED_MSG = "数据库连接数用尽";

			public static final String CAN_NOT_FIND_SERVICE = "56000007";
			public static final String CAN_NOT_FIND_SERVICE_MSG = "找不到服务";

			public static final String PARAMETER_ERROR = "56000008";
			public static final String PARAMETER_ERROR_MSG = "参数异常";

			public static final String MISSING_REQUIRED_PARAMETERS = "56000009";
			public static final String MISSING_REQUIRED_PARAMETERS_MSG = "缺少必填参数";

			public static final String PARAMETER_VALUE_OUT_OF_THE_RANGE = "56000010";
			public static final String PARAMETER_VALUE_OUT_OF_THE_RANGE_MSG = "参数值超出取值范围";

			public static final String SIGNATURE_VERIFICATION_FAILED = "56000011";
			public static final String SIGNATURE_VERIFICATION_FAILED_MSG = "签名验证失败";

			public static final String REQUEST_TIMED_OUT = "56000012";
			public static final String REQUEST_TIMED_OUT_MSG = "请求超时";

			public static final String NO_RIGHT_TO_USE = "56000013";
			public static final String NO_RIGHT_TO_USE_MSG = "无权调用";

			public static final String INTERFACE_VERSION_DOES_NOT_SUPPORT = "56000014";
			public static final String INTERFACE_VERSION_DOES_NOT_SUPPORT_MSG = "接口版本不支持";
		
		
		
		
		public static final String INTERNAL_SERVER_ERROR = "5600000";
		public static final String LOGIC_ERROR = "5600001";
		public static final String SYSTEMVARIABLE_ERROR = "5600002";
		public static final String SYSTEM_API_ERROR = "5600003";
		public static final String ZOOKEEPER_PUSH_DATA_ERROR = "5100004";
		public static final String ID_NOT_EXISTS = "5600005";
		public static final String SYSTEM_MODULE_DELETED = "5600006";

		public static final String INTERNAL_SERVER_ERROR_MSG = "服务异常，请稍后再试！";
		public static final String LOGIC_ERROR_MSG = "业务逻辑错误！";
		public static final String SYSTEMVARIABLE_ERROR_MSG = "系统参数异常！";
		public static final String SYSTEM_API_ERROR_MSG = "后台接口服务异常，请联系系统管理员！";
		public static final String ZOOKEEPER_PUSH_DATA_ERROR_MSG = "推送数据到ZOOKEEPER异常！";
		public static final String ID_NOT_EXISTS_MSG = "ID对应数据不存在！";
		public static final String SYSTEM_MODULE_DELETED_MSG = "模块状态为删除,请先恢复模块状态！";
	}

	/**
	 * 
	 * Description:请求参数相关的异常信息
	 * 
	 * @PackageName:com.lynn.tkj.cs.core.constants
	 * @ClassName:Param
	 * @author xiongweitao
	 * @date 2015年11月17日 下午5:30:18
	 */
	public interface Param {
		public static final String PARAM_INVALID = "5600000";
		public static final String PASSWORD_INVALID = "5600007";
		public static final String IP_INVALID = "5600009";
		public static final String VARIABLE_EXISTS = "5600012";
		public static final String VARIABLE_NOT_EXISTS = "5600013";
		public static final String SYSTEM_EXISTS = "5600014";
		public static final String MODULE_EXISTS = "5600015";

		public static final String PARAM_INVALID_MSG = "参数无效！";
		public static final String PASSWORD_INVALID_MSG = "密码无效！";
		public static final String IP_INVALID_MSG = "IP地址无效！";
		public static final String VARIABLE_EXISTS_MSG = "变量已存在！";
		public static final String VARIABLE_NOT_EXISTS_MSG = "变量不存在！";
		public static final String SYSTEM_EXISTS_MSG = "系统已存在！";
		public static final String MODULE_EXISTS_MSG = "模块已存在！";

	}

	// zk连接异常
	public interface ZooKeeper {
		public static final String ZK_SYNCHRONIZE_FAIL = "560000110";
		public static final String ZK_SYNCHRONIZE_FAIL_MSG = "同步到ZK失败,请联系管理员";
	}

	/**
	 * 用户异常信息
	 */
	public interface User {
		public static final String USER_PASSWORD_MISSMATCH = "5600003";
		public static final String USER_PASSWORD_MSIIMATCH_MSG = "帐户密码不匹配！";

		public static final String USER_STATUS_ERROR = "5600004";
		public static final String USER_STATUS_ERROR_MSG = "用户状态异常";

		public static final String USER_NOT_EXISTS = "5600005";
		public static final String USER_NOT_EXISTS_MSG = "用户不存在！";

		public static final String USER_NOT_LOGIN = "5600006";
		public static final String USER_NOT_LOGIN_MSG = "用户未登录！";

		public static final String USER_PAS_IS_WRONG = "5600007";
		public static final String USER_PAS_IS_WRONG_MSG = "密码错误";

		public static final String USER_IS_BLOCK = "5600008";
		public static final String USER_IS_BLOCK_MSG = "账户锁定";

	}

	// 接口加密常量
	public interface Secret {
		public static final String SIGN_ERROR = "1000000";
		public static final String SIGN_ERROR_MSG = "请求签名验证失败!";
	}

	// 应用服务器常量
	public interface Server {
		public static final String SYSTEM_IP_EXISTS = "5600001";
		public static final String SYSTEM_IP_UPDATE_EXISTS = "5600002";
		public static final String SYSTEM_IP_EXISTS_MSG = "应用服务IP已存在！";
		public static final String SYSTEM_IP_UPDATE_EXISTS_MSG = "更新后的应用服务IP已存在！";
	}
	
	
}
