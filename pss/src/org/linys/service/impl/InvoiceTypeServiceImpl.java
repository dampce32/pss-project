package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.InvoiceTypeDAO;
import org.linys.model.InvoiceType;
import org.linys.service.InvoiceTypeService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public class InvoiceTypeServiceImpl extends
		BaseServiceImpl<InvoiceType, String> implements InvoiceTypeService {
	@Resource
	private InvoiceTypeDAO invoiceTypeDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.InvoiceTypeService#save(org.linys.model.InvoiceType)
	 */
	@Override
	public ServiceResult save(InvoiceType model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写发票类型信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getInvoiceTypeName())){
			result.setMessage("请填写发票类型姓名");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getInvoiceTypeId())){//新增
			invoiceTypeDAO.save(model);
		}else{
			InvoiceType oldModel = invoiceTypeDAO.load(model.getInvoiceTypeId());
			if(oldModel==null){
				result.setMessage("该发票类型已不存在");
				return result;
			}
			oldModel.setInvoiceTypeName(model.getInvoiceTypeName());
			invoiceTypeDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.InvoiceTypeService#delete(org.linys.model.InvoiceType)
	 */
	@Override
	public ServiceResult delete(InvoiceType model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getInvoiceTypeId())){
			result.setMessage("请选择要删除的发票类型");
			return result;
		}
		InvoiceType oldModel = invoiceTypeDAO.load(model.getInvoiceTypeId());
		if(oldModel==null){
			result.setMessage("该发票类型已不存在");
			return result;
		}else{
			invoiceTypeDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.InvoiceTypeService#query(org.linys.model.InvoiceType, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(InvoiceType model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<InvoiceType> list = invoiceTypeDAO.invoiceTypeDAO(model,page,rows);
		
		String[] properties = {"invoiceTypeId","invoiceTypeName"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.InvoiceTypeService#getTotalCount(org.linys.model.InvoiceType)
	 */
	@Override
	public ServiceResult getTotalCount(InvoiceType model) {
		ServiceResult result = new ServiceResult(false);
		Long data = invoiceTypeDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
}
