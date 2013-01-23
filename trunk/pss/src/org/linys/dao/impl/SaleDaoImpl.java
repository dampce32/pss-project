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
import org.linys.dao.SaleDao;
import org.linys.model.Sale;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @Description: 订单Dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class SaleDaoImpl extends BaseDAOImpl<Sale, String> implements SaleDao {

	public Pager querySale(Pager pager,Sale sale,Date beginDate,Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(Sale.class);
		
		criteria.createAlias("employee", "employee", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		
		if(StringUtils.isNotEmpty(sale.getSaleCode())){
			criteria.add(Restrictions.like("saleCode", sale.getSaleCode(), MatchMode.ANYWHERE));
		}
		
		if(sale.getCustomer()!=null && StringUtils.isNotEmpty(sale.getCustomer().getCustomerName())){
			criteria.add(Restrictions.like("customer.customerName", sale.getCustomer().getCustomerName(), MatchMode.ANYWHERE));
		}
		if(beginDate!=null){	
			criteria.add(Restrictions.ge("saleDate", beginDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("saleDate", endDate));
		}
		if(sale.getStatus()!=null && sale.getStatus()>=0){
			criteria.add(Restrictions.eq("status", sale.getStatus()));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		
		criteria.addOrder(Order.desc("saleCode"));
		
		pager.setTotalCount(total);
		pager.setList(criteria.list());
		return pager;
	}

	@Override
	public Sale getSale(String saleId) {
		Assert.notNull(saleId, "saleId is required");
		Criteria criteria = getCurrentSession().createCriteria(Sale.class);
		
		criteria.createAlias("employee", "employee", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bank", "bank", CriteriaSpecification.LEFT_JOIN);

		criteria.add(Restrictions.eq("saleId", saleId));
		
		return (Sale) criteria.uniqueResult();
	}
	
}
