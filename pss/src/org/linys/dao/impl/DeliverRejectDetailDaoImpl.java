package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.DeliverRejectDetailDao;
import org.linys.model.DeliverReject;
import org.linys.model.DeliverRejectDetail;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * 
 * @Description:  销售退货单dao实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class DeliverRejectDetailDaoImpl extends BaseDAOImpl<DeliverRejectDetail, String> implements DeliverRejectDetailDao {

	@SuppressWarnings("unchecked")
	public List<DeliverRejectDetail> queryDeliverRejectDetail(DeliverReject deliverReject) {
		Assert.notNull(deliverReject, "deliverReject is required");
		Criteria criteria = getCurrentSession().createCriteria(DeliverRejectDetail.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("deliverReject", deliverReject));
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		
		return criteria.list();
	}

}
