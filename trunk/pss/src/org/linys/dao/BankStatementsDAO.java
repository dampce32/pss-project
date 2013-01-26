package org.linys.dao;

import java.util.List;

import org.linys.model.BankStatements;
/**
 * @Description:银行账单DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-26
 * @author lys
 * @vesion 1.0
 */
public interface BankStatementsDAO extends BaseDAO<BankStatements,String>{
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-1-26 下午10:13:10
	 * @author lys
	 * @update logs
	 * @param bankStatementsId
	 * @return
	 */
	BankStatements init(String bankStatementsId);
	/**
	 * @Description: 分页查询银行账单
	 * @Create: 2013-1-26 下午10:16:01
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<BankStatements> query(BankStatements model, Integer page, Integer rows);
	/**
	 * @Description: 统计银行账单
	 * @Create: 2013-1-26 下午10:16:27
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(BankStatements model);

}
