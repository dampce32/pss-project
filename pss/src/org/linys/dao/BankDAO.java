package org.linys.dao;

import java.util.List;

import org.linys.model.Bank;
/**
 * @Description:银行DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface BankDAO extends BaseDAO<Bank,String>{
	/**
	 * @Description: 分页查询银行
	 * @Create: 2012-12-29 下午3:09:08
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Bank> query(Bank model, Integer page, Integer rows);
	/**
	 * @Description: 统计银行
	 * @Create: 2012-12-29 下午3:09:33
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Bank model);

}
