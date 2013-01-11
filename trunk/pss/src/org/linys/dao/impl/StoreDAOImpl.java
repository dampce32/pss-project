package org.linys.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.linys.dao.StoreDAO;
import org.linys.model.Store;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDAOImpl extends BaseDAOImpl<Store, String> implements
		StoreDAO {
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

}
