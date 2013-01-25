package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.RejectDAO;
import org.linys.model.Reject;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

@Repository
public class RejectDAOImpl extends BaseDAOImpl<Reject, String> implements
		RejectDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RejectDAO#query(org.linys.model.Reject, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Reject> query(Reject model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Reject.class);
		
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getRejectCode())){
			criteria.add(Restrictions.like("rejectCode", model.getRejectCode(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.RejectDAO#getTotalCount(org.linys.model.Reject)
	 */
	@Override
	public Long getTotalCount(Reject model) {
		Criteria criteria  = getCurrentSession().createCriteria(Reject.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getRejectCode())){
			criteria.add(Restrictions.like("rejectCode", model.getRejectCode(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RejectDAO#getMaxCode(java.lang.String)
	 */
	@Override
	public String getMaxCode(String rejectCode) {
		Criteria criteria  = getCurrentSession().createCriteria(Reject.class);
		
		criteria.add(Restrictions.like("rejectCode", rejectCode,MatchMode.START));
		criteria.setProjection(Projections.max("rejectCode"));
		return criteria.uniqueResult()==null?null:criteria.uniqueResult().toString();
	}

}
