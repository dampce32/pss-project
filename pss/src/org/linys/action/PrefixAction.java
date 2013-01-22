package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Prefix;
import org.linys.service.PrefixService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @Description:编号前缀Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-21
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class PrefixAction extends BaseAction implements ModelDriven<Prefix> {

	private static final long serialVersionUID = 6775913327638887581L;
	private static final Logger logger = Logger.getLogger(PrefixAction.class);
	@Resource
	private PrefixService prefixService;
	Prefix model = new Prefix();
	@Override
	public Prefix getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存编号前缀
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prefixService.save(model);
		} catch (Exception e) {
			result.setMessage("保存编号前缀失败");
			logger.error("保存编号前缀失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除编号前缀
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prefixService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除编号前缀失败");
			logger.error("删除编号前缀失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询编号前缀 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = prefixService.query(model);
		} catch (Exception e) {
			result.setMessage("查询编号前缀失败");
			logger.error("查询编号前缀失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
