package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.DefaultPackagingDAO;
import org.linys.model.DefaultPackaging;
import org.springframework.stereotype.Repository;
/**
 * @Description:默认商品组装DAO实现类
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author lys
 * @vesion 1.0
 */
@Repository
public class DefaultPackagingDAOImpl extends
		BaseDAOImpl<DefaultPackaging, String> implements DefaultPackagingDAO {
	/*
	 * (non-Javadoc)   
	 * @see org.linys.dao.DefaultPackagingDAO#queryByProductId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultPackaging> queryByProductId(String productId) {
		Criteria criteria  = getCurrentSession().createCriteria(DefaultPackaging.class);
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("parentProduct.productId", productId));
		return criteria.list();
	}
}
