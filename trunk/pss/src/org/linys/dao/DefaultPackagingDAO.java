package org.linys.dao;

import java.util.List;

import org.linys.model.DefaultPackaging;
import org.linys.model.Product;
/**
 * @Description:默认商品组装DAO
 * @Copyright: 福州骏华信息有限公司 (c)2013
 * @Created Date : 2013-2-3
 * @author lys
 * @vesion 1.0
 */
public interface DefaultPackagingDAO extends BaseDAO<DefaultPackaging,String>{
	/**
	 * @Description: 取得商品product下的默认商品组装
	 * @Create: 2013-2-3 下午12:05:40
	 * @author lys
	 * @update logs
	 * @param product
	 * @return
	 */
	List<DefaultPackaging> queryByProductId(Product product);

}
