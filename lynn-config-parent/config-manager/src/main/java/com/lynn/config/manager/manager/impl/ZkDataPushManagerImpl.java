package com.lynn.config.manager.manager.impl;

import com.alibaba.fastjson.JSONObject;
import com.lynn.config.api.constants.ExceptionConstants;
import com.lynn.config.api.constants.ZkConstants;
import com.lynn.config.api.constants.enums.ModuleStatus;
import com.lynn.config.api.dto.StaticSystemVariableDto;
import com.lynn.config.api.entity.ActiveSystemVariable;
import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.entity.SystemVariable;
import com.lynn.config.api.exception.CommonException;
import com.lynn.config.api.manager.ActiveSystemVariableManager;
import com.lynn.config.api.manager.StaticSystemVariableManager;
import com.lynn.config.api.manager.ZkDataPushManager;
import com.lynn.config.service.service.SystemModuleService;
import com.lynn.config.service.zookeeperScheme.factory.ZookeeperFactory;
import com.lynn.config.service.zookeeperScheme.template.TreeCacheTemplate;
import com.lynn.config.api.utils.ValidateUtil;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name :
 * com.lynn.config.bsw.manager.impl Description : Author : cailinfeng Date :
 * 2016/1/29
 */
@Service
public class ZkDataPushManagerImpl implements ZkDataPushManager {

	private static final Logger logger = LoggerFactory.getLogger(ZkDataPushManager.class);

	@Resource
	private StaticSystemVariableManager staticSystemVariableManager;

	@Resource
	private ActiveSystemVariableManager activeSystemVariableManager;

	@Resource
	private SystemModuleService systemModuleService;

	/**
	 * 方法 pushListToZk 功能描述 ：将SystemVariable子类数据推送到zookeeper中
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/29
	 * @param
	 * @return
	 * 
	 */
	@Override
	public <T extends SystemVariable> Map<String, Map<String, String>> pushListToZk(List<T> listToPush) throws Exception {
		logger.debug("pushListToZk 推送数据到zookeeper，参数{}", listToPush);
		if (listToPush == null || listToPush.size() == 0) {
			return null;
		}
		// 最终需要推送的map数据，格式为 key{modulePath(String)} -- >
		// Map(key{nid},value(value))
		Map<String, Map<String, String>> pathAndDataMap = new HashMap<>();

		/**
		 * 排序规整数据，填充到pathAndDataMap
		 * -start----------------------------------------
		 * ----------------------------------
		 */
		Collections.sort(listToPush, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				if (o1.getSystemName().compareTo(o2.getSystemName()) != 0) {
					return o1.getSystemName().compareTo(o2.getSystemName());
				} else {
					return o1.getModuleName().compareTo(o2.getModuleName());
				}
			}
		});

		Map<String, String> zkConfigMap = new HashMap<String, String>();
		String preSystem = "";
		String curSystem = "";
		for (SystemVariable temp : listToPush) {
			// 当前后台系统数据不推送到zookeeper
			if (ZkConstants.SYSTEM_EXCLUDE_BACKSTAGE.equals(temp.getSystemName())) {
				continue;
			}
			curSystem = temp.getSystemName();
			if (!preSystem.equals(curSystem)) {
				StaticSystemVariableDto queryTempSystem = new StaticSystemVariableDto();
				queryTempSystem.setSystemName(curSystem);
				zkConfigMap = activeSystemVariableManager.getZkConfigBySystem(queryTempSystem);
			}

			// 获取需要设置的数据所在的节点位置
			String zkPath = "";
			if (temp instanceof ActiveSystemVariable) {
				zkPath = zkConfigMap.get(ZkConstants.KEY_ZK_ACTIV_NODE) + "/" + temp.getModuleName();
			} else {
				zkPath = zkConfigMap.get(ZkConstants.KEY_ZK_STATIC_NODE) + "/" + temp.getModuleName();
			}
			Map<String, String> tempMap = pathAndDataMap.get(zkPath);
			if (tempMap == null) {
				Map<String, String> mapToAdd = new HashMap<String, String>();
				if (!ValidateUtil.isNullOrEmpty(temp.getNid())) {
					mapToAdd.put(temp.getNid(), temp.getValue() + "");
				}
				pathAndDataMap.put(zkPath, mapToAdd);
			} else {
				if (!ValidateUtil.isNullOrEmpty(temp.getNid())) {
					tempMap.put(temp.getNid(), temp.getValue() + "");
				}
			}

			preSystem = temp.getSystemName();
		}
		/**
		 * 排序规整数据，填充到pathAndDataMap
		 * -end------------------------------------------
		 * --------------------------------
		 */
		Set<Map.Entry<String, Map<String, String>>> set = pathAndDataMap.entrySet();

		/**
		 * 推送数据到zookeeper
		 */
		CuratorFramework client = ZookeeperFactory.get(zkConfigMap);

		try {
			for (Map.Entry<String, Map<String, String>> mEntry : set) {
				try {
					TreeCacheTemplate.setOrCreateValue(client, mEntry.getKey().toString(), JSONObject.toJSONString(mEntry.getValue()));
				} catch (Exception e) {
					logger.error("pushData to zookeeper error , error data {}", mEntry, e);
					if (client != null) {
						ZookeeperFactory.close();
					}
					throw new CommonException(ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR,
							ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR_MSG);
				}
			}
		} finally {
			if (client != null) {
				ZookeeperFactory.close();
			}
		}
		return pathAndDataMap;
	}

	/**
	 * 方法 pushSystemModuleToZk 功能描述 ：推送创建的模块到zookeeper，注意该方法会清空原有的数据
	 * 如有需要，请调用ZkDataPushManager.pushListToZk(List<T extends SystemVariable>
	 * dataList )
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/29
	 * @param
	 * @return
	 * 
	 */
	@Override
	public <T extends SystemModule> void pushSystemModuleToZk(List<T> systemModules) throws Exception {
		logger.debug("pushSystemModuleToZk 推送模块节点到zookeeper，参数{}", systemModules);
		// 根据父节点，筛选出该list中所有父节点
		Collections.sort(systemModules, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.getParentModuleId().compareTo(o2.getParentModuleId());
			}
		});

		// 获取所有有效的系统名称
		List<SystemModule> allSystems = systemModuleService.getAllSystems();
		Map<String, String> systemMap = new HashMap<String, String>();
		for (SystemModule sys : allSystems) {
			systemMap.put(sys.getId(), sys.getModuleName());
		}

		// 整合systemModules，key{system->{String}} >> value {modules -> { List }}
		Map<String, List<SystemModule>> pushMap = new HashMap<>();
		if (systemMap.size() > 0) {
			for (SystemModule sys : systemModules) {
				if (ValidateUtil.isNullOrEmpty(pushMap.get(systemMap.get(sys.getParentModuleId())))) {
					List<SystemModule> listModule = new ArrayList<>();
					if (!"0".equals(sys.getParentModuleId())) {
						listModule.add(sys);
						pushMap.put(systemMap.get(sys.getParentModuleId()), listModule);
					}
				} else {
					if ("0".equals(sys.getParentModuleId())) {
						continue;
					}
					pushMap.get(systemMap.get(sys.getParentModuleId())).add(sys);
				}
			}
		}

		Set<Map.Entry<String, List<SystemModule>>> entries = pushMap.entrySet();
		String zkPath = "";
		// 遍历系统
		CuratorFramework client = null;
		try {
			for (Map.Entry<String, List<SystemModule>> e : entries) {
				StaticSystemVariableDto s = new StaticSystemVariableDto();
				s.setSystemName(e.getKey().toString());
				Map<String, String> zkconfigMap = activeSystemVariableManager.getZkConfigBySystem(s);
				client = ZookeeperFactory.get(zkconfigMap);
				List<SystemModule> list = (List<SystemModule>) e.getValue();
				// 遍历每个模块，并推送
				for (SystemModule sm : list) {
					if (ModuleStatus.ACTIVE.equals(sm.getFlag())) {
						zkPath = zkconfigMap.get(ZkConstants.KEY_ZK_ACTIV_NODE) + "/" + sm.getModuleName();
					} else {
						zkPath = zkconfigMap.get(ZkConstants.KEY_ZK_STATIC_NODE) + "/" + sm.getModuleName();
					}
					try {
						TreeCacheTemplate.setOrCreateValue(client, zkPath, "");
					} catch (Exception e1) {
						logger.error("pushData to zookeeper error , error data {}", zkPath, e1);
						if (client != null) {
							ZookeeperFactory.close();
						}
						throw new CommonException(ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR,
								ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR_MSG);
					}
				}
			}
		} finally {
			if (client != null) {
				ZookeeperFactory.close();
			}
		}
	}

	/**
	 * 方法 delSystemModuleToZk 功能描述 ：删除节点
	 * 
	 * @author cailinfeng
	 * @createTime 2016/1/29
	 * @param
	 * @return
	 * 
	 */
	@Override
	public <T extends SystemModule> void delSystemModuleToZk(List<T> systemModules) throws Exception {
		logger.debug("pushSystemModuleToZk 推送模块节点到zookeeper，参数{}", systemModules);
		// 根据父节点，筛选出该list中所有父节点
		Collections.sort(systemModules, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				return o1.getParentModuleId().compareTo(o2.getParentModuleId());
			}
		});

		// 获取所有有效的系统名称
		List<SystemModule> allSystems = systemModuleService.getAllSystems();
		Map<String, String> systemMap = new HashMap<String, String>();
		for (SystemModule sys : allSystems) {
			systemMap.put(sys.getId(), sys.getModuleName());
		}

		// 整合systemModules，key{system->{String}} >> value {modules -> { List }}
		Map<String, List<SystemModule>> pushMap = new HashMap<>();
		if (systemMap.size() > 0) {
			for (SystemModule sys : systemModules) {
				if (ValidateUtil.isNullOrEmpty(pushMap.get(systemMap.get(sys.getParentModuleId())))) {
					List<SystemModule> listModule = new ArrayList<>();
					if (!"0".equals(sys.getParentModuleId())) {
						listModule.add(sys);
						pushMap.put(systemMap.get(sys.getParentModuleId()), listModule);
					}
				} else {
					if ("0".equals(sys.getParentModuleId())) {
						continue;
					}
					pushMap.get(systemMap.get(sys.getParentModuleId())).add(sys);
				}
			}
		}

		Set<Map.Entry<String, List<SystemModule>>> entries = pushMap.entrySet();
		String zkPath = "";
		// 遍历系统
		CuratorFramework client = null;
		try {
			for (Map.Entry<String, List<SystemModule>> e : entries) {
				StaticSystemVariableDto s = new StaticSystemVariableDto();
				s.setSystemName(e.getKey().toString());
				Map<String, String> zkconfigMap = activeSystemVariableManager.getZkConfigBySystem(s);
				client = ZookeeperFactory.get(zkconfigMap);
				List<SystemModule> list = (List<SystemModule>) e.getValue();
				// 遍历每个模块，并推送
				for (SystemModule sm : list) {
					if (ModuleStatus.ACTIVE.equals(sm.getFlag())) {
						zkPath = zkconfigMap.get(ZkConstants.KEY_ZK_ACTIV_NODE) + "/" + sm.getModuleName();
					} else {
						zkPath = zkconfigMap.get(ZkConstants.KEY_ZK_STATIC_NODE) + "/" + sm.getModuleName();
					}
					try {
						TreeCacheTemplate.removeNode(client, zkPath);
					} catch (Exception e1) {
						logger.error("pushData to zookeeper error , error data {}", zkPath, e1);
						if (client != null) {
							ZookeeperFactory.close();
						}
						throw new CommonException(ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR,
								ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR_MSG);
					}
				}
			}
		} finally {
			if (client != null) {
				ZookeeperFactory.close();
			}
		}
	}

	@Override
	public <T extends StaticSystemVariable> Map<String, Map<String, String>> RefreshStaticZk(List<T> listToPush) throws Exception {
		logger.debug("RefreshStaticZk 刷新数据到zookeeper，参数{}", listToPush);
		if (listToPush == null || listToPush.size() == 0) {
			return null;
		}
		// 需要先删除的路径,例如/root/customer/static
		List<String> deletePath = new ArrayList<String>();
		// 最终需要推送的map数据，格式为 key{modulePath(String)} -- >
		// Map(key{nid},value(value))
		Map<String, Map<String, String>> pathAndDataMap = new HashMap<>();

		/**
		 * 排序规整数据，填充到pathAndDataMap
		 * -start----------------------------------------
		 * ----------------------------------
		 */
		Collections.sort(listToPush, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				if (o1.getSystemName().compareTo(o2.getSystemName()) != 0) {
					return o1.getSystemName().compareTo(o2.getSystemName());
				} else {
					return o1.getModuleName().compareTo(o2.getModuleName());
				}
			}
		});

		Map<String, String> zkConfigMap = new HashMap<String, String>();
		String preSystem = "";
		String curSystem = "";
		for (SystemVariable temp : listToPush) {
			// 当前后台系统数据不推送到zookeeper
			if (ZkConstants.SYSTEM_EXCLUDE_BACKSTAGE.equals(temp.getSystemName())) {
				continue;
			}
			curSystem = temp.getSystemName();
			if (!preSystem.equals(curSystem)) {
				StaticSystemVariableDto queryTempSystem = new StaticSystemVariableDto();
				queryTempSystem.setSystemName(curSystem);
				zkConfigMap = activeSystemVariableManager.getZkConfigBySystem(queryTempSystem);
			}

			// 删除的路径
			deletePath.add(zkConfigMap.get(ZkConstants.KEY_ZK_STATIC_NODE));
			// 获取需要设置的数据所在的节点位置
			String zkPath = "";
			zkPath = zkConfigMap.get(ZkConstants.KEY_ZK_STATIC_NODE) + "/" + temp.getModuleName();
			Map<String, String> tempMap = pathAndDataMap.get(zkPath);
			if (tempMap == null) {
				Map<String, String> mapToAdd = new HashMap<String, String>();
				if (!ValidateUtil.isNullOrEmpty(temp.getNid())) {
					mapToAdd.put(temp.getNid(), temp.getValue() + "");
				}
				pathAndDataMap.put(zkPath, mapToAdd);
			} else {
				if (!ValidateUtil.isNullOrEmpty(temp.getNid())) {
					tempMap.put(temp.getNid(), temp.getValue() + "");
				}
			}

			preSystem = temp.getSystemName();
		}
		/**
		 * 排序规整数据，填充到pathAndDataMap
		 * -end------------------------------------------
		 * --------------------------------
		 */
		Set<Map.Entry<String, Map<String, String>>> set = pathAndDataMap.entrySet();

		// 获取Client对象
		CuratorFramework client = ZookeeperFactory.get(zkConfigMap);

		try {
			for (String path : deletePath) {
				try {
					TreeCacheTemplate.removeNode(client, path);
				} catch (Exception e) {
					logger.error("delete path error , error data {}", path, e);
					if (client != null) {
						ZookeeperFactory.close();
					}
					throw new CommonException(ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR,
							ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR_MSG);
				}
			}
			/**
			 * 推送数据到zookeeper
			 */
			for (Map.Entry<String, Map<String, String>> mEntry : set) {
				try {
					TreeCacheTemplate.setOrCreateValue(client, mEntry.getKey().toString(), JSONObject.toJSONString(mEntry.getValue()));
				} catch (Exception e) {
					logger.error("pushData to zookeeper error , error data {}", mEntry, e);
					if (client != null) {
						ZookeeperFactory.close();
					}
					throw new CommonException(ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR,
							ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR_MSG);
				}
			}
		} finally {
			if (client != null) {
				ZookeeperFactory.close();
			}
		}
		return pathAndDataMap;
	}

	@Override
	public void clean() {
		logger.debug("全清zk");
		CuratorFramework client = null;
		List<ActiveSystemVariable> result = activeSystemVariableManager.getZkConfig();
		Map<String, String> zkconfigMap = new HashMap<String, String>();
		for (ActiveSystemVariable activeSystemVariable : result) {
			zkconfigMap.put(activeSystemVariable.getNid(), activeSystemVariable.getValue());
		}
		client = ZookeeperFactory.get(zkconfigMap);
		try {
			TreeCacheTemplate.clean(client);
		} catch (Exception e) {
			if (client != null) {
				ZookeeperFactory.close();
			}
			logger.error("clean zookeeper", e);
			throw new CommonException(ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR, ExceptionConstants.System.ZOOKEEPER_PUSH_DATA_ERROR_MSG);

		} finally {
			if (client != null) {
				ZookeeperFactory.close();
			}
		}
	}
}
