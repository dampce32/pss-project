package org.linys.service;

import java.util.Date;

import org.linys.model.Deliver;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 出库service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-24
 * @author longweier
 * @vesion 1.0
 */
public interface DeliverService extends BaseService<Deliver, String> {
	
	/**
	 * 
	 * @Description: 新增出库单
	 * @Create: 2013-1-24 下午02:23:22
	 * @author longweier
	 * @update logs
	 * @param type			   出库类型  other--其他出库
	 * @param deliver        出库单主信息
	 * @param saleDetailIds  从哪个订单出的
	 * @param productIds     物品ID
	 * @param colorIds       颜色ID
	 * @param qtys           数量
	 * @param prices         价格
	 * @param discounts      折扣
	 * @param note1s         备注1
	 * @param note2s  		  备注2
	 * @param note3s		 备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult addDeliver(String type,Deliver deliver,String saleDetailIds, String productIds,String colorIds,String qtys, 
			String prices, String discounts,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description:    修改出库单
	 * @Create: 2013-1-24 下午02:27:51
	 * @author longweier
	 * @update logs
	 * @param type					出库类型  other--其他出库
	 * @param deliver				出库单主信息
	 * @param deliverDetailIds      出库单详情ID
	 * @param delDeliverDetailIds   删除ID
	 * @param deliver        		出库单主信息
	 * @param saleDetailIds  		从哪个订单出的
	 * @param productIds     		物品ID
	 * @param colorIds       		颜色ID
	 * @param qtys           		数量
	 * @param prices        		价格
	 * @param discounts      		折扣
	 * @param note1s         		备注1
	 * @param note2s  		 		备注2
	 * @param note3s		 		备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateDeliver(String type,Deliver deliver,String deliverDetailIds,String delDeliverDetailIds,String saleDetailIds, String productIds,String colorIds,String qtys, 
			String prices, String discounts,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 单独删除出库单
	 * @Create: 2013-1-24 下午02:30:54
	 * @author longweier
	 * @update logs
	 * @param deliverId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult deleteDeliver(String deliverId);
	
	/**
	 * 
	 * @Description: 批量删除出库单
	 * @Create: 2013-1-24 下午02:40:02
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDeleteDeliver(String ids);
	
	/**
	 * 
	 * @Description: 订单修改出库单状态
	 * @Create: 2013-1-24 下午02:40:43
	 * @author longweier
	 * @update logs
	 * @param type
	 * @param deliver
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatusDeliver(String type,Deliver deliver);
	
	/**
	 * 
	 * @Description: 批量修改状态
	 * @Create: 2013-1-24 下午02:42:04
	 * @author longweier
	 * @update logs
	 * @param type
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulUpdateStatusDeliver(String type,String ids,Integer status);
	
	/**
	 * 
	 * @Description: 查询出库单信息
	 * @Create: 2013-1-24 下午02:44:28
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param deliver
	 * @param beginDate
	 * @param endDate
	 * @param type         出库类型
	 * @return
	 * @throws Exception
	 */
	public String queryDeliver(Integer pageNumber,Integer pageSize,Deliver deliver,Date beginDate,Date endDate,String type);
	
	/**
	 * 
	 * @Description: 初始化出库单
	 * @Create: 2013-1-24 下午02:44:55
	 * @author longweier
	 * @update logs
	 * @param deliverId
	 * @return
	 * @throws Exception
	 */
	public String initDeliver(String deliverId);
}
