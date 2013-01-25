package org.linys.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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

	public Sale getSale(String saleId) {
		Assert.notNull(saleId, "saleId is required");
		Criteria criteria = getCurrentSession().createCriteria(Sale.class);
		
		criteria.createAlias("employee", "employee", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("bank", "bank", CriteriaSpecification.LEFT_JOIN);

		criteria.add(Restrictions.eq("saleId", saleId));
		
		return (Sale) criteria.uniqueResult();
	}

	public Pager queryDeliver(Pager pager, Sale sale, String[] idArray,Date beginDate, Date endDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("from(select * ");
		sb.append("from T_Sale where customerId = :customerId ");
		if(StringUtils.isNotEmpty(sale.getSaleCode())){
			sb.append("and saleCode like :saleCode ");
		}
		sb.append("and saleDate between :beginDate and :endDate and status = 1 ) a ");
		sb.append("left join T_SaleDetail b on a.saleId = b.saleId ");
		sb.append("where b.qty - b.hadSaleQty > 0 and b.saleDetailId not in(:idArray) ");

		StringBuilder sql = new StringBuilder("select distinct a.saleId,a.saleCode,a.sourceCode,a.saleDate,a.deliverDate,a.note ").append(sb).append("order by a.saleDate,a.saleCode");
		StringBuilder toltalSql = new StringBuilder("select count(distinct(a.saleId))").append(sb);

		Query query = getCurrentSession().createSQLQuery(sql.toString());
		Query totalQuery = getCurrentSession().createSQLQuery(toltalSql.toString());
		//设置开始时间
		if(beginDate==null){
			query.setDate("beginDate", new Date(0L));
			totalQuery.setDate("beginDate", new Date(0L));
		}else{
			query.setDate("beginDate", beginDate);
			totalQuery.setDate("beginDate", beginDate);
		}
		//设置终止时间
		if(endDate==null){
			query.setDate("endDate", new Date());
			totalQuery.setDate("endDate", new Date());
		}else{
			query.setDate("endDate", endDate);
			totalQuery.setDate("endDate", endDate);
		}
		
		query.setString("customerId", sale.getCustomer().getCustomerId());
		query.setParameterList("idArray", idArray);
		
		totalQuery.setString("customerId", sale.getCustomer().getCustomerId());
		totalQuery.setParameterList("idArray", idArray);
		
		if(StringUtils.isNotEmpty(sale.getSaleCode())){
			query.setString("saleCode","%"+sale.getSaleCode()+"%");
			totalQuery.setString("saleCode","%"+sale.getSaleCode()+"%");
		}
		query.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		Long total = new Long(totalQuery.uniqueResult().toString());
		
		pager.setList(query.list());
		pager.setTotalCount(total);
		return pager;
	}
}
