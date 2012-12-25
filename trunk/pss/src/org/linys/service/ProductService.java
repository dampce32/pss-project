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
	 * @Description: 添加商品
	 * @Create: 2012-12-23 下午5:45:03
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult add(Product model);
	/**
	 * @Description: 修改商品
	 * @Create: 2012-12-23 下午5:45:27
	 * @author lys
	 * @update logs
	 * @param model
	 * @return
	 */
	ServiceResult update(Product model);
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

}
