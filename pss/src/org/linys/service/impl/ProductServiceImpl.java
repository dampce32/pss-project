package org.linys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.DefaultPackagingDAO;
import org.linys.dao.ProductDAO;
import org.linys.model.DefaultPackaging;
import org.linys.model.Product;
import org.linys.service.ProductService;
import org.linys.util.JSONUtil;
import org.linys.util.StringUtil;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product, String> implements ProductService {
	@Resource
	private ProductDAO productDAO;
	@Resource
	private DefaultPackagingDAO defaultPackagingDAO;
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
				"qtyStore","priceStore","amountStore","buyingPrice","salePrice"};
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
	public ServiceResult save(Product model, String defaultPackagingIds, String deleleIds, String productIds, String qtys) {
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
		
		String[] defaultPackagingIdArray = StringUtil.split(defaultPackagingIds);
		String[] deleleIdArray = StringUtil.split(deleleIds);
		String[] productIdArray = StringUtil.split(productIds);
		String[] qtyArray = StringUtil.split(qtys);
		for (int i = 0; i < productIdArray.length&&StringUtils.isNotEmpty(productIdArray[i]); i++) {
			String qty = qtyArray[i];
			if("0".equals(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			
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
			for (int i = 0; i < productIdArray.length; i++) {
				String productId = productIdArray[i];
				String qty = qtyArray[i];
				
				DefaultPackaging defaultPackaging = new DefaultPackaging();
				
				Product product = new Product();
				product.setProductId(productId);
				defaultPackaging.setProduct(product);
				
				defaultPackaging.setParentProduct(model);
				
				if(StringUtils.isNotEmpty(qty)){//数量没输入保护
					defaultPackaging.setQty(new Double(qty));
				}else{
					defaultPackaging.setQty(0.0);
				}
				
				defaultPackagingDAO.save(defaultPackaging);
			}
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
			oldModel.setBuyingPrice(model.getBuyingPrice());
			oldModel.setSalePrice(model.getSalePrice());
			
			//删除已删的默认商品组装
			if(!"".equals(deleleIdArray)){
				for (String deleleId : deleleIdArray) {
					if(StringUtils.isNotEmpty(deleleId)){
						DefaultPackaging  oldDefaultPackaging = defaultPackagingDAO.load(deleleId);
						if(oldModel!=null){
							defaultPackagingDAO.delete(oldDefaultPackaging);
						}
					}
				}
			}
			//根据采购单明细Id更新或新增
			for (int i = 0 ;i<defaultPackagingIdArray.length;i++) {
				String defaultPackagingId = defaultPackagingIdArray[i];
				String productId = productIdArray[i];
				String qty = qtyArray[i];
				if(StringUtils.isEmpty(defaultPackagingId)){//新增
					
					DefaultPackaging defaultPackaging = new DefaultPackaging();
					
					Product product = new Product();
					product.setProductId(productId);
					defaultPackaging.setProduct(product);
					
					if(StringUtils.isNotEmpty(qty)){//数量没输入保护
						defaultPackaging.setQty(new Double(qty));
					}else{
						defaultPackaging.setQty(0.0);
					}
					defaultPackaging.setParentProduct(model);
					defaultPackagingDAO.save(defaultPackaging);
				}else{
					DefaultPackaging  oldDefaultPackaging = defaultPackagingDAO.load(defaultPackagingId);
					
					if(StringUtils.isNotEmpty(qty)){//数量没输入保护
						oldDefaultPackaging.setQty(new Double(qty));
					}else{
						oldDefaultPackaging.setQty(0.0);
					}
				}
			}
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
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName",
				"qtyStore","buyingPrice","salePrice","priceStore"};
		String data = JSONUtil.toJson(list,properties,total);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#selectReject(org.linys.model.Product, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult selectReject(Product model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Product> list = productDAO.queryReject(model,page,rows);
		Long total = productDAO.getTotalCountReject(model);
		
		String[] properties = {"productId","productCode","productName","note",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","unit.dataDictionaryName:unitName",
				"color.dataDictionaryId:colorId","color.dataDictionaryName:colorName",
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName",
				"qtyStore"};
		String data = JSONUtil.toJson(list,properties,total);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#queryStore(org.linys.model.Product, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult queryStore(Product model, Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		List<Product> list = productDAO.query(model,page,rows);
		
		String[] properties = {"productId","productCode","productName","note",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","unit.dataDictionaryName:unitName",
				"color.dataDictionaryId:colorId","color.dataDictionaryName:colorName",
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName",
				"qtyStore","priceStore","amountStore"};
		Long total = productDAO.getTotalCount(model);
		result.addData("total", total);
		String data = JSONUtil.toJson(list,properties,total);
		result.addData("datagridData", data);
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#selectDefaultPacking(org.linys.model.Product, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public ServiceResult selectDefaultPacking(Product model, String ids,
			Integer page, Integer rows) {
		ServiceResult result = new ServiceResult(false);
		
		String[] idArray = {""};
		if(StringUtils.isNotEmpty(ids)){
			idArray = StringUtil.split(ids);
		}
		//默认商品组装，选择商品
		List<Product> list = productDAO.querySelectDefaultPacking(model,idArray,page,rows);
		Long total = productDAO.getTotalCountSelectDefaultPacking(model,idArray);
		
		String[] properties = {"productId","productCode","productName",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","unit.dataDictionaryName:unitName",
				"color.dataDictionaryId:colorId","color.dataDictionaryName:colorName",
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName","buyingPrice"};
		String data = JSONUtil.toJson(list,properties,total);
		result.addData("datagridData", data);
		
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#init(java.lang.String)
	 */
	@Override
	public ServiceResult init(String productId) {
		ServiceResult result = new ServiceResult(false);
		Product product = productDAO.load(productId);
		String[] properties = {"productId","productCode","productName","note",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","color.dataDictionaryId:colorId","size.dataDictionaryId:sizeId","buyingPrice","salePrice"};
		String productData = JSONUtil.toJson(product,properties);
		result.addData("productData",productData);
		
		List<DefaultPackaging> defaultPackagingList = defaultPackagingDAO.queryByProductId(product);
		String[] propertiesDetail = {"defaultPackagingId","product.productId","product.productCode","product.productName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName",
				"qty","product.buyingPrice:price","amount"};
		String defaultPackagingData = JSONUtil.toJson(defaultPackagingList,propertiesDetail);
		result.addData("defaultPackagingData", defaultPackagingData);
		result.setIsSuccess(true);
		return result;
	}
	
	public ServiceResult selectDefaultPacking(Product product) {
		ServiceResult result = new ServiceResult(false);
		if(product==null || StringUtils.isEmpty(product.getProductId())){
			result.setMessage("参数有误");
			return result;
		}
		List<DefaultPackaging> defaultPackagingList = defaultPackagingDAO.queryByProductId(product);
		String[] propertiesDetail = {"product.productId","product.productCode","product.productName","product.color.dataDictionaryName:colorName",
				"product.size.dataDictionaryName:sizeName","product.unit.dataDictionaryName:unitName",
				"qty","product.salePrice:price","saleAmount:amount"};
		String defaultPackagingData = JSONUtil.toJson(defaultPackagingList,propertiesDetail);
		result.addData("defaultPackagingData", defaultPackagingData);
		result.setIsSuccess(true);
		return result;
	}
}
