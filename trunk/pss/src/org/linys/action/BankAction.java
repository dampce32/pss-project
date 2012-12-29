package org.linys.action;

import javax.annotation.Resource;

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
			e.printStackTrace();
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
			result.setMessage("删除银行失败");
			result.setIsSuccess(false);
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
