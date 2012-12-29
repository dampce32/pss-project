package org.linys.action;

import javax.annotation.Resource;

import org.linys.model.Employee;
import org.linys.service.EmployeeService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:员工Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class EmployeeAction extends BaseAction implements ModelDriven<Employee> {
	
	private static final long serialVersionUID = -4977568026797413934L;
	@Resource
	private EmployeeService employeeService;
	Employee model = new Employee();
	@Override
	public Employee getModel() {
		return model;
	}
	/**
	 * @Description: 保存员工
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = employeeService.save(model);
		} catch (Exception e) {
			result.setMessage("保存员工失败");
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除员工
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = employeeService.delete(model);
		} catch (Exception e) {
			result.setMessage("删除员工失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分页查询员工 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = employeeService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询员工失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计员工
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = employeeService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计员工失败");
			result.setIsSuccess(false);
			e.printStackTrace();
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	
	

}
