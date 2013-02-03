package org.linys.dao;

import java.util.List;

import org.linys.model.Product;
/**
 * @Description:商品DAO
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
public interface ProductDAO extends BaseDAO<Product,String>{
	/**
	 * @Description: 分组查询 商品
	 * @Create: 2012-12-23 下午6:08:22
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Product> query(Product model, Integer page, Integer rows);
	/**
	 * @Description: 统计 商品
	 * @Create: 2012-12-23 下午6:08:50
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCount(Product model);
	/**
	 * @Description: 退货分页查询商品
	 * @Create: 2013-1-11 下午12:49:59
	 * @author lys
	 * @update logs
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Product> queryReject(Product model, Integer page, Integer rows);
	/**
	 * @Description: 退货统计商品
	 * @Create: 2013-1-11 下午12:50:23
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	Long getTotalCountReject(Product model);
	/**
	 * @Description: 默认商品组装，分页选择商品
	 * @Create: 2013-2-3 上午10:25:03
	 * @author lys
	 * @update logs
	 * @param model
	 * @param idArray
	 * @param page
	 * @param rows
	 * @return
	 */
	List<Product> querySelectDefaultPacking(Product model, String[] idArray,
			Integer page, Integer rows);
	/**
	 * @Description: 默认商品组装，选择统计商品
	 * @Create: 2013-2-3 上午10:25:34
	 * @author lys
	 * @update logs
	 * @param model
	 * @param ids
	 * @return
	 */
	Long getTotalCountSelectDefaultPacking(Product model, String[] idArray);

}
