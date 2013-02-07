package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.InvoiceTypeDAO;
import org.linys.model.InvoiceType;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class InvoiceTypeDAOImpl extends BaseDAOImpl<InvoiceType, String>
		implements InvoiceTypeDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.InvoiceTypeDAO#invoiceTypeDAO(org.linys.model.InvoiceType, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceType> invoiceTypeDAO(InvoiceType model, Integer page,
			Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(InvoiceType.class);
		
		
		if(model!=null&&StringUtils.isNotEmpty(model.getInvoiceTypeName())){
			criteria.add(Restrictions.like("invoiceTypeName", model.getInvoiceTypeName(),MatchMode.ANYWHERE));
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
		
		criteria.addOrder(Order.asc("invoiceTypeName"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.InvoiceTypeDAO#getTotalCount(org.linys.model.InvoiceType)
	 */
	@Override
	public Long getTotalCount(InvoiceType model) {
		Criteria criteria  = getCurrentSession().createCriteria(InvoiceType.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getInvoiceTypeName())){
			criteria.add(Restrictions.like("invoiceTypeName", model.getInvoiceTypeName(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}

}
