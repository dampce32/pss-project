package org.linys.dao;

import java.util.List;

import org.linys.model.Deliver;
import org.linys.model.DeliverDetail;

/**
 * 
 * @Description: 出库明细dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
public interface DeliverDetailDao extends BaseDAO<DeliverDetail, String> {
	
	/**
	 * 
	 * @Description: 查询某个出库单的出库明细
	 * @Create: 2013-1-24 上午11:27:24
	 * @author longweier
	 * @update logs
	 * @param deliver
	 * @return
	 * @throws Exception
	 */
	public List<DeliverDetail> queryDeliverDetail(Deliver deliver);
}
