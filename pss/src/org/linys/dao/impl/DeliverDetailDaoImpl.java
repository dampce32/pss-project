package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.DeliverDetailDao;
import org.linys.model.Deliver;
import org.linys.model.DeliverDetail;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @Description: 出库明细dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class DeliverDetailDaoImpl extends BaseDAOImpl<DeliverDetail, String> implements DeliverDetailDao {

	@SuppressWarnings("unchecked")
	public List<DeliverDetail> queryDeliverDetail(Deliver deliver) {
		Assert.notNull(deliver, "sale is required");
		Criteria criteria = getCurrentSession().createCriteria(DeliverDetail.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("saleDetail", "saleDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("saleDetail.sale", "sale", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("deliver", deliver));
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}

}
