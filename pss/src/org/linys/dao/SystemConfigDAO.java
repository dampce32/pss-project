package org.linys.dao;

import org.linys.model.SystemConfig;
/**
 * @Description:系统配置DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author lys
 * @vesion 1.0
 */
public interface SystemConfigDAO extends BaseDAO<SystemConfig,String>{
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-2-4 下午2:42:15
	 * @author lys
	 * @update logs
	 * @return
	 */
	SystemConfig init();

}
