package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ProductPriceRangeDAO;
import org.linys.model.Product;
import org.linys.model.ProductPriceRange;
import org.springframework.stereotype.Repository;
/**
 * @description 商品价格区间DAO实现类
 * @copyright 福州骏华信息有限公司 (c)2015
 * @createDate 2015-6-16
 * @author 以宋
 * @vesion 1.0
 */
@Repository
public class ProductPriceRangeDAOImpl extends
		BaseDAOImpl<ProductPriceRange, String> implements ProductPriceRangeDAO {

	@SuppressWarnings("unchecked")
	@Override
	public ProductPriceRange getPriceByQty(ProductPriceRange model) {
		Criteria criteria  = getCurrentSession().createCriteria(ProductPriceRange.class);
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductId())){
			criteria.add(Restrictions.eq("product", model.getProduct()));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getPriceLevel())){
			criteria.add(Restrictions.eq("priceLevel", model.getPriceLevel()));
		}
		if(model!=null&&model.getQty()!=null){
			criteria.add(Restrictions.and(Restrictions.le("qtyBegin", model.getQty()), Restrictions.ge("qtyEnd", model.getQty())));
		}
		criteria.addOrder(Order.asc("qtyBegin"));
		List<ProductPriceRange> list =  criteria.list();
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductPriceRange> queryByProduct(Product product,
			String priceLevel) {
		Criteria criteria  = getCurrentSession().createCriteria(ProductPriceRange.class);
		if(product!=null&&StringUtils.isNotEmpty(product.getProductId())){
			criteria.add(Restrictions.eq("product", product));
		}
		if(StringUtils.isNotEmpty(priceLevel)){
			criteria.add(Restrictions.eq("priceLevel", priceLevel));
		}
		criteria.addOrder(Order.asc("qtyBegin"));
		return criteria.list();
	}

}
