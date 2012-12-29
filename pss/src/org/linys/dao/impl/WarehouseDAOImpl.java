package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.WarehouseDAO;
import org.linys.model.Warehouse;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

@Repository
public class WarehouseDAOImpl extends BaseDAOImpl<Warehouse, String> implements
		WarehouseDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.WarehouseDAO#query(org.linys.model.Warehouse, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Warehouse> query(Warehouse model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Warehouse.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getWarehouseCode())){
			criteria.add(Restrictions.like("warehouseCode", model.getWarehouseCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getWarehouseName())){
			criteria.add(Restrictions.like("warehouseName", model.getWarehouseName(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.WarehouseDAO#getTotalCount(org.linys.model.Warehouse)
	 */
	@Override
	public Long getTotalCount(Warehouse model) {
		Criteria criteria  = getCurrentSession().createCriteria(Warehouse.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getWarehouseCode())){
			criteria.add(Restrictions.like("warehouseCode", model.getWarehouseCode(),MatchMode.ANYWHERE));
		}
		
		if(model!=null&&StringUtils.isNotEmpty(model.getWarehouseName())){
			criteria.add(Restrictions.like("warehouseName", model.getWarehouseName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
