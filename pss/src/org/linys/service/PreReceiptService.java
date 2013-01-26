package org.linys.service;

import java.util.Date;

import org.linys.model.PreReceipt;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 预收service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author longweier
 * @vesion 1.0
 */
public interface PreReceiptService extends BaseService<PreReceipt, String> {
	
	/**
	 * 
	 * @Description: 保存预收
	 * @Create: 2013-1-26 下午09:31:57
	 * @author longweier
	 * @update logs
	 * @param preReceipt
	 * @return
	 * @throws Exception
	 */
	public ServiceResult savePreReceipt(PreReceipt preReceipt);
	
	/**
	 * 
	 * @Description: 单条删除
	 * @Create: 2013-1-26 下午09:32:07
	 * @author longweier
	 * @update logs
	 * @param preReceiptId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult deletePreReceipt(String preReceiptId);
	
	/**
	 * 
	 * @Description: 批量删除
	 * @Create: 2013-1-26 下午09:32:20
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDeletePreReceipt(String ids);
	
	/**
	 * 
	 * @Description: 单条修改状态
	 * @Create: 2013-1-26 下午09:32:28
	 * @author longweier
	 * @update logs
	 * @param preReceipt
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatusPreReceipt(PreReceipt preReceipt);
	
	/**
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-1-26 下午09:32:41
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulUpdateStatusPreReceipt(String ids,Integer status);
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-1-26 下午09:32:52
	 * @author longweier
	 * @update logs
	 * @param preReceiptId
	 * @return
	 * @throws Exception
	 */
	public String initPreReceipt(String preReceiptId);
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-26 下午09:33:01
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param preReceipt
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String queryPreReceipt(Integer pageNumber,Integer pageSize,PreReceipt preReceipt,Date beginDate,Date endDate);
}
