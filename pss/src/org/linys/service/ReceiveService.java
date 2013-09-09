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
	 * @param endDate 
	 * @param beginDate 
	 * @return
	 */
	ServiceResult query(String kind, Receive model, Integer page, Integer rows, String beginDate, String endDate);
	/**
	 * @Description: 统计收货
	 * @Create: 2012-12-29 下午9:55:30
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @param endDate 
	 * @param beginDate 
	 * @return
	 */
	ServiceResult getTotalCount(String kind, Receive model, String beginDate, String endDate);
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
	 * @param note3s2 
	 * @return
	 */
	ServiceResult save(String kind, Receive model, String receiveDetailIds, String buyDetailIds,
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
	ServiceResult mulDelete(String ids);
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
	ServiceResult mulUpdateStatus(String kind, String ids, Receive model);
	/**
	 * @Description: 批量清款
	 * @Create: 2013-1-12 下午3:34:04
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateIsPay(String ids, Receive model);
	/**
	 * @Description: 将选择的采购单中的采购明细，添加到收货单明细中
	 * @Create: 2013-1-15 下午10:02:09
	 * @author lys
	 * @update logs
	 * @param ids --选择的采购单
	 * @param ids2 --已添加入库单的采购明细
	 * @return
	 */
	ServiceResult querySelectBuyDetail(String ids, String ids2);
	/**
	 * @Description: 修改审核状态
	 * @Create: 2013-1-16 下午9:11:52
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateStatus(String kind, Receive model);
	/**
	 * @Description: 删除
	 * @Create: 2013-1-16 下午9:29:40
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Receive model);
	/**
	 * @Description: 清款
	 * @Create: 2013-1-16 下午9:50:24
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateIsPay(Receive model);
	/**
	 * @Description: 查询欠款的入库单
	 * @Create: 2013-1-20 上午9:46:56
	 * @author lys
	 * @update logs
	 * @param beginDate
	 * @param endDate
	 * @param supplierId
	 * @param ids
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult queryNeedPay(String beginDate, String endDate,
			String supplierId, String ids, Receive model, Integer page,
			Integer rows);
	/**
	 * @Description: 取得入库单系统配置
	 * @Created: 2013-9-9 下午9:13:20
	 * @Author lys
	 * @return
	 */
	ServiceResult getSysConfig();
	
	

}
