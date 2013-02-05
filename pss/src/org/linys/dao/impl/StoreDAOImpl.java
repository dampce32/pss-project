package org.linys.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.linys.dao.StoreDAO;
import org.linys.model.Product;
import org.linys.model.Store;
import org.linys.model.Warehouse;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class StoreDAOImpl extends BaseDAOImpl<Store, String> implements StoreDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.StoreDAO#query(org.linys.model.Store, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> query(Store model, Integer page,
			Integer rows) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select b.productId,b.productCode,b.productName,f.productTypeName, ");
		sql.append( "	c.dataDictionaryId colorId,c.dataDictionaryName colorName,d.dataDictionaryId sizeId,d.dataDictionaryName sizeName,e.dataDictionaryId unitId,e.dataDictionaryName unitName, ");
		sql.append( "	SUM(a.qty) qty,SUM(a.amount) amount ");
		sql.append( "from T_Store a ");
		sql.append( "left join T_Product b on a.productId = b.productId ");
		sql.append( "left join T_DataDictionary c on b.colorId = c.dataDictionaryId  ");
		sql.append( "left join T_DataDictionary d on b.sizeId = d.dataDictionaryId ");
		sql.append( "left join T_DataDictionary e on b.unitId = e.dataDictionaryId ");
		sql.append( "left join T_ProductType f on b.productTypeID = f.productTypeID where 1 =1 ");
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductCode())){
			sql.append( " and b.productCode like :productCode ");
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductName())){
			sql.append( " and b.productName like :productName ");
		}
		sql.append( "group by b.productId,b.productCode,b.productName,f.productTypeName, ");
		sql.append( " c.dataDictionaryId,c.dataDictionaryName,d.dataDictionaryId,d.dataDictionaryName,e.dataDictionaryId,e.dataDictionaryName ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductCode())){
			query.setString("productCode" ,"%"+model.getProduct().getProductCode()+"%");
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductName())){
			query.setString("productName" ,"%"+model.getProduct().getProductName()+"%");
		}
		
		if(page==null||page<1){
			page=1;
		}
		if(rows==null||rows<1){
			rows=1;
		}
		Integer begin = (page-1)*rows;
		query.setFirstResult(begin);
		query.setMaxResults(rows);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.StoreDAO#getTotalCount(org.linys.model.Store)
	 */
	@Override
	public Long getTotalCount(Store model) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select count(1) from( ");
		sql.append( "select b.productId,b.productCode,b.productName,f.productTypeName, ");
		sql.append( "	c.dataDictionaryId colorId,c.dataDictionaryName colorName,d.dataDictionaryId sizeId,d.dataDictionaryName sizeName,e.dataDictionaryId unitId,e.dataDictionaryName unitName, ");
		sql.append( "	SUM(a.qty) qty,SUM(a.amount) amount ");
		sql.append( "from T_Store a ");
		sql.append( "left join T_Product b on a.productId = b.productId ");
		sql.append( "left join T_DataDictionary c on b.colorId = c.dataDictionaryId  ");
		sql.append( "left join T_DataDictionary d on b.sizeId = d.dataDictionaryId ");
		sql.append( "left join T_DataDictionary e on b.unitId = e.dataDictionaryId ");
		sql.append( "left join T_ProductType f on b.productTypeID = f.productTypeID where 1 =1 ");
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductCode())){
			sql.append( " and b.productCode like :productCode ");
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductName())){
			sql.append( " and b.productName like :productName ");
		}
		sql.append( "group by b.productId,b.productCode,b.productName,f.productTypeName, ");
		sql.append( " c.dataDictionaryId,c.dataDictionaryName,d.dataDictionaryId,d.dataDictionaryName,e.dataDictionaryId,e.dataDictionaryName )a ");
		
		Query query = getCurrentSession().createSQLQuery(sql.toString());
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductCode())){
			query.setString("productCode" ,"%"+model.getProduct().getProductCode()+"%");
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductName())){
			query.setString("productName" ,"%"+model.getProduct().getProductName()+"%");
		}
		query.setProperties(Projections.rowCount());
		return new Long(query.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.StoreDAO#selectReject(org.linys.model.Store, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Store> selectReject(Store model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Store.class);
		
		criteria.createAlias("product", "product",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.color", "color",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.size", "size",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.unit", "unit",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&model.getWarehouse()!=null&&StringUtils.isNotEmpty(model.getWarehouse().getWarehouseId())){
			criteria.add(Restrictions.eq("warehouse.warehouseId", model.getWarehouse().getWarehouseId()));
		}
		
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductId())){
			criteria.add(Restrictions.eq("product.productId", model.getProduct().getProductId()));
		}
		
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductCode())){
			criteria.add(Restrictions.like("product.productCode", model.getProduct().getProductCode(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductName())){
			criteria.add(Restrictions.like("product.productName", model.getProduct().getProductName(), MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.StoreDAO#getTotalCountSelectReject(org.linys.model.Store)
	 */
	@Override
	public Long getTotalCountSelectReject(Store model) {
		Criteria criteria  = getCurrentSession().createCriteria(Store.class);
		
		criteria.createAlias("product", "product",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.productType", "productType",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&model.getWarehouse()!=null&&StringUtils.isNotEmpty(model.getWarehouse().getWarehouseId())){
			criteria.add(Restrictions.eq("warehouse.warehouseId", model.getWarehouse().getWarehouseId()));
		}
		
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductId())){
			criteria.add(Restrictions.eq("product.productId", model.getProduct().getProductId()));
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductCode())){
			criteria.add(Restrictions.like("product.productCode", model.getProduct().getProductCode(), MatchMode.ANYWHERE));
		}
		if(model!=null&&model.getProduct()!=null&&StringUtils.isNotEmpty(model.getProduct().getProductName())){
			criteria.add(Restrictions.like("product.productName", model.getProduct().getProductName(), MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	
	public Store getStore(Product product, Warehouse warehouse) {
		Assert.notNull(product, "product is required");
		Assert.notNull(warehouse, "warehouse is required");
		
		Criteria criteria = getCurrentSession().createCriteria(Store.class);
		criteria.createAlias("product", "product",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.productType", "productType",CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("product",product));
		criteria.add(Restrictions.eq("warehouse", warehouse));
		
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		
		return (Store) criteria.uniqueResult();
	}

}
