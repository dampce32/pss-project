package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.ProductPriceRange;
import org.linys.service.ProductPriceRangeService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

/**
 * @description 商品价格区间Action
 * @copyright 福州骏华信息有限公司 (c)2015
 * @createDate 2015-6-16
 * @author 以宋
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ProductPriceRangeAction extends BaseAction implements
		ModelDriven<ProductPriceRange> {
	
	private static final long serialVersionUID = 3678593739089833160L;
	private static final Logger logger = Logger.getLogger(ProductPriceRangeAction.class);
	ProductPriceRange model = new ProductPriceRange();
	@Resource
	ProductPriceRangeService productPriceRangeService;
	

	@Override
	public ProductPriceRange getModel() {
		return model;
	}
	
	/**
	 * @description  根据数量查询价格
	 * @createTime 2015-6-16 下午11:48:11
	 * @author 以宋
	 * @updateLogs
	 */
	public void getPriceByQty(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productPriceRangeService.getPriceByQty(model);
		} catch (Exception e) {
			result.setMessage("根据数量查询价格失败");
			logger.error("根据数量查询价格失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}

}
