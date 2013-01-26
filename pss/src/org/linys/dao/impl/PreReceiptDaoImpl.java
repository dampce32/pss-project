package org.linys.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.bean.Pager;
import org.linys.dao.PreReceiptDao;
import org.linys.model.PreReceipt;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description: 预收dao实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class PreReceiptDaoImpl extends BaseDAOImpl<PreReceipt, String> implements PreReceiptDao {

	public Pager queryPreReceipt(Pager pager, PreReceipt preReceipt,Date beginDate, Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(PreReceipt.class);
		
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		
		if(StringUtils.isNotEmpty(preReceipt.getPreReceiptCode())){
			criteria.add(Restrictions.like("preReceiptCode", preReceipt.getPreReceiptCode(),MatchMode.ANYWHERE));
		}
		if(preReceipt.getCustomer()!=null && StringUtils.isNotEmpty(preReceipt.getCustomer().getCustomerName())){
			criteria.add(Restrictions.like("customer.customerName", preReceipt.getCustomer().getCustomerName(),MatchMode.ANYWHERE));
		}
		if(preReceipt.getStatus()!=null && preReceipt.getStatus()>=0){
			criteria.add(Restrictions.eq("status", preReceipt.getStatus()));
		}
		if(beginDate!=null){
			criteria.add(Restrictions.ge("preReceiptDate", beginDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("preReceiptDate", endDate));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		
		pager.setTotalCount(total);
		pager.setList(criteria.list());
		return pager;
	}

}
