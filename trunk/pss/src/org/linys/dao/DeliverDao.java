package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.Deliver;

/**
 * 
 * @Description: 出库单dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
public interface DeliverDao extends BaseDAO<Deliver, String> {
	
	/**
	 * 
	 * @Description: 查询出库单
	 * @Create: 2013-1-24 上午10:56:50
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param deliver
	 * @param beginDate
	 * @param endDate
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public Pager queryDeliver(Pager pager,Deliver deliver,Date beginDate,Date endDate,String type);
}
