package org.linys.service;

import java.util.Date;

import org.linys.model.Receipt;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description:收款service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-28
 * @author longweier
 * @vesion 1.0
 */
public interface ReceiptService extends BaseService<Receipt, String> {
	
	/**
	 * 
	 * @Description: 新增收款单
	 * @Create: 2013-1-28 下午03:34:45
	 * @author longweier
	 * @update logs
	 * @param receipt			   收款主信息
	 * @param receiptDetailIdIds 收款明细ID
	 * @param sourceIds			  收款来源ID
	 * @param sourceCodes		  收款来源编号
	 * @param sourceDates		  收款来源日期
	 * @param receiptKinds		 收款种类
	 * @param amounts   		 应收金额
	 * @param receiptedAmounts  已收金额
	 * @param discountAmounts   打折金额
	 * @param receiptAmounts    实收金额
	 * @return
	 * @throws Exception
	 */
	public ServiceResult addReceipt(Receipt receipt,String sourceIds, String sourceCodes,String sourceDates, String receiptKinds,
			String amounts, String receiptedAmounts, String discountAmounts,String receiptAmounts);
	
	/**
	 * 
	 * @Description:  更新
	 * @Create: 2013-1-28 下午03:38:26
	 * @author longweier
	 * @update logs
	 * @param receipt
	 * @param receiptDetailIdIds
	 * @param delreceiptDetailIdIds
	 * @param sourceIds
	 * @param sourceCodes
	 * @param sourceDates
	 * @param receiptKinds
	 * @param amounts
	 * @param receiptedAmounts
	 * @param discountAmounts
	 * @param receiptAmounts
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateReceipt(Receipt receipt,String receiptDetailIdIds,String delreceiptDetailIdIds,String sourceIds, String sourceCodes,
			String sourceDates, String receiptKinds,String amounts, String receiptedAmounts, String discountAmounts,String receiptAmounts);

	/**
	 * 
	 * @Description: 删除收款单
	 * @Create: 2013-1-28 下午03:39:32
	 * @author longweier
	 * @update logs
	 * @param receiptId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult deleteReceipt(String receiptId);
	
	/**
	 * 
	 * @Description: 批量删除收款单
	 * @Create: 2013-1-28 下午03:40:22
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDeleteReceipt(String ids);
	
	/**
	 * 
	 * @Description: 修改收款单状态
	 * @Create: 2013-1-28 下午03:41:06
	 * @author longweier
	 * @update logs
	 * @param receipt
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatusReceipt(Receipt receipt);
	
	/**
	 * 
	 * @Description: 批量修改收款单状态
	 * @Create: 2013-1-28 下午03:51:51
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulUpdateStatusReceipt(String ids,Integer status);
	
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-1-28 下午04:50:29
	 * @author longweier
	 * @update logs
	 * @param receiptId
	 * @return
	 * @throws Exception
	 */
	public String initReceipt(String receiptId);
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-28 下午04:52:01
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param receipt
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String queryReceipt(Integer pageNumber,Integer pageSize,Receipt receipt,Date beginDate,Date endDate);
	
	/**
	 * 
	 * @Description: 查询需要添加的单子
	 * @Create: 2013-1-29 上午10:48:28
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param receipt
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public String queryNeedReceipt(Integer pageNumber,Integer pageSize,Receipt receipt,String ids);
}
