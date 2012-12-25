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

}
