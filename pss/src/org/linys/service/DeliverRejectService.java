package org.linys.service;

import java.util.Date;

import org.linys.model.DeliverReject;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 销售退货单
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author longweier
 * @vesion 1.0
 */
public interface DeliverRejectService extends BaseService<DeliverReject, String> {
	
	/**
	 * 
	 * @Description: 新增销售退货单
	 * @Create: 2013-1-25 下午11:16:33
	 * @author longweier
	 * @update logs
	 * @param deliverReject    销售出库主信息
	 * @param productIds       产品ID
	 * @param colorIds		       颜色ID
	 * @param qtys			       数量
	 * @param prices		       价格
	 * @param note1s	                  备注1
	 * @param note2s		       备注2
	 * @param note3s		       备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult addDeliverReject(DeliverReject deliverReject,String productIds,String colorIds,String qtys, 
			String prices,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 
	 * @Create: 2013-1-25 下午11:19:20
	 * @author longweier
	 * @update logs
	 * @param deliverReject    			   销售出库主信息
	 * @param deliverRejectDetailIds     
	 * @param delDeliverRejectDetailIds
	 * @param productIds
	 * @param colorIds
	 * @param qtys
	 * @param prices
	 * @param note1s
	 * @param note2s
	 * @param note3s
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateDeliverReject(DeliverReject deliverReject,String deliverRejectDetailIds,String delDeliverRejectDetailIds,String productIds,String colorIds,String qtys, 
			String prices,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 单条删除
	 * @Create: 2013-1-25 下午11:22:37
	 * @author longweier
	 * @update logs
	 * @param deliverRejectId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult deleteDeliverReject(String deliverRejectId);
	
	/**
	 * 
	 * @Description: 批量删除
	 * @Create: 2013-1-25 下午11:23:07
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDeleteDeliverReject(String ids);
	
	/**
	 * 
	 * @Description: 单条更新
	 * @Create: 2013-1-25 下午11:23:33
	 * @author longweier
	 * @update logs
	 * @param deliverReject
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatusDeliverReject(DeliverReject deliverReject);
	
	/**
	 * 
	 * @Description: 批量更新
	 * @Create: 2013-1-25 下午11:24:06
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulUpdateStatusDeliverReject(String ids,Integer status);
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-1-25 下午11:24:38
	 * @author longweier
	 * @update logs
	 * @param deliverRejectId
	 * @return
	 * @throws Exception
	 */
	public String initDeliverReject(String deliverRejectId);
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-1-25 下午11:25:25
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param deliverReject
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String queryDeliverReject(Integer pageNumber,Integer pageSize,DeliverReject deliverReject,Date beginDate,Date endDate);
}
