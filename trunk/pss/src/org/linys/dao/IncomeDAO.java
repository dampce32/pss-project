package org.linys.dao;

import java.util.List;

import org.linys.model.Income;
/**
 * @Description:收入DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-25
 * @author lys
 * @vesion 1.0
 */
public interface IncomeDAO extends BaseDAO<Income,String>{
	/**
	 * @Description: 分页查询收入
	 * @Create: 2013-1-25 下午10:33:45
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Income> query(Income model, Integer page, Integer rows);
	/**
	 * @Description: 统计收入
	 * @Create: 2013-1-25 下午10:34:49
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Income model);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-25 下午10:47:43
	 * @author lys
	 * @update logs
	 * @param incomeId
	 * @return
	 */
	Income init(String incomeId);

}
