package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.CustomerTypeDao;
import org.linys.model.CustomerType;
import org.linys.service.CustomerTypeService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

/**
 * 
 * @Description: 客户类型dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author longweier
 * @vesion 1.0
 */
@Service
public class CustomerTypeServiceImpl extends BaseServiceImpl<CustomerType, String> implements CustomerTypeService {

	@Resource
	private CustomerTypeDao customerTypeDao;
	
	public ServiceResult saveCustomerType(CustomerType customerType) {
		ServiceResult result = new ServiceResult(false);
		if(StringUtils.isEmpty(customerType.getCustomerTypeCode())){
			result.setMessage("客户类型编号不能为空");
			return result;
		}
		if(StringUtils.isEmpty(customerType.getCustomerTypeName())){
			result.setMessage("客户类型名称不能为空");
			return result;
		}
		//新增
		if(customerType.getCustomerTypeID()==null){
			customerTypeDao.save(customerType);
		//修改
		}else{
			customerTypeDao.update(customerType);
		}
		result.setIsSuccess(true);
		return result;
	}

	public String queryCombobox() {
		List<CustomerType> list = customerTypeDao.queryAll();
		String[] propertyList = {"customerTypeID","customerTypeName"}; 
		String jsonArray = JSONUtil.toJsonWithoutRows(list, propertyList);
		return jsonArray;
	}

	public String queryCustomerType() {
		List<CustomerType> list = customerTypeDao.queryAll();
		String[] propertyList = {"customerTypeID","customerTypeCode","customerTypeName","note"}; 
		String jsonArray = JSONUtil.toJson(list, propertyList);
		return jsonArray;
	}

}
