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
import org.linys.dao.ReceiveDAO;
import org.linys.model.Receive;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class ReceiveDAOImpl extends BaseDAOImpl<Receive, String> implements
		ReceiveDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#query(org.linys.model.Receive, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Receive> query(String kind,Receive model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Receive.class);
		
		criteria.createAlias("warehouse", "warehouse",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("employee", "employee",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("invoiceType", "invoiceType",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getReceiveCode())){
			criteria.add(Restrictions.like("receiveCode", model.getReceiveCode(),MatchMode.ANYWHERE));
		}
		if("other".equals(kind)){
			criteria.add(Restrictions.isNull("supplier"));
		}else{
			criteria.add(Restrictions.isNotNull("supplier"));
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
	 * @see org.linys.dao.ReceiveDAO#getTotalCount(org.linys.model.Receive)
	 */
	@Override
	public Long getTotalCount(String kind,Receive model) {
		Criteria criteria  = getCurrentSession().createCriteria(Receive.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getReceiveCode())){
			criteria.add(Restrictions.like("receiveCode", model.getReceiveCode(),MatchMode.ANYWHERE));
		}
		if("other".equals(kind)){
			criteria.add(Restrictions.isNull("supplier"));
		}else{
			criteria.add(Restrictions.isNotNull("supplier"));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#newCode(java.lang.String)
	 */
	@Override
	public String getMaxCode(String receiveCode) {
		Criteria criteria  = getCurrentSession().createCriteria(Receive.class);
		
		criteria.add(Restrictions.like("receiveCode", receiveCode,MatchMode.START));
		criteria.setProjection(Projections.max("receiveCode"));
		return criteria.uniqueResult()==null?null:criteria.uniqueResult().toString();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#querySelectBuyDetail(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> querySelectBuyDetail(String[] idArray, String[] idArray2) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.buyId,b.buyCode,a.buyDetailId,a.productId,c.productCode,c.productName, ");
		sb.append("d.dataDictionaryName unitName,e.dataDictionaryName sizeName,a.colorId,f.dataDictionaryName colorName, ");
		sb.append("	a.qty-a.receiveQty qty,a.price ");
		sb.append("from T_BuyDetail a ");
		sb.append("left join T_Buy b on a.buyId = b.buyId ");
		sb.append("left join T_Product c on a.productId = c.productId ");
		sb.append("left join T_DataDictionary d on c.unitId  = d.dataDictionaryId ");
		sb.append("left join T_DataDictionary e on c.sizeId = e.dataDictionaryId ");
		sb.append("left join T_DataDictionary f on c.colorId = f.dataDictionaryId ");
		sb.append("where a.buyId in (:idArray) ");
		sb.append("and a.buyDetailId not in(:idArray2) and a.qty - a.receiveQty >  0  ");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setParameterList("idArray", idArray);
		query.setParameterList("idArray2",idArray2);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
