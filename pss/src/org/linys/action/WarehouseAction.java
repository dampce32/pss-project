package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Warehouse;
import org.linys.service.WarehouseService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:仓库Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class WarehouseAction extends BaseAction implements
		ModelDriven<Warehouse> {
	
	private static final long serialVersionUID = 3845037671376044135L;
	private static final Logger logger = Logger.getLogger(WarehouseAction.class);
	@Resource
	private WarehouseService warehouseService;
	Warehouse model = new Warehouse();
	@Override
	public Warehouse getModel() {
		return model;
	}
	
	/**
	 * @Description: 保存仓库
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = warehouseService.save(model);
		} catch (Exception e) {
			result.setMessage("保存仓库失败");
			logger.error("保存仓库失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除仓库
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = warehouseService.delete(model);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("记录已被使用，不能删除");
			}else{
				result.setMessage("删除仓库失败");
				logger.error("删除仓库失败", e);
			}
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询仓库 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = warehouseService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询仓库失败");
			result.setIsSuccess(false);
			logger.error("查询仓库失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计仓库
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = warehouseService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计仓库失败");
			result.setIsSuccess(false);
			logger.error("统计仓库失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: combobox查询
	 * @Create: 2012-12-29 下午11:18:19
	 * @author lys
	 * @update logs
	 */
	public void queryCombobox() {
		String jsonString = warehouseService.queryCombobox();
		ajaxJson(jsonString);
	}
	
}
