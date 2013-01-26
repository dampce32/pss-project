package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Expense;
import org.linys.service.ExpenseService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:费用支出Action
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ExpenseAction extends BaseAction implements ModelDriven<Expense> {
	private static final long serialVersionUID = 1060724295685961797L;
	private static final Logger logger = Logger.getLogger(ExpenseAction.class);
	@Resource
	private ExpenseService expenseService;
	Expense model = new Expense();
	@Override
	public Expense getModel() {
		return model;
	}

	/**
	 * @Description: 保存费用支出
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.save(model);
		} catch (Exception e) {
			result.setMessage("保存费用支出失败");
			logger.error("保存费用支出失败", e);
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
			result = expenseService.init(model);
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除费用支出
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除费用支出失败");
			result.setIsSuccess(false);
			logger.error("删除费用支出失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量删除费用支出
	 * @Create: 2013-1-8 下午10:28:15
	 * @author lys
	 * @update logs
	 */
	public void mulDelete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.mulDelete(ids);
		} catch (Exception e) {
			result.setMessage("批量删除费用支出失败");
			logger.error("批量删除费用支出失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询费用支出 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("分页查询费用支出失败");
			logger.error("分页查询费用支出失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计费用支出
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计费用支出失败");
			logger.error("统计费用支出失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 修改费用支出状态
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.updateStatus(model);
		} catch (Exception e) {
			result.setMessage("修改费用支出状态失败");
			logger.error("修改费用支出状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 批量修改费用支出状态
	 * @Create: 2013-1-9 下午11:50:34
	 * @author lys
	 * @update logs
	 */
	public void mulUpdateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = expenseService.mulUpdateStatus(ids,model);
		} catch (Exception e) {
			result.setMessage("批量修改费用支出状态失败");
			logger.error("批量修改费用支出状态失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
