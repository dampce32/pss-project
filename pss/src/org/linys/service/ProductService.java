package org.linys.service;

import org.linys.model.Product;
import org.linys.vo.ServiceResult;
/**
 * @Description:商品Service
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
public interface ProductService extends BaseService<Product,String>{
	/**
	 * @Description: 删除商品
	 * @Create: 2012-12-23 下午5:45:43
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult delete(Product model);
	/**
	 * @Description: 分组查询 商品 
	 * @Create: 2012-12-23 下午5:45:59
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult query(Product model, Integer page, Integer rows);
	/**
	 * @Description:  统计 商品
	 * @Create: 2012-12-23 下午5:46:14
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult getTotalCount(Product model);
	/**
	 * @Description: 保存商品
	 * @Create: 2012-12-28 上午12:14:38
	 * @author lys
	 * @update logs
	 * @param model
	 * @param qtys 
	 * @param productIds 
	 * @param deleleIds 
	 * @param defaultPackagingIds 
	 * @return
	 */
	ServiceResult save(Product model, String defaultPackagingIds, String deleleIds, String productIds, String qtys);
	/**
	 * @Description: 选择商品
	 * @Create: 2012-12-30 上午10:25:02
	 * @author lys
	 * @param kind 
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult select(Product model, Integer page, Integer rows);
	/**
	 * @Description: 退货选择商品
	 * @Create: 2013-1-11 下午12:48:49
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult selectReject(Product model, Integer page, Integer rows);
	/**
	 * @Description: 查询当前库存的商品
	 * @Create: 2013-1-12 下午1:45:28
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult queryStore(Product model, Integer page, Integer rows);
	/**
	 * @Description: 默认商品组装，选择商品
	 * @Create: 2013-2-3 上午10:23:36
	 * @author lys
	 * @update logs
	 * @param model
	 * @param ids
	 * @param page
	 * @param rows
	 * @return
	 */
	ServiceResult selectDefaultPacking(Product model, String ids, Integer page,
			Integer rows);
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-2-3 下午12:01:29
	 * @author lys
	 * @update logs
	 * @param productId
	 * @return
	 */
	ServiceResult init(String productId);

}
