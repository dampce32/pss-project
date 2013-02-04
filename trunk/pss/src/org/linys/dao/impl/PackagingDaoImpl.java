package org.linys.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.linys.bean.Pager;
import org.linys.dao.PackagingDao;
import org.linys.model.Packaging;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Description:
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class PackagingDaoImpl extends BaseDAOImpl<Packaging, String> implements PackagingDao {

	public Pager queryPackaging(Pager pager, Packaging packaging,Date beginDate, Date endDate) {
		Criteria criteria = getCurrentSession().createCriteria(Packaging.class);
		
		criteria.createAlias("product", "product", CriteriaSpecification.LEFT_JOIN);
		
		if(StringUtils.isNotEmpty(packaging.getPackagingCode())){
			criteria.add(Restrictions.like("packagingCode", packaging.getPackagingCode(), MatchMode.ANYWHERE));
		}
		if(packaging.getProduct()!=null && StringUtils.isNotEmpty(packaging.getProduct().getProductName())){
			criteria.add(Restrictions.like("product.productName", packaging.getProduct().getProductName(),MatchMode.ANYWHERE));
		}
		if(packaging.getStatus()!=null && packaging.getStatus()>=0){
			criteria.add(Restrictions.eq("status", packaging.getStatus()));
		}
		if(beginDate!=null){
			criteria.add(Restrictions.ge("packagingDate", beginDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("packagingDate", endDate));
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
