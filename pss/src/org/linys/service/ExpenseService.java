package org.linys.service;

import org.linys.model.Expense;
import org.linys.vo.ServiceResult;
/**
 * @Description:费用支出Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
public interface ExpenseService extends BaseService<Expense,String>{
	/**
	 * @Description: 保存费用支出
	 * @Create: 2013-1-26 下午9:01:54
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Expense model);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-26 下午9:02:28
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult init(Expense model);
	/**
	 * @Description: 删除费用支出
	 * @Create: 2013-1-26 下午9:02:48
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Expense model);
	/**
	 * @Description: 批量删除费用支出
	 * @Create: 2013-1-26 下午9:03:00
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 分页查询费用支出
	 * @Create: 2013-1-26 下午9:03:13
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Expense model, Integer page, Integer rows);
	/**
	 * @Description: 统计费用支出
	 * @Create: 2013-1-26 下午9:03:27
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Expense model);
	/**
	 * @Description: 修改费用支出状态
	 * @Create: 2013-1-26 下午9:03:42
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateStatus(Expense model);
	/**
	 * @Description: 批量修改费用支出状态
	 * @Create: 2013-1-26 下午9:03:55
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, Expense model);

}
