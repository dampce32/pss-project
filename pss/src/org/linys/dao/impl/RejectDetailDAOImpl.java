package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.RejectDetailDAO;
import org.linys.model.RejectDetail;
import org.springframework.stereotype.Repository;
@Repository
public class RejectDetailDAOImpl extends BaseDAOImpl<RejectDetail, String>
		implements RejectDetailDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.RejectDetailDAO#queryByRejectId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<RejectDetail> queryByRejectId(String rejectId) {
		Criteria criteria  = getCurrentSession().createCriteria(RejectDetail.class);
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("reject", "reject", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("reject.rejectId", rejectId));
		return criteria.list();
	}

}
