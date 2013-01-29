package org.linys.dao.impl;

import java.util.Date;
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
import org.linys.bean.Pager;
import org.linys.dao.BuyDAO;
import org.linys.model.Buy;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;
@Repository
public class BuyDAOImpl extends BaseDAOImpl<Buy, String> implements BuyDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#query(org.linys.model.Buy, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<Buy> query(Buy model, Integer page, Integer rows) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			criteria.add(Restrictions.like("buyCode", model.getBuyCode(),MatchMode.ANYWHERE));
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
	 * @see org.linys.dao.BuyDAO#getTotalCount(org.linys.model.Buy)
	 */
	public Long getTotalCount(Buy model) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		
		if(model!=null&&StringUtils.isNotEmpty(model.getBuyCode())){
			criteria.add(Restrictions.like("buyCode", model.getBuyCode(),MatchMode.ANYWHERE));
		}
		criteria.setProjection(Projections.rowCount());
		return new Long(criteria.uniqueResult().toString());
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#queryReceive(java.util.Date, java.util.Date, java.lang.String, java.lang.String[], org.linys.model.Buy, java.lang.Integer, java.lang.Integer)
	 */
	public Pager queryReceive(Pager pager,Date beginDate,Date endDate, String[] idArray, Buy model) {
		StringBuilder sb = new StringBuilder();
		sb.append("from(select * ");
		sb.append("from T_Buy where supplierId = :supplierId ");
		if(StringUtils.isNotEmpty(model.getBuyCode())){
			sb.append("and buyCode like :buyCode ");
		}
		sb.append("and buyDate between :beginDate and :endDate and status = 1 ) a ");
		sb.append("left join T_BuyDetail b on a.buyId = b.buyId ");
		sb.append("where b.qty - b.receiveQty > 0 and b.buyDetailId not in(:idArray) ");

		StringBuilder sql = new StringBuilder("select distinct a.buyId,a.buyCode,a.buyDate,a.sourceCode ").append(sb).append("order by a.buyDate,a.buyCode");
		StringBuilder toltalSql = new StringBuilder("select count(distinct(a.buyId))").append(sb);

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
		
		query.setString("supplierId", model.getSupplier().getSupplierId());
		query.setParameterList("idArray", idArray);
		
		totalQuery.setString("supplierId", model.getSupplier().getSupplierId());
		totalQuery.setParameterList("idArray", idArray);
		
		if(StringUtils.isNotEmpty(model.getBuyCode())){
			query.setString("buyCode","%"+model.getBuyCode()+"%");
			totalQuery.setString("buyCode","%"+model.getBuyCode()+"%");
		}
		query.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		Long total = new Long(totalQuery.uniqueResult().toString());
		
		pager.setList(query.list());
		pager.setTotalCount(total);
		return pager;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#init(java.lang.String)
	 */
	@Override
	public Buy init(String buyId) {
		Criteria criteria  = getCurrentSession().createCriteria(Buy.class);
		criteria.add(Restrictions.eq("buyId", buyId));
		criteria.createAlias("supplier", "supplier",CriteriaSpecification.LEFT_JOIN);
		
		return (Buy) criteria.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDAO#countBuy(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> countBuy(String buyId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * ");
		sb.append("from(select count(1) receiveCount ");
		sb.append("	from t_receivedetail a ");
		sb.append("	left join t_buydetail b on a.buyDetailId = b.buyDetailId ");
		sb.append("	where buyId = :buyId)a ");
		sb.append("left join(select count(1) payCount ");
		sb.append("	from t_paydetail a ");
		sb.append("	where buyId = :buyId)b on 1 =1  ");

		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setString("buyId", buyId);
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
