package org.linys.dao;

import java.util.List;

import org.linys.model.DeliverReject;
import org.linys.model.DeliverRejectDetail;

/**
 * 
 * @Description:  销售退货明细dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
public interface DeliverRejectDetailDao extends BaseDAO<DeliverRejectDetail, String> {
	
	/**
	 * 
	 * @Description: 查询销售退货单明细
	 * @Create: 2013-1-26 上午12:35:02
	 * @author longweier
	 * @update logs
	 * @param deliverReject
	 * @return
	 * @throws Exception
	 */
	public List<DeliverRejectDetail> queryDeliverRejectDetail(DeliverReject deliverReject);
}
