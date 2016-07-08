package com.lynn.config.api.manager;

import com.lynn.config.api.entity.StaticSystemVariable;
import com.lynn.config.api.entity.SystemModule;
import com.lynn.config.api.entity.SystemVariable;

import java.util.List;
import java.util.Map;

/**
 * Copyright @ 2013QIANLONG. All right reserved. Class Name :
 * com.lynn.config.bsw.manager Description : Author : cailinfeng Date :
 * 2016/1/29
 */
public interface ZkDataPushManager {

	public <T extends SystemVariable> Map<String, Map<String, String>> pushListToZk(List<T> dataList) throws Exception;

	public <T extends SystemModule> void pushSystemModuleToZk(List<T> systemModules) throws Exception;

	public <T extends SystemModule> void delSystemModuleToZk(List<T> systemModules) throws Exception;

	/**
	 * 
	 * Description:推送系统的静态参数到zk,先删除该系统的所有静态节点,重建新节点
	 * 
	 * @param systemNames
	 * @param listToPush
	 * @return
	 * @author xiongweitao
	 * @throws Exception
	 * @date 2016年3月9日 下午4:35:56
	 */
	public <T extends StaticSystemVariable> Map<String, Map<String, String>> RefreshStaticZk(List<T> listToPush) throws Exception;

	/**
	 * 
	 * Description:全清zk
	 * 
	 * @author xiongweitao
	 * @date 2016年3月10日 下午4:31:40
	 */
	public void clean();
}
