package org.linys.dao;

import java.util.List;

import org.linys.model.Product;
import org.linys.model.ProductPriceRange;
/**
 * @description 商品价格区间DAO
 * @copyright 福州骏华信息有限公司 (c)2015
 * @createDate 2015-6-16
 * @author 以宋
 * @vesion 1.0
 */
public interface ProductPriceRangeDAO  extends BaseDAO<ProductPriceRange,String>{
	/**
	 * @description  根据数量查询价格
	 * @createTime 2015-6-16 下午11:57:27
	 * @author 以宋
	 * @updateLogs 
	 * @param model
	 * @return
	 */
	ProductPriceRange getPriceByQty(ProductPriceRange model);
	/**
	 * @description   查询商品product价格等级为priceLevel的价格区间
	 * @createTime 2015-6-17 下午11:48:02
	 * @author 以宋
	 * @updateLogs 
	 * @param product
	 * @param priceLevel
	 * @return
	 */
	List<ProductPriceRange> queryByProduct(Product product, String priceLevel);

}
