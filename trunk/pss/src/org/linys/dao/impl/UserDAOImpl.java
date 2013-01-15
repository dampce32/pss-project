package org.linys.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.linys.dao.UserDAO;
import org.linys.model.User;
import org.linys.vo.GobelConstants;
import org.springframework.stereotype.Repository;

@Repository("userDAO")
public class UserDAOImpl  extends BaseDAOImpl<User,String> implements UserDAO{

	public User login(User user) {
		if(user!=null){
			String[] paramNams = {"userCode","userPwd"};
			Object[] values ={user.getUserCode(),user.getUserPwd()};
			return load(paramNams, values);
		}
		return null;
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.UserDAO#query(int, int, org.linys.model.User)
	 */
	@SuppressWarnings("unchecked")
	public List<User> query(int page, int rows, User model) {
		Criteria criteria = getCurrentSession().createCriteria(User.class);
		if(model!=null&&StringUtils.isNotEmpty(model.getUserCode())){
			criteria.add(Restrictions.like("userCode",model.getUserCode(),MatchMode.ANYWHERE));
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getUserName())){
			criteria.add(Restrictions.like("userName",model.getUserName(),MatchMode.ANYWHERE));
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
		return criteria.list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.UserDAO#count(org.linys.model.User)
	 */
	public Long count(User model) {
		StringBuilder hql = new StringBuilder("select count(*) from User model where 1 = 1 ");
		if(model!=null&&StringUtils.isNotEmpty(model.getUserCode())){
			hql.append(" and model.userCode like :userCode");
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getUserName())){
			hql.append(" and model.userName like :userName");
		}
		Query query  = getCurrentSession().createQuery(hql.toString());
		if(model!=null&&StringUtils.isNotEmpty(model.getUserCode())){
			query.setString("userCode", "%"+model.getUserCode()+"%");
		}
		if(model!=null&&StringUtils.isNotEmpty(model.getUserName())){
			query.setString("userName", "%"+model.getUserName()+"%");
		}
		return (Long) query.uniqueResult();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.UserDAO#getRooRight(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getRooRight(String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select b.rightId,MAX(b.State) state,c.isLeaf,c.rightName,c.rightUrl ");
		sql.append( "from(select RoleId from T_UserRole ");
		sql.append( "where userId = :userId) a ");
		sql.append( "left join T_RoleRight b on a.RoleId = b.RoleID ");
		sql.append( "left join T_Right c on b.RightId = c.RightId ");
		sql.append( "where c.ParentRightId is null ");
		sql.append( "group by  b.RightID,c.IsLeaf,c.RightName,c.RightURL");
		return getCurrentSession().createSQLQuery(sql.toString()).setString("userId", userId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.UserDAO#getChildrenRight(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getChildrenRight(String userId,String rightId) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select b.rightId,MAX(b.State) state,c.isLeaf,c.rightName,c.rightUrl ");
		sql.append( "from(select RoleId from T_UserRole ");
		sql.append( "where userId = :userId) a ");
		sql.append( "left join T_RoleRight b on a.RoleId = b.RoleID ");
		sql.append( "left join T_Right c on b.RightId = c.RightId ");
		sql.append( "where c.ParentRightId = :rightId ");
		sql.append( "group by  b.RightID,c.IsLeaf,c.RightName,c.RightURL ");
		sql.append( "having max(b.State) = 1 ");
		return getCurrentSession().createSQLQuery(sql.toString()).setString("userId", userId).setString("rightId", rightId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.UserDAO#queryUserRight(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryUserRight(String userId) {
		StringBuilder sql = new StringBuilder();
		sql.append( "select b.RightID,MAX(b.State) State ");
		sql.append( "from(select roleId from T_UserRole ");
		sql.append( "where userId = :userId) a ");
		sql.append( "left join T_RoleRight b on a.roleId = b.RoleID ");
		sql.append( "group by b.RightID");
		return getCurrentSession().createSQLQuery(sql.toString()).setString("userId", userId).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	
	}
}
