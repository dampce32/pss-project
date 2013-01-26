package org.linys.dao;

import java.util.List;

import org.linys.model.Expense;
/**
 * @Description:费用支出DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
public interface ExpenseDAO extends BaseDAO<Expense,String>{
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-26 下午9:09:19
	 * @author lys
	 * @update logs
	 * @param expenseId
	 * @return
	 */
	Expense init(String expenseId);
	/**
	 * @Description: 分页查询费用支出
	 * @Create: 2013-1-26 下午9:09:41
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Expense> query(Expense model, Integer page, Integer rows);
	/**
	 * @Description: 统计费用支出
	 * @Create: 2013-1-26 下午9:09:59
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Expense model);

}
