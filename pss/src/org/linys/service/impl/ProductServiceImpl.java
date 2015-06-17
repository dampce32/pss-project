package org.linys.service.impl;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.linys.dao.DataDictionaryDAO;
import org.linys.dao.DefaultPackagingDAO;
import org.linys.dao.ProductDAO;
import org.linys.dao.ProductPriceRangeDAO;
import org.linys.dao.ProductTypeDAO;
import org.linys.model.DataDictionary;
import org.linys.model.DefaultPackaging;
import org.linys.model.Product;
import org.linys.model.ProductPriceRange;
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
	@Resource
	private ProductPriceRangeDAO productPriceRangeDAO;
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
	public ServiceResult save(Product model, String defaultPackagingIds, String deleleIds, String productIds, String qtys, 
			String productPriceRangeIdsWholesalePrice, String deleleIdsWholesalePrice, String pricesWholesalePrice, String qtyBeginsWholesalePrice, String qtyEndsWholesalePrice,
			String productPriceRangeIdsVipPrice, String deleleIdsVipPrice, String pricesVipPrice, String qtyBeginsVipPrice, String qtyEndsVipPrice, 
			String productPriceRangeIdsMemberPrice, String deleleIdsMemberPrice, String pricesMemberPrice, String qtyBeginsMemberPrice, String qtyEndsMemberPrice, 
			String productPriceRangeIdsSalePrice, String deleleIdsSalePrice, String pricesSalePrice, String qtyBeginsSalePrice, String qtyEndsSalePrice) {
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
		
		String[] productPriceRangeIdWholesalePriceArray = StringUtil.split(productPriceRangeIdsWholesalePrice);
		String[] deleleIdWholesalePriceArray = StringUtil.split(deleleIdsWholesalePrice);
		String[] priceWholesalePriceArray = StringUtil.split(pricesWholesalePrice);
		String[] qtyBeginWholesalePriceArray = StringUtil.split(qtyBeginsWholesalePrice);
		String[] qtyEndWholesalePriceArray = StringUtil.split(qtyEndsWholesalePrice);
		
		String[] productPriceRangeIdVipPriceArray = StringUtil.split(productPriceRangeIdsVipPrice);
		String[] deleleIdVipPriceArray = StringUtil.split(deleleIdsVipPrice);
		String[] priceVipPriceArray = StringUtil.split(pricesVipPrice);
		String[] qtyBeginVipPriceArray = StringUtil.split(qtyBeginsVipPrice);
		String[] qtyEndVipPriceArray = StringUtil.split(qtyEndsVipPrice);
		
		String[] productPriceRangeIdMemberPriceArray = StringUtil.split(productPriceRangeIdsMemberPrice);
		String[] deleleIdMemberPriceArray = StringUtil.split(deleleIdsMemberPrice);
		String[] priceMemberPriceArray = StringUtil.split(pricesMemberPrice);
		String[] qtyBeginMemberPriceArray = StringUtil.split(qtyBeginsMemberPrice);
		String[] qtyEndMemberPriceArray = StringUtil.split(qtyEndsMemberPrice);
		
		String[] productPriceRangeIdSalePriceArray = StringUtil.split(productPriceRangeIdsSalePrice);
		String[] deleleIdSalePriceArray = StringUtil.split(deleleIdsSalePrice);
		String[] priceSalePriceArray = StringUtil.split(pricesSalePrice);
		String[] qtyBeginSalePriceArray = StringUtil.split(qtyBeginsSalePrice);
		String[] qtyEndSalePriceArray = StringUtil.split(qtyEndsSalePrice);
		
		
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
			
			for (int i = 0; i < priceWholesalePriceArray.length&&StringUtils.isNotEmpty(priceWholesalePriceArray[i]); i++) {
				Double price = Double.parseDouble(priceWholesalePriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginWholesalePriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndWholesalePriceArray[i]);
				
				ProductPriceRange productPriceRange = new ProductPriceRange();
				productPriceRange.setProduct(model);
				productPriceRange.setPriceLevel("wholesalePrice");
				productPriceRange.setPrice(price);
				productPriceRange.setQtyBegin(qtyBegin);
				productPriceRange.setQtyEnd(qtyEnd);
				productPriceRangeDAO.save(productPriceRange);
			}
			
			for (int i = 0; i < priceVipPriceArray.length&&StringUtils.isNotEmpty(priceVipPriceArray[i]); i++) {
				Double price = Double.parseDouble(priceVipPriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginVipPriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndVipPriceArray[i]);
				
				ProductPriceRange productPriceRange = new ProductPriceRange();
				productPriceRange.setProduct(model);
				productPriceRange.setPriceLevel("vipPrice");
				productPriceRange.setPrice(price);
				productPriceRange.setQtyBegin(qtyBegin);
				productPriceRange.setQtyEnd(qtyEnd);
				productPriceRangeDAO.save(productPriceRange);
			}
			
			for (int i = 0; i < priceMemberPriceArray.length&&StringUtils.isNotEmpty(priceMemberPriceArray[i]); i++) {
				Double price = Double.parseDouble(priceMemberPriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginMemberPriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndMemberPriceArray[i]);
				
				ProductPriceRange productPriceRange = new ProductPriceRange();
				productPriceRange.setProduct(model);
				productPriceRange.setPriceLevel("memberPrice");
				productPriceRange.setPrice(price);
				productPriceRange.setQtyBegin(qtyBegin);
				productPriceRange.setQtyEnd(qtyEnd);
				productPriceRangeDAO.save(productPriceRange);
			}
			
			for (int i = 0; i < priceSalePriceArray.length&&StringUtils.isNotEmpty(priceSalePriceArray[i]); i++) {
				Double price = Double.parseDouble(priceSalePriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginSalePriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndSalePriceArray[i]);
				
				ProductPriceRange productPriceRange = new ProductPriceRange();
				productPriceRange.setProduct(model);
				productPriceRange.setPriceLevel("salePrice");
				productPriceRange.setPrice(price);
				productPriceRange.setQtyBegin(qtyBegin);
				productPriceRange.setQtyEnd(qtyEnd);
				productPriceRangeDAO.save(productPriceRange);
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
						if(oldDefaultPackaging!=null){
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
			//删除已删的批发价格区间
			if(!"".equals(deleleIdWholesalePriceArray)){
				for (String deleleIdWholesalePrice : deleleIdWholesalePriceArray) {
					if(StringUtils.isNotEmpty(deleleIdWholesalePrice)){
						ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(deleleIdWholesalePrice);
						if(oldProductPriceRange!=null){
							productPriceRangeDAO.delete(oldProductPriceRange);
						}
					}
				}
			}
			//根据Id更新或新增
			for (int i = 0 ;i<priceWholesalePriceArray.length&&StringUtils.isNotEmpty(priceWholesalePriceArray[i]);i++) {
				String productPriceRangeIdWholesalePrice = productPriceRangeIdWholesalePriceArray[i];
				Double price = Double.parseDouble(priceWholesalePriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginWholesalePriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndWholesalePriceArray[i]);
				
				if(StringUtils.isEmpty(productPriceRangeIdWholesalePrice)){//新增
					
					ProductPriceRange productPriceRange = new ProductPriceRange();
					productPriceRange.setProduct(oldModel);
					productPriceRange.setPriceLevel("wholesalePrice");
					productPriceRange.setPrice(price);
					productPriceRange.setQtyBegin(qtyBegin);
					productPriceRange.setQtyEnd(qtyEnd);
					productPriceRangeDAO.save(productPriceRange);
				}else{
					ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(productPriceRangeIdWholesalePrice);
					oldProductPriceRange.setPrice(price);
					oldProductPriceRange.setQtyBegin(qtyBegin);
					oldProductPriceRange.setQtyEnd(qtyEnd);
				}
			}
			
			//删除已删的VIP价格区间
			if(!"".equals(deleleIdVipPriceArray)){
				for (String deleleIdVipPrice : deleleIdVipPriceArray) {
					if(StringUtils.isNotEmpty(deleleIdVipPrice)){
						ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(deleleIdVipPrice);
						if(oldProductPriceRange!=null){
							productPriceRangeDAO.delete(oldProductPriceRange);
						}
					}
				}
			}
			//根据Id更新或新增
			for (int i = 0 ;i<priceVipPriceArray.length&&StringUtils.isNotEmpty(priceVipPriceArray[i]);i++) {
				String productPriceRangeIdVipPrice = productPriceRangeIdVipPriceArray[i];
				Double price = Double.parseDouble(priceVipPriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginVipPriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndVipPriceArray[i]);
				
				if(StringUtils.isEmpty(productPriceRangeIdVipPrice)){//新增
					
					ProductPriceRange productPriceRange = new ProductPriceRange();
					productPriceRange.setProduct(oldModel);
					productPriceRange.setPriceLevel("vipPrice");
					productPriceRange.setPrice(price);
					productPriceRange.setQtyBegin(qtyBegin);
					productPriceRange.setQtyEnd(qtyEnd);
					productPriceRangeDAO.save(productPriceRange);
				}else{
					ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(productPriceRangeIdVipPrice);
					oldProductPriceRange.setPrice(price);
					oldProductPriceRange.setQtyBegin(qtyBegin);
					oldProductPriceRange.setQtyEnd(qtyEnd);
				}
			}
			
			//删除已删的会员价格区间
			if(!"".equals(deleleIdMemberPriceArray)){
				for (String deleleIdMemberPrice : deleleIdMemberPriceArray) {
					if(StringUtils.isNotEmpty(deleleIdMemberPrice)){
						ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(deleleIdMemberPrice);
						if(oldProductPriceRange!=null){
							productPriceRangeDAO.delete(oldProductPriceRange);
						}
					}
				}
			}
			//根据Id更新或新增
			for (int i = 0 ;i<priceMemberPriceArray.length&&StringUtils.isNotEmpty(priceMemberPriceArray[i]);i++) {
				String productPriceRangeIdMemberPrice = productPriceRangeIdMemberPriceArray[i];
				Double price = Double.parseDouble(priceMemberPriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginMemberPriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndMemberPriceArray[i]);
				
				if(StringUtils.isEmpty(productPriceRangeIdMemberPrice)){//新增
					
					ProductPriceRange productPriceRange = new ProductPriceRange();
					productPriceRange.setProduct(oldModel);
					productPriceRange.setPriceLevel("memberPrice");
					productPriceRange.setPrice(price);
					productPriceRange.setQtyBegin(qtyBegin);
					productPriceRange.setQtyEnd(qtyEnd);
					productPriceRangeDAO.save(productPriceRange);
				}else{
					ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(productPriceRangeIdMemberPrice);
					oldProductPriceRange.setPrice(price);
					oldProductPriceRange.setQtyBegin(qtyBegin);
					oldProductPriceRange.setQtyEnd(qtyEnd);
				}
			}
			
			//删除已删的零售价格区间
			if(!"".equals(deleleIdSalePriceArray)){
				for (String deleleIdSalePrice : deleleIdSalePriceArray) {
					if(StringUtils.isNotEmpty(deleleIdSalePrice)){
						ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(deleleIdSalePrice);
						if(oldProductPriceRange!=null){
							productPriceRangeDAO.delete(oldProductPriceRange);
						}
					}
				}
			}
			//根据Id更新或新增
			for (int i = 0 ;i<priceSalePriceArray.length&&StringUtils.isNotEmpty(priceSalePriceArray[i]);i++) {
				String productPriceRangeIdSalePrice = productPriceRangeIdSalePriceArray[i];
				Double price = Double.parseDouble(priceSalePriceArray[i]);
				Double qtyBegin = Double.parseDouble(qtyBeginSalePriceArray[i]);
				Double qtyEnd = Double.parseDouble(qtyEndSalePriceArray[i]);
				
				if(StringUtils.isEmpty(productPriceRangeIdSalePrice)){//新增
					
					ProductPriceRange productPriceRange = new ProductPriceRange();
					productPriceRange.setProduct(oldModel);
					productPriceRange.setPriceLevel("salePrice");
					productPriceRange.setPrice(price);
					productPriceRange.setQtyBegin(qtyBegin);
					productPriceRange.setQtyEnd(qtyEnd);
					productPriceRangeDAO.save(productPriceRange);
				}else{
					ProductPriceRange  oldProductPriceRange = productPriceRangeDAO.load(productPriceRangeIdSalePrice);
					oldProductPriceRange.setPrice(price);
					oldProductPriceRange.setQtyBegin(qtyBegin);
					oldProductPriceRange.setQtyEnd(qtyEnd);
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
		
		List<ProductPriceRange> productPriceRangeWholesalePriceList = productPriceRangeDAO.queryByProduct(product,"wholesalePrice");
		String[] propertiesProductPriceRange = {"productPriceRangeId","price","qtyBegin","qtyEnd"};
		String productPriceRangeWholesalePriceData = JSONUtil.toJson(productPriceRangeWholesalePriceList,propertiesProductPriceRange);
		result.addData("productPriceRangeWholesalePriceData", productPriceRangeWholesalePriceData);
		
		List<ProductPriceRange> productPriceRangeVipPriceList = productPriceRangeDAO.queryByProduct(product,"vipPrice");
		String productPriceRangeVipPriceData = JSONUtil.toJson(productPriceRangeVipPriceList,propertiesProductPriceRange);
		result.addData("productPriceRangeVipPriceData", productPriceRangeVipPriceData);
		
		List<ProductPriceRange> productPriceRangeMemberPriceList = productPriceRangeDAO.queryByProduct(product,"memberPrice");
		String productPriceRangeMemberPriceData = JSONUtil.toJson(productPriceRangeMemberPriceList,propertiesProductPriceRange);
		result.addData("productPriceRangeMemberPriceData", productPriceRangeMemberPriceData);
		
		List<ProductPriceRange> productPriceRangeSalePriceList = productPriceRangeDAO.queryByProduct(product,"salePrice");
		String productPriceRangeSalePriceData = JSONUtil.toJson(productPriceRangeSalePriceList,propertiesProductPriceRange);
		result.addData("productPriceRangeSalePriceData", productPriceRangeSalePriceData);
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
