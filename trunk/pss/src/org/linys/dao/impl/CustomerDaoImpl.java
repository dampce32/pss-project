package org.linys.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.bean.Pager;
import org.linys.dao.CustomerDao;
import org.linys.model.Customer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @Description: 客户dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class CustomerDaoImpl extends BaseDAOImpl<Customer, String> implements CustomerDao {

	public Pager queryCustomer(Pager pager, Customer customer) {
		Criteria criteria = getCurrentSession().createCriteria(Customer.class);
		if(StringUtils.isNotEmpty(customer.getCustomerCode())){
			criteria.add(Restrictions.like("customerCode", customer.getCustomerCode(),MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotEmpty(customer.getCustomerName())){
			criteria.add(Restrictions.like("customerName", customer.getCustomerName(),MatchMode.ANYWHERE));
		}
		if(customer.getStatus()!=null && customer.getStatus()>=0){
			criteria.add(Restrictions.eq("status", customer.getStatus()));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		criteria.addOrder(Order.asc("customerCode"));
		
		pager.setTotalCount(total);
		pager.setList(criteria.list());
		return pager;
	}

	public void updateStataus(String customerId, Integer status) {
		Assert.notNull(customerId, "customerId is required");
		Assert.notNull(status,"status is required");
		String hql ="update Customer set status=:status where customerId=:customerId";
		Query query = getCurrentSession().createQuery(hql);
		query.setInteger("status", status);
		query.setString("customerId", customerId);
		
		query.executeUpdate();
	}
}
