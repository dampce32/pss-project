package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.PayDetailDAO;
import org.linys.model.PayDetail;
import org.springframework.stereotype.Repository;
/**
 * @Description:付款明细DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
@Repository
public class PayDetailDAOImpl extends BaseDAOImpl<PayDetail,String> implements PayDetailDAO{
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.PayDetailDAO#queryByPayId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PayDetail> queryByPayId(String payId) {
		Criteria criteria  = getCurrentSession().createCriteria(PayDetail.class);
		criteria.createAlias("receive", "receive", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("pay.payId", payId));
		return criteria.list();
	}

}
