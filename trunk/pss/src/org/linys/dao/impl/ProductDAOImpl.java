package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ProductDAO;
import org.linys.model.Product;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDAOImpl extends BaseDAOImpl<Product, String> implements ProductDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#query(org.linys.model.Product, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<Product> query(Product model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		criteria.createAlias("color", "color", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("size", "size", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("unit", "unit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("productType", "productType", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.eq("productCode", model.getProductCode()));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.eq("productName", model.getProductName()));
		}
		
		if(page==null||page<1){
			page = 1;
		}
		if(rows==null||rows<0){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#getTotalCount(org.linys.model.Product)
	 */
	public Long getTotalCount(Product model) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.eq("productCode", model.getProductCode()));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.eq("productName", model.getProductName()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
