package org.linys.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.bean.Pager;
import org.linys.dao.DeliverRejectDao;
import org.linys.model.DeliverReject;

public class DeliverRejectDaoImpl extends BaseDAOImpl<DeliverReject, String> implements DeliverRejectDao {

	public Pager queryDeliverReject(Pager pager, DeliverReject deliverReject,Date beginDate, Date endDate) {
		
		Criteria criteria = getCurrentSession().createCriteria(DeliverReject.class);
		
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		
		if(StringUtils.isNotEmpty(deliverReject.getDeliverRejectCode())){
			criteria.add(Restrictions.like("deliverRejectCode", deliverReject.getDeliverRejectCode()));
		}
		
		if(StringUtils.isNotEmpty(deliverReject.getSourceCode())){
			criteria.add(Restrictions.like("sourceCode", deliverReject.getSourceCode()));
		}
		if(deliverReject.getCustomer()!=null &&StringUtils.isNotEmpty(deliverReject.getCustomer().getCustomerName())){
			criteria.add(Restrictions.like("customer.customerName", deliverReject.getCustomer().getCustomerName(),MatchMode.ANYWHERE));
		}
		if(beginDate!=null){
			criteria.add(Restrictions.ge("deliverRejectDate", beginDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("deliverRejectDate", endDate));
		}
		
		if(deliverReject.getStatus()!=null && deliverReject.getStatus()>=0){
			criteria.add(Restrictions.eq("status", deliverReject.getStatus()));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		
		criteria.addOrder(Order.desc("deliverRejectCode"));
		
		pager.setTotalCount(total);
		pager.setList(criteria.list());
		return pager;
	}

}
