package com.lynn.config.api.manager;

import com.lynn.config.api.dto.PageInfo;
import com.lynn.config.api.dto.Pagenation;
import com.lynn.config.api.entity.SystemServerIp;

import java.util.List;

/**
 * Created by fancongchun on 2016/1/22.
 */
public interface SystemServerIpManager {
	PageInfo<SystemServerIp> getSystemServerIpByCondition(SystemServerIp systemServerIp, Pagenation pagenation);

	public List<String> getgetSystemName();

	public void addOrUpdateSystemVariable(SystemServerIp systemServerIp);

	/**
	 * 
	 * Description:检测所有的服务器,将宕机服务器记录到数据库
	 * 
	 * @author xiongweitao
	 * @param ipAddress 
	 * @date 2016年2月24日 上午11:50:37
	 */
	void checkAllServer(String ipAddress);
}
