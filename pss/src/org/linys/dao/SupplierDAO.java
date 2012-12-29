package org.linys.dao;

import java.util.List;

import org.linys.model.Supplier;
/**
 * @Description:供应商DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface SupplierDAO extends BaseDAO<Supplier,String>{
	/**
	 * @Description: 分页查询供应商
	 * @Create: 2012-12-29 上午11:41:14
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Supplier> query(Supplier model, Integer page, Integer rows);
	/**
	 * @Description: 统计供应商
	 * @Create: 2012-12-29 上午11:41:33
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Supplier model);

}
