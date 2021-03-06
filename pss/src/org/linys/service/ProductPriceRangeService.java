package org.linys.service;

import org.linys.model.ProductPriceRange;
import org.linys.vo.ServiceResult;
/**
 * @description 商品价格区间Service
 * @copyright 福州骏华信息有限公司 (c)2015
 * @createDate 2015-6-16
 * @author 以宋
 * @vesion 1.0
 */
public interface ProductPriceRangeService extends BaseService<ProductPriceRange,String>{
	/**
	 * @description  根据数量查询价格
	 * @createTime 2015-6-16 下午11:52:18
	 * @author 以宋
	 * @updateLogs 
	 * @param model
	 * @return
	 */
	ServiceResult getPriceByQty(ProductPriceRange model);

}
