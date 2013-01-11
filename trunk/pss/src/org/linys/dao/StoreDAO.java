package org.linys.dao;

import java.util.List;
import java.util.Map;

import org.linys.model.Store;
/**
 * @Description:库存DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-1-11
 * @author lys
 * @vesion 1.0
 */
public interface StoreDAO extends BaseDAO<Store,String>{
	/**
	 * @Description: 分页查询当前库存
	 * @Create: 2013-1-11 下午11:10:47
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Map<String, Object>> query(Store model, Integer page, Integer rows);
	/**
	 * @Description: 统计当前库存
	 * @Create: 2013-1-11 下午11:12:12
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Store model);

}
