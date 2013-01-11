package org.linys.dao;

import java.util.List;

import org.linys.model.Reject;
/**
 * @Description:退货DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
public interface RejectDAO extends BaseDAO<Reject,String>{
	/**
	 * @Description: 分页查询退货
	 * @Create: 2013-1-11 上午11:16:09
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Reject> query(Reject model, Integer page, Integer rows);
	/**
	 * @Description: 统计退货
	 * @Create: 2013-1-11 上午11:16:29
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Reject model);
	/**
	 * @Description: 取得最大的退货单号
	 * @Create: 2013-1-11 上午11:17:20
	 * @author lys
	 * @update logs
	 * @param rejectCode
	 * @return
	 */
	String getMaxCode(String rejectCode);

}
