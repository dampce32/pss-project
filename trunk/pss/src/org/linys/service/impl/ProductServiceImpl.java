package org.linys.service.impl;

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
		
		String[] properties = {"productId","productCode","productName","note",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","unit.dataDictionaryName:unitName",
				"color.dataDictionaryId:colorId","color.dataDictionaryName:colorName",
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName",
				"qtyStore","priceStore","amountStore"};
		String data = JSONUtil.toJson(list,properties);
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
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#save(org.linys.model.Product)
	 */
	public ServiceResult save(Product model) {
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
		
		if(StringUtils.isEmpty(model.getProductId())){//新增
			Product oldModel = productDAO.load("productCode", model.getProductCode());
			if(oldModel!=null){
				result.setMessage("该商品编号已存在");
				return result;
			}
			model.setQtyStore(0.0);
			model.setAmountStore(0.0);
			productDAO.save(model);
		}else{
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
			oldModel.setNote(model.getNote());
			
			productDAO.update(oldModel);
		}
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#select(org.linys.model.Product, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult select(Product model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Product> list = productDAO.query(model,page,rows);
		Long total = productDAO.getTotalCount(model);
		
		String[] properties = {"productId","productCode","productName","note",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","unit.dataDictionaryName:unitName",
				"color.dataDictionaryId:colorId","color.dataDictionaryName:colorName",
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName"};
		String data = JSONUtil.toJson(list,properties,total);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
}
