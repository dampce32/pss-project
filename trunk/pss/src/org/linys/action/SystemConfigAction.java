package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.SystemConfig;
import org.linys.service.SystemConfigService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:系统配置Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class SystemConfigAction extends BaseAction implements
		ModelDriven<SystemConfig> {
	
	private static final long serialVersionUID = -4011682019548419772L;
	private static final Logger logger = Logger.getLogger(SystemConfig.class);
	@Resource
	private SystemConfigService systemConfigService;
	SystemConfig model = new SystemConfig();
	@Override
	public SystemConfig getModel() {
		return model;
	}
	/**
	 * @Description: 保存系统配置信息
	 * @Create: 2013-2-4 下午2:15:38
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = systemConfigService.save(model);
		} catch (Exception e) {
			result.setMessage("保存系统配置信息失败");
			logger.error("保存系统配置信息失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-25 下午10:44:27
	 * @author lys
	 * @update logs
	 */
	public void init(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = systemConfigService.init();
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
