package org.linys.dao.impl;

import java.util.Date;
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
import org.linys.dao.BuyDAO;
import org.linys.model.Buy;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class BuyDAOImpl extends BaseDAOImpl<Buy, String> implements
		BuyDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#query(org.linys.model.Buy, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Buy> query(Buy model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("employee", "employee",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("invoiceType", "invoiceType",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			criteria.add(Restrictions.like("buyCode", model.getBuyCode(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.BuyDAO#getTotalCount(org.linys.model.Buy)
	 */
	@Override
	public Long getTotalCount(Buy model) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			criteria.add(Restrictions.like("buyCode", model.getBuyCode(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#queryReceive(java.util.Date, java.util.Date, java.lang.String, java.lang.String[], org.linys.model.Buy, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryReceive(Date beginDate,
			Date endDate, String supplierId, String[] idArray, Buy model,
			Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder();
		sb.append("select b.buyId,b.buyCode,b.buyDate ");
		sb.append("from(select distinct b.buyId ");
		sb.append("		from(select * ");
		sb.append("			from T_Buy a ");
		sb.append("			where a.buyDate between :beginDate and :endDate and a.status = 1 and a.supplierId = :supplierId )a ");
		sb.append("		left join T_BuyDetail b on a.buyId = b.buyId ");
		sb.append("		where b.qty - b.receiveQty > 0 and b.buyDetailId not in(:idArray)) a ");
		sb.append("left join T_Buy b on a.buyId = b.buyId ");
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			sb.append("where b.buyCode like :buyCode ");
		}
		
		sb.append("order by b.buyDate,b.buyCode");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setDate("beginDate", beginDate);
		query.setDate("endDate", endDate);
		query.setString("supplierId", supplierId);
		query.setParameterList("idArray", idArray);
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			query.setString("buyCode","%'"+model.getBuyCode()+"'%");
		}
		if(page==null||page<1){
			page = 1;
		}
		if(rows==null||rows<0){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		
		query.setFirstResult(begin);
		query.setMaxResults(rows);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#init(java.lang.String)
	 */
	@Override
	public Buy init(String buyId) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		criteria.add(Restrictions.eq("buyId", buyId));
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bank", "bank",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("employee", "employee",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("invoiceType", "invoiceType",CriteriaSpecification.LEFT_JOIN);
		
		return (Buy) criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#getTotalReceive(java.util.Date, java.util.Date, java.lang.String, java.lang.String[], org.linys.model.Buy)
	 */
	@Override
	public Long getTotalReceive(Date beginDate, Date endDate,
			String supplierId, String[] idArray, Buy model) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(b.buyId) ");
		sb.append("from(select distinct b.buyId ");
		sb.append("		from(select * ");
		sb.append("			from T_Buy a ");
		sb.append("			where a.buyDate between :beginDate and :endDate and a.status = 1 and a.supplierId = :supplierId )a ");
		sb.append("		left join T_BuyDetail b on a.buyId = b.buyId ");
		sb.append("		where b.qty - b.receiveQty > 0 and b.buyDetailId not in(:idArray)) a ");
		sb.append("left join T_Buy b on a.buyId = b.buyId ");
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			sb.append("where b.buyCode like :buyCode ");
		}
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setDate("beginDate", beginDate);
		query.setDate("endDate", endDate);
		query.setString("supplierId", supplierId);
		query.setParameterList("idArray", idArray);
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			query.setString("buyCode","%'"+model.getBuyCode()+"'%");
		}
		return new Long(query.uniqueResult().toString());
	}
	
	

}
