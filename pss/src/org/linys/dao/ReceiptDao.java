package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.Customer;
import org.linys.model.Receipt;

/**
 * 
 * @Description: 收款dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-28
 * @author longweier
 * @vesion 1.0
 */
public interface ReceiptDao extends BaseDAO<Receipt, String> {
	
	/**
	 * 
	 * @Description: 查询收款单
	 * @Create: 2013-1-28 上午09:27:33
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param receipt
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager queryReceipt(Pager pager,Receipt receipt,Date beginDate,Date endDate);
	
	/**
	 * 
	 * @Description: 查询需要添加的付款单
	 * @Create: 2013-1-28 上午09:29:06
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param customer
	 * @param idArray
	 * @return
	 * @throws Exception
	 */
	public Pager queryNeedCheck(Pager pager,Customer customer,String[] idArray);
}
