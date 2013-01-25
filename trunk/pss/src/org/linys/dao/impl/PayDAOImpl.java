package org.linys.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.linys.dao.PayDAO;
import org.linys.model.Pay;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
/**
 * @Description:付款单DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Repository
public class PayDAOImpl extends BaseDAOImpl<Pay, String> implements PayDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PayDAO#query(org.linys.model.Pay, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Pay> query(Pay model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Pay.class);
		
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("employee", "employee",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getPayCode())){
			criteria.add(Restrictions.like("payCode", model.getPayCode(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.PayDAO#getTotalCount(org.linys.model.Pay)
	 */
	@Override
	public Long getTotalCount(Pay model) {
		Criteria criteria  = getCurrentSession().createCriteria(Pay.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getPayCode())){
			criteria.add(Restrictions.like("payCode", model.getPayCode(),MatchMode.ANYWHERE));
		}
		
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PayDAO#init(java.lang.String)
	 */
	@Override
	public Pay init(String payId) {
		Criteria criteria  = getCurrentSession().createCriteria(Pay.class);
		
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("employee", "employee",CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bank", "bank",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("payId", payId));
		return criteria.uniqueResult()==null?null:(Pay)criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PayDAO#queryNeedCheck(java.lang.String, java.lang.String[], java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryNeedCheck(String supplierId,
			String[] idArray, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select * ");
        sb.append("from(select a.receiveId sourceId,a.receiveCode sourceCode,'采购入库' payKind,a.receiveDate sourceDate,a.amount,a.discountAmount+a.payAmount+a.checkAmount payedAmount,a.amount-a.discountAmount-a.payAmount-a.checkAmount needPayAmount ");
        sb.append("	from t_receive a ");
        sb.append("	where a.`status`  = 1 and a.isPay = 0 and a.supplierId =:supplierId ");
        sb.append("	union all ");
        sb.append("	select a.buyId,a.buyCode,'采购单预付款',a.buyDate,-a.payAmount,0-a.checkAmount,-a.payAmount+a.checkAmount ");
        sb.append("	from t_buy a ");
        sb.append("	where a.`status`  = 1 and a.supplierId = :supplierId and a.payAmount-a.checkAmount>0 ");
        sb.append("	union all  ");
        sb.append("	select a.rejectId,a.rejectCode,'退货单',a.rejectDate,-a.amount,0-a.checkAmount-a.payAmount,-a.amount+a.payAmount + a.checkAmount ");
        sb.append("	from t_reject a ");
		sb.append("	where a.`status`  = 1 and a.supplierId = :supplierId and a.amount-a.payAmount - a.checkAmount>0 ");
		sb.append("	union ALL ");
		sb.append("	select a.prepayId,a.prepayCode,'预付单预付款',a.prepayDate,-a.amount,0-a.checkAmount,-a.amount+a.checkAmount ");
		sb.append("	from t_prepay a ");
		sb.append("	where a.`status` = 1 and a.supplierId = :supplierId and a.amount - a.checkAmount >0)a ");
		sb.append("where a.sourceId not in (:idArray) ");
		
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setString("supplierId", supplierId);
		query.setParameterList("idArray", idArray);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	@Override
	public Long getTotalCountNeedCheck(String supplierId, String[] idArray) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("select count(sourceId) ");
        sb.append("from(select a.receiveId sourceId,a.receiveCode sourceCode,'采购入库' payKind,a.receiveDate sourceDate,a.amount,a.discountAmount+a.payAmount+a.checkAmount payAmount,a.amount-a.discountAmount-a.payAmount-a.checkAmount needPay ");
        sb.append("	from t_receive a ");
        sb.append("	where a.`status`  = 1 and a.isPay = 0 and a.supplierId =:supplierId ");
        sb.append("	union all ");
        sb.append("	select a.buyId,a.buyCode,'采购单预付款',a.buyDate,-a.payAmount,-a.checkAmount,-a.payAmount+a.checkAmount ");
        sb.append("	from t_buy a ");
        sb.append("	where a.`status`  = 1 and a.supplierId = :supplierId and a.payAmount-a.checkAmount>0 ");
        sb.append("	union all  ");
        sb.append("	select a.rejectId,a.rejectCode,'退货单',a.rejectDate,-a.amount,-a.checkAmount-a.payAmount,-a.amount+a.payAmount+a.checkAmount ");
        sb.append("	from t_reject a ");
		sb.append("	where a.`status`  = 1 and a.supplierId = :supplierId and a.amount-a.payAmount - a.checkAmount>0 ");
		sb.append("	union ALL ");
		sb.append("	select a.prepayId,a.prepayCode,'预付单预付款',a.prepayDate,-a.amount,-a.checkAmount,-a.amount+a.checkAmount ");
		sb.append("	from t_prepay a ");
		sb.append("	where a.`status` = 1 and a.supplierId = :supplierId and a.amount - a.checkAmount >0)a ");
		sb.append("where a.sourceId not in (:idArray) ");
		
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setString("supplierId", supplierId);
		query.setParameterList("idArray", idArray);
		return new Long(query.uniqueResult().toString());
	}

}
