package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.SupplierDAO;
import org.linys.model.Supplier;
import org.linys.service.SupplierService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @Description:供应商Service实现类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Service
public class SupplierServiceImpl extends BaseServiceImpl<Supplier, String>
		implements SupplierService {
	@Resource
	private SupplierDAO supplierDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#save(org.linys.model.Supplier)
	 */
	@Override
	public ServiceResult save(Supplier model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写供应商信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getSupplierCode())){
			result.setMessage("请填写供应商编号");
			return result;
		}
		if(StringUtils.isEmpty(model.getSupplierName())){
			result.setMessage("请填写供应商名称");
			return result;
		}
		
		if(StringUtils.isEmpty(model.getSupplierId())){//新增
			Supplier oldModel = supplierDAO.load("supplierCode", model.getSupplierCode());
			if(oldModel!=null){
				result.setMessage("该供应商编号已存在");
				return result;
			}
			supplierDAO.save(model);
		}else{
			Supplier oldModel = supplierDAO.load(model.getSupplierId());
			if(oldModel==null){
				result.setMessage("该供应商已不存在");
				return result;
			}else if(!oldModel.getSupplierCode().equals(model.getSupplierCode())){
				Supplier oldSupplier = supplierDAO.load("supplierCode", model.getSupplierCode());
				if(oldSupplier!=null){
					result.setMessage("该供应商编号已存在");
					return result;
				}
			}
			oldModel.setSupplierCode(model.getSupplierCode());
			oldModel.setSupplierName(model.getSupplierName());
			
			supplierDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#delete(org.linys.model.Supplier)
	 */
	@Override
	public ServiceResult delete(Supplier model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getSupplierId())){
			result.setMessage("请选择要删除的供应商");
			return result;
		}
		Supplier oldModel = supplierDAO.load(model.getSupplierId());
		if(oldModel==null){
			result.setMessage("该供应商已不存在");
			return result;
		}else{
			supplierDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#query(org.linys.model.Supplier, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult query(Supplier model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Supplier> list = supplierDAO.query(model,page,rows);
		
		String[] properties = {"supplierId","supplierCode","supplierName"};
		String data = JSONUtil.toJson(list,properties);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.SupplierService#getTotalCount(org.linys.model.Supplier)
	 */
	@Override
	public ServiceResult getTotalCount(Supplier model) {
		ServiceResult result = new ServiceResult(false);
		Long data = supplierDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}

}
