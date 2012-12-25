package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ProductTypeDAO;
import org.linys.model.ProductType;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTypeDAOImpl extends BaseDAOImpl<ProductType, String> implements ProductTypeDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#selectRoot()
	 */
	public List<ProductType> selectRoot() {
		Criteria criteria = getCurrentSession().createCriteria(ProductType.class);
		criteria.add(Restrictions.isNull("parentProductType"));
		@SuppressWarnings("unchecked")
		List<ProductType> list = criteria.list();
		return list;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#selectTreeNode(org.linys.model.ProductType)
	 */
	@SuppressWarnings("unchecked")
	public List<ProductType> selectTreeNode(ProductType model) {
		Criteria criteria = getCurrentSession().createCriteria(ProductType.class);
		criteria.createAlias("parentProductType", "model").add(Restrictions.eq("model.productTypeId", model.getProductTypeId()));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#query(java.lang.Integer, java.lang.Integer, org.linys.model.ProductType)
	 */
	@SuppressWarnings("unchecked")
	public List<ProductType> query(Integer page, Integer rows, ProductType model) {
		Criteria criteria = getCurrentSession().createCriteria(ProductType.class);
		if(StringUtils.isEmpty(model.getProductTypeId())){
			criteria.createAlias("parentProductType", "parentProductType");
			criteria.add(Restrictions.isNull("parentProductType.parentProductType"));
		}else{
			criteria.createAlias("parentProductType", "model").add(Restrictions.eq("model.productTypeId",model.getProductTypeId()));
		}
		if(StringUtils.isNotEmpty(model.getProductTypeCode())){
			criteria.add(Restrictions.like("productTypeCode", model.getProductTypeCode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(model.getProductTypeName())){
			criteria.add(Restrictions.like("productTypeName", model.getProductTypeName(),MatchMode.ANYWHERE));
		} 
		if(page<1){
			page = 1;
		}
		if(rows<1){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#count(org.linys.model.ProductType)
	 */
	public Long count(ProductType model) {
		Criteria criteria = getCurrentSession().createCriteria(ProductType.class);
		if(StringUtils.isEmpty(model.getProductTypeId())){
			criteria.createAlias("parentProductType", "parentProductType");
			criteria.add(Restrictions.isNull("parentProductType.parentProductType"));
		}else{
			criteria.createAlias("parentProductType", "model").add(Restrictions.eq("model.productTypeId",model.getProductTypeId()));
		}
		if(StringUtils.isNotEmpty(model.getProductTypeCode())){
			criteria.add(Restrictions.like("productTypeCode", model.getProductTypeCode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(model.getProductTypeName())){
			criteria.add(Restrictions.like("productTypeName", model.getProductTypeName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#updateIsLeaf(java.lang.String, boolean)
	 */
	public void updateIsLeaf(String productTypeId, boolean isLeaf) {
		StringBuilder hql = new StringBuilder("update ProductType set isLeaf = :isLeaf where productTypeId = :productTypeId");
		getCurrentSession().createQuery(hql.toString()).setBoolean("isLeaf", isLeaf).setString("productTypeId", productTypeId).executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#countChildren(java.lang.String)
	 */
	public Long countChildren(String parentID) {
		StringBuilder hql = new StringBuilder("select count(*) from ProductType model where model.parentProductType.productTypeId =:productTypeId ");
		return new Long(getCurrentSession().createQuery(hql.toString()).setString("productTypeId", parentID).uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#queryCombogrid(org.linys.model.ProductType, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<ProductType> queryCombogrid(ProductType model, Integer page,
			Integer rows) {
		Criteria criteria = getCurrentSession().createCriteria(ProductType.class);
		criteria.add(Restrictions.isNotNull("parentProductType"));
		
		if(StringUtils.isNotEmpty(model.getProductTypeName())){
			criteria.add(Restrictions.like("productTypeName", model.getProductTypeName(),MatchMode.ANYWHERE));
		} 
		
		if(page<1){
			page = 1;
		}
		if(rows<1){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		Integer begin = (page-1)*rows;
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ProductTypeDAO#getTotalCountCombogrid(org.linys.model.ProductType)
	 */
	public Long getTotalCountCombogrid(ProductType model) {
		Criteria criteria = getCurrentSession().createCriteria(ProductType.class);
		criteria.add(Restrictions.isNotNull("parentProductType"));
		
		if(StringUtils.isNotEmpty(model.getProductTypeName())){
			criteria.add(Restrictions.like("productTypeName", model.getProductTypeName(),MatchMode.ANYWHERE));
		} 
		criteria.setProjection(Projections.rowCount());
		String total = criteria.uniqueResult().toString();
		return new Long(total);
	}
}
