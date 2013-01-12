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
	/**
	 * @Description: 采购退货选择商品
	 * @Create: 2013-1-12 上午9:46:21
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult selectReject(Store model, Integer page, Integer rows);
	/**
	 * @Description: 查询商品的在各个仓库的当前库存情况
	 * @Create: 2013-1-12 下午1:49:16
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult queryByProduct(Store model, Integer page, Integer rows);

}
