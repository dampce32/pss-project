package org.linys.dao;

import java.util.List;

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
	 * @Description: 取得当天最大的采购单编号
	 * @Create: 2013-1-7 下午10:16:20
	 * @author lys
	 * @update logs
	 * @param BuyCode
	 * @return
	 */
	String getMaxCode(String BuyCode);

}
