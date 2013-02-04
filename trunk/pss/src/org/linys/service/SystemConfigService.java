package org.linys.service;

import org.linys.model.SystemConfig;
import org.linys.vo.ServiceResult;
/**
 * @Description:系统配置Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author lys
 * @vesion 1.0
 */
public interface SystemConfigService extends BaseService<SystemConfig,String>{
	/**
	 * @Description: 保存系统配置信息
	 * @Create: 2013-2-4 下午2:16:59
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(SystemConfig model);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-2-4 下午2:17:19
	 * @author lys
	 * @update logs
	 * @return
	 */
	ServiceResult init();

}
