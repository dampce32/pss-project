package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.CustomerType;
import org.linys.service.CustomerTypeService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

@Controller
@Scope("prototype")
public class CustomerTypeAction extends BaseAction implements ModelDriven<CustomerType> {

	private static final long serialVersionUID = 3798900351325663388L;

	private static Logger logger = Logger.getLogger(CustomerTypeAction.class);
	
	@Resource
	private CustomerTypeService customerTypeService;
	
	private CustomerType customerType = new CustomerType();
	
	@Override
	public CustomerType getModel() {
		return customerType;
	}

	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = customerTypeService.saveCustomerType(customerType);
		} catch (Exception e) {
			logger.error("保存客户类型失败", e);
			result.setMessage("后台出错");
		}
		ajaxJson(result.toJSON());
	}
	
	public void query(){
		String jsonString = customerTypeService.queryCustomerType();
		ajaxJson(jsonString);
	}
	
	public void queryCombobox(){
		String jsonString = customerTypeService.queryCombobox();
		ajaxJson(jsonString);
	}
}
