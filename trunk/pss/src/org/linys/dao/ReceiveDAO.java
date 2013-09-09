package org.linys.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.linys.model.Receive;
/**
 * @Description:收货DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface ReceiveDAO extends BaseDAO<Receive,String>{
	/**
	 * @Description: 分页查询收货
	 * @Create: 2012-12-29 下午9:58:06
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @param endDate 
	 * @param beginDate 
	 * @return
	 */
	List<Receive> query(String kind,Receive model, Integer page, Integer rows, Date beginDate, Date endDate);
	/**
	 * @Description: 统计收货
	 * @Create: 2012-12-29 下午9:58:32
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(String kind,Receive model, Date beginDate, Date endDate);
	/**
	 * @Description: 取得当天最大的收货单编号
	 * @Create: 2013-1-7 下午10:16:20
	 * @author lys
	 * @update logs
	 * @param receiveCode
	 * @return
	 */
	String getMaxCode(String receiveCode);
	/**
	 * @Description: 将选择的采购单中的采购明细，添加到收货单明细中
	 * @Create: 2013-1-15 下午10:05:14
	 * @author lys
	 * @update logs
	 * @param idArray
	 * @param idArray2
	 * @return
	 */
	List<Map<String, Object>> querySelectBuyDetail(String[] idArray, String[] idArray2);
	/**
	 * @Description: 查询欠款的入库单
	 * @Create: 2013-1-20 上午9:48:29
	 * @author lys
	 * @update logs
	 * @param beginDateDate
	 * @param endDateDate
	 * @param supplierId
	 * @param idArray
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> queryNeedPay(Date beginDateDate,
			Date endDateDate, String supplierId, String[] idArray,
			Receive model, Integer page, Integer rows);
	/**
	 * @Description: 统计欠款的入库单
	 * @Create: 2013-1-20 上午9:48:56
	 * @author lys
	 * @update logs
	 * @param beginDateDate
	 * @param endDateDate
	 * @param supplierId
	 * @param idArray
	 * @param model
	 * @return
	 */
	Long getTotalReceive(Date beginDateDate, Date endDateDate,
			String supplierId, String[] idArray, Receive model);
	/**
	 * @Description: 取得本入库单已付金额
	 * @Create: 2013-1-21 下午4:30:15
	 * @author lys
	 * @update logs
	 * @param receiveId
	 * @return
	 */
	Double getNeedPayAmount(String receiveId);
	/**
	 * @Description: 判断入库单是否已经进入付款单
	 * @Create: 2013-1-30 上午10:32:11
	 * @author lys
	 * @update logs
	 * @param receiveId
	 * @return
	 */
	List<Map<String,Object>> countReceive(String receiveId);

}
