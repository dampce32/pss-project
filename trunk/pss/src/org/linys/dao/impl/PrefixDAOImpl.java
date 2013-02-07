package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.PrefixDAO;
import org.linys.model.Prefix;
import org.springframework.stereotype.Repository;
/**
 * @Description: 编号前缀DAO实现
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-21
 * @author lys
 * @vesion 1.0
 */
@Repository
public class PrefixDAOImpl extends BaseDAOImpl<Prefix, String> implements PrefixDAO{
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PrefixDAO#query(org.linys.model.Prefix)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Prefix> query(Prefix model) {
		Criteria criteria  = getCurrentSession().createCriteria(Prefix.class);
		if(model!=null&&StringUtils.isNotEmpty(model.getPrefixCode())){
			criteria.add(Restrictions.like("prefixCode", model.getPrefixCode(), MatchMode.ANYWHERE));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getPrefixName())){
			criteria.add(Restrictions.like("prefixName", model.getPrefixName(), MatchMode.ANYWHERE));
		}
		
		criteria.addOrder(Order.asc("prefixCode"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PrefixDAO#getPrefix(java.lang.String, java.lang.String)
	 */
	@Override
	public String getPrefix(String prefixCode) {
		Criteria criteria  = getCurrentSession().createCriteria(Prefix.class);
		criteria.add(Restrictions.eq("prefixCode",prefixCode));
		criteria.setProjection(Projections.property("prefix"));
		return criteria.uniqueResult().toString();
	}

}
