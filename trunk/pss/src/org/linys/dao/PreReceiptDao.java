package org.linys.dao;

import java.util.Date;

import org.linys.bean.Pager;
import org.linys.model.PreReceipt;

/**
 * 
 * @Description: 预收dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
public interface PreReceiptDao extends BaseDAO<PreReceipt, String> {
	
	/**
	 * 
	 * @Description:查询 
	 * @Create: 2013-1-26 下午09:03:17
	 * @author longweier
	 * @update logs
	 * @param pager
	 * @param preReceipt
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Pager queryPreReceipt(Pager pager,PreReceipt preReceipt,Date beginDate,Date endDate);
}
