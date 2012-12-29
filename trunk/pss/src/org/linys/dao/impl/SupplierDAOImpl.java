package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.SupplierDAO;
import org.linys.model.Supplier;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
/**
 * @Description:供应商DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
@Repository
public class SupplierDAOImpl extends BaseDAOImpl<Supplier, String> implements
		SupplierDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.SupplierDAO#query(org.linys.model.Supplier, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Supplier> query(Supplier model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Supplier.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getSupplierCode())){
			criteria.add(Restrictions.eq("supplierCode", model.getSupplierCode()));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getSupplierName())){
			criteria.add(Restrictions.eq("supplierName", model.getSupplierName()));
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
	 * @see org.linys.dao.SupplierDAO#getTotalCount(org.linys.model.Supplier)
	 */
	@Override
	public Long getTotalCount(Supplier model) {
		Criteria criteria  = getCurrentSession().createCriteria(Supplier.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getSupplierCode())){
			criteria.add(Restrictions.eq("supplierCode", model.getSupplierCode()));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getSupplierName())){
			criteria.add(Restrictions.eq("supplierName", model.getSupplierName()));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
