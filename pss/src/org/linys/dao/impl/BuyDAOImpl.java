package org.linys.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
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
	@Override
	public List<Map<String, Object>> queryReceive(Date beginDateDate,
			Date endDateDate, String supplierId, String[] idArray, Buy model,
			Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		
		
		return null;
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
	
	

}
