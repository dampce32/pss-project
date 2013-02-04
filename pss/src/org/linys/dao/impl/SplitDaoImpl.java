package org.linys.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.bean.Pager;
import org.linys.dao.SplitDao;
import org.linys.model.Split;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description: 拆分dao的实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class SplitDaoImpl extends BaseDAOImpl<Split, String> implements	SplitDao {

	public Pager querySplit(Pager pager, Split split, Date beginDate,Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(Split.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		
		if(StringUtils.isNotEmpty(split.getSplitCode())){
			criteria.add(Restrictions.like("splitCode", split.getSplitCode(), MatchMode.ANYWHERE));
		}
		if(split.getProduct()!=null && StringUtils.isNotEmpty(split.getProduct().getProductName())){
			criteria.add(Restrictions.like("product.productName", split.getProduct().getProductName(),MatchMode.ANYWHERE));
		}
		if(split.getStatus()!=null && split.getStatus()>=0){
			criteria.add(Restrictions.eq("status", split.getStatus()));
		}
		if(beginDate!=null){
			criteria.add(Restrictions.ge("splitDate", beginDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("splitDate", endDate));
		}
		Long total = new Long(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult(pager.getBeginCount()).setMaxResults(pager.getPageSize());
		
		pager.setTotalCount(total);
		pager.setList(criteria.list());
		
		return pager;
	}

}
