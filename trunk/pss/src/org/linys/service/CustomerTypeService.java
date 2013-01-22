package org.linys.service;

import org.linys.model.CustomerType;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 客户类型service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author longweier
 * @vesion 1.0
 */
public interface CustomerTypeService extends BaseService<CustomerType, String> {
	
	/**
	 * 
	 * @Description: 新增或修改客户类型
	 * @Create: 2013-1-20 下午03:53:26
	 * @author longweier
	 * @update logs
	 * @param customerType
	 * @return
	 * @throws Exception
	 */
	public ServiceResult saveCustomerType(CustomerType customerType);
	
	/**
	 * 
	 * @Description: 查询客户列表 
	 * @Create: 2013-1-20 下午03:53:43
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public String queryCustomerType();
	
	/**
	 * 
	 * @Description: 查询客户combobox
	 * @Create: 2013-1-20 下午03:53:54
	 * @author longweier
	 * @update logs
	 * @return
	 * @throws Exception
	 */
	public String queryCombobox();
}
