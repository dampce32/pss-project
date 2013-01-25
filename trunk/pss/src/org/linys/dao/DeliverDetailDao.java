package org.linys.dao;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 
	 * @Description: 将选中的订单插入出库明细
	 * @Create: 2013-1-25 下午03:22:44
	 * @author longweier
	 * @update logs
	 * @param idArray
	 * @param idArray2
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> querySelectSaleDetail(String[] idArray,String[] idArray2);
}
