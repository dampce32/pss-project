package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.SaleDetailDao;
import org.linys.model.Sale;
import org.linys.model.SaleDetail;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @Description: 订单明细dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class SaleDetailDaoImpl extends BaseDAOImpl<SaleDetail, String> implements SaleDetailDao {

	@SuppressWarnings("unchecked")
	public List<SaleDetail> querySaleDetail(Sale sale) {
		Assert.notNull(sale, "sale is required");
		Criteria criteria = getCurrentSession().createCriteria(SaleDetail.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("color", "color", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("sale", sale));
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}

}
