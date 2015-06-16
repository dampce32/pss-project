package org.linys.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.linys.dao.ProductDAO;
import org.linys.dao.ProductPriceRangeDAO;
import org.linys.model.Product;
import org.linys.model.ProductPriceRange;
import org.linys.service.ProductPriceRangeService;
import org.linys.vo.ServiceResult;
import org.springframework.stereotype.Service;
/**
 * @description  商品价格区间Service实现类
 * @copyright 福州骏华信息有限公司 (c)2015
 * @createDate 2015-6-16
 * @author 以宋
 * @vesion 1.0
 */
@Service
public class ProductPriceRangeServiceImpl extends
		BaseServiceImpl<ProductPriceRange, String> implements
		ProductPriceRangeService {
	@Resource
	private ProductPriceRangeDAO productPriceRangeDAO;
	@Resource
	private ProductDAO productDAO;

	@Override
	public ServiceResult getPriceByQty(ProductPriceRange model) {
		ServiceResult result = new ServiceResult(false);
		if(model==null||model.getProduct()==null||StringUtils.isEmpty(model.getProduct().getProductId())
				||StringUtils.isEmpty(model.getPriceLevel())||model.getQty()==null){
			result.setIsSuccess(true);
			return result; 
		}
		ProductPriceRange productPriceRange = productPriceRangeDAO.getPriceByQty(model);
		Double price = null;
		if(productPriceRange==null){
			Product product = productDAO.load(model.getProduct().getProductId());
			if("wholesalePrice".equals(model.getPriceLevel())){
				price = product.getWholesalePrice();
			}else if("vipPrice".equals(model.getPriceLevel())){
				price = product.getVipPrice();
			}else if("memberPrice".equals(model.getPriceLevel())){
				price = product.getMemberPrice();
			}else{
				price = product.getSalePrice();
			}
		}
		if(productPriceRange!=null&&productPriceRange.getPrice()!=null){
			price = productPriceRange.getPrice();
		}
		result.addData("price", price);
		result.setIsSuccess(true);
		return result;
	}
}
