package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.ReportConfig;
import org.linys.service.ReportConfigService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ReportConfigAction extends BaseAction implements
		ModelDriven<ReportConfig> {
	
	private static final long serialVersionUID = 3734907022462806575L;
	private static final Logger logger = Logger.getLogger(ReportConfigAction.class);
	@Resource
	private ReportConfigService reportConfigService;
	ReportConfig model = new ReportConfig();
	@Override
	public ReportConfig getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存报表配置
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = reportConfigService.save(model);
		} catch (Exception e) {
			result.setMessage("保存报表配置失败");
			logger.error("保存报表配置失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除报表配置
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = reportConfigService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除报表配置失败");
			logger.error("删除报表配置失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询报表配置 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = reportConfigService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询报表配置失败");
			logger.error("查询报表配置失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计报表配置
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = reportConfigService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计报表配置失败");
			result.setIsSuccess(false);
			logger.error("统计报表配置失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
}
