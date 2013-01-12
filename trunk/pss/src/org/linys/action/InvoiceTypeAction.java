package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.InvoiceType;
import org.linys.service.InvoiceTypeService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:发票类型Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class InvoiceTypeAction extends BaseAction implements
		ModelDriven<InvoiceType> {
	private static final long serialVersionUID = -8602019585327076685L;
	private static final Logger logger = Logger.getLogger(InvoiceTypeAction.class);
	@Resource
	private InvoiceTypeService invoiceTypeService;
	InvoiceType model = new InvoiceType();
	@Override
	public InvoiceType getModel() {
		return model;
	}

	/**
	 * @Description: 保存发票类型
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = invoiceTypeService.save(model);
		} catch (Exception e) {
			result.setMessage("保存发票类型失败");
			logger.error("保存发票类型失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除发票类型
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = invoiceTypeService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除发票类型失败");
			result.setIsSuccess(false);
			logger.error("删除发票类型失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询发票类型 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = invoiceTypeService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询发票类型失败");
			result.setIsSuccess(false);
			logger.error("查询发票类型失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计发票类型
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = invoiceTypeService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计发票类型失败");
			result.setIsSuccess(false);
			logger.error("统计发票类型失败", e);
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
		String jsonString = invoiceTypeService.queryCombobox();
		ajaxJson(jsonString);
	}
}
