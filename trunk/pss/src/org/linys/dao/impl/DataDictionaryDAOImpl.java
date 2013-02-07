package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.DataDictionaryDAO;
import org.linys.model.DataDictionary;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

@Repository
public class DataDictionaryDAOImpl extends BaseDAOImpl<DataDictionary, String> implements DataDictionaryDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.DataDictionaryDAO#query(org.linys.model.DataDictionary, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<DataDictionary> query(DataDictionary model, Integer page, Integer rows) {
		
		Criteria criteria  = getCurrentSession().createCriteria(DataDictionary.class);
		criteria.add(Restrictions.eq("dataDictionaryKind", model.getDataDictionaryKind()));
		
		if(model!=null&&StringUtils.isNotEmpty(model.getDataDictionaryName())){
			criteria.add(Restrictions.like("dataDictionaryName", model.getDataDictionaryName(), MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.DataDictionaryDAO#getTotalCount(org.linys.model.DataDictionary)
	 */
	public Long getTotalCount(DataDictionary model) {
		Criteria criteria  = getCurrentSession().createCriteria(DataDictionary.class);
		criteria.add(Restrictions.eq("dataDictionaryKind", model.getDataDictionaryKind()));
		
		if(model!=null&&StringUtils.isNotEmpty(model.getDataDictionaryName())){
			criteria.add(Restrictions.like("dataDictionaryName", model.getDataDictionaryName(), MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
