package org.linys.service;

import org.linys.model.Employee;
import org.linys.vo.ServiceResult;

/**
 * @Description:员工Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface EmployeeService extends BaseService<Employee,String>{
	/**
	 * @Description: 保存员工
	 * @Create: 2012-12-29 下午3:39:42
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Employee model);
	/**
	 * @Description: 删除员工
	 * @Create: 2012-12-29 下午3:39:57
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Employee model);
	/**
	 * @Description: 分页查询员工
	 * @Create: 2012-12-29 下午3:40:10
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Employee model, Integer page, Integer rows);
	/**
	 * @Description: 统计员工
	 * @Create: 2012-12-29 下午3:40:22
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Employee model);
	/**
	 * @Description: combobox查询
	 * @Create: 2012-12-29 下午11:32:24
	 * @author lys
	 * @update logs
	 * @return
	 */
	String queryCombobox();

}
