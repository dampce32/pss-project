package org.linys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ProductDAO;
import org.linys.model.Product;
import org.linys.service.ProductService;
import org.linys.util.JSONUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
	@Resource
	private ProductDAO productDAO;
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#add(org.linys.model.Product)
	 */
	public ServiceResult add(Product model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null){
			result.setMessage("请填写商品信息");
			return result;
		}
		if(StringUtils.isEmpty(model.getProductCode())){
			result.setMessage("请填写商品编号");
			return result;
		}
		if(model.getProductType()==null){
			result.setMessage("请选择商品类型");
			return result;
		}
		Product oldModel = productDAO.load("productCode", model.getProductCode());
		if(oldModel!=null){
			result.setMessage("该商品编号已存在");
			return result;
		}
		productDAO.save(model);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#update(org.linys.model.Product)
	 */
	public ServiceResult update(Product model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getProductId())){
			result.setMessage("请选择商品");
			return result;
		}
		if(StringUtils.isEmpty(model.getProductCode())){
			result.setMessage("请填写商品编号");
			return result;
		}
		if(model.getProductType()==null){
			result.setMessage("请选择商品类型");
			return result;
		}
		Product oldModel = productDAO.load(model.getProductId());
		if(oldModel==null){
			result.setMessage("该商品已不存在");
			return result;
		}else if(!oldModel.getProductCode().equals(model.getProductCode())){
			Product oldProduct = productDAO.load("productCode", model.getProductCode());
			if(oldProduct!=null){
				result.setMessage("该商品编号已存在");
				return result;
			}
		}
		oldModel.setProductCode(model.getProductCode());
		oldModel.setProductName(model.getProductName());
		oldModel.setProductType(model.getProductType());
		oldModel.setColor(model.getColor());
		oldModel.setSize(model.getSize());
		oldModel.setUnit(model.getUnit());
		oldModel.setQtyStore(model.getQtyStore());
		oldModel.setAmountStore(model.getAmountStore());
		oldModel.setNote(model.getNote());
		
		productDAO.save(model);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#delete(org.linys.model.Product)
	 */
	public ServiceResult delete(Product model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||StringUtils.isEmpty(model.getProductId())){
			result.setMessage("请选择要删除的商品");
			return result;
		}
		Product oldModel = productDAO.load(model.getProductId());
		if(oldModel==null){
			result.setMessage("该商品已不存在");
			return result;
		}else{
			productDAO.delete(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#query(org.linys.model.Product, java.lang.Integer, java.lang.Integer)
	 */
	public ServiceResult query(Product model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Product> list = productDAO.query(model,page,rows);
		
		List<String> propertyList = new ArrayList<String>();
		
		propertyList.add("productId");
		propertyList.add("productType");
		propertyList.add("productCode");
		propertyList.add("productName");
		
		String data = JSONUtil.toJson(list,propertyList);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#getTotalCount(org.linys.model.Product)
	 */
	public ServiceResult getTotalCount(Product model) {
		ServiceResult result = new ServiceResult(false);
		Long data = productDAO.getTotalCount(model);
		result.addData("total", data);
		result.setIsSuccess(true);
		return result;
	}
}
