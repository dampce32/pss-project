package org.linys.service;

import org.linys.model.Customer;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 客户service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
public interface CustomerService extends BaseService<Customer, String> {
	
	/**
	 * 
	 * @Description: 新建或保存客户
	 * @Create: 2013-1-22 上午09:07:59
	 * @author longweier
	 * @update logs
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public ServiceResult saveCustomer(Customer customer);
	
	/**
	 * 
	 * @Description: 修改状态
	 * @Create: 2013-1-22 上午09:08:17
	 * @author longweier
	 * @update logs
	 * @param customerId
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatus(String customerId,Integer status);
	
	/**
	 * 
	 * @Description: 查询客户列表
	 * @Create: 2013-1-22 上午09:08:26
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public String queryCustomer(Integer pageNumber,Integer pageSize,Customer customer);
	
	/**
	 * 
	 * @Description: 查询客户Combogrid
	 * @Create: 2013-1-22 上午09:08:40
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public String queryCombogrid(Integer pageNumber,Integer pageSize,Customer customer);
}
