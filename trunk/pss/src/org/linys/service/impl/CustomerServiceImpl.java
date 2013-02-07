package org.linys.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.bean.Pager;
import org.linys.dao.CustomerDao;
import org.linys.model.Customer;
import org.linys.service.CustomerService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 客户service实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, String> implements CustomerService {

	@Resource
	private CustomerDao customerDao;
	
	public String queryCustomer(Integer pageNumber, Integer pageSize,Customer customer) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = customerDao.queryCustomer(pager, customer);
		String[] property = {"customerId","customerCode","customerName","contacter","phone","fax","status","note","customerType.customerTypeID","customerType.customerTypeName","addr"};
		String jsonArray = JSONUtil.toJson(pager.getList(), property, pager.getTotalCount());
		return jsonArray;
	}

	public String queryCombogrid(Integer pageNumber, Integer pageSize,Customer customer) {
		Pager pager = new Pager(pageNumber, pageSize);
		pager = customerDao.queryCustomer(pager, customer);
		String[] property = {"customerId","customerCode","customerName"};
		String jsonArray = JSONUtil.toJson(pager.getList(), property, pager.getTotalCount());
		return jsonArray;
	}

	public ServiceResult saveCustomer(Customer customer) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(customer.getCustomerCode())){
			result.setMessage("客户编号不能为空");
			return result;
		}
		if(StringUtils.isEmpty(customer.getCustomerName())){
			result.setMessage("客户名称不能为空");
			return result;
		}
		Customer model = customerDao.load("customerCode", customer.getCustomerCode());
		//新增
		if(customer.getCustomerId()==null){
			if(model!=null){
				result.setMessage("该客户编号已存在");
				return result;
			}
			//新建的话默认有效
			customer.setStatus(1);
			customerDao.save(customer);
		//修改
		}else{
			if(model!=null && !model.getCustomerId().equals(customer.getCustomerId())){
				result.setMessage("该客户编号已存在");
				return result;
			}
			customerDao.evict(model);
			customerDao.update(customer);
		}
		result.setIsSuccess(true);
		return result;
	}

	public ServiceResult updateStatus(String customerId, Integer status) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(customerId) || status==null){
			result.setMessage("参数错误");
			return result;
		}
		customerDao.updateStataus(customerId, status);
		result.setIsSuccess(true);
		return result;
	}

}
