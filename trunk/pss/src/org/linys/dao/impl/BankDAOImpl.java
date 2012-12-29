package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.BankDAO;
import org.linys.model.Bank;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class BankDAOImpl extends BaseDAOImpl<Bank, String> implements BankDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BankDAO#query(org.linys.model.Bank, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Bank> query(Bank model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Bank.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBankShortName())){
			criteria.add(Restrictions.like("bankShortName", model.getBankShortName(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBankName())){
			criteria.add(Restrictions.like("bankName", model.getBankName(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.BankDAO#getTotalCount(org.linys.model.Bank)
	 */
	@Override
	public Long getTotalCount(Bank model) {
		Criteria criteria  = getCurrentSession().createCriteria(Bank.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBankShortName())){
			criteria.add(Restrictions.like("bankShortName", model.getBankShortName(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBankName())){
			criteria.add(Restrictions.like("bankName", model.getBankName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
