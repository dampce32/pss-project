package org.linys.dao;

import java.util.List;

import org.linys.model.Sale;
import org.linys.model.SaleDetail;

/**
 * 
 * @Description: 订单明细dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
public interface SaleDetailDao extends BaseDAO<SaleDetail, String> {
	
	/**
	 * 
	 * 
	 * @Description: 查询某个订单先的订单明细
	 * @Create: 2013-1-22 下午11:31:11
	 * @author longweier
	 * @update logs
	 * @param sale
	 * @return
	 * @throws Exception
	 */
	public List<SaleDetail> querySaleDetail(Sale sale);
}
