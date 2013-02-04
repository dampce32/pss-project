package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.PackagingDetailDao;
import org.linys.model.Packaging;
import org.linys.model.PackagingDetail;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @Description: 组装明细dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class PackagingDetailDaoImpl extends BaseDAOImpl<PackagingDetail, String> implements PackagingDetailDao {

	@SuppressWarnings("unchecked")
	public List<PackagingDetail> queryByPackaging(Packaging packaging) {
		Assert.notNull(packaging,"packaging is required");
		Criteria criteria = getCurrentSession().createCriteria(PackagingDetail.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("packaging", packaging));
		
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		
		return criteria.list();
	}

}
