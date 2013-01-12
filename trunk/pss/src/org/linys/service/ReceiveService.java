package org.linys.service;

import org.linys.model.Receive;
import org.linys.vo.ServiceResult;
/**
 * @Description:采购入库Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface ReceiveService extends BaseService<Receive,String>{
	/**
	 * @Description: 分页查询收货
	 * @Create: 2012-12-29 下午9:55:19
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(String kind, Receive model, Integer page, Integer rows);
	/**
	 * @Description: 统计收货
	 * @Create: 2012-12-29 下午9:55:30
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(String kind, Receive model);
	/**
	 * @Description: 保存收货单
	 * @Create: 2013-1-3 上午10:45:08
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @param receiveDetailIds
	 * @param delReceiveDetailIds
	 * @param productIds
	 * @param colorIds
	 * @param qtys
	 * @param prices
	 * @param note1s
	 * @param note2s
	 * @param note3s
	 * @return
	 */
	ServiceResult save(String kind, Receive model, String receiveDetailIds,
			String delReceiveDetailIds, String productIds, String colorIds,
			String qtys, String prices, String note1s, String note2s,
			String note3s);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-8 下午10:29:50
	 * @author lys
	 * @update logs
	 * @param receiveId
	 * @return
	 */
	ServiceResult init(String receiveId);
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
	 * @param kind  用来区分是否审核的是其他入库
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateShzt(String kind, String ids, Receive model);
	/**
	 * @Description: 批量清款
	 * @Create: 2013-1-12 下午3:34:04
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateShzt(String ids, Receive model);

}
