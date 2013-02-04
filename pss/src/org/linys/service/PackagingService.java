package org.linys.service;

import java.util.Date;

import org.linys.model.Packaging;
import org.linys.vo.ServiceResult;

/**
 * 
 * @Description: 组装service
 * @Copyright: 福州骏华科技信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author longweier
 * @vesion 1.0
 */
public interface PackagingService extends BaseService<Packaging, String> {

	/**
	 * 
	 * @Description: 新增组装单
	 * @Create: 2013-2-3 下午09:25:47
	 * @author longweier
	 * @update logs
	 * @param packaging    组装单主信息
	 * @param productIds   商品ID
	 * @param qtys         数量
	 * @param prices       单价
	 * @param note1s       备注1
	 * @param note2s       备注2
	 * @param note3s	        备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult addPackaging(Packaging packaging,String productIds,String qtys,String prices,String warehouseIds,String note1s, String note2s,String note3s);
	
	/**
	 * 
	 * @Description: 修改组装单
	 * @Create: 2013-2-3 下午09:27:16
	 * @author longweier
	 * @update logs
	 * @param packaging             组装单主信息
	 * @param packagingDetailIds    明细ID
	 * @param delPackagingDetailIds 删除明细ID
	 * @param productIds			商品ID
	 * @param qtys					数量
	 * @param prices				价格
	 * @param note1s				备注1
	 * @param note2s				备注2
	 * @param note3s				备注3
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updatePackaging(Packaging packaging,String packagingDetailIds,String delPackagingDetailIds,
				String productIds,String qtys,String prices,String warehouseIds,String note1s, String note2s,String note3s );
	
	/**
	 * 
	 * @Description: 删除组装单
	 * @Create: 2013-2-3 下午09:29:17
	 * @author longweier
	 * @update logs
	 * @param packagingId
	 * @return
	 * @throws Exception
	 */
	public ServiceResult deletePackaging(String packagingId);
	
	/**
	 * 
	 * @Description: 批量删除组装单
	 * @Create: 2013-2-3 下午09:29:49
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulDeletePackaging(String ids);
	
	/**
	 * 
	 * @Description: 修改组装单状态
	 * @Create: 2013-2-3 下午09:30:21
	 * @author longweier
	 * @update logs
	 * @param packaging
	 * @return
	 * @throws Exception
	 */
	public ServiceResult updateStatus(Packaging packaging);
	
	/**
	 * 
	 * @Description: 批量修改昨天
	 * @Create: 2013-2-3 下午09:31:09
	 * @author longweier
	 * @update logs
	 * @param ids
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public ServiceResult mulUpdateStatus(String ids,Integer status);
	
	/**
	 * 
	 * @Description: 初始化
	 * @Create: 2013-2-3 下午09:31:44
	 * @author longweier
	 * @update logs
	 * @param packagingId
	 * @return
	 * @throws Exception
	 */
	public String init(String packagingId);
	
	/**
	 * 
	 * @Description: 查询
	 * @Create: 2013-2-3 下午09:32:30
	 * @author longweier
	 * @update logs
	 * @param pageNumber
	 * @param pageSize
	 * @param packaging
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String query(Integer pageNumber,Integer pageSize,Packaging packaging,Date beginDate,Date endDate);
}
