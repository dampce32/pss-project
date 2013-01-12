package org.linys.service;

import org.linys.model.Buy;
import org.linys.vo.ServiceResult;
/**
 * @Description:采购单Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface BuyService extends BaseService<Buy,String>{
	/**
	 * @Description: 分页查询采购单
	 * @Create: 2012-12-29 下午9:55:19
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query( Buy model, Integer page, Integer rows);
	/**
	 * @Description: 统计采购单
	 * @Create: 2012-12-29 下午9:55:30
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount( Buy model);
	/**
	 * @Description: 保存采购单
	 * @Create: 2013-1-3 上午10:45:08
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @param BuyDetailIds
	 * @param delBuyDetailIds
	 * @param productIds
	 * @param colorIds
	 * @param qtys
	 * @param prices
	 * @param note1s
	 * @param note2s
	 * @param note3s
	 * @return
	 */
	ServiceResult save( Buy model, String buyDetailIds,
			String delBuyDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-8 下午10:29:50
	 * @author lys
	 * @update logs
	 * @param BuyId
	 * @return
	 */
	ServiceResult init(String buyId);
	/**
	 * @Description: 批量删除
	 * @Create: 2013-1-9 下午10:30:39
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDel(String ids);
	/**
	 * @Description: 批量修改状态
	 * @Create: 2013-1-9 下午11:51:15
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateShzt(String ids, Buy model);

}
