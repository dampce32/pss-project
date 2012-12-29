package org.linys.dao;

import java.util.List;

import org.linys.model.Warehouse;
/**
 * @Description:仓库DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-29
 * @author lys
 * @vesion 1.0
 */
public interface WarehouseDAO extends BaseDAO<Warehouse,String>{
    /**
     * @Description: 分页查询仓库
     * @Create: 2012-12-29 下午2:31:35
     * @author lys
     * @update logs
     * @param model
     * @param page
     * @param rows
     * @return
     */
	List<Warehouse> query(Warehouse model, Integer page, Integer rows);
	/**
	 * @Description: 统计仓库
	 * @Create: 2012-12-29 下午2:31:44
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Warehouse model);

}
