package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.SplitDetailDao;
import org.linys.model.Split;
import org.linys.model.SplitDetail;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description: 拆分明细dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class SplitDetailDaoImpl extends BaseDAOImpl<SplitDetail, String> implements SplitDetailDao {

	@SuppressWarnings("unchecked")
	public List<SplitDetail> querySplitDetail(Split split) {
		
		Criteria criteria = getCurrentSession().createCriteria(SplitDetail.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("split", split));
		
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		
		return criteria.list();
	}

}
