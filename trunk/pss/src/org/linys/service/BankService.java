package org.linys.service;

import org.linys.model.Bank;
import org.linys.vo.ServiceResult;
/**
 * @Description:银行Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface BankService extends BaseService<Bank,String>{
	/**
	 * @Description: 保存银行
	 * @Create: 2012-12-29 下午3:03:29
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Bank model);
	/**
	 * @Description: 删除银行
	 * @Create: 2012-12-29 下午3:03:42
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Bank model);
	/**
	 * @Description: 分页查询银行
	 * @Create: 2012-12-29 下午3:03:54
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Bank model, Integer page, Integer rows);
	/**
	 * @Description: 统计银行
	 * @Create: 2012-12-29 下午3:04:08
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Bank model);

}
