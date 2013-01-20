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
	 * @return
	 */
	ServiceResult query(Pay model, Integer page, Integer rows);
	/**
	 * @Description: 统计付款单
	 * @Create: 2013-1-20 下午2:58:33
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Pay model);
	/**
	 * @Description: 保存付款单
	 * @Create: 2013-1-20 下午3:09:18
	 * @author lys
	 * @update logs
	 * @param model
	 * @param payDetailIds
	 * @param delPayDetailIds
	 * @param receiveIds
	 * @param payKinds
	 * @param amounts
	 * @param payedAmounts
	 * @param discountAmounts
	 * @param payAmounts
	 * @param payAmounts2 
	 * @return
	 */
	ServiceResult save(Pay model, String payDetailIds, String delPayDetailIds,
			String receiveIds, String payKinds, String amounts,
			String payedAmounts,String discountedAmounts, String discountAmounts, String payAmounts);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-20 下午3:09:43
	 * @author lys
	 * @update logs
	 * @param payId
	 * @return
	 */
	ServiceResult init(String payId);

}
