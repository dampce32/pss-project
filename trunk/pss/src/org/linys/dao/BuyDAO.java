package org.linys.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.linys.model.Buy;
/**
 * @Description:采购单DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface BuyDAO extends BaseDAO<Buy,String>{
	/**
	 * @Description: 分页查询采购单
	 * @Create: 2012-12-29 下午9:58:06
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Buy> query(Buy model, Integer page, Integer rows);
	/**
	 * @Description: 统计采购单
	 * @Create: 2012-12-29 下午9:58:32
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Buy model);
	/**
	 * @Description: 从采购单添加商品“查询还没有完成采购单任务的采购单
	 * @Create: 2013-1-15 上午12:09:59
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
	List<Map<String, Object>> queryReceive(Date beginDateDate,
			Date endDateDate, String supplierId, String[] idArray, Buy model,
			Integer page, Integer rows);
	/**
	 * @Description: 打开初始化采购单
	 * @Create: 2013-1-15 下午8:40:20
	 * @author lys
	 * @update logs
	 * @param buyId
	 * @return
	 */
	Buy init(String buyId);

}
