package org.linys.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.linys.dao.ReceiveDAO;
import org.linys.model.Receive;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class ReceiveDAOImpl extends BaseDAOImpl<Receive, String> implements
		ReceiveDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#query(org.linys.model.Receive, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Receive> query(String kind,Receive model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Receive.class);
		
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getReceiveCode())){
			criteria.add(Restrictions.like("receiveCode", model.getReceiveCode(),MatchMode.ANYWHERE));
		}
		if("other".equals(kind)){
			criteria.add(Restrictions.isNull("supplier"));
		}else{
			criteria.add(Restrictions.isNotNull("supplier"));
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
		
		criteria.addOrder(Order.desc("receiveDate"));
		criteria.addOrder(Order.desc("receiveCode"));
		
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#getTotalCount(org.linys.model.Receive)
	 */
	@Override
	public Long getTotalCount(String kind,Receive model) {
		Criteria criteria  = getCurrentSession().createCriteria(Receive.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getReceiveCode())){
			criteria.add(Restrictions.like("receiveCode", model.getReceiveCode(),MatchMode.ANYWHERE));
		}
		if("other".equals(kind)){
			criteria.add(Restrictions.isNull("supplier"));
		}else{
			criteria.add(Restrictions.isNotNull("supplier"));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#newCode(java.lang.String)
	 */
	@Override
	public String getMaxCode(String receiveCode) {
		Criteria criteria  = getCurrentSession().createCriteria(Receive.class);
		
		criteria.add(Restrictions.like("receiveCode", receiveCode,MatchMode.START));
		criteria.setProjection(Projections.max("receiveCode"));
		return criteria.uniqueResult()==null?null:criteria.uniqueResult().toString();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#querySelectBuyDetail(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> querySelectBuyDetail(String[] idArray, String[] idArray2) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.buyId,b.buyCode,a.buyDetailId,a.productId,c.productCode,c.productName, ");
		sb.append("d.dataDictionaryName unitName,e.dataDictionaryName sizeName,a.colorId,f.dataDictionaryName colorName, ");
		sb.append("	a.qty-a.receiveQty qty,a.price ,a.note1,a.note2,a.note3 ");
		sb.append("from T_BuyDetail a ");
		sb.append("left join T_Buy b on a.buyId = b.buyId ");
		sb.append("left join T_Product c on a.productId = c.productId ");
		sb.append("left join T_DataDictionary d on c.unitId  = d.dataDictionaryId ");
		sb.append("left join T_DataDictionary e on c.sizeId = e.dataDictionaryId ");
		sb.append("left join T_DataDictionary f on c.colorId = f.dataDictionaryId ");
		sb.append("where a.buyId in (:idArray) ");
		sb.append("and a.buyDetailId not in(:idArray2) and a.qty - a.receiveQty >  0  ");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setParameterList("idArray", idArray);
		query.setParameterList("idArray2",idArray2);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> queryNeedPay(Date beginDateDate,
			Date endDateDate, String supplierId, String[] idArray,
			Receive model, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.receiveId,a.receiveCode,a.receiveDate,a.amount,a.payAmount+IFNULL(b.payAmount,0) payedAmount, ");
		sb.append("a.discountAmount+IFNULL(b.discountAmount,0) discountedAmount,a.amount-a.payAmount-IFNULL(b.payAmount,0)-a.discountAmount-IFNULL(b.discountAmount,0) needPayAmount ");
		sb.append("from t_receive a ");
		sb.append("left join(select a.receiveId,sum(b.payAmount) payAmount,sum(b.discountAmount ) discountAmount ");
		sb.append("	from(select * ");
		sb.append("		from t_receive a ");
		sb.append("     where a.supplierId=:supplierId and a.`status` = 1 and a.isPay = 0  ");
		sb.append("		and a.receiveDate BETWEEN :beginDateDate and :endDateDate ");
		sb.append(") a	left join t_paydetail b on a.receiveId = b.receiveId ");
		sb.append("	left join t_pay c on b.payId = c.payId ");
		sb.append("	where c.status = 1  ");
		sb.append("	GROUP BY a.receiveId)b on a.receiveId = b.receiveId ");
		sb.append("where a.supplierId=:supplierId and a.`status` = 1 and a.isPay = 0   ");
		sb.append("and a.receiveDate BETWEEN :beginDateDate and :endDateDate ");
		sb.append(" and a.receiveId not in(:idArray) ");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setDate("beginDateDate", beginDateDate);
		query.setDate("endDateDate", endDateDate);
		query.setString("supplierId", supplierId);
		query.setParameterList("idArray", idArray);
		
		if(page==null||page<1){
			page = 1;
		}
		if(rows==null||rows<0){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		
		query.setFirstResult(begin);
		query.setMaxResults(rows);
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#getTotalReceive(java.util.Date, java.util.Date, java.lang.String, java.lang.String[], org.linys.model.Receive)
	 */
	@Override
	public Long getTotalReceive(Date beginDateDate, Date endDateDate,
			String supplierId, String[] idArray, Receive model) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(a.receiveId) ");
		sb.append("from t_receive a ");
		sb.append("left join(select a.receiveId,sum(b.payAmount) payAmount,sum(b.discountAmount ) discountAmount ");
		sb.append("	from(select * ");
		sb.append("		from t_receive a ");
		sb.append("     where a.supplierId=:supplierId and a.`status` = 1 and a.isPay = 0  ");
		sb.append("		and a.receiveDate BETWEEN :beginDateDate and :endDateDate ");
		sb.append(") a	left join t_paydetail b on a.receiveId = b.receiveId ");
		sb.append("	left join t_pay c on b.payId = c.payId ");
		sb.append("where c.status = 1 ");
		sb.append("	GROUP BY a.receiveId)b on a.receiveId = b.receiveId ");
		sb.append("where a.supplierId=:supplierId and a.`status` = 1 and a.isPay = 0   ");
		sb.append("and a.receiveDate BETWEEN :beginDateDate and :endDateDate   and a.receiveId not in(:idArray) ");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setDate("beginDateDate", beginDateDate);
		query.setDate("endDateDate", endDateDate);
		query.setString("supplierId", supplierId);
		query.setParameterList("idArray", idArray);
		return new Long(query.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#getNeedPayAmount(java.lang.String)
	 */
	@Override
	public Double getNeedPayAmount(String receiveId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.amount-a.payAmount-a.discountAmount-IFNULL(b.discountAmount,0) -IFNULL(b.payAmount,0) needPayAmount ");
		sb.append("from(select a.receiveId,a.amount,a.payAmount,a.discountAmount ");
		sb.append("	from t_receive a  ");
		sb.append("	where a.receiveId = :receiveId)a ");
		sb.append("left join(select a.receiveId,sum(a.discountAmount) discountAmount ,sum(a.payAmount) payAmount ");
		sb.append("	from t_paydetail a ");
		sb.append("	left join t_pay b on a.payId = b.payId ");
		sb.append("	where b.`status` = 1 and  a.receiveId = :receiveId ");
		sb.append("	group by a.receiveId)b on a.receiveId = b.receiveId ");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setString("receiveId", receiveId);
		return new Double(query.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDAO#countReceive(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> countReceive(String receiveId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(a.receiveId) countPay ");
		sb.append("from t_paydetail a  ");
		sb.append("where a.receiveId = :receiveId ");

		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setString("receiveId", receiveId);
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}
