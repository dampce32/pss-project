package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Customer;
import org.linys.service.CustomerService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * @Description: 客户action
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class CustomerAction extends BaseAction implements ModelDriven<Customer> {

	private static final long serialVersionUID = 4622328080132498105L;

	private static Logger logger = Logger.getLogger(CustomerAction.class);
	
	@Resource
	private CustomerService customerService;
	
	private Customer customer = new Customer();
	
	public Customer getModel() {
		return customer;
	}

	/**
	 * 
	 * @Description: 新增和修改客户
	 * @Create: 2013-1-22 上午09:34:42
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = customerService.saveCustomer(customer);
		} catch (RuntimeException e) {
			logger.error("保存客户出错", e);
			result.setMessage(e.getMessage());
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 修改状态
	 * @Create: 2013-1-22 上午09:34:56
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void updateStatus(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = customerService.updateStatus(customer.getCustomerId(), customer.getStatus());
		} catch (RuntimeException e) {
			logger.error("修改客户状态出错", e);
			result.setMessage(e.getMessage());
		}
		ajaxJson(result.toJSON());
	}
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-22 上午09:35:02
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void query(){
		String jsonString = customerService.queryCustomer(page, rows, customer);
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询combogrid
	 * @Create: 2013-1-22 上午09:35:13
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void queryCombogrid(){
		customer.setCustomerName(q);
		customer.setStatus(1);
		String jsonString = customerService.queryCombogrid(page, rows, customer);
		ajaxJson(jsonString);
	}
}
