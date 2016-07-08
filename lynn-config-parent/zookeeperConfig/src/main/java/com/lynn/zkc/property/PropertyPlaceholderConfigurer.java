package com.lynn.zkc.property;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.lynn.zkc.cache.CacheManager;
import com.lynn.zkc.cache.InnitCache;
import com.lynn.zkc.constants.SystemConstants;
import com.lynn.zkc.util.OrderProperties;
import com.lynn.zkc.zookeeper.ZkClientFactory;

/**
 * 
 * Description:zookeeper连接和数据初始化
 * 
 * @PackageName:com.lynn.zkc.property
 * @ClassName:PropertyPlaceholderConfigurer
 * @author xiongweitao
 * @date 2016年3月9日 上午9:25:27
 */
public class PropertyPlaceholderConfigurer extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyPlaceholderConfigurer.class);

	// 存储位置WEB-INF下
	private static final String FILENAME = "conf.properties";

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

		try {
			// 初始化静态变量和动态变量
			new InnitCache().innitParam(props);

			// 静态变量写回classpath上一级,即WEB-INF下
			String path = this.getClass().getClassLoader().getResource("/").getPath();
			// 如果该路径不存在,创建该目录
			File parentFile = new File(path).getParentFile();
			if (!parentFile.exists() || !parentFile.isDirectory()) {
				parentFile.mkdirs();
			}
			File file = new File(parentFile, FILENAME);
			// OrderProperties用于静态变量的回写
			OrderProperties oPro = new OrderProperties();
			OutputStream outputStream = new FileOutputStream(file);
			Map<String, Object> staticMap = CacheManager.getStaticMap();
			if (staticMap != null && !staticMap.isEmpty()) {
				for (Entry<String, Object> entry : staticMap.entrySet()) {
					oPro.setProperty(entry.getKey(), entry.getValue().toString());
					// 将静态系统参数回写到本方法的形参props中
					props.setProperty(entry.getKey(), entry.getValue().toString());
				}
				oPro.store(outputStream, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
				outputStream.close();
			}
		} catch (Exception e) {
			LOGGER.error("配置文件处理失败", e);
			throw new RuntimeException();
		}
		// 如果动态参数来自于配置服务,则尝试连接ZK重新初始化数据
		if (SystemConstants.SERVERDATA.equals(CacheManager.getActiveMapValue(SystemConstants.DATAFROM))) {
			Integer retryTime = Integer.parseInt((String) CacheManager.getStaticMapValue("zk.disconnect.retryTime"));
			// 开启定时任务,如果动态参数来自于配置服务,则尝试连接ZK重新初始化数据
			final Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					LOGGER.debug("执行定时任务,从ZK获取动态参数");
					if (SystemConstants.SERVERDATA.equals(CacheManager.getActiveMapValue(SystemConstants.DATAFROM))) {
						try {
							if (ZkClientFactory.getZkClient() == null) {
								throw new RuntimeException("连接ZOOKEEPER失败");
							} else {
								new InnitCache().innitActiveMapByZk();
							}
						} catch (Exception e) {
							LOGGER.error("通过ZK初始化动态参数失败", e);
						}
					}
					// ZK连接成功,动态参数来自于ZK,结束该定时任务
					if (SystemConstants.ZKDATA.equals(CacheManager.getActiveMapValue(SystemConstants.DATAFROM))) {
						timer.cancel();
					}
				}
			}, retryTime, retryTime);
		}

		super.processProperties(beanFactoryToProcess, props);
	}
}
