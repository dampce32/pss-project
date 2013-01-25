package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.DeliverReject;

/**
 * 
 * 
 * @Description: 销售退货dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author longweier
 * @vesion 1.0
 */
public interface DeliverRejectDao extends BaseDAO<DeliverReject, String> {

	/**
	 * 
	 * @Description: 查询销售退货单
	 * @Create: 2013-1-25 下午10:57:28
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param deliverReject
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager queryDeliverReject(Pager pager,DeliverReject deliverReject,Date beginDate,Date endDate);
}
