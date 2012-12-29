package org.linys.service;

import org.linys.model.Warehouse;
import org.linys.vo.ServiceResult;
/**
 * @Description:仓库Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface WarehouseService extends BaseService<Warehouse,String>{
	/**
	 * @Description: 保存仓库
	 * @Create: 2012-12-29 下午2:23:01
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult save(Warehouse model);
	/**
	 * @Description: 删除仓库
	 * @Create: 2012-12-29 下午2:23:11
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Warehouse model);
	/**
	 * @Description: 分页查询仓库
	 * @Create: 2012-12-29 下午2:23:44
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Warehouse model, Integer page, Integer rows);
	/**
	 * @Description: 统计仓库
	 * @Create: 2012-12-29 下午2:24:00
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Warehouse model);

}
