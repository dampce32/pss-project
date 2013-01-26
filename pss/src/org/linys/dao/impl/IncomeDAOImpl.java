package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.IncomeDAO;
import org.linys.model.Income;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
/**
 * @Description:收入DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
@Repository
public class IncomeDAOImpl extends BaseDAOImpl<Income, String> implements
		IncomeDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.IncomeDAO#query(org.linys.model.Income, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Income> query(Income model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Income.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getIncomeName())){
			criteria.add(Restrictions.like("incomeName", model.getIncomeName(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.IncomeDAO#getTotalCount(org.linys.model.Income)
	 */
	@Override
	public Long getTotalCount(Income model) {
		Criteria criteria  = getCurrentSession().createCriteria(Income.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getIncomeName())){
			criteria.add(Restrictions.like("incomeName", model.getIncomeName(),MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.IncomeDAO#init(java.lang.String)
	 */
	@Override
	public Income init(String incomeId) {
		Criteria criteria  = getCurrentSession().createCriteria(Income.class);
		criteria.add(Restrictions.eq("incomeId", incomeId));
		
		return  criteria.uniqueResult()==null?null:(Income)criteria.uniqueResult();
	}

}
