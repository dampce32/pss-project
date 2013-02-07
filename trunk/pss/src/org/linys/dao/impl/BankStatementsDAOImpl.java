package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.BankStatementsDAO;
import org.linys.model.BankStatements;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
/**
 * @Description:银行账单DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
@Repository
public class BankStatementsDAOImpl extends BaseDAOImpl<BankStatements, String> implements BankStatementsDAO{
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BankStatementsDAO#init(java.lang.String)
	 */
	@Override
	public BankStatements init(String bankStatementsId) {
		Criteria criteria  = getCurrentSession().createCriteria(BankStatements.class);
		criteria.add(Restrictions.eq("bankStatementsId", bankStatementsId));
		
		return  criteria.uniqueResult()==null?null:(BankStatements)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BankStatementsDAO#query(org.linys.model.BankStatements, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BankStatements> query(BankStatements model, Integer page,
			Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(BankStatements.class);
		
		
		if(page==null||page<1){
			page = 1;
		}
		if(rows==null||rows<0){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		
		criteria.addOrder(Order.desc("bankStatementsDate"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BankStatementsDAO#getTotalCount(org.linys.model.BankStatements)
	 */
	@Override
	public Long getTotalCount(BankStatements model) {
		Criteria criteria  = getCurrentSession().createCriteria(BankStatements.class);
		
		
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
