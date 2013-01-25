package org.linys.dao;

import java.util.List;
import java.util.Map;

import org.linys.model.Pay;

/**
 * @Description:付款单DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-20
 * @author lys
 * @vesion 1.0
 */
public interface PayDAO extends BaseDAO<Pay,String>{
	/**
	 * @Description: 分页查询付款单
	 * @Create: 2013-1-20 下午3:11:27
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Pay> query(Pay model, Integer page, Integer rows);
	/**
	 * @Description: 统计付款单
	 * @Create: 2013-1-20 下午3:12:35
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Pay model);
	/**
	 * @Description: 打开初始化付款单
	 * @Create: 2013-1-21 下午3:30:09
	 * @author lys
	 * @update logs
	 * @param payId
	 * @return
	 */
	Pay init(String payId);
	/**
	 * @Description: 分页查询需要对账的单子（采购单、入库单、退货单、预付单）
	 * @Create: 2013-1-24 下午2:36:47
	 * @author lys
	 * @update logs
	 * @param supplierId
	 * @param idArray
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> queryNeedCheck(String supplierId,
			String[] idArray, Integer page, Integer rows);
	/**
	 * @Description: 统计查询需要对账的单子（采购单、入库单、退货单、预付单）
	 * @Create: 2013-1-24 下午2:37:45
	 * @author lys
	 * @update logs
	 * @param supplierId
	 * @param idArray
	 * @return
	 */
	Long getTotalCountNeedCheck(String supplierId, String[] idArray);

}
