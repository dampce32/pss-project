package org.linys.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.linys.model.Product;
import org.linys.service.ProductService;
import org.linys.vo.ServiceResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
/**
 * @Description:商品Action
 * @Copyright: 福州骏华信息有限公司 (c)2012
 * @Created Date : 2012-12-23
 * @author lys
 * @vesion 1.0
 */
@Controller
@Scope("prototype")
public class ProductAction extends BaseAction implements ModelDriven<Product> {
	
	private static final long serialVersionUID = -7463394380104260815L;
	private static final Logger logger = Logger.getLogger(ProductAction.class);
	@Resource
	private ProductService productService;
	Product model = new Product();
	public Product getModel() {
		return model;
	}
	/**
	 * @Description: 保存商品
	 * @Create: 2012-12-23 下午5:42:17
	 * @author lys
	 * @update logs
	 */
	public void save(){
		ServiceResult result = new ServiceResult(false);
		try {
			String defaultPackagingIds = getParameter("defaultPackagingIds");
			String deleleIds = getParameter("deleleIds");
			String productIds = getParameter("productIds");
			String qtys = getParameter("qtys");
			result = productService.save(model,defaultPackagingIds,deleleIds,productIds,qtys);
		} catch (Exception e) {
			result.setMessage("保存商品失败");
			logger.error("保存商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 打开初始化
	 * @Create: 2013-2-3 下午12:00:55
	 * @author lys
	 * @update logs
	 */
	public void init(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.init(model.getProductId());
		} catch (Exception e) {
			result.setMessage("打开初始化失败");
			result.setIsSuccess(false);
			logger.error("打开初始化失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 删除商品
	 * @Create: 2012-12-18 下午10:56:44
	 * @author lys
	 * @update logs
	 */
	public void delete(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.delete(model);
		} catch (Exception e) {
			if(e instanceof org.springframework.dao.DataIntegrityViolationException){
				result.setMessage("该商品已被使用，不能删除");
			}else{
				result.setMessage("删除商品失败");
				logger.error("删除商品失败", e);
			}
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description:分组查询 商品 
	 * @Create: 2012-12-18 下午10:58:02
	 * @author lys
	 * @update logs
	 */
	public void query(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.query(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询商品失败");
			result.setIsSuccess(false);
			logger.error("查询商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 统计 商品
	 * @Create: 2012-12-18 下午10:59:26
	 * @author lys
	 * @update logs
	 */
	public void getTotalCount(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.getTotalCount(model);
		} catch (Exception e) {
			result.setMessage("统计商品失败");
			result.setIsSuccess(false);
			logger.error("统计商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 选择商品
	 * @Create: 2012-12-30 上午10:24:19
	 * @author lys
	 * @update logs
	 */
	public void select(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.select(model,page,rows);
		} catch (Exception e) {
			result.setMessage("选择商品失败");
			result.setIsSuccess(false);
			logger.error("选择商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 退货选择商品
	 * @Create: 2013-1-11 下午12:48:15
	 * @author lys
	 * @update logs
	 */
	public void selectReject(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.selectReject(model,page,rows);
		} catch (Exception e) {
			result.setMessage("退货选择商品失败");
			result.setIsSuccess(false);
			logger.error("退货选择商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	/**
	 * @Description: 查询当前库存的商品
	 * @Create: 2013-1-12 下午1:44:38
	 * @author lys
	 * @update logs
	 */
	public void queryStore(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.queryStore(model,page,rows);
		} catch (Exception e) {
			result.setMessage("查询当前库存的商品失败");
			result.setIsSuccess(false);
			logger.error("查询当前库存的商品失败", e);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * @Description: 默认商品组装，选择商品
	 * @Create: 2013-2-3 上午10:22:37
	 * @author lys
	 * @update logs
	 */
	public void selectDefaultPacking(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.selectDefaultPacking(model,ids,page,rows);
		} catch (Exception e) {
			result.setMessage("默认商品组装，选择商品失败");
			logger.error("默认商品组装，选择商品失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
	
	/**
	 * 
	 * @Description: 查询某个商品的默认保证
	 * @Create: 2013-2-4 下午02:19:41
	 * @author longweier
	 * @update logs
	 * @throws Exception
	 */
	public void queryDefaultPacking(){
		ServiceResult result = new ServiceResult(false);
		try {
			result = productService.selectDefaultPacking(model);
		} catch (Exception e) {
			result.setMessage("默认商品组装，选择商品失败");
			logger.error("默认商品组装，选择商品失败", e);
			result.setIsSuccess(false);
		}
		String jsonString = result.toJSON();
		ajaxJson(jsonString);
	}
}
