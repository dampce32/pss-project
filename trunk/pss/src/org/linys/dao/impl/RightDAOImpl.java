package org.linys.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.RightDAO;
import org.linys.model.Right;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

/**
 * @Description: 权限DAO实现
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-10-5
 * @author lys
 * @vesion 1.0
 */
@Repository("rightDAO")
public class RightDAOImpl extends BaseDAOImpl<Right, String> implements RightDAO {
	
	
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#selectRoot()
	 */
	@SuppressWarnings("unchecked")
	public List<Right> selectRoot() {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.add(Restrictions.isNull("parentRight"));
		List<Right> list = criteria.list();
		criteria.addOrder(Order.asc("array"));
		return list;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#query(int, int, org.linys.model.Right)
	 */
	@SuppressWarnings("unchecked")
	public List<Right> query(int page, int rows, Right model) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		if(StringUtils.isEmpty(model.getRightId())){
			criteria.add(Restrictions.isNull("parentRight"));
		}else{
			criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId",model.getRightId()));
		}
		if(StringUtils.isNotEmpty(model.getRightName())){
			criteria.add(Restrictions.like("rightName", model.getRightName(),MatchMode.ANYWHERE));
		} 
		if(page<1){
			page = 1;
		}
		if(rows<1){
			rows = GobelConstants.DEFAULTPAGESIZE;
		}
		
		Integer begin = (page-1)*rows;
		criteria.setFirstResult(begin);
		criteria.setMaxResults(rows);
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#count(org.linys.model.Right)
	 */
	public Long count(Right model) {
		StringBuilder hql = new StringBuilder("select count(*) from Right model where 1 = 1 ");
		if(model.getRightId()!=null){
			hql.append(" and model.parentRight.rightId=:rightId");
		}else{
			hql.append(" and model.parentRight is null");
		}
		if(StringUtils.isNotEmpty(model.getRightName())){
			hql.append(" and model.rightName like :rightName");
		} 
		Query query  = getCurrentSession().createQuery(hql.toString());
		if(model!=null&&model.getRightId()!=null){
			query.setString("rightId", model.getRightId());
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getRightName())){
			query.setString("rightName", "%"+model.getRightName()+"%");
		}
		return (Long) query.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#updateIsLeaf(java.lang.String, boolean)
	 */
	public void updateIsLeaf(String rightId, boolean isLeaf) {
		StringBuilder hql = new StringBuilder("update Right set isLeaf = :isLeaf where rightId = :rightId");
		getCurrentSession().createQuery(hql.toString()).setBoolean("isLeaf", isLeaf).setString("rightId", rightId).executeUpdate();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#countChildren(java.lang.String)
	 */
	public Long countChildren(String rightId) {
		StringBuilder hql = new StringBuilder("select count(*) from Right model where model.parentRight.rightId =:rightId ");
		return (Long) getCurrentSession().createQuery(hql.toString()).setString("rightId", rightId).uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#getChildren(org.linys.model.Right)
	 */
	@SuppressWarnings("unchecked")
	public List<Right> getChildren(Right model) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId", model.getRightId()));
		criteria.addOrder(Order.asc("array"));
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#getParentRight(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	public Right getParentRight(String rightId) {
		StringBuilder hql = new StringBuilder("select model.parentRight from Right model where model.rightId = :rightId");
		List list  = getCurrentSession().createQuery(hql.toString()).setString("rightId", rightId).list();
		if(list.size()==1){
			return (Right) list.get(0);
		}
		return null;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RightDAO#getMaxArray(java.lang.String)
	 */
	@Override
	public Integer getMaxArray(String rightId) {
		Criteria criteria = getCurrentSession().createCriteria(Right.class);
		criteria.createAlias("parentRight", "model").add(Restrictions.eq("model.rightId", rightId));
		criteria.setProjection(Projections.max("array"));
		return criteria.uniqueResult()==null?0:(Integer)criteria.uniqueResult();
	}
}
