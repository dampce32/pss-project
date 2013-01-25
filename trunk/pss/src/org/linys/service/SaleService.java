package org.linys.service;

import java.util.Date;

import org.linys.model.Sale;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 订单service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-22
 * @author longweier
 * @vesion 1.0
 */
public interface SaleService extends BaseService<Sale, String> {
	
	/**
	 * 
	 * @Description: 新增订单
	 * @Create: 2013-1-22 下午09:14:28
	 * @author longweier
	 * @update logs 
	 * @param sale         订单主信息
	 * @param productIds   商品ID
 	 * @param colorIds     颜色ID
	 * @param qtys         数量
	 * @param prices       单价
	 * @param discounts    折扣
	 * @param note1s       备注1
	 * @param note2s       备注2
	 * @param note3s       备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult addSale(Sale sale,String productIds,String colorIds,String qtys, 
								String prices, String discounts,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * 
	 * @Description: 更新订单
	 * @Create: 2013-1-22 下午09:20:24
	 * @author longweier
	 * @update logs
	 * @param sale				订单主信息
	 * @param saleDetailIds     订单明细ID
	 * @param delSaleDetailIds  删除的订单明细ID
	 * @param productIds        商品ID
	 * @param colorIds			颜色ID
	 * @param qtys				数量
	 * @param prices			单价
	 * @param discounts			折扣
	 * @param note1s			备注1
	 * @param note2s			备注2
	 * @param note3s			备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateSale(Sale sale,String saleDetailIds,String delSaleDetailIds, String productIds, String colorIds,
									String qtys, String prices, String discounts,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 批量删除订单
	 * @Create: 2013-1-22 下午09:22:35
	 * @author longweier
	 * @update logs
	 * @param ids   		ID号
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDeleteSale(String ids); 
	
	/**
	 * 
	 * @Description: 批量更新状态
	 * @Create: 2013-1-22 下午09:23:24
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
	 * @Description: 删除订单
	 * @Create: 2013-1-22 下午09:24:14
	 * @author longweier
	 * @update logs
	 * @param saleId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult deleteSale(String saleId);
	
	/**
	 * 
	 * @Description: 更新订单状态
	 * @Create: 2013-1-22 下午09:24:43
	 * @author longweier
	 * @update logs
	 * @param sale
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatus(Sale sale);
	
	/**
	 * 
	 * @Description: 查询订单
	 * @Create: 2013-1-22 下午10:33:23
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param sale
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String querySale(Integer pageNumber,Integer pageSize,Sale sale,Date beginDate,Date endDate);
	
	/**
	 * 
	 * @Description: 初始化销售单界面
	 * @Create: 2013-1-22 下午11:25:19
	 * @author longweier
	 * @update logs
	 * @param saleId
	 * @return
	 * @throws Exception
	 */
	public String initSale(String saleId);
	
	/**
	 * 
	 * @Description:  查询某个客户还未出库的订单
	 * @Create: 2013-1-25 上午11:01:30
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param sale
	 * @param ids
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String queryDeliver(Integer pageNumber,Integer pageSize,Sale sale,String ids,Date beginDate,Date endDate);
}
