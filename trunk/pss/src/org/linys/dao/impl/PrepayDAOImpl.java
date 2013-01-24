package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.PrepayDAO;
import org.linys.model.Prepay;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
/**
 * @Description:预付单DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-23
 * @author lys
 * @vesion 1.0
 */
@Repository
public class PrepayDAOImpl extends BaseDAOImpl<Prepay, String> implements
		PrepayDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PrepayDAO#query(org.linys.model.Prepay, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Prepay> query(Prepay model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Prepay.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getPrepayCode())){
			criteria.add(Restrictions.like("prepayCode", model.getPrepayCode(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.PrepayDAO#getTotalCount(org.linys.model.Prepay)
	 */
	@Override
	public Long getTotalCount(Prepay model) {
		Criteria criteria  = getCurrentSession().createCriteria(Prepay.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getPrepayCode())){
			criteria.add(Restrictions.like("prepayCode", model.getPrepayCode(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
