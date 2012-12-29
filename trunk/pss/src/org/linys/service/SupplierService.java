package org.linys.service;

import org.linys.model.Supplier;
import org.linys.vo.ServiceResult;
/**
 * @Description:供应商Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface SupplierService extends BaseService<Supplier,String>{
	/**
	 * @Description: 保存供应商
	 * @Create: 2012-12-29 上午11:31:15
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Supplier model);
	/**
	 * @Description: 删除供应商
	 * @Create: 2012-12-29 上午11:31:30
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Supplier model);
	/**
	 * @Description: 分页查询供应商 
	 * @Create: 2012-12-29 上午11:32:29
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Supplier model, Integer page, Integer rows);
	/**
	 * @Description: 统计供应商
	 * @Create: 2012-12-29 上午11:32:47
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Supplier model);

}
