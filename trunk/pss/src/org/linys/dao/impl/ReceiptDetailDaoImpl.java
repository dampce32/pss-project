package org.linys.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.linys.dao.ReceiptDetailDao;
import org.linys.model.Receipt;
import org.linys.model.ReceiptDetail;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @Description: 收款明细dao实现类
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-29
 * @author longweier
 * @vesion 1.0
 */
@Repository
public class ReceiptDetailDaoImpl extends BaseDAOImpl<ReceiptDetail, String> implements ReceiptDetailDao {

	@SuppressWarnings("unchecked")
	public List<ReceiptDetail> queryReceiptDetail(Receipt receipt) {
		Assert.notNull(receipt, "receipt is required");
		Criteria criteria = getCurrentSession().createCriteria(ReceiptDetail.class);
		
		criteria.createAlias("sale", "sale", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("deliver", "deliver", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("deliverReject", "deliverReject", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("preReceipt", "preReceipt", CriteriaSpecification.LEFT_JOIN);
		
		criteria.add(Restrictions.eq("receipt", receipt));
		
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}}
