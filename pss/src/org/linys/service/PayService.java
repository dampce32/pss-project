package org.linys.service;

import org.linys.model.Pay;
import org.linys.vo.ServiceResult;
/**
 * @Description:付款单Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
public interface PayService extends BaseService<Pay,String>{
	/**
	 * @Description: 分页查询付款单
	 * @Create: 2013-1-20 下午2:58:15
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @param endDate 
	 * @param beginDate 
	 * @return
	 */
	ServiceResult query(Pay model, Integer page, Integer rows, String beginDate, String endDate);
	/**
	 * @Description: 统计付款单
	 * @Create: 2013-1-20 下午2:58:33
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Pay model, String beginDate, String endDate);
	/**
	 * @Description: 保存付款单
	 * @Create: 2013-1-20 下午3:09:18
	 * @author lys
	 * @update logs
	 * @param model
	 * @param payDetailIds
	 * @param delPayDetailIds
	 * @param sourceIds
	 * @param payKinds
	 * @param amounts
	 * @param payedAmounts
	 * @param discountAmounts
	 * @param payAmounts 
	 * @param discountAmounts2 
	 * @param payAmounts2 
	 * @return
	 */
	ServiceResult save(Pay model, String payDetailIds, String delPayDetailIds,
			String sourceIds, String sourceCodes, String sourceDates, String payKinds, String amounts,
			String payedAmounts,String discountedAmounts, String discountAmounts);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-20 下午3:09:43
	 * @author lys
	 * @update logs
	 * @param payId
	 * @return
	 */
	ServiceResult init(String payId);
	/**
	 * @Description: 删除付款单
	 * @Create: 2013-1-21 下午3:56:18
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Pay model);
	/**
	 * @Description: 批量删除付款单
	 * @Create: 2013-1-21 下午3:56:30
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 修改付款单状态
	 * @Create: 2013-1-21 下午3:56:42
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateStatus(Pay model);
	/**
	 * @Description: 批量修改付款单状态
	 * @Create: 2013-1-21 下午3:57:01
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Pay model);
	/**
	 * @Description: 分页查询需要对账的单子（采购单、入库单、退货单、预付单）
	 * @Create: 2013-1-24 下午2:31:25
	 * @author lys
	 * @update logs
	 * @param supplierId
	 * @param ids
	 * @param rows 
	 * @param page 
	 * @return
	 */
	ServiceResult queryNeedCheck(String supplierId, String ids, Integer page, Integer rows);

}
