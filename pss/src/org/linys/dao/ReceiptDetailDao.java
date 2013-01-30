package org.linys.dao;

import java.util.List;

import org.linys.model.Receipt;
import org.linys.model.ReceiptDetail;

/**
 * 
 * @Description: 收款明细dao
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-28
 * @author longweier
 * @vesion 1.0
 */
public interface ReceiptDetailDao extends BaseDAO<ReceiptDetail, String> {
	
	/**
	 * 
	 * @Description: 查询某个收款单的收款明细
	 * @Create: 2013-1-28 上午09:30:43
	 * @author longweier
	 * @update logs
	 * @param receipt
	 * @return
	 * @throws Exception
	 */
	public List<ReceiptDetail> queryReceiptDetail(Receipt receipt);
}
