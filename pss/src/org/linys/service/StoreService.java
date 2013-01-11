package org.linys.service;

import org.linys.model.Store;
import org.linys.vo.ServiceResult;
/**
 * @Description:库存Service
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
public interface StoreService extends BaseService<Store,String>{
	/**
	 * @Description: 分页查询当前库存
	 * @Create: 2013-1-11 下午11:01:44
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Store model, Integer page, Integer rows);
	/**
	 * @Description: 统计当前库存
	 * @Create: 2013-1-11 下午11:01:56
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Store model);

}
