package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.Sale;

/**
 * 
 * @Description: 订单dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
public interface SaleDao extends BaseDAO<Sale, String> {
	
	/**
	 * 
	 * @Description: 查询订单
	 * @Create: 2013-1-22 下午05:20:06
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param sale
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager querySale(Pager pager,Sale sale,Date beginDate,Date endDate);
	
	/**
	 * 
	 * @Description: 根据订单ID获取订单
	 * @Create: 2013-1-22 下午11:27:06
	 * @author longweier
	 * @update logs
	 * @param saleId
	 * @return
	 * @throws Exception
	 */
	public Sale getSale(String saleId);
	
	/**
	 * 
	 * @Description: 查询某个客户还未出库的订单
	 * @Create: 2013-1-25 上午11:03:11
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param sale
	 * @param idArray
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager queryDeliver(Pager pager,Sale sale,String[] idArray,Date beginDate,Date endDate);
}
