package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ExpenseDAO;
import org.linys.model.Expense;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
/**
 * @Description:费用支出DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
@Repository
public class ExpenseDAOImpl extends BaseDAOImpl<Expense, String> implements
		ExpenseDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ExpenseDAO#init(java.lang.String)
	 */
	@Override
	public Expense init(String expenseId) {
		Criteria criteria  = getCurrentSession().createCriteria(Expense.class);
		criteria.add(Restrictions.eq("expenseId", expenseId));
		
		return  criteria.uniqueResult()==null?null:(Expense)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ExpenseDAO#query(org.linys.model.Expense, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Expense> query(Expense model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Expense.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getExpenseName())){
			criteria.add(Restrictions.like("expenseName", model.getExpenseName(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.ExpenseDAO#getTotalCount(org.linys.model.Expense)
	 */
	@Override
	public Long getTotalCount(Expense model) {
		Criteria criteria  = getCurrentSession().createCriteria(Expense.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getExpenseName())){
			criteria.add(Restrictions.like("expenseName", model.getExpenseName(),MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	

}
