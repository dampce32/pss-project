package org.linys.service;

import java.util.Date;

import org.linys.model.Split;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 拆分service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-4
 * @author longweier
 * @vesion 1.0
 */
public interface SplitService extends BaseService<Split, String> {
	
	/**
	 * 
	 * @Description: 新增拆分
	 * @Create: 2013-2-4 下午05:23:19
	 * @author longweier
	 * @update logs
	 * @param split			拆分主信息
	 * @param productIds	产品ID
	 * @param qtys			数量
	 * @param prices		单价
	 * @param warehouseIds  仓库ID
	 * @param note1s		备注1
	 * @param note2s		备注2
	 * @param note3s		备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult addSplit(Split split,String productIds,String qtys,String prices,String warehouseIds,
									String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 修改拆分
	 * @Create: 2013-2-4 下午05:26:39
	 * @author longweier
	 * @update logs
	 * @param split   			拆分主信息
	 * @param splitDetailIds	拆分明细ID
	 * @param delSplitDetailIds 删除的拆分明细ID
	 * @param productIds		产品ID
	 * @param qtys				数量
	 * @param prices			单价
	 * @param warehouseIds		仓库ID
	 * @param note1s			备注1
	 * @param note2s			备注2
	 * @param note3s			备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateSplit(Split split,String splitDetailIds,String delSplitDetailIds,String productIds,String qtys,
							String prices,String warehouseIds,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 删除拆分单
	 * @Create: 2013-2-5 上午09:25:20
	 * @author longweier
	 * @update logs
	 * @param splitId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult delete(String splitId);
	
	/**
	 * 
	 * @Description: 批量删除拆分单
	 * @Create: 2013-2-5 上午09:25:35
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDelete(String ids);
	
	/**
	 * 
	 * @Description: 修改状态
	 * @Create: 2013-2-5 上午09:25:44
	 * @author longweier
	 * @update logs
	 * @param split
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatus(Split split);
	
	/**
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-2-5 上午09:25:55
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulUpdateStatus(String ids, Integer status);
	
	/**
	 * 
	 * @Description: 初始化 
	 * @Create: 2013-2-5 上午09:26:05
	 * @author longweier
	 * @update logs
	 * @param splitId
	 * @return
	 * @throws Exception
	 */
	public String init(String splitId);
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-2-5 上午09:26:34
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param split
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String query(Integer pageNumber,Integer pageSize,Split split,Date beginDate,Date endDate);
}
