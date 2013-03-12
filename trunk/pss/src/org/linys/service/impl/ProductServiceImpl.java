package org.linys.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.linys.dao.DataDictionaryDAO;
import org.linys.dao.DefaultPackagingDAO;
import org.linys.dao.ProductDAO;
import org.linys.dao.ProductTypeDAO;
import org.linys.model.DataDictionary;
import org.linys.model.DefaultPackaging;
import org.linys.model.Product;
import org.linys.model.ProductType;
import org.linys.service.ProductService;
import org.linys.util.FileUtil;
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
	@Resource
	private DataDictionaryDAO dataDictionaryDAO;
	@Resource
	private ProductTypeDAO productTypeDAO;
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
				"qtyStore","priceStore","amountStore","buyingPrice",
				"wholesalePrice","vipPrice","memberPrice","salePrice","status"};
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
		if(model.getProductType()==null||StringUtils.isEmpty(model.getProductType().getProductTypeId())){
			result.setMessage("请选择商品类型");
			return result;
		}
		//输入原来下拉选择项中没有情况验证
		//商品类型
		if(model.getProductType()!=null&&StringUtils.isNotEmpty(model.getProductType().getProductTypeId())){
			ProductType productType = productTypeDAO.get(model.getProductType().getProductTypeId());
			if(productType==null){
				result.setMessage("请选择商品类型");
				return result;
			}
		}
		//单位
		if(model.getUnit()!=null&&StringUtils.isNotEmpty(model.getUnit().getDataDictionaryId())){
			DataDictionary unit = dataDictionaryDAO.get(model.getUnit().getDataDictionaryId());
			if(unit==null){
				unit = new DataDictionary();
				unit.setDataDictionaryKind("unit");
				unit.setDataDictionaryName(model.getUnit().getDataDictionaryId());
				dataDictionaryDAO.save(unit);
				result.addData("isUnitChange", true);
			}
			model.setUnit(unit);
		}
		//规格
		if(model.getSize()!=null&&StringUtils.isNotEmpty(model.getSize().getDataDictionaryId())){
			DataDictionary size = dataDictionaryDAO.get(model.getSize().getDataDictionaryId());
			if(size==null){
				size = new DataDictionary();
				size.setDataDictionaryKind("size");
				size.setDataDictionaryName(model.getSize().getDataDictionaryId());
				dataDictionaryDAO.save(size);
				result.addData("isSizeChange", true);
			}
			model.setSize(size);
		}
		//颜色
		if(model.getColor()!=null&&StringUtils.isNotEmpty(model.getColor().getDataDictionaryId())){
			DataDictionary color = dataDictionaryDAO.get(model.getColor().getDataDictionaryId());
			if(color==null){
				color = new DataDictionary();
				color.setDataDictionaryKind("color");
				color.setDataDictionaryName(model.getColor().getDataDictionaryId());
				dataDictionaryDAO.save(color);
				result.addData("isColorChange", true);
			}
			model.setColor(color);
		}
		
		String[] defaultPackagingIdArray = StringUtil.split(defaultPackagingIds);
		String[] deleleIdArray = StringUtil.split(deleleIds);
		String[] productIdArray = StringUtil.split(productIds);
		String[] qtyArray = StringUtil.split(qtys);
		for (int i = 0; i < productIdArray.length&&StringUtils.isNotEmpty(productIdArray[i]); i++) {
			String qty = qtyArray[i];
			if("0".equals(qty) || StringUtils.isEmpty(qty)){
				result.setMessage("第"+(i+1)+"行商品数量不能为0");
				return result;
			}
			
		}
		if(StringUtils.isEmpty(model.getProductId())){//新增
			//检查编号是否已存在
			Product oldModel = productDAO.load("productCode",model.getProductCode());
			if(oldModel!=null){
				result.setMessage("商品编号已存在");
				return result;
			}
			model.setPriceStore(0.0);
			model.setQtyStore(0.0);
			model.setAmountStore(0.0);
			productDAO.save(model);
			for (int i = 0; i < productIdArray.length&&StringUtils.isNotEmpty(productIdArray[i]); i++) {
				String productId = productIdArray[i];
				String qty = qtyArray[i];
				
				DefaultPackaging defaultPackaging = new DefaultPackaging();
				
				Product product = new Product();
				product.setProductId(productId);
				defaultPackaging.setProduct(product);
				
				defaultPackaging.setParentProduct(model);
				
				if(StringUtils.isNotEmpty(qty)){//数量没输入保护
					defaultPackaging.setQty(new Integer(qty));
				}else{
					defaultPackaging.setQty(0);
				}
				defaultPackagingDAO.save(defaultPackaging);
			}
		}else{
			Product oldModel = productDAO.load(model.getProductId());
			if(oldModel==null){
				result.setMessage("该商品已不存在");
				return result;
			}
			if(!oldModel.getProductCode().equals(model.getProductCode())){
				Product oldProduct = productDAO.load("productCode", model.getProductCode());
				if(oldProduct!=null){
					result.setMessage("该商品编号已存在，请重新输入商品编号");
					return result;
				}
			}
			//输入原来下拉选择项中没有情况验证
			//单位
			if(model.getUnit()!=null&&StringUtils.isNotEmpty(model.getUnit().getDataDictionaryId())){
				DataDictionary unit = dataDictionaryDAO.get(model.getUnit().getDataDictionaryId());
				if(unit==null){
					unit = new DataDictionary();
					unit.setDataDictionaryKind("unit");
					unit.setDataDictionaryName(model.getUnit().getDataDictionaryId());
					dataDictionaryDAO.save(unit);
					result.addData("isUnitChange", true);
				}
				model.setUnit(unit);
			}
			//规格
			if(model.getSize()!=null&&StringUtils.isNotEmpty(model.getSize().getDataDictionaryId())){
				DataDictionary size = dataDictionaryDAO.get(model.getSize().getDataDictionaryId());
				if(size==null){
					size = new DataDictionary();
					size.setDataDictionaryKind("size");
					size.setDataDictionaryName(model.getSize().getDataDictionaryId());
					dataDictionaryDAO.save(size);
					result.addData("isSizeChange", true);
				}
				model.setSize(size);
			}
			//颜色
			if(model.getColor()!=null&&StringUtils.isNotEmpty(model.getColor().getDataDictionaryId())){
				DataDictionary color = dataDictionaryDAO.get(model.getColor().getDataDictionaryId());
				if(color==null){
					color = new DataDictionary();
					color.setDataDictionaryKind("color");
					color.setDataDictionaryName(model.getColor().getDataDictionaryId());
					dataDictionaryDAO.save(color);
					result.addData("isColorChange", true);
				}
				model.setColor(color);
			}
			
			oldModel.setProductCode(model.getProductCode());
			oldModel.setProductType(model.getProductType());
			oldModel.setProductName(model.getProductName());
			oldModel.setColor(model.getColor());
			oldModel.setSize(model.getSize());
			oldModel.setUnit(model.getUnit());
			oldModel.setNote(model.getNote());
			//修改不填0保护
			if(model.getSalePrice()==null){
				model.setSalePrice(0.0);
			}
			if(model.getWholesalePrice()==null){
				model.setWholesalePrice(0.0);
			}
			if(model.getVipPrice()==null){
				model.setVipPrice(0.0);
			}
			if(model.getMemberPrice()==null){
				model.setMemberPrice(0.0);
			}
			
			oldModel.setSalePrice(model.getSalePrice());
			oldModel.setWholesalePrice(model.getWholesalePrice());
			oldModel.setVipPrice(model.getVipPrice());
			oldModel.setMemberPrice(model.getMemberPrice());
			oldModel.setBuyingPrice(model.getBuyingPrice());
			
			
			oldModel.setStatus(model.getStatus());
			
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
			for (int i = 0 ;i<defaultPackagingIdArray.length&&StringUtils.isNotEmpty(productIdArray[i]);i++) {
				String defaultPackagingId = defaultPackagingIdArray[i];
				String productId = productIdArray[i];
				String qty = qtyArray[i];
				if(StringUtils.isEmpty(defaultPackagingId)){//新增
					
					DefaultPackaging defaultPackaging = new DefaultPackaging();
					
					Product product = new Product();
					product.setProductId(productId);
					defaultPackaging.setProduct(product);
					
					if(StringUtils.isNotEmpty(qty)){//数量没输入保护
						defaultPackaging.setQty(new Integer(qty));
					}else{
						defaultPackaging.setQty(0);
					}
					defaultPackaging.setParentProduct(model);
					defaultPackagingDAO.save(defaultPackaging);
				}else{
					DefaultPackaging  oldDefaultPackaging = defaultPackagingDAO.load(defaultPackagingId);
					
					if(StringUtils.isNotEmpty(qty)){//数量没输入保护
						oldDefaultPackaging.setQty(new Integer(qty));
					}else{
						oldDefaultPackaging.setQty(0);
					}
				}
			}
		}
		result.addData("productId", model.getProductId());
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
		
		List<Product> list = productDAO.select(model,page,rows);
		Long total = productDAO.getTotalCountSelect(model);
		
		String[] properties = {"productId","productCode","productName","note",
				"productType.productTypeId","productType.productTypeName",
				"unit.dataDictionaryId:unitId","unit.dataDictionaryName:unitName",
				"color.dataDictionaryId:colorId","color.dataDictionaryName:colorName",
				"size.dataDictionaryId:sizeId","size.dataDictionaryName:sizeName",
				"qtyStore","buyingPrice","wholesalePrice","vipPrice","memberPrice","salePrice","priceStore"};
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
				"unit.dataDictionaryId:unitId","color.dataDictionaryId:colorId","size.dataDictionaryId:sizeId","buyingPrice",
				"wholesalePrice","vipPrice","memberPrice","salePrice","status"};
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
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#newCode(java.lang.String)
	 */
	@Override
	public ServiceResult newCode(String productCode) {
		ServiceResult result = new ServiceResult(false);
		if(productCode==null){
			result.setMessage("参数有误");
			return result;
		}
		result.addData("productCode", productDAO.getNewProductCode(productCode));
		result.setIsSuccess(true);
		return result;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.service.ProductService#uploadImg(java.io.File, java.lang.String, java.lang.String)
	 */
	@Override
	public ServiceResult uploadImg(File file, String templatePath, String productId,
			String fileName) {
		ServiceResult result = new ServiceResult(false);
		Product oldModel = productDAO.load(productId);
		if(oldModel==null){
			result.setMessage("该商品已不存在");
			return result;
		}
		File imgFile = new File(templatePath+File.separator+"productImg"+File.separator+productId+".png");
		if(imgFile.exists()){
			FileUtils.deleteQuietly(imgFile);
		}
		FileUtil.saveFile(file, templatePath+File.separator+"productImg"+File.separator+productId+".png");
		result.setIsSuccess(true);
		return result;
	}
}
