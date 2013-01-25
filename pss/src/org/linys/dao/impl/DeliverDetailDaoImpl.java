package org.linys.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> querySelectSaleDetail(String[] idArray,String[] idArray2) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.saleId,b.saleCode,a.saleDetailId,a.productId,c.productCode,c.productName, ");
		sb.append("d.dataDictionaryName unitName,e.dataDictionaryName sizeName,a.colorId,f.dataDictionaryName colorName, ");
		sb.append("a.qty-a.hadSaleQty qty,a.price ,a.note1,a.note2,a.note3 ");
		sb.append("from T_SaleDetail a ");
		sb.append("left join T_Sale b on a.saleId = b.saleId ");
		sb.append("left join T_Product c on a.productId = c.productId ");
		sb.append("left join T_DataDictionary d on c.unitId  = d.dataDictionaryId ");
		sb.append("left join T_DataDictionary e on c.sizeId = e.dataDictionaryId ");
		sb.append("left join T_DataDictionary f on c.colorId = f.dataDictionaryId ");
		sb.append("where a.saleId in (:idArray) ");
		sb.append("and a.saleDetailId not in(:idArray2) and a.qty - a.hadSaleQty >  0  ");
		
		Query query = getCurrentSession().createSQLQuery(sb.toString());
		query.setParameterList("idArray", idArray);
		query.setParameterList("idArray2",idArray2);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}
