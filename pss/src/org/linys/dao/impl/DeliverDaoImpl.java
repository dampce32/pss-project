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
import org.linys.dao.DeliverDao;
import org.linys.model.Deliver;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description: 出库单dao实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class DeliverDaoImpl extends BaseDAOImpl<Deliver, String> implements DeliverDao {

	public Pager queryDeliver(Pager pager, Deliver deliver, Date beginDate,Date endDate,String type) {
		Criteria criteria = getCurrentSession().createCriteria(Deliver.class);
		
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		
		//出库类型
		if("other".equals(type)){
			criteria.add(Restrictions.isNull("customer"));
		}else{
			criteria.add(Restrictions.isNotNull("customer"));
		}
		
		if(StringUtils.isNotEmpty(deliver.getDeliverCode())){
			criteria.add(Restrictions.like("deliverCode", deliver.getDeliverCode(), MatchMode.ANYWHERE));
		}
		if(deliver.getCustomer()!=null && StringUtils.isNotEmpty(deliver.getCustomer().getCustomerName())){
			criteria.add(Restrictions.like("customer.customerName", deliver.getCustomer().getCustomerName(), MatchMode.ANYWHERE));
		}
		if(deliver.getWarehouse()!=null && StringUtils.isNotEmpty(deliver.getWarehouse().getWarehouseId())){
			criteria.add(Restrictions.eq("warehouse", deliver.getWarehouse()));
		}
		if(beginDate!=null){	
			criteria.add(Restrictions.ge("deliverDate", beginDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("deliverDate", endDate));
		}
		if(deliver.getStatus()!=null && deliver.getStatus()>=0){
			criteria.add(Restrictions.eq("status", deliver.getStatus()));
		}
		if(deliver.getIsReceipt()!=null && deliver.getIsReceipt()>=0){
			criteria.add(Restrictions.eq("isReceipt", deliver.getIsReceipt()));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		
		criteria.addOrder(Order.desc("deliverCode"));
		
		pager.setTotalCount(total);
		pager.setList(criteria.list());
		return pager;
	}

}
