package org.linys.service;

import org.linys.model.Reject;
import org.linys.vo.ServiceResult;
/**
 * @Description:退货Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
public interface RejectService extends BaseService<Reject,String>{
	/**
	 * @Description: 分页查询退货
	 * @Create: 2013-1-11 上午11:08:55
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Reject model, Integer page, Integer rows);
	/**
	 * @Description: 统计退货
	 * @Create: 2013-1-11 上午11:09:14
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Reject model);
	/**
	 * @Description: 保存退货单
	 * @Create: 2013-1-11 上午11:09:28
	 * @author lys
	 * @update logs
	 * @param model
	 * @param rejectDetailIds
	 * @param delRejectDetailIds
	 * @param productIds
	 * @param colorIds
	 * @param qtys
	 * @param prices
	 * @param note1s
	 * @param note2s
	 * @param note3s
	 * @return
	 */
	ServiceResult save(Reject model, String rejectDetailIds,
			String delRejectDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-11 上午11:09:43
	 * @author lys
	 * @update logs
	 * @param rejectId
	 * @return
	 */
	ServiceResult init(String rejectId);
	/**
	 * @Description: 批量删除
	 * @Create: 2013-1-11 上午11:09:56
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDel(String ids);
	/**
	 * @Description: 批量修改审核状态
	 * @Create: 2013-1-11 上午11:10:08
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateShzt(String ids, Reject model);

}
