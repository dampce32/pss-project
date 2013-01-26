package org.linys.service;

import org.linys.model.BankStatements;
import org.linys.vo.ServiceResult;
/**
 * @Description:银行账单Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
public interface BankStatementsService extends BaseService<BankStatements,String>{
	/**
	 * @Description: 保存银行账单
	 * @Create: 2013-1-26 下午10:02:10
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(BankStatements model);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-26 下午10:02:25
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult init(BankStatements model);
	/**
	 * @Description: 删除银行账单
	 * @Create: 2013-1-26 下午10:02:40
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(BankStatements model);
	/**
	 * @Description: 批量删除银行账单
	 * @Create: 2013-1-26 下午10:02:55
	 * @author lys
	 * @update logs
	 * @param ids
	 * @return
	 */
	ServiceResult mulDelete(String ids);
	/**
	 * @Description: 分页查询银行账单 
	 * @Create: 2013-1-26 下午10:03:07
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(BankStatements model, Integer page, Integer rows);
	/**
	 * @Description: 统计银行账单
	 * @Create: 2013-1-26 下午10:03:20
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(BankStatements model);
	/**
	 * @Description: 修改银行账单状态
	 * @Create: 2013-1-26 下午10:03:31
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult updateStatus(BankStatements model);
	/**
	 * @Description: 批量修改银行账单状态
	 * @Create: 2013-1-26 下午10:03:45
	 * @author lys
	 * @update logs
	 * @param ids
	 * @param model
	 * @return
	 */
	ServiceResult mulUpdateStatus(String ids, BankStatements model);

}
