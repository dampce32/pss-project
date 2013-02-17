package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
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
		criteria.createAlias("productType", "productType", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.like("productCode", model.getProductCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.like("productName", model.getProductName(),MatchMode.ANYWHERE));
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
		
		criteria.addOrder(Order.asc("productCode"));
		
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#getTotalCount(org.linys.model.Product)
	 */
	public Long getTotalCount(Product model) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.like("productCode", model.getProductCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.like("productName", model.getProductName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#queryReject(org.linys.model.Product, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> queryReject(Product model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		criteria.createAlias("productType", "productType", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.not(Restrictions.eq("qtyStore", 0.0)));
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.like("productCode", model.getProductCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.like("productName", model.getProductName(),MatchMode.ANYWHERE));
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
		criteria.addOrder(Order.asc("productCode"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#getTotalCountReject(org.linys.model.Product)
	 */
	@Override
	public Long getTotalCountReject(Product model) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		
		criteria.add(Restrictions.not(Restrictions.eq("qtyStore", 0.0)));
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.like("productCode", model.getProductCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.like("productName", model.getProductName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#querySelectDefaultPacking(org.linys.model.Product, java.lang.String[], java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> querySelectDefaultPacking(Product model, String[] idArray,
			Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		criteria.createAlias("productType", "productType", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.like("productCode", model.getProductCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.like("productName", model.getProductName(),MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.not(Restrictions.in("productId", idArray)));
		if(page==null||page<1){
			page = 1;
		}
		if(rows==null||rows<0){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("productCode"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#getTotalCountSelectDefaultPacking(org.linys.model.Product, java.lang.String[])
	 */
	@Override
	public Long getTotalCountSelectDefaultPacking(Product model,
			String[] idArray) {
		Criteria criteria  = getCurrentSession().createCriteria(Product.class);
		criteria.createAlias("productType", "productType", CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductCode())){
			criteria.add(Restrictions.like("productCode", model.getProductCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getProductName())){
			criteria.add(Restrictions.like("productName", model.getProductName(),MatchMode.ANYWHERE));
		}
		criteria.add(Restrictions.not(Restrictions.in("productId", idArray)));
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductDAO#getNewProductCode(java.lang.String)
	 */
	@Override
	public String getNewProductCode(String productCode) {
		StringBuilder hql = new StringBuilder();
		hql.append("select max(productCode) " );
		hql.append("from Product ");
		hql.append("where  productCode like :productCode");
		Query query = getCurrentSession().createQuery(hql.toString());
		query.setString("productCode", productCode+"%");
		Object result = query.uniqueResult();
		String maxCode = result==null?null:result.toString();
		int index = 0;
		if(maxCode!=null){
			String code = maxCode.substring(productCode.length(), maxCode.length());
			if(StringUtils.isNotEmpty(code)){
				index = Integer.parseInt(code);	
			}
		}
		maxCode = productCode+String.format("%04d", index+1);
		return maxCode;
	}

}
