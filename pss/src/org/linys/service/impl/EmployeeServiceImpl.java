package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.EmployeeDAO;
import org.linys.model.Employee;
import org.linys.service.EmployeeService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee, String>
		implements EmployeeService {
	@Resource
	private EmployeeDAO employeeDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.EmployeeService#save(org.linys.model.Employee)
	 */
	@Override
	public ServiceResult save(Employee model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写员工信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getEmployeeName())){
			result.setMessage("请填写员工姓名");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getEmployeeId())){//新增
			employeeDAO.save(model);
		}else{
			Employee oldModel = employeeDAO.load(model.getEmployeeId());
			if(oldModel==null){
				result.setMessage("该员工已不存在");
				return result;
			}
			oldModel.setEmployeeName(model.getEmployeeName());
			employeeDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.EmployeeService#delete(org.linys.model.Employee)
	 */
	@Override
	public ServiceResult delete(Employee model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getEmployeeId())){
			result.setMessage("请选择要删除的员工");
			return result;
		}
		Employee oldModel = employeeDAO.load(model.getEmployeeId());
		if(oldModel==null){
			result.setMessage("该员工已不存在");
			return result;
		}else{
			employeeDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.EmployeeService#query(org.linys.model.Employee, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Employee model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Employee> list = employeeDAO.query(model,page,rows);
		
		String[] properties = {"employeeId","employeeName"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.EmployeeService#getTotalCount(org.linys.model.Employee)
	 */
	@Override
	public ServiceResult getTotalCount(Employee model) {
		ServiceResult result = new ServiceResult(false);
		Long data = employeeDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.EmployeeService#queryCombobox()
	 */
	@Override
	public String queryCombobox() {
		List<Employee> list = employeeDAO.queryAll();
		String[] properties = {"employeeId","employeeName"};
		String jsonString = JSONUtil.toJsonWithoutRows(list,properties);
		return jsonString;
	}

}
