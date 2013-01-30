package org.linys.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.linys.bean.Pager;
import org.linys.dao.ReceiptDao;
import org.linys.model.Customer;
import org.linys.model.Receipt;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description: 收款dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-28
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class ReceiptDaoImpl extends BaseDAOImpl<Receipt, String> implements ReceiptDao {

	public Pager queryReceipt(Pager pager, Receipt receipt, Date beginDate,Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(Receipt.class);
		
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		
		if(StringUtils.isNotEmpty(receipt.getReceiptCode())){
			criteria.add(Restrictions.like("receiptCode", receipt.getReceiptCode(), MatchMode.ANYWHERE));
		}
		
		if(receipt.getCustomer()!=null && StringUtils.isNotEmpty(receipt.getCustomer().getCustomerName())){
			criteria.add(Restrictions.like("customer.customerName", receipt.getCustomer().getCustomerName()));
		}
		if(receipt.getStatus()!=null && receipt.getStatus()>=0){
			criteria.add(Restrictions.eq("status", receipt.getStatus()));
		}
		if(beginDate!=null){
			criteria.add(Restrictions.ge("receiptDate", beginDate));
		}
		
		if(endDate!=null){
			criteria.add(Restrictions.le("receiptDate", endDate));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());

		pager.setTotalCount(total);
		pager.setList(criteria.list());
		
		return pager;
	}
	
	public Pager queryNeedCheck(Pager pager, Customer customer, String[] idArray) {
		StringBuilder sb = new StringBuilder();
		
        sb.append("from(select a.deliverId sourceId,a.deliverCode sourceCode,'销售出库' receiptKind,a.deliverDate sourceDate,a.amount,a.discountAmount+a.receiptedAmount+a.checkAmount receiptedAmount,a.amount-a.discountAmount-a.receiptedAmount-a.checkAmount needReceiptAmount ");
        sb.append("	from T_Deliver a ");
        sb.append("	where a.status = 1 and a.isReceipt = 0 and a.customerId =:customerId ");
        sb.append("	union all ");
        sb.append("	select a.saleId,a.saleCode,'订单预收款',a.saleDate,-a.receiptedAmount,-a.checkAmount,0-a.receiptedAmount+a.checkAmount ");
        sb.append("	from T_Sale a ");
        sb.append("	where a.status = 1 and a.customerId =:customerId and a.receiptedAmount-a.checkAmount>0 ");
        sb.append("	union all  ");
        sb.append("	select a.deliverRejectId,a.deliverRejectCode,'退货单',a.deliverRejectDate,-a.amount,0-a.checkAmount-a.payedAmount,-a.amount+a.payedAmount + a.checkAmount ");
        sb.append("	from t_DeliverReject a ");
		sb.append("	where a.status = 1 and a.customerId =:customerId and a.amount-a.payedAmount - a.checkAmount>0 ");
		sb.append("	union ALL ");
		sb.append("	select a.preReceiptId,a.preReceiptCode,'预收单预收款',a.preReceiptDate,-a.amount,0-a.checkAmount,-a.amount+a.checkAmount ");
		sb.append("	from T_PreReceipt a ");
		sb.append("	where a.status = 1 and a.customerId =:customerId and a.amount - a.checkAmount >0)a ");
		sb.append("where a.sourceId not in (:idArray) ");
		
		StringBuilder hql = new StringBuilder("select * ").append(sb);
		StringBuilder totalHql = new StringBuilder("select count(sourceId) ").append(sb);
		
		Query query = getCurrentSession().createSQLQuery(hql.toString());
		Query totalQuery = getCurrentSession().createSQLQuery(totalHql.toString());
		
		query.setString("customerId", customer.getCustomerId());
		query.setParameterList("idArray", idArray);
		totalQuery.setString("customerId", customer.getCustomerId());
		totalQuery.setParameterList("idArray", idArray);
		
		Long total = new Long(totalQuery.uniqueResult().toString());
		
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		
		pager.setTotalCount(total);
		pager.setList(query.list());
		return pager;
	}


}
