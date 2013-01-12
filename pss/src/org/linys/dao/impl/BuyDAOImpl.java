package org.linys.dao.impl;

import java.util.List;

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
	 * @see org.linys.dao.BuyDAO#newCode(java.lang.String)
	 */
	@Override
	public String getMaxCode(String buyCode) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		
		criteria.add(Restrictions.like("buyCode", buyCode,MatchMode.START));
		criteria.setProjection(Projections.max("buyCode"));
		return criteria.uniqueResult()==null?null:criteria.uniqueResult().toString();
	}

}
