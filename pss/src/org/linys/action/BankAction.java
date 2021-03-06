package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Bank;
import org.linys.service.BankService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:银行Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class BankAction extends BaseAction implements ModelDriven<Bank> {
	private static final long serialVersionUID = -3463156581452256691L;
	private static final Logger logger = Logger.getLogger(BankAction.class);
	@Resource
	private BankService bankService;
	Bank model = new Bank();
	@Override
	public Bank getModel() {
		return model;
	}

	/**
	 * @Description: 保存银行
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = bankService.save(model);
		} catch (Exception e) {
			result.setMessage("保存银行失败");
			logger.error("保存银行失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除银行
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = bankService.delete(model);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("该记录已被使用，不能删除");
			}else{
				result.setMessage("删除银行失败");
				logger.error("删除银行失败", e);
			}
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询银行 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = bankService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询银行失败");
			result.setIsSuccess(false);
			logger.error("查询银行失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计银行
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = bankService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计银行失败");
			result.setIsSuccess(false);
			logger.error("统计银行失败", e);
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
		String jsonString = bankService.queryCombobox();
		ajaxJson(jsonString);
	}
}
