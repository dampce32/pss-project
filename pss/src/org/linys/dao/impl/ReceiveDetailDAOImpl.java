package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ReceiveDetailDAO;
import org.linys.model.ReceiveDetail;
import org.springframework.stereotype.Repository;
@Repository
public class ReceiveDetailDAOImpl extends BaseDAOImpl<ReceiveDetail, String>
		implements ReceiveDetailDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.ReceiveDetailDAO#queryByReceiveId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiveDetail> queryByReceiveId(String receiveId) {
		Criteria criteria  = getCurrentSession().createCriteria(ReceiveDetail.class);
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("buyDetail", "buyDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("buyDetail.buy", "buy", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("receive.receiveId", receiveId));
		return criteria.list();
	}

}
