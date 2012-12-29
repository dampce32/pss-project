package org.linys.dao;

import java.util.List;

import org.linys.model.Employee;
/**
 * @Description:员工DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface EmployeeDAO extends BaseDAO<Employee, String> {
	/**
	 * @Description: 分页查询员工
	 * @Create: 2012-12-29 下午3:44:11
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Employee> query(Employee model, Integer page, Integer rows);
	/**
	 * @Description: 统计员工
	 * @Create: 2012-12-29 下午3:44:31
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Employee model);

}
