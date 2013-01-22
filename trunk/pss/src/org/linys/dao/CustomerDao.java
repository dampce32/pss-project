package org.linys.dao;

import org.linys.bean.Pager;
import org.linys.model.Customer;

/**
 * 
 * @Description: 客户dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
public interface CustomerDao extends BaseDAO<Customer, String> {
	
	/**
	 * 
	 * @Description: 更新客户状态
	 * @Create: 2013-1-22 上午09:10:00
	 * @author longweier
	 * @update logs
	 * @param customerId
	 * @param status
	 * @throws Exception
	 */
	public void updateStataus(String customerId,Integer status);
	
	/**
	 * 
	 * @Description: 查询客户
	 * @Create: 2013-1-22 上午09:10:14
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param customer
	 * @return
	 * @throws Exception
	 */
	public Pager queryCustomer(Pager pager,Customer customer);
}
