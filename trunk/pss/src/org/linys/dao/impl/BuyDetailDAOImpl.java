package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.BuyDetailDAO;
import org.linys.model.BuyDetail;
import org.springframework.stereotype.Repository;
@Repository
public class BuyDetailDAOImpl extends BaseDAOImpl<BuyDetail, String>
		implements BuyDetailDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.BuyDetailDAO#queryByBuyId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BuyDetail> queryByBuyId(String buyId) {
		Criteria criteria  = getCurrentSession().createCriteria(BuyDetail.class);
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.unit", "unit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("product.size", "size", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("color", "color", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("buy.buyId", buyId));
		return criteria.list();
	}

}
